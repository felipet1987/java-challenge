package cl.talavera.challenge;

import cl.talavera.challenge.core.port.AuthUseCasePort;
import cl.talavera.challenge.web.ErrorResponse;
import cl.talavera.challenge.web.SignupEndpoint;
import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.domain.SignupSuccess;
import cl.talavera.challenge.web.UserValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class SignUpEndpointShould {

    private AuthUseCasePort useCase;
    private SignupEndpoint endpoint;
    private UserValidation validation;
    public static final User REQUEST = User.builder()
            .email("")
            .password("")
            .build();


    @BeforeEach
    void setUp() {
         useCase = mock(AuthUseCasePort.class);
         validation = mock(UserValidation.class);
         endpoint = new SignupEndpoint(useCase,validation);
    }

    @Test
    void register_an_user() {


        SignupSuccess expected = SignupSuccess.builder()
                .build();

        when(validation.isEmailValid("")).thenReturn(true);
        when(validation.isPaswordValid("")).thenReturn(true);
        when(useCase.signup(REQUEST)).thenReturn(expected);
        ResponseEntity<SignupSuccess> response = endpoint.post(REQUEST);


        verify(useCase,times(1)).signup(REQUEST);
        assertThat(response.getBody(),is(expected));
    }

    @Test
    void return_message_when_exception() {

        ErrorResponse expected = ErrorResponse.builder().mensaje("error").build();

        when(validation.isEmailValid("")).thenReturn(true);
        when(validation.isPaswordValid("")).thenReturn(true);
        when(useCase.signup(REQUEST)).thenThrow(new RuntimeException("error"));
        ResponseEntity response = endpoint.post(REQUEST);

        verify(useCase,times(1)).signup(REQUEST);
        assertThat(response.getBody(),is(expected));

    }


    @Test
    void return_error_when_is_not_valid() {
        User request = User.builder().email("").build();
        ErrorResponse expected = ErrorResponse.builder().mensaje("email no es valido").build();

        when(validation.isEmailValid("")).thenReturn(false);
        when(validation.isPaswordValid("")).thenReturn(true);
        ResponseEntity response = endpoint.post(request);

        assertThat(response.getBody(),is(expected));
    }
}
