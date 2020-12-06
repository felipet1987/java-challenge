package cl.talavera.challenge.core.port;


import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.domain.SignupSuccess;


public interface AuthUseCasePort {
    SignupSuccess signup(User request);
}
