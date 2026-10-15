package com.mitern.mitern.repositories;


import com.mitern.mitern.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByCreatedByOrderByCreatedAtDesc(
            String createdBy
    );

    Optional<Job> findByIdAndCreatedBy(
            Long id,
            String createdBy
    );
}

