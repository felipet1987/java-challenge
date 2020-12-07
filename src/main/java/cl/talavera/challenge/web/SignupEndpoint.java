package cl.talavera.challenge.web;


import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.port.AuthUseCasePort;
import cl.talavera.challenge.core.domain.SignupSuccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SignupEndpoint {
    private final AuthUseCasePort useCase;
    private final UserValidation validation;

    Logger logger = LoggerFactory.getLogger(SignupEndpoint.class);

    public SignupEndpoint(AuthUseCasePort service, UserValidation validation) {
        this.useCase = service;
        this.validation = validation;
    }

    @PostMapping(path = "/auth/signup")
    public ResponseEntity post(@RequestBody User request) {

        logger.info("signing up... " + request.getEmail());
        if(!validation.isEmailValid(request.getEmail())){
            return presentError(HttpStatus.BAD_REQUEST, "Email no es valido");
        };
        if(!validation.isPaswordValid(request.getPassword())){
            return presentError(HttpStatus.BAD_REQUEST, "password no es valida");
        };

        try {
            return ResponseEntity.ok(useCase.signup(request));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return presentError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private ResponseEntity presentError(HttpStatus internalServerError, String message) {

        logger.error("error on sign-up :... " + message);
        return ResponseEntity.status(internalServerError)
                .body(ErrorResponse.builder()
                        .mensaje(message)
                        .build());
    }
}
