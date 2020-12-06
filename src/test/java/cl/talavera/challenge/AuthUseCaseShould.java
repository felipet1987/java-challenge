package cl.talavera.challenge;

import cl.talavera.challenge.core.port.AuthDaoPort;
import cl.talavera.challenge.core.port.AuthUseCasePort;
import cl.talavera.challenge.core.usecase.AuthUseCase;
import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.domain.SignupSuccess;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class AuthUseCaseShould {
    @Test
    void signup() {
        AuthDaoPort authDao = mock(AuthDaoPort.class);
        AuthUseCasePort useCase = new AuthUseCase(authDao);
        User request = User.builder().build();

        useCase.signup(request);

        verify(authDao,times(1)).signup(request);
        verify(authDao,times(1)).signupSuccess(request);
    }

    private SignupSuccess expected() {
        return SignupSuccess.builder().build();
    }
}
