package com.ctorres.pokequiz.repository;

import com.ctorres.pokequiz.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
            join fetch q.state
            join fetch q.difficultLevel
            left join fetch qu.answers
            where q.id = :id and q.user.id = :userId
            """)
    Optional<Quiz> findQuizWithQuestionsAndAnswersByIdAndUserId(@Param("id") Long quizId, @Param("userId") Long userId);

    Optional<Quiz> findQuizByIdAndUserId(@Param("id") Long quizId, @Param("userId") Long userId);

    @Modifying(flushAutomatically = true)
    @Query("""
            update Quiz q set
            q.state = :newState
            where q.id = :quizId and q.state = :previousState
            """)
    int compareAndSetState(
            @Param("quizId") Long quizId,
            @Param("newState") State newState,
            @Param("previousState") State previousState);
}