package com.ctorres.pokequiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuiz_IdAndQuiz_User_Id(Long quizId, Long quizUserId);
}