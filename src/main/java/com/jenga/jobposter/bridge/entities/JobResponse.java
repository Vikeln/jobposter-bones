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
@Entity(name = "job_response")
public class JobResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String phone;
    private String email;
    private boolean followedUp = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job", referencedColumnName = "id")
    @JsonIgnore
    @ToString.Exclude
    private Job job;

    @Lob
    @JsonIgnore
    @ToString.Exclude
    private byte[] applicantDocument;
    private String applicantDocumentName;
}
