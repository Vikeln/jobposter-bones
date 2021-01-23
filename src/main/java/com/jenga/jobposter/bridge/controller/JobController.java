package com.jenga.jobposter.bridge.controller;

import com.jenga.jobposter.bridge.entities.Job;
import com.jenga.jobposter.bridge.entities.JobResponse;
import com.jenga.jobposter.bridge.models.JobModel;
import com.jenga.jobposter.bridge.models.JobResponseModel;
import com.jenga.jobposter.bridge.models.SingleItemResponse;
import com.jenga.jobposter.bridge.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping("{id}")
    public SingleItemResponse getUserJobs(@PathVariable(name = "id") Integer id) {
        return jobService.getUserJobs(id);
    }

    @GetMapping("view/{id}")
    public SingleItemResponse getJobs(@PathVariable(name = "id") Integer id) {
        return jobService.geJobs(id);
    }

    @GetMapping("application/markJobResponse/{id}")
    public SingleItemResponse markJobResponse(@PathVariable(name = "id") Integer id, @RequestParam String apiPassword) {
        return jobService.markJobResponse(id, apiPassword);
    }

    @PostMapping
    public SingleItemResponse createJob(@RequestBody JobModel jobModel, @RequestParam String apiPassword) {
        return jobService.createJob(jobModel, apiPassword);
    }

    @PostMapping("application")
    public SingleItemResponse createJobApplication(@RequestBody JobResponseModel jobModel) {
        return jobService.createJobResponse(jobModel);
    }

    @RequestMapping(value = "banner/{name}", method = RequestMethod.GET)
    public void showJobImage(@PathVariable("name") String name, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Job job = jobService.getJobByBBannerName(name);
        response.setStatus(HttpServletResponse.SC_OK);
        if (job == null || job.getJobBannerName() == null)
            return;
        String fileName = String.format("%s", job.getJobBannerName());
        response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(job.getJobBanner());
        response.getOutputStream().close();
    }

    @RequestMapping(value = "application/docs/{application}", method = RequestMethod.GET)
    public void showJobResponseImage(@PathVariable("application") Integer application, HttpServletResponse response, HttpServletRequest request)
            throws ServletException, IOException {
        Optional<JobResponse> job = jobService.getJobResponse(application);
        response.setStatus(HttpServletResponse.SC_OK);
        if (!job.isPresent() || job.get().getApplicantDocumentName() == null)
            return;
        String fileName = String.format("%s", job.get().getApplicantDocumentName());
        response.addHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setContentType("application/pdf");

        response.getOutputStream().write(job.get().getApplicantDocument());
        response.getOutputStream().close();
    }
}
