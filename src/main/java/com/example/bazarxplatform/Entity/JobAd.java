package com.example.bazarxplatform.Entity;

import com.example.bazarxplatform.Enums.EducationLevel;
import com.example.bazarxplatform.Enums.ExperienceLevel;
import com.example.bazarxplatform.Enums.JobCategory;
import com.example.bazarxplatform.Enums.WorkSchedule;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Entity
@Table(name = "job_ads")
@Data
@EqualsAndHashCode(callSuper = true)
public class JobAd extends BaseAd {

    @Enumerated(EnumType.STRING)
    private JobCategory category;

    @Enumerated(EnumType.STRING)
    private WorkSchedule workSchedule;

    @Enumerated(EnumType.STRING)
    private ExperienceLevel experienceRequired;

    @Enumerated(EnumType.STRING)
    private EducationLevel educationLevel;

    private BigDecimal salary; // nullable
}