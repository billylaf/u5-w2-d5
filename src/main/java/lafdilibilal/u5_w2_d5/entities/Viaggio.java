package lafdilibilal.u5_w2_d5.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lafdilibilal.u5_w2_d5.enums.StatoViaggio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Viaggio {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_viaggio;


    private String destinazione;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatoViaggio stato;
    
    @JsonIgnore
    @OneToMany(mappedBy = "viaggio", cascade = CascadeType.ALL)
    private List<Prenotazione> prenotazioni;


    public Viaggio(String destinazione, LocalDate data, StatoViaggio stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Viaggio{" +
                "id_viaggio=" + id_viaggio +
                ", destinazione='" + destinazione + '\'' +
                ", data=" + data +
                ", stato=" + stato +
                ", prenotazioni=" + prenotazioni +
                '}';
    }
}
