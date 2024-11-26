package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Challenge;
import com.challet.challetservice.domain.entity.User;
import com.challet.challetservice.domain.entity.UserChallenge;
import java.util.List;

public interface UserChallengeRepositoryCustom {

    List<UserChallenge> getChallengeByPaymentCategory(String category, User user);

}
