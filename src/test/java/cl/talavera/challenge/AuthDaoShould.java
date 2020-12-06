package cl.talavera.challenge;

import cl.talavera.challenge.core.domain.Phone;
import cl.talavera.challenge.core.domain.User;
import cl.talavera.challenge.persistence.JWT;
import cl.talavera.challenge.persistence.Timer;
import cl.talavera.challenge.web.UserValidation;
import cl.talavera.challenge.persistence.dao.AuthDao;
import cl.talavera.challenge.persistence.model.PhoneModel;
import cl.talavera.challenge.persistence.model.UserModel;
import cl.talavera.challenge.persistence.repository.PhoneRepository;
import cl.talavera.challenge.persistence.repository.UserRepository;
import cl.talavera.challenge.core.port.AuthDaoPort;
import cl.talavera.challenge.core.domain.SignupSuccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthDaoShould {

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static final String ID = "e169e36f-6b74-49d9-8aea-6ccd136ad719";
    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private AuthDaoPort dao;
    private JWT jwt;
    private Timer timer;
    private UserValidation validation;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        phoneRepository = mock(PhoneRepository.class);
        jwt = mock(JWT.class);
        timer = mock(Timer.class);
        validation = mock(UserValidation.class);
        dao = new AuthDao(
                userRepository,
                phoneRepository,
                jwt,
                timer
                );
    }

    @Test
    void signup_and_save_on_repositories() throws ParseException {


        when(timer.now()).thenReturn(FORMAT.parse("01-01-2020"));
        dao.signup(user());

        verify(timer,times(1)).now();
        verify(userRepository,times(1)).save(userModel());
        verify(phoneRepository,times(1)).save(phoneModel());;
    }

    @Test
    void return_success_response() throws ParseException {
        User request = User.builder()
                .email("correo").password("pass")
                .build();

        when(jwt.generate("pass")).thenReturn("dummy-token");
        when(userRepository.findByEmail("correo")).thenReturn(createdUser());
        SignupSuccess result = dao.signupSuccess(request);

        verify(jwt,times(1)).generate("pass");
        verify(userRepository,times(1)).findByEmail("correo");
        assertThat(result,is(expectedSuccess()));

    }

    private Optional<UserModel> createdUser() throws ParseException {
        return Optional.of(UserModel.builder()
                .id(UUID.fromString("e169e36f-6b74-49d9-8aea-6ccd136ad719"))
                .password("pass")
                .created(FORMAT.parse("01-01-2020"))
                .active(true)
                .lastLogin(FORMAT.parse("01-01-2020"))
                .modified(FORMAT.parse("01-01-2020"))
                .build());
    }

    private SignupSuccess expectedSuccess() {
        SignupSuccess success = SignupSuccess.builder()
                .id("e169e36f-6b74-49d9-8aea-6ccd136ad719")
                .isActive(true)
                .token("dummy-token")
                .created("01-01-2020")
                .lastLogin("01-01-2020")
                .modified("01-01-2020")
                .build();
        return success;
    }

    private PhoneModel phoneModel() {
        PhoneModel phoneModel = PhoneModel.builder()
                .citycode("34")
                .email("timmy@world.com")
                .contrycode("56")
                .number("123456")
                .build();
        return phoneModel;
    }

    private UserModel userModel() throws ParseException {
        UserModel userModel = UserModel.builder()
                .name("timmy")
                .email("timmy@world.com")
                .password("pass")
                .created(FORMAT.parse("01-01-2020"))
                .modified(FORMAT.parse("01-01-2020"))
                .lastLogin(FORMAT.parse("01-01-2020"))
                .active(true)
                .build();
        return userModel;
    }

    private User user() {
        List<Phone> phones = asList(Phone.builder()
                .contrycode("56")
                .number("123456")
                .citycode("34")
                .build());
        User request = User.builder()
                .name("timmy")
                .email("timmy@world.com")
                .password("pass")
                .phones(phones)
                .build();
        return request;
    }


}
