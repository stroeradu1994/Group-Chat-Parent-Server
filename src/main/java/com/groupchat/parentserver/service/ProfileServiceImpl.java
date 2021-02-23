package com.groupchat.parentserver.service;

import com.google.common.collect.Sets;
import com.groupchat.parentserver.dto.CreateProfileRequest;
import com.groupchat.parentserver.model.Profile;
import com.groupchat.parentserver.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    ProfileRepo profileRepo;

    public Profile createProfile(@RequestBody CreateProfileRequest createProfileRequest) {
        Profile profile = new Profile();
        profile.setName(createProfileRequest.getName());
        profile.setOnline(true);
        return profileRepo.save(profile);
    }

    public Set<Profile> getProfiles() {
        return Sets.newHashSet(profileRepo.findAll());
    }
}
