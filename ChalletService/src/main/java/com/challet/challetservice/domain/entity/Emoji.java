package com.challet.challetservice.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Emoji {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_transaction_id", nullable = false)
    private SharedTransaction sharedTransaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmojiType type;

    public static Emoji createEmoji(User user, SharedTransaction sharedTransaction, EmojiType type) {
        Emoji emoji = Emoji.builder()
            .user(user)
            .sharedTransaction(sharedTransaction)
            .type(type)
            .build();
        user.getEmojis().add(emoji);
        return emoji;
    }

    public void updateEmoji(EmojiType newEmojiType) {
        this.type = newEmojiType;
    }

}
