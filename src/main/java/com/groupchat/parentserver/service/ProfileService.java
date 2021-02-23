package com.groupchat.parentserver.service;

import com.groupchat.parentserver.dto.CreateProfileRequest;
import com.groupchat.parentserver.model.Profile;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

public interface ProfileService {
    Profile createProfile(@RequestBody CreateProfileRequest createProfileRequest);

    List<Profile> getProfiles();
}
