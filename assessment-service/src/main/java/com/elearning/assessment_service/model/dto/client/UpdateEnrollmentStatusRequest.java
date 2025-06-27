      
package com.elearning.assessment_service.model.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateEnrollmentStatusRequest {
    private Long userId;
    private Long courseId;
    private String newStatus;
}

