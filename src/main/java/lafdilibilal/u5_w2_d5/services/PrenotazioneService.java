package lafdilibilal.u5_w2_d5.services;

import lafdilibilal.u5_w2_d5.DTO.PrenotazioneDTO;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.entities.Prenotazione;
import lafdilibilal.u5_w2_d5.entities.Viaggio;
import lafdilibilal.u5_w2_d5.enums.StatoViaggio;
import lafdilibilal.u5_w2_d5.exceptions.BadRequestException;
import lafdilibilal.u5_w2_d5.exceptions.NotFoundException;
import lafdilibilal.u5_w2_d5.repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final DipendenteService dipendenteService;
    private final ViaggioService viaggioService;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository,
                               DipendenteService dipendenteService,
                               ViaggioService viaggioService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendenteService = dipendenteService;
        this.viaggioService = viaggioService;
    }

    public List<Prenotazione> findAll() {
        return this.prenotazioneRepository.findAll();
    }

    @Transactional
    public Prenotazione save(PrenotazioneDTO body) {
        Dipendente dipendente = this.dipendenteService.findById(body.getDipendenteUsername());

        Viaggio viaggio = this.viaggioService.findById(body.getViaggioId());

        if (viaggio.getStato() == StatoViaggio.COMPLETATO) {
            throw new BadRequestException("Impossibile prenotare un viaggio già completato");
        }

        if (this.prenotazioneRepository.existsByDipendenteUsernameAndData(
                dipendente.getUsername(), body.getData())) {
            throw new BadRequestException("Il dipendente ha già una prenotazione per la data: " + body.getData());
        }

        Prenotazione newPrenotazione = new Prenotazione(
                body.getData(),
                body.getNote(),
                viaggio,
                dipendente
        );

        Prenotazione saved = this.prenotazioneRepository.save(newPrenotazione);
        log.info("Prenotazione con id " + saved.getId_prenotazione() + " creata per dipendente " + dipendente.getUsername());
        return saved;
    }

    public Prenotazione findById(Long id) {
        return this.prenotazioneRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prenotazione con id " + id + " non trovata"));
    }

    public void findByIdAndDelete(Long id) {
        Prenotazione found = this.findById(id);
        this.prenotazioneRepository.delete(found);
        log.info("Prenotazione con id " + id + " eliminata");
    }

    public List<Prenotazione> findByDipendenteUsername(String username) {

        this.dipendenteService.findById(username);
        return this.prenotazioneRepository.findByDipendenteUsername(username);
    }
}