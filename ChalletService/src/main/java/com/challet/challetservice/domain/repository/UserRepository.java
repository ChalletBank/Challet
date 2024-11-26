package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByPhoneNumber(String username);

    Boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByRefreshToken(String resetToken);

}
