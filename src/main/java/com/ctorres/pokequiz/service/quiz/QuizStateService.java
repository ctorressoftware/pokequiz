package com.ctorres.pokequiz.service.quiz;

import com.ctorres.pokequiz.entity.Quiz;
import com.ctorres.pokequiz.enums.QuizState;
import com.ctorres.pokequiz.exception.QuizInvalidStateGenerationException;
import com.ctorres.pokequiz.exception.QuizInvalidStateTransitionException;
import com.ctorres.pokequiz.repository.QuizRepository;
import com.ctorres.pokequiz.repository.StateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        quiz.setState(generating);
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generating, created);
        if (claimed == 0) throw new QuizInvalidStateGenerationException(quiz.getId());
    }

    public void markReady(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var ready = stateRepository.getReferenceById(QuizState.READY.getId());
        quiz.setState(ready);
        var claimed = quizRepository.compareAndSetState(quiz.getId(), ready, generating);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
    }

    public void markInProgress(Quiz quiz) {
        var ready = stateRepository.getReferenceById(QuizState.READY.getId());
        var inProgress = stateRepository.getReferenceById(QuizState.IN_PROGRESS.getId());
        quiz.setState(inProgress);
        var claimed = quizRepository.compareAndSetState(quiz.getId(), inProgress, ready);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
    }

    public void markCompleted(Quiz quiz) {
        var inProgress = stateRepository.getReferenceById(QuizState.IN_PROGRESS.getId());
        var completed = stateRepository.getReferenceById(QuizState.COMPLETED.getId());
        quiz.setState(completed);
        var claimed = quizRepository.compareAndSetState(quiz.getId(), completed, inProgress);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markGeneratingError(Quiz quiz) {
        var generating = stateRepository.getReferenceById(QuizState.GENERATING.getId());
        var generatingError = stateRepository.getReferenceById(QuizState.GENERATING_ERROR.getId());
        quiz.setState(generatingError);
        var claimed = quizRepository.compareAndSetState(quiz.getId(), generatingError, generating);
        if (claimed == 0) throw new QuizInvalidStateTransitionException(quiz.getId());
    }
}
