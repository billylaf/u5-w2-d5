package lafdilibilal.u5_w2_d5.services;

import lafdilibilal.u5_w2_d5.DTO.StatoViaggioDTO;
import lafdilibilal.u5_w2_d5.DTO.ViaggioDTO;
import lafdilibilal.u5_w2_d5.entities.Viaggio;
import lafdilibilal.u5_w2_d5.enums.StatoViaggio;
import lafdilibilal.u5_w2_d5.exceptions.BadRequestException;
import lafdilibilal.u5_w2_d5.exceptions.NotFoundException;
import lafdilibilal.u5_w2_d5.repositories.ViaggioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    public List<Viaggio> findAll() {
        return this.viaggioRepository.findAll();
    }

    public Viaggio save(ViaggioDTO body) {

        if (body.getData().isBefore(LocalDate.now())) {
            throw new BadRequestException("La data del viaggio non può essere passata");
        }

        StatoViaggio stato = StatoViaggio.IN_PROGRAMMA;
        if (body.getStato() != null && !body.getStato().isEmpty()) {
            try {
                stato = StatoViaggio.valueOf(body.getStato().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Stato non valido. Valori permessi: IN_PROGRAMMA, COMPLETATO");
            }
        }

        Viaggio newViaggio = new Viaggio(
                body.getDestinazione(),
                body.getData(),
                stato
        );

        Viaggio saved = this.viaggioRepository.save(newViaggio);
        log.info("Viaggio con id " + saved.getId_viaggio() + " creato");
        return saved;
    }

    public Viaggio findById(Long id) {
        return this.viaggioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Viaggio con id " + id + " non trovato"));
    }

    public Viaggio findByIdAndUpdate(Long id, ViaggioDTO body) {
        Viaggio found = this.findById(id);


        if (body.getData().isBefore(LocalDate.now())) {
            throw new BadRequestException("La data del viaggio non può essere passata");
        }

        found.setDestinazione(body.getDestinazione());
        found.setData(body.getData());

        if (body.getStato() != null && !body.getStato().isEmpty()) {
            try {
                StatoViaggio stato = StatoViaggio.valueOf(body.getStato().toUpperCase());
                found.setStato(stato);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Stato non valido. Valori permessi: IN_PROGRAMMA, COMPLETATO");
            }
        }

        return this.viaggioRepository.save(found);
    }

    public void findByIdAndDelete(Long id) {
        Viaggio found = this.findById(id);
        this.viaggioRepository.delete(found);
        log.info("Viaggio con id " + id + " eliminato");
    }

    public Viaggio updateStato(Long id, StatoViaggioDTO body) {
        Viaggio found = this.findById(id);

        try {
            StatoViaggio nuovoStato = StatoViaggio.valueOf(body.getStato().toUpperCase());
            found.setStato(nuovoStato);
            log.info("Stato viaggio " + id + " aggiornato a " + nuovoStato);
            return this.viaggioRepository.save(found);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato non valido. Valori permessi: IN_PROGRAMMA, COMPLETATO");
        }
    }
}
