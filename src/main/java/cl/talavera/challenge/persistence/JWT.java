package cl.talavera.challenge.persistence;

import org.springframework.stereotype.Service;

@Service
public class JWT {
    public String generate(String password) {
        return password;
    }
}
