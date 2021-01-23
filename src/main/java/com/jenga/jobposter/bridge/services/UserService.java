package com.jenga.jobposter.bridge.services;

import com.jenga.jobposter.bridge.entities.User;
import com.jenga.jobposter.bridge.models.SingleItemResponse;

import java.util.Collection;

public interface UserService {
    SingleItemResponse createUser(User user, String password);

    SingleItemResponse getUsers(String password);

    SingleItemResponse deactivateUser(Integer user, String password);
}
