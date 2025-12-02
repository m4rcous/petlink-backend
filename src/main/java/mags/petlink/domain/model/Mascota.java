package mags.petlink.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "hora_ingresa", nullable = false)
    private LocalTime horaIngresa;

    @Column(name = "internado", nullable = false)
    private boolean internado = false;

    @OneToOne
    @JoinColumn(name = "collar_id")
    private Collar collar;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HistorialLatidos> historialLatidos = new ArrayList<>();
}
