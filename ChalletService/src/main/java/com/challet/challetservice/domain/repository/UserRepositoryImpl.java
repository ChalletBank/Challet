package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.dto.response.UserInfoMessageResponseDTO;
import com.challet.challetservice.domain.entity.QUser;
import com.challet.challetservice.domain.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserInfoMessageResponseDTO getUserInfoMessage(String phoneNumber){
        QUser user = QUser.user;

        User foundUser = queryFactory
            .selectFrom(user)
            .where(user.phoneNumber.eq(phoneNumber))
            .fetchOne();

        Boolean gender = foundUser.getGender();
        int age = foundUser.getAge();

        int lowerBound = (age / 10) * 10;
        int upperBound = lowerBound + 10;

        List<String> userInfo = queryFactory
            .select(user.phoneNumber)
            .from(user)
            .where(user.gender.eq(gender)
                .and(user.age.between(lowerBound, upperBound - 1)))
            .fetch();

        return UserInfoMessageResponseDTO.from(userInfo, gender, lowerBound);
    }
}
