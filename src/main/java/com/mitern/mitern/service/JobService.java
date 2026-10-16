package com.mitern.mitern.service;


import com.mitern.mitern.model.Job;
import com.mitern.mitern.model.JobRequest;
import com.mitern.mitern.model.JobStatus;
import com.mitern.mitern.repositories.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getMyJobs(String email) {
        return jobRepository
                .findByCreatedByOrderByCreatedAtDesc(email);
    }

    public Job getJob(Long id, String email) {
        return jobRepository
                .findByIdAndCreatedBy(id, email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Job not found"
                ));
    }

    public Job createJob(JobRequest request, String email) {
        Job job = new Job();

        applyRequest(job, request);
        job.setCreatedBy(email);

        return jobRepository.save(job);
    }

    public Job updateJob(
            Long id,
            JobRequest request,
            String email) {

        Job job = getJob(id, email);

        applyRequest(job, request);

        return jobRepository.save(job);
    }

    public void deleteJob(Long id, String email) {
        Job job = getJob(id, email);
        jobRepository.delete(job);
    }

    private void applyRequest(Job job, JobRequest request) {
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setWorkArrangement(request.getWorkArrangement());
        job.setCompensation(request.getCompensation());
        job.setApplicationDeadline(
                request.getApplicationDeadline()
        );

        job.setStatus(
                request.getStatus() != null
                        ? JobStatus.PENDING
                        : JobStatus.DRAFT
        );
    }
}

