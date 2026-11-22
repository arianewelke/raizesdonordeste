package com.arianewelke.raizesdonordeste.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroResponse> handleException(ApiException ex, HttpServletRequest request ) {
        ErroResponse body = ErroResponse.of(ex.getCodigo(), ex.getMessage(), List.of(), request.getRequestURI());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleValidacao(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<DetalheErro> detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> new DetalheErro(f.getField(), f.getDefaultMessage()))
                .toList();
        ErroResponse body = ErroResponse.of("VALIDACAO_INVALIDA",
                "Há campos inválidos na requisição.", detalhes, request.getRequestURI());
        return ResponseEntity.status(422).body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> handleJsonInvalido(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErroResponse body = ErroResponse.of("REQUISICAO_INVALIDA",
                "Corpo da requisição ausente ou com JSON malformado.", List.of(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErroResponse> handleAcessoNegado(AccessDeniedException ex, HttpServletRequest request) {
        ErroResponse body = ErroResponse.of("ACESSO_NEGADO",
                "Você não tem permissão para executar esta ação.", List.of(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> handleErroInesperado(Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado em {}", request.getRequestURI(), ex);
        ErroResponse body = ErroResponse.of("ERRO_INTERNO",
                "Ocorreu um erro inesperado.", List.of(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);

    }
}
