package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.dto.api.QuizResult;
import com.ctorres.pokequiz.exception.BadRequestException;
import java.time.Instant;
import java.util.Collection;

public final class QuizDtoResponse {
    private final Long quizId;
    private final Instant initialDate;
    private final Instant endDate;
    private final String state;
    private final String difficultLevel;
    private final Collection<QuestionDto> questions;
    private final QuizResult quizResult;

    private QuizDtoResponse(
            Long quizId,
            Instant initialDate,
            Instant endDate,
            String state,
            String difficultLevel,
            Collection<QuestionDto> questions,
            QuizResult quizResult) {
        this.quizId = quizId;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
        this.questions = questions;
        this.quizResult = quizResult;
    }

    private static void requireNonNull(Object value, String field) {
        if (value == null) {
            throw new BadRequestException(field + " is required");
        }
    }

    public Long getQuizId() {
        return quizId;
    }
    public Instant getInitialDate() {
        return initialDate;
    }
    public Instant getEndDate() {
        return endDate;
    }
    public String getState() {
        return state;
    }
    public String getDifficultLevel() {
        return difficultLevel;
    }
    public Collection<QuestionDto> getQuestions() {
        return questions;
    }
    public QuizResult getQuizResult() {
        return quizResult;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long quizId;
        private Instant initialDate;
        private Instant endDate;
        private String state;
        private String difficultLevel;
        private Collection<QuestionDto> questions;
        private QuizResult quizResult;

        public Builder quizId(Long quizId) {
            this.quizId = quizId;
            return this;
        }

        public Builder initialDate(Instant initialDate) {
            this.initialDate = initialDate;
            return this;
        }

        public Builder endDate(Instant endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder difficultLevel(String difficultLevel) {
            this.difficultLevel = difficultLevel;
            return this;
        }

        public Builder questions(Collection<QuestionDto> questions) {
            this.questions = questions;
            return this;
        }

        public Builder quizResult(QuizResult quizResult) {
            this.quizResult = quizResult;
            return this;
        }

        public QuizDtoResponse build() {
            requireNonNull(quizId, "quizId");
            requireNonNull(initialDate, "initialDate");
            requireNonNull(state, "state");
            requireNonNull(difficultLevel, "difficultLevel");
            requireNonNull(questions, "questions");
            return new QuizDtoResponse(quizId, initialDate, endDate, state, difficultLevel, questions, quizResult);
        }
    }
}
