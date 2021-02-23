package com.groupchat.parentserver.repo;

import com.groupchat.parentserver.model.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepo extends CrudRepository<Profile, String> {
}
