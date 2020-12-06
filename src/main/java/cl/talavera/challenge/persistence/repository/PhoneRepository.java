package cl.talavera.challenge.persistence.repository;


import cl.talavera.challenge.persistence.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneModel, UUID> {
}
