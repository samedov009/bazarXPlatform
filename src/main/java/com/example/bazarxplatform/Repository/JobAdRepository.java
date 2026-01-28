package com.example.bazarxplatform.Repository;

import com.example.bazarxplatform.Entity.JobAd;
import com.example.bazarxplatform.Enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobAdRepository extends JpaRepository<JobAd, Long> {

    List<JobAd> findByStatus(AdStatus status);

    List<JobAd> findByCity(City city);

    List<JobAd> findByStatusAndCity(AdStatus status, City city);

    List<JobAd> findByUserId(Long userId);

    List<JobAd> findByCategory(JobCategory category);

    List<JobAd> findByWorkSchedule(WorkSchedule workSchedule);

    List<JobAd> findByExperienceRequired(ExperienceLevel experienceLevel);
}
