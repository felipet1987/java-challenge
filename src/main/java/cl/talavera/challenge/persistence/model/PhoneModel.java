package cl.talavera.challenge.persistence.model;


import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Builder
@Entity
public class PhoneModel {

    @Id
    @GeneratedValue
    private UUID id;
    private String email;
    private String number;
    private String citycode;
    private String contrycode;
}
