package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.UserAnswer;

import java.util.Collection;

public interface UserAnswerRepository {
    Collection<UserAnswer> saveAll(Collection<UserAnswer> userAnswers);
}
