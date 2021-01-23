package com.jenga.jobposter.bridge.services;

import com.jenga.jobposter.bridge.entities.Job;
import com.jenga.jobposter.bridge.entities.JobResponse;
import com.jenga.jobposter.bridge.models.JobModel;
import com.jenga.jobposter.bridge.models.JobResponseModel;
import com.jenga.jobposter.bridge.models.SingleItemResponse;

import java.util.Optional;

public interface JobService {
    SingleItemResponse getUserJobs(Integer id);

    SingleItemResponse geJobs(Integer id);

    SingleItemResponse createJobResponse(JobResponseModel responseModel);

    Optional<JobResponse> getJobResponse(Integer jobResponse);

    SingleItemResponse markJobResponse(Integer jobResponse, String password);

    SingleItemResponse createJob(JobModel jobModel, String password);

    Job getJobByBBannerName(String name);
}
