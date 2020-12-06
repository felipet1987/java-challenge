package cl.talavera.challenge.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {



    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private Date created;
    private Date modified;
    private Date lastLogin;
}
