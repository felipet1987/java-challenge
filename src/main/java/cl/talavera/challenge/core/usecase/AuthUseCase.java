package cl.talavera.challenge.core.usecase;

import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.core.port.AuthDaoPort;
import cl.talavera.challenge.core.port.AuthUseCasePort;
import cl.talavera.challenge.core.domain.SignupSuccess;
import org.springframework.stereotype.Service;


@Service
public class AuthUseCase implements AuthUseCasePort {
    private final AuthDaoPort dao;

    public AuthUseCase(AuthDaoPort dao) {
        this.dao = dao;
    }

    @Override
    public SignupSuccess signup(User request) {
        dao.signup(request);
        return dao.signupSuccess(request);
    }
}
