package com.tryout_dc.main.repository;

import com.tryout_dc.main.models.JobsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJobsRepository extends JpaRepository<JobsModel, Long> {
}
