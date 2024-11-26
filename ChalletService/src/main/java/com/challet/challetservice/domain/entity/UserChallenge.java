package com.challet.challetservice.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_challenge")
public class UserChallenge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    @Column(name = "spending_amount", nullable = false)
    private Long spendingAmount;

    @Builder.Default
    @OneToMany(mappedBy = "userChallenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharedTransaction> sharedTransactions = new ArrayList<>();

    public static UserChallenge fromUserAndChallenge(User user, Challenge challenge) {
        UserChallenge userChallenge = UserChallenge.builder()
            .user(user)
            .challenge(challenge)
            .spendingAmount(0L)
            .build();

        user.getUserChallenges().add(userChallenge);
        challenge.getUserChallenges().add(userChallenge);
        return userChallenge;
    }

    public void addSpendingAmount(Long amount) {
        this.spendingAmount += amount;
    }

    public void updateSpendingAmount(Long oldAmount, Long newAmount) {
        this.spendingAmount -= oldAmount;
        this.spendingAmount += newAmount;
    }

}
