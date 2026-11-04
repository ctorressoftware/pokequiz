package com.ctorres.pokequiz.infrastructure.persistence.repository;

import com.ctorres.pokequiz.infrastructure.persistence.entity.QuizEntity;
import com.ctorres.pokequiz.infrastructure.persistence.entity.StateEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataQuizRepository extends JpaRepository<QuizEntity, UUID> {

    // TODO: Check if this query method has to change its name.
    @Query("""
            select distinct q
            from QuizEntity q
            left join fetch q.questionEntities qu
            join fetch q.stateEntity
            join fetch q.difficultLevelEntity
            left join fetch qu.answerEntities
            left join fetch qu.userAnswerEntities
            where q.id = :id and q.userEntity.id = :userId
            """)
    Optional<QuizEntity> findQuizWithQuestionsAndAnswersByIdAndUserId(@Param("id") UUID quizId, @Param("userId") UUID userId);

    Optional<QuizEntity> findQuizByIdAndUserId(@Param("id") UUID quizId, @Param("userId") UUID userId);

    @QueryHints({@QueryHint(name = "javax.persistence.query.timeout", value = "3000")})
    @Modifying(flushAutomatically = true)
    @Query("""
            update QuizEntity q set
            q.stateEntity = :newStateEntity
            where q.id = :quizId and q.stateEntity = :previousStateEntity
            """)
    int compareAndSetState(
            @Param("quizId") UUID quizId,
            @Param("newStateEntity") StateEntity newStateEntity,
            @Param("previousStateEntity") StateEntity previousStateEntity);
}