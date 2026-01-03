package com.ctorres.pokequiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.entity.Quiz;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("""
            select distinct q
            from Quiz q
            left join fetch q.questions qu
            left join fetch qu.answers a
            where q.id = :id
            """)
    Optional<Quiz> findQuizWithQuestionsAndAnswers(@Param("id") Long quizId);
}