package com.ctorres.pokequiz.dto.api.response;

import com.ctorres.pokequiz.entity.DifficultLevel;
import com.ctorres.pokequiz.entity.State;
import java.time.Instant;

public final class QuizDtoResponse {
    private final Long quizId;
    private final Instant initialDate;
    private final Instant endDate;
    private final String state;
    private final String difficultLevel;

    private QuizDtoResponse(
            Long quizId,
            Instant initialDate,
            Instant endDate,
            String state,
            String difficultLevel) {
        this.quizId = quizId;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.state = state;
        this.difficultLevel = difficultLevel;
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

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long quizId;
        private Instant initialDate;
        private Instant endDate;
        private String state;
        private String difficultLevel;

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

        public QuizDtoResponse build() {
            if (quizId == null || initialDate == null || state == null || difficultLevel == null) {
                throw new IllegalArgumentException("Check all the variables and set a value to the required.");
            }
            return new QuizDtoResponse(quizId, initialDate, endDate, state, difficultLevel);
        }
    }
}
