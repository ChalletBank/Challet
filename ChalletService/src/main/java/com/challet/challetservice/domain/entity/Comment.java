package com.challet.challetservice.domain.entity;

import com.challet.challetservice.domain.dto.request.CommentRegisterRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_transaction_id", nullable = false)
    private SharedTransaction sharedTransaction;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    public static Comment create(User user, SharedTransaction sharedTransaction, CommentRegisterRequestDTO request) {
        return Comment.builder()
            .user(user)
            .sharedTransaction(sharedTransaction)
            .content(request.content())
            .build();
    }

}
