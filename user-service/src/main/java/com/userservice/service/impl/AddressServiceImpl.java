package com.usermanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.dto.AddressDTO;
import com.usermanagement.entity.Address;
import com.usermanagement.entity.User;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.AddressRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.IAddressService;
import com.usermanagement.util.AddressConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressConverter addressConverter;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String ADDRESS_CACHE_KEY = "ADDRESS::";

    @Override
    public AddressDTO getAddressByUsername(String username) {
        String cacheKey = ADDRESS_CACHE_KEY + username;

        // Check Redis cache first
        String cachedData = redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            try {
                log.info("📦 Address data fetched from Redis for username: {}", username);
                return objectMapper.readValue(cachedData, AddressDTO.class);
            } catch (JsonProcessingException e) {
                log.error("❌ Error parsing cached address JSON for user {}: {}", username, e.getMessage());
            }
        }

        // Fetch user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Address address = user.getAddress();
        if (address == null) {
            throw new ResourceNotFoundException("Address not found for username: " + username);
        }

        AddressDTO addressDTO = addressConverter.toDTO(address);

        // Cache result in Redis
        try {
            redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(addressDTO), Duration.ofMinutes(10));
            log.info("✅ Address data cached in Redis for user {}", username);
        } catch (JsonProcessingException e) {
            log.error("❌ Error caching address data for user {}: {}", username, e.getMessage());
        }

        return addressDTO;
    }

    @Override
    public AddressDTO updateAddress(String username, AddressDTO addressDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Address existingAddress = user.getAddress();

        if (existingAddress == null) {
            existingAddress = new Address();
            existingAddress.setUser(user);
        }

        // Map updated fields
        existingAddress.setStreet(addressDTO.getStreet());
        existingAddress.setCity(addressDTO.getCity());
        existingAddress.setState(addressDTO.getState());
        existingAddress.setZipcode(addressDTO.getZipcode());
        existingAddress.setCountry(addressDTO.getCountry());

        Address savedAddress = addressRepository.save(existingAddress);
        AddressDTO updatedDTO = addressConverter.toDTO(savedAddress);

        // Update cache
        try {
            redisTemplate.opsForValue().set(ADDRESS_CACHE_KEY + username,
                    objectMapper.writeValueAsString(updatedDTO), Duration.ofMinutes(10));
            log.info("🔁 Redis cache updated for user address: {}", username);
        } catch (JsonProcessingException e) {
            log.error("❌ Error serializing address for Redis cache: {}", e.getMessage());
        }

        return updatedDTO;
    }
}
