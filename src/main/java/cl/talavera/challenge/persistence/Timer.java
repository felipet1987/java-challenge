package cl.talavera.challenge.persistence;


import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class Timer {
    public Date now() {
       return Date.from(Instant.now());
    }
}
