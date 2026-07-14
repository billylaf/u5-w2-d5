package lafdilibilal.u5_w2_d5.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lafdilibilal.u5_w2_d5.DTO.DipendenteDTO;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.exceptions.BadRequestException;
import lafdilibilal.u5_w2_d5.exceptions.NotFoundException;
import lafdilibilal.u5_w2_d5.repositories.DipendenteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DipendenteService {

    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary cloudinary;
    private final PasswordEncoder bcrypt;

    public DipendenteService(DipendenteRepository dipendenteRepository, Cloudinary cloudinary, PasswordEncoder bcrypt) {
        this.dipendenteRepository = dipendenteRepository;
        this.cloudinary = cloudinary;
        this.bcrypt = bcrypt;
    }

    public List<Dipendente> findAll() {
        return this.dipendenteRepository.findAll();
    }

    public Dipendente save(DipendenteDTO body) {
        if (this.dipendenteRepository.existsById(body.getUsername())) {
            throw new BadRequestException("Username già esistente: " + body.getUsername());
        }

        if (this.dipendenteRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new BadRequestException("Email già esistente: " + body.getEmail());
        }

        Dipendente newDipendente = new Dipendente(
                body.getUsername(),
                body.getNome(),
                body.getCognome(),
                body.getEmail(),
                this.bcrypt.encode(body.getPassword())
        );

        Dipendente saved = this.dipendenteRepository.save(newDipendente);
        log.info("Dipendente con username " + saved.getUsername() + " creato");
        return saved;
    }

    public Dipendente findById(String username) {
        return this.dipendenteRepository.findById(username)
                .orElseThrow(() -> new NotFoundException("Dipendente con username " + username + " non trovato"));
    }

    public Dipendente findByIdAndUpdate(String username, DipendenteDTO body) {
        Dipendente found = this.findById(username);

        if (!found.getEmail().equals(body.getEmail())) {
            this.dipendenteRepository.findByEmail(body.getEmail())
                    .ifPresent(d -> {
                        if (!d.getUsername().equals(username)) {
                            throw new BadRequestException("Email già utilizzata da un altro dipendente");
                        }
                    });
        }

        found.setNome(body.getNome());
        found.setCognome(body.getCognome());
        found.setEmail(body.getEmail());

        return this.dipendenteRepository.save(found);
    }

    public void findByIdAndDelete(String username) {
        Dipendente found = this.findById(username);
        this.dipendenteRepository.delete(found);
        log.info("Dipendente con username " + username + " eliminato");
    }

    public Dipendente uploadAvatar(String username, MultipartFile file) {
        Dipendente found = this.findById(username);
        try {
            Map uploadResult = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = (String) uploadResult.get("url");
            found.setAvatar(url);
            return this.dipendenteRepository.save(found);
        } catch (IOException e) {
            throw new BadRequestException("errore durante upload del img");
        }
    }


    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("il dipendente con email " + email + " non e stato trovato"));
    }
}