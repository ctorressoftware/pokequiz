package com.ctorres.pokequiz.application.port.in.auth.refresh;

public interface RefreshUseCase {
    RefreshResult refresh(RefreshCommand command);
}