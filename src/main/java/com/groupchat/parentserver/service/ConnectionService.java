package com.groupchat.parentserver.service;

import javassist.NotFoundException;

public interface ConnectionService {

    void connect(String id) throws NotFoundException;

    void disconnect(String id) throws NotFoundException;
}
