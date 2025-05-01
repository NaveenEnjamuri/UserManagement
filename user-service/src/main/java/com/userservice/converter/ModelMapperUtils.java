package com.userservice.converter;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperUtils {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    private static final ModelMapper mapper = new ModelMapper();

    public static <D, T> D map(final T entity, Class<D> outClass) {
        if (entity == null) return null;
        return mapper.map(entity, outClass);
    }

}
