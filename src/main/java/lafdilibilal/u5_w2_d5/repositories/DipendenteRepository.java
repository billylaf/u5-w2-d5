package lafdilibilal.u5_w2_d5.repositories;

import lafdilibilal.u5_w2_d5.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DipendenteRepository extends JpaRepository<Dipendente, String> {
    Optional<Dipendente> findByEmail(String email);
}
