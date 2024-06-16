package com.example.test.controller;

import com.example.test.service.ServiceBusSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class ServiceBusController {

    @Autowired
    private ServiceBusSenderService serviceBusSenderService;

    @PostMapping("/send")
    public String sendMessage(@RequestBody String message) {
        serviceBusSenderService.sendMessage(message);
        return "Sent successfully!";
    }

    @GetMapping("/receive")
    public void receiveMessage() throws InterruptedException {
        serviceBusSenderService.receiveMessage();
    }
}
