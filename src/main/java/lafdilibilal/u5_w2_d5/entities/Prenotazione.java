package lafdilibilal.u5_w2_d5.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Prenotazioni", uniqueConstraints = @UniqueConstraint(columnNames = {"username_dipendente", "data"}))
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_prenotazione;

    private LocalDate data;

    private String note;

    @ManyToOne
    @JoinColumn(name = "username_dipendente")
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name = "id_viaggio")
    private Viaggio viaggio;

    public Prenotazione(LocalDate data, String note, Viaggio viaggio, Dipendente dipendente) {
        this.data = data;
        this.note = note;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }

    @Override
    public String toString() {
        return "Prenotazione{" +
                "id_prenotazione=" + id_prenotazione +
                ", data=" + data +
                ", note='" + note + '\'' +
                ", dipendente=" + dipendente +
                ", viaggio=" + viaggio +
                '}';
    }
}
