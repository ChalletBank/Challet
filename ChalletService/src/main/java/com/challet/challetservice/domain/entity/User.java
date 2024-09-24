package com.challet.challetservice.domain.entity;

import com.challet.challetservice.domain.dto.request.UserRegisterRequestDTO;
import com.challet.challetservice.global.util.JwtUtil;
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
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "password", nullable = false, length = 6)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "profile_image", nullable = true)
    private String profileImage;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", nullable = false, columnDefinition = "TINYINT")
    private Integer gender;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "refresh_token", nullable = true)
    private String refreshToken;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserChallenge> userChallenges = new ArrayList<>();

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static User createUser(UserRegisterRequestDTO request, JwtUtil jwtUtil) {
        return User.builder()
            .phoneNumber(request.phoneNumber())
            .password(request.password())
            .nickname(request.nickname())
            .profileImage(request.profileImage())
            .age(request.age())
            .gender(request.gender())
            .name(request.name())
            .refreshToken(jwtUtil.generateRefreshToken(request.phoneNumber()))
            .build();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}