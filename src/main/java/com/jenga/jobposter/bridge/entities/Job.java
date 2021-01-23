package com.jenga.jobposter.bridge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "job_title", unique = true, updatable = false)
    private String jobTitle;
    @Column(columnDefinition = "text", name = "description")
    private String description;
    @Column(columnDefinition = "text", name = "job_details")
    private String jobDetails;
    private String currency;
    private String location;
    private Double salary;
    private boolean active = true;

    @Lob
    @JsonIgnore
    @ToString.Exclude
    private byte[] jobBanner;
    private String jobBannerName;


    @ManyToOne(optional = false)
    @JoinColumn(name = "user", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    public Job(Integer id) {
        this.id = id;
    }

}
