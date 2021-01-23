package com.jenga.jobposter.bridge.models;

import com.jenga.jobposter.bridge.entities.JobResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponseModel {
    private String name;
    private String phone;
    private String email;

    private String userDocument;
    private Integer job;

    public JobResponse jobResponse(){
        JobResponse response = new JobResponse();
        response.setName(this.name);
        response.setPhone(this.phone);
        response.setEmail(this.email);
        return response;
    }
}
