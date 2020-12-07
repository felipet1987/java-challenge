package cl.talavera.challenge.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Service
public class JWT {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String generate(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
