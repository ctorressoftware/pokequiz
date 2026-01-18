package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import com.ctorres.pokequiz.exception.QuizInvalidStateGenerationException;
import com.ctorres.pokequiz.exception.QuizInvalidStateTransitionException;
import com.ctorres.pokequiz.repository.QuizRepository;
import com.ctorres.pokequiz.repository.StateRepository;
import org.springframework.stereotype.Service;

@Service
public class QuizStateService {
    private final QuizRepository quizRepository;
    private final StateRepository stateRepository;

    public QuizStateService(QuizRepository quizRepository, StateRepository stateRepository) {
        this.quizRepository = quizRepository;
        this.stateRepository = stateRepository;
    }

    public void claimGenerating(Quiz quiz) {
        var created = stateRepository.getReferenceById(QuizState.CREATED.getId());
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generating, created);
        if (claimed == 0) throw new QuizInvalidStateGenerationException(quiz.getId());
        quiz.setState(generating);
    }

    public void markGenerated(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var generated = stateRepository.getReferenceById(QuizState.GENERATED.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generated, generating);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
        quiz.setState(generated);
    }

    public void markGeneratingError(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var generatingError = stateRepository.getReferenceById(QuizState.GENERATING_ERROR.getId());
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generatingError, generating);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
        quiz.setState(generatingError);
    }
}
