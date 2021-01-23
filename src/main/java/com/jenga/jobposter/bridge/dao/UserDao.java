package com.jenga.jobposter.bridge.dao;

import com.jenga.jobposter.bridge.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}
