package com.jenga.jobposter.bridge.models;

import com.jenga.jobposter.bridge.entities.Job;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobModel {

    private Integer id;
    private Integer user;
    private String title;
    private String description;
    private String currency;
    private String location;
    private Double salary;
    private String jobBanner;

    public Job getJob() {
        Job job = new Job();
        job.setCurrency(this.currency);
        job.setDescription(this.description);
        job.setJobTitle(this.title);
        job.setLocation(this.location);
        job.setSalary(this.salary);
        return job;
    }
}
