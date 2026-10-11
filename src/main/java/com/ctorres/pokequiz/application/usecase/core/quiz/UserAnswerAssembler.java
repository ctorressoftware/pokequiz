package com.ctorres.pokequiz.application.usecase.core.quiz;

import com.ctorres.pokequiz.infrastructure.web.request.quiz.UserAnswerDtoRequest;
import com.ctorres.pokequiz.infrastructure.persistence.entity.Question;
import com.ctorres.pokequiz.infrastructure.persistence.entity.Quiz;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserAnswer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserAnswerAssembler {

    protected Map<Long, Question> questionsById(Set<Question> questions) {
        return questions.stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        q -> q
                ));
    }

    protected void validateAllAnswered(Set<Long> questionsIds, Set<Long> userAnswerQuestionsIds){
        var hasEachQuestionBeenAnswered = questionsIds.equals(userAnswerQuestionsIds);
        if (!hasEachQuestionBeenAnswered) {
            throw new IllegalArgumentException("Each quiz's question has to have an answer.");
        }
    }

    protected Map<Long, UserAnswer> createUserAnswersByQuestionId(
            Collection<UserAnswerDtoRequest> userAnswerRequestDtos,
            Map<Long, Question> questionsById) {

        return userAnswerRequestDtos.stream()
                .collect(Collectors.toMap(
                        UserAnswerDtoRequest::getQuestionId,
                        dto -> {
                            var q = questionsById.get(dto.getQuestionId());
                            if (q == null) {
                                throw new IllegalArgumentException("Invalid questionId=" + dto.getQuestionId());
                            }
                            return new UserAnswer(dto.getDescription(), dto.getValue(), true, q);
                        },
                        (a, b) -> {
                            throw new IllegalArgumentException(
                                    "Duplicate answer for questionId=" + a.getQuestion().getId()
                            );
                        }
                ));
    }

    public void applyToQuiz(Quiz quiz, Map<Long, UserAnswer> userAnswersByQuestionId) {
        quiz.getQuestions().forEach(question -> question
                .setSingleUserAnswer(userAnswersByQuestionId.get(question.getId())));
    }
}
