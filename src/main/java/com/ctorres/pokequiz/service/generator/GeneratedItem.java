package com.ctorres.pokequiz.service.generator;

import java.util.List;

public class GeneratedItem {
    
    private GeneratedQuestion generatedQuestion;
    private List<GeneratedAnswer> generatedAnswers;

    public GeneratedItem() {
    }

    public GeneratedItem(GeneratedQuestion generatedQuestion, List<GeneratedAnswer> generatedAnswers) {
        this.generatedQuestion = generatedQuestion;
        this.generatedAnswers = generatedAnswers;
    }

    public GeneratedQuestion getGeneratedQuestion() {
        return generatedQuestion;
    }

    public void setGeneratedQuestion(GeneratedQuestion generatedQuestion) {
        this.generatedQuestion = generatedQuestion;
    }

    public List<GeneratedAnswer> getGeneratedAnswers() {
        return generatedAnswers;
    }

    public void setGeneratedAnswers(List<GeneratedAnswer> generatedAnswers) {
        this.generatedAnswers = generatedAnswers;
    }
}
