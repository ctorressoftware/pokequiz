package com.ctorres.pokequiz.application.port.out;

import com.ctorres.pokequiz.domain.model.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question save(Question question);
    Collection<Question> saveAll(Collection<Question> questions);
}
