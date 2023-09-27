package tic4303.miniproject.server.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tic4303.miniproject.server.model.Interest;
import tic4303.miniproject.server.service.AppService;

@Controller
@RequestMapping("/api")
public class AppController {

    @Autowired
    private AppService appSvc;

    @PostMapping("/submit")
    @ResponseBody

    public ResponseEntity<String> submitForm(@RequestBody Interest interest) {
        System.out.printf(">>> query param: name=%s\n", interest.getName());
        System.out.printf(">>> query param: email=%s\n", interest.getEmail());
        
        appSvc.submitForm(interest);
        
        // Create a map to hold the data
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "Submit successfull!");
        // Convert the map to JSON using Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse = objectMapper.writeValueAsString(responseMap);
        } catch (JsonProcessingException e) {
            // Handle the exception
            jsonResponse = "{\"error\":\"Failed to create JSON response\"}";
        }
        
        return ResponseEntity.ok(jsonResponse);

    }

    @GetMapping("/dashboard/interests")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> getInterests() {
        
        String jsonArrInterestsStr = appSvc.getInterests();

        return ResponseEntity.ok(jsonArrInterestsStr);
    }
    
}
