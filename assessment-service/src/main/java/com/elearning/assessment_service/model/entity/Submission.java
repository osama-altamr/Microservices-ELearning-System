package com.elearning.assessment_service.model.entity;

import com.elearning.assessment_service.model.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long quizId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;

    @CreationTimestamp
    private LocalDateTime submittedAt;

        @OneToMany(
            mappedBy = "submission",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default // Ensure the list is initialized by the builder
    private List<SubmittedAnswer> submittedAnswers = new ArrayList<>();

    public void addSubmittedAnswer(SubmittedAnswer answer) {
        submittedAnswers.add(answer);
        answer.setSubmission(this);
    }
}