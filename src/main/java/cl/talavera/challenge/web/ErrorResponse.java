package cl.talavera.challenge.web;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String mensaje;
}
