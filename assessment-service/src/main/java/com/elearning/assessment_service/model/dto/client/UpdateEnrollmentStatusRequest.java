      
package com.elearning.assessment_service.model.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnrollmentStatusRequest {
    private Long userId;
    private Long courseId;
    private String newStatus;
}

