package com.challet.challetservice.domain.entity;

import com.challet.challetservice.domain.dto.request.ChallengeRegisterRequestDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "challenge")
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "create_date", nullable = false, columnDefinition = "DATE")
    private LocalDate createDate;

    @Column(name = "start_date", nullable = false, columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false, columnDefinition = "DATE")
    private LocalDate endDate;

    @Column(name = "max_participants", nullable = false)
    private Integer maxParticipants;

    @Column(name = "spending_limit", nullable = false)
    private Long spendingLimit;

    @Column(name = "invite_code", nullable = true)
    private String inviteCode;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChallenge> userChallenges = new ArrayList<>();

    public static Challenge createChallenge(ChallengeRegisterRequestDTO request, String code) {
        return Challenge.builder()
            .category(request.category())
            .title(request.title())
            .spendingLimit(request.spendingLimit())
            .createDate(LocalDate.now())
            .startDate(request.startDate())
            .endDate(request.endDate())
            .maxParticipants(request.maxParticipants())
            .inviteCode(code)
            .status(ChallengeStatus.RECRUITING)
            .build();
    }

    public void startChallenge(){
        this.status = ChallengeStatus.PROGRESSING;
    }

    public void endChallenge(){
        this.status = ChallengeStatus.END;
    }

}
