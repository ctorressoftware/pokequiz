package com.ctorres.pokequiz.infrastructure.persistence.adapter;

import com.ctorres.pokequiz.application.port.out.QuestionRepository;
import com.ctorres.pokequiz.domain.model.Question;
import com.ctorres.pokequiz.infrastructure.mapper.QuestionMapper;
import com.ctorres.pokequiz.infrastructure.persistence.entity.QuestionEntity;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataQuestionRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JpaQuestionRepositoryAdapter implements QuestionRepository {

    private final SpringDataQuestionRepository repository;
    private final QuestionMapper questionMapper;

    public JpaQuestionRepositoryAdapter(SpringDataQuestionRepository repository) {
        this.repository = repository;
        this.questionMapper = new QuestionMapper();
    }

    @Override
    public Question save(Question question) {
        QuestionEntity saved = repository.save(questionMapper.map(question));
        return questionMapper.map(saved);
    }

    @Override
    public Collection<Question> saveAll(Collection<Question> questions) {

        Collection<QuestionEntity> saved = repository.saveAll(questions.stream()
                .map(questionMapper::map)
                .collect(Collectors.toSet()));

        return saved.stream()
                .map(questionMapper::map)
                .collect(Collectors.toSet());
    }
}