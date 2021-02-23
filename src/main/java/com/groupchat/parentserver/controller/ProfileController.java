package com.groupchat.parentserver.controller;


import com.groupchat.parentserver.dto.CreateProfileRequest;
import com.groupchat.parentserver.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/")
    public ResponseEntity<?> createProfile(@RequestBody CreateProfileRequest createProfileRequest) {
        return ResponseEntity.ok(profileService.createProfile(createProfileRequest));
    }

    @GetMapping("/")
    public ResponseEntity<?> getProfiles() {
        return ResponseEntity.ok(profileService.getProfiles());
    }
}
