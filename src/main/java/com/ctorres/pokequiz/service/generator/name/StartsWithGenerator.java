package com.ctorres.pokequiz.service.generator.name;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ctorres.pokequiz.dto.api.generator.GeneratedAnswer;
import com.ctorres.pokequiz.dto.api.generator.GeneratedItem;
import com.ctorres.pokequiz.dto.api.generator.GeneratedQuestion;

@Component
public class StartsWithGenerator implements NameQuestionGenerator {
    
    final String QUESTION_FORM = "Its name starts with...";

    public GeneratedItem generate() {

        GeneratedItem item = new GeneratedItem();
        GeneratedQuestion question = new GeneratedQuestion(QUESTION_FORM);
        List<GeneratedAnswer> answers = new ArrayList<>();
        /*answers.add(new GeneratedAnswer("A", true));
        answers.add(new GeneratedAnswer("B", false));
        answers.add(new GeneratedAnswer("C", false));
        answers.add(new GeneratedAnswer("D", false));*/

        item.setGeneratedQuestion(question);
        item.setGeneratedAnswers(answers);

        return item;
    }
}