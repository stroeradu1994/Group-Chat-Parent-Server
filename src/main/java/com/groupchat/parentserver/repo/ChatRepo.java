package com.groupchat.parentserver.repo;

import com.groupchat.parentserver.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepo extends CrudRepository<Message, String> {
}
