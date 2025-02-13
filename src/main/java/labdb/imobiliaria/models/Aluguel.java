package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "alugueis")
public @Data class Aluguel implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Locacao locacao;

    @Temporal(TemporalType.DATE)
    private Date dataVencimento;

    private double valorPago;

    @Temporal(TemporalType.DATE)
    private Date dataPagamento;

    private String obs;
}
