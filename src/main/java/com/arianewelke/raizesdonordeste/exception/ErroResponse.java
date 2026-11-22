package com.arianewelke.raizesdonordeste.exception;

import java.time.Instant;
import java.util.List;

public record ErroResponse(
        String error,
        String message,
        List<DetalheErro> details,
        Instant timestamp,
        String path
) {
    public static ErroResponse of (String error, String message, List<DetalheErro> details, String path) {
        return new ErroResponse(error, message, details, Instant.now(), path);
    }
}
