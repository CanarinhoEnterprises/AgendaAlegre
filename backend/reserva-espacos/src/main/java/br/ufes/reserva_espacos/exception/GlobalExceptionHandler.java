package br.ufes.reserva_espacos.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Padroniza o corpo das respostas de erro em toda a API, garantindo que
 * a mensagem específica lançada pelas services chegue até o frontend
 * no formato {"mensagem": "..."}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> tratarRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("mensagem", e.getMessage() != null ? e.getMessage() : "Erro ao processar a solicitação."));
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(org.springframework.web.bind.MethodArgumentNotValidException e) {
        String mensagem = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fe -> fe.getDefaultMessage())
                .orElse("Dados inválidos.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensagem", mensagem));
    }
}
