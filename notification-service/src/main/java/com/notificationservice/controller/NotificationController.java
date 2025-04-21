package com.notificationservice.controller;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.service.INotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notify")
@Api(tags = "Notification Controller")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/send")
    @ApiOperation("Send test email directly")
    public String sendNotification(@RequestBody NotificationRequest request) {
        notificationService.sendEmail(request);
        return "Email sent!";
    }
}








//package com.notificationservice.controller;
//
//import com.notificationservice.dto.NotificationRequest;
//import com.notificationservice.service.INotificationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/notify")
//public class NotificationController {
//
//    @Autowired
//    private INotificationService notificationService;
//
//    @PostMapping("/send")
//    public String sendMail(@RequestBody NotificationRequest request) {
//        notificationService.sendEmail(request);
//        return "Mail Sent";
//    }
//}
