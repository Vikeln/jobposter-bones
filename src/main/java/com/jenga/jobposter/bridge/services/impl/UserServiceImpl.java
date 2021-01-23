package com.jenga.jobposter.bridge.services.impl;

import com.jenga.jobposter.bridge.dao.UserDao;
import com.jenga.jobposter.bridge.entities.User;
import com.jenga.jobposter.bridge.models.Response;
import com.jenga.jobposter.bridge.models.SingleItemResponse;
import com.jenga.jobposter.bridge.models.Status;
import com.jenga.jobposter.bridge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Value("${user-api-password}")
    private String apiPassword;

    @Override
    public SingleItemResponse createUser(User user, String password) {
        if (!apiPassword.equalsIgnoreCase(password))
            return SingleItemResponse.returnData(Response.BAD_CREDS.status(), null);
        return SingleItemResponse.returnData(Response.SUCCESS.status(), userDao.save(user));
    }

    @Override
    public SingleItemResponse getUsers(String password) {
        if (!apiPassword.equalsIgnoreCase(password))
            return SingleItemResponse.returnData(Response.BAD_CREDS.status(), null);
        return SingleItemResponse.returnData(Response.SUCCESS.status(), userDao.findAll());

    }

    public static Status notFoundStatus(String name) {
        Status status1 = Response.NOT_FOUND.status();
        return new Status(status1.getCode(), MessageFormat.format(status1.getMessage(), name));
    }

    @Override
    public SingleItemResponse deactivateUser(Integer userId, String password) {
        if (!apiPassword.equalsIgnoreCase(password))
            return SingleItemResponse.returnData(Response.BAD_CREDS.status(), null);
        SingleItemResponse response = new SingleItemResponse();
        Optional<User> optionalUser = userDao.findById(userId);
        if (!optionalUser.isPresent())
            return SingleItemResponse.returnData(notFoundStatus("User"), null);
        User user = optionalUser.get();
        user.setActive(false);
        return SingleItemResponse.returnData(Response.SUCCESS.status(), userDao.save(user));
    }
}
