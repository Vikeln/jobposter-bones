package com.jenga.jobposter.bridge.dao;

import com.jenga.jobposter.bridge.entities.JobResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobResponseDao extends JpaRepository<JobResponse, Integer> {
}
