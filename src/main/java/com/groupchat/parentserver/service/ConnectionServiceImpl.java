package com.groupchat.parentserver.service;

import com.google.common.collect.Sets;
import com.groupchat.parentserver.dto.CreateProfileRequest;
import com.groupchat.parentserver.model.Profile;
import com.groupchat.parentserver.repo.ProfileRepo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.Set;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    ProfileRepo profileRepo;

    public void connect(String id) throws NotFoundException {
        updateProfileOnlineState(id, true);
    }

    public void disconnect(String id) throws NotFoundException {
        updateProfileOnlineState(id, false);
    }

    private void updateProfileOnlineState(String id, Boolean state) throws NotFoundException {
        Profile profile = getProfile(id);
        profile.setOnline(state);
        profileRepo.save(profile);
    }

    private Profile getProfile(String id) throws NotFoundException {
        Optional<Profile> profileOptional = profileRepo.findById(id);
        if (profileOptional.isPresent()) {
            return profileOptional.get();
        } else {
            throw new NotFoundException("Profile with id " + id + " was not found.");
        }
    }

    public Set<Profile> getProfiles() {
        return Sets.newHashSet(profileRepo.findAll());
    }
}

