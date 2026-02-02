package com.ctorres.pokequiz.repository;

import com.ctorres.pokequiz.entity.State;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ctorres.pokequiz.entity.Quiz;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // TODO: Check if this query method has to change its name.
    @Query("""
            select distinct q
            from Quiz q
            left join fetch q.questions qu
            join fetch q.state
            join fetch q.difficultLevel
            left join fetch qu.answers
            left join fetch qu.userAnswers
            where q.id = :id and q.user.id = :userId
            """)
    Optional<Quiz> findQuizWithQuestionsAndAnswersByIdAndUserId(@Param("id") Long quizId, @Param("userId") Long userId);

    Optional<Quiz> findQuizByIdAndUserId(@Param("id") Long quizId, @Param("userId") Long userId);

    @QueryHints({@QueryHint(name = "javax.persistence.query.timeout", value = "3000")})
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