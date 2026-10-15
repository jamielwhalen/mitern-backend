package com.mitern.mitern.controllers;


import com.mitern.mitern.model.Job;
import com.mitern.mitern.model.JobRequest;
import com.mitern.mitern.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@PreAuthorize("hasRole('BUSINESS')")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<Job> getMyJobs(Authentication authentication) {
        return jobService.getMyJobs(authentication.getName());
    }

    @GetMapping("/{id}")
    public Job getJob(
            @PathVariable Long id,
            Authentication authentication) {

        return jobService.getJob(
                id,
                authentication.getName()
        );
    }

    @PostMapping
    public ResponseEntity<Job> createJob(
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        System.out.println("here");

        Job createdJob = jobService.createJob(
                request,
                authentication.getName()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdJob);
    }

    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            Authentication authentication) {

        return jobService.updateJob(
                id,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(
            @PathVariable Long id,
            Authentication authentication) {

        jobService.deleteJob(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
}
