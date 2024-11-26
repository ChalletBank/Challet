package com.challet.challetservice.domain.repository;

import com.challet.challetservice.domain.entity.Comment;
import com.challet.challetservice.domain.entity.SharedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

}
