package com.jenga.jobposter.bridge.controller;

import com.jenga.jobposter.bridge.entities.User;
import com.jenga.jobposter.bridge.models.SingleItemResponse;
import com.jenga.jobposter.bridge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public SingleItemResponse getUsers(@RequestParam String apiPassword) {
        return userService.getUsers(apiPassword);
    }

    @GetMapping("deactivate/{id}")
    public SingleItemResponse deactivateUser(@PathVariable(name = "id") Integer id, @RequestParam String apiPassword) {
        return userService.deactivateUser(id, apiPassword);
    }

    @PostMapping
    public SingleItemResponse createUser(@RequestBody User user, @RequestParam String apiPassword) {
        return userService.createUser(user, apiPassword);
    }
}
