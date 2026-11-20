package com.ctorres.pokequiz.infrastructure.web.response.quiz;

import java.util.UUID;

public record UserAnswerDto(UUID questionId, String description, String value) {}
