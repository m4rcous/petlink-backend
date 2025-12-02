package mags.petlink.domain.model;

import jakarta.persistence.*;
import lombok.*;
import mags.petlink.domain.enums.EstadoCollar;

@Entity
@Table(name = "collares")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCollar estado = EstadoCollar.DISPONIBLE;

    @OneToOne(mappedBy = "collar")
    private Mascota mascota;
}
