package com.jenga.jobposter.bridge.dao;

import com.jenga.jobposter.bridge.entities.Job;
import com.jenga.jobposter.bridge.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface JobDao extends JpaRepository<Job, Integer> {
    Collection<Job> findAllByUserAndActive(User user, boolean active);

    boolean existsDistinctByJobTitle(String job);

    Job findDistinctByJobBannerNameContains(String logoName);
}
