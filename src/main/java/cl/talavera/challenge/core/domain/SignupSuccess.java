package cl.talavera.challenge.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupSuccess {
    private String id;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private Boolean isActive;
}
