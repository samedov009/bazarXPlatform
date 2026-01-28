package com.example.bazarxplatform.Dto.Response;

import com.example.bazarxplatform.Enums.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JobAdResponse extends AdResponse {

    private JobCategory category;
    private WorkSchedule workSchedule;
    private ExperienceLevel experienceRequired;
    private EducationLevel educationLevel;
    private BigDecimal salary;
}
