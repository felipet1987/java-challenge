package cl.talavera.challenge.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Phone {
    private String number;
    private String citycode;
    private String contrycode;
}
