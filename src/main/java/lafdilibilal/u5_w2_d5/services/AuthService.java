package lafdilibilal.u5_w2_d5.services;

import lafdilibilal.u5_w2_d5.DTO.LoginDTO;
import lafdilibilal.u5_w2_d5.entities.Dipendente;
import lafdilibilal.u5_w2_d5.exceptions.UnauthorizedException;
import lafdilibilal.u5_w2_d5.security.JWTTools;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final DipendenteService dipendenteService;
    private final JWTTools jwtTools;
    private final PasswordEncoder bcrypt;

    public AuthService(DipendenteService dipendentiService, JWTTools jwtTools, PasswordEncoder bcrypt) {
        this.dipendenteService = dipendentiService;
        this.jwtTools = jwtTools;
        this.bcrypt = bcrypt;
    }

    public String checkCredentialsAndGenerateToken(LoginDTO body) {
        Dipendente found = this.dipendenteService.findByEmail(body.email());

        if (this.bcrypt.matches(body.password(), found.getPassword())) {
            return this.jwtTools.generateToken(found);
        } else {
            throw new UnauthorizedException("credenziali sbagliate");
        }
    }
}
