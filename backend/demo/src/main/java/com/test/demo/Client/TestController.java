package com.test.demo.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private TestService myService;

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() {
        // Call the service to get the message
        String message = myService.getMessage();
        return ResponseEntity.ok(message);
    }
}
