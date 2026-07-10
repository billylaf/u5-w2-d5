package lafdilibilal.u5_w2_d5.repositories;

import lafdilibilal.u5_w2_d5.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Prenotazione p WHERE p.dipendente.username = :username AND p.data = :data")
    boolean existsByDipendenteUsernameAndData(@Param("username") String username, @Param("data") LocalDate data);

    List<Prenotazione> findByDipendenteUsername(String username);
}
