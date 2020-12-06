package cl.talavera.challenge.persistence.dao;


import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.persistence.JWT;
import cl.talavera.challenge.persistence.Timer;
import cl.talavera.challenge.persistence.model.PhoneModel;
import cl.talavera.challenge.persistence.model.UserModel;
import cl.talavera.challenge.persistence.repository.PhoneRepository;
import cl.talavera.challenge.persistence.repository.UserRepository;
import cl.talavera.challenge.core.port.AuthDaoPort;
import cl.talavera.challenge.core.domain.SignupSuccess;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class AuthDao implements AuthDaoPort {
    private final UserRepository userRepository;
    private final PhoneRepository phoneRepository;
    private final JWT jwt;
    public static final DateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private final Timer timer;

    public AuthDao(UserRepository userRepository, PhoneRepository phoneRepository, JWT jwt, Timer timer) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.jwt = jwt;
        this.timer = timer;
    }



    @Override
    public void signup(User user) {

        if(isDuplicate(user)){
            throw new RuntimeException("El correo ya registrado");
        }

        saveUser(user);
        user.getPhones().forEach(p -> {
            savePhone(user, p);
        });
    }

    private void saveUser(User user) {

        Date now = timer.now();
        userRepository.save(UserModel.builder()
                .name(user.getName())
                .created(now)
                .password(user.getPassword())
                .email(user.getEmail())
                .modified(now)
                .lastLogin(now)
                .active(true)
                .build());
    }

    private void savePhone(User user, cl.talavera.challenge.core.domain.Phone p) {
        phoneRepository.save(PhoneModel.builder()
                .number(p.getNumber())
                .email(user.getEmail())
                .contrycode(p.getContrycode())
                .citycode(p.getCitycode())
                .build());
    }

    private boolean isDuplicate(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }


    @Override
    public SignupSuccess signupSuccess(User request) {
        UserModel u = userRepository.findByEmail(request.getEmail()).get();
        return SignupSuccess.builder()
                .id(u.getId().toString())
                .created(FORMAT.format(u.getCreated()))
                .modified(FORMAT.format(u.getModified()))
                .lastLogin(FORMAT.format(u.getLastLogin()))
                .isActive(u.isActive())
                .token(jwt.generate(u.getPassword()))
                .build();
    }
}
