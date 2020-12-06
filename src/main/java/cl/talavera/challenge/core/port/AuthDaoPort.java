package cl.talavera.challenge.core.port;


import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.domain.SignupSuccess;


public interface AuthDaoPort {
    void signup(User request);
    SignupSuccess signupSuccess(User request);
}
