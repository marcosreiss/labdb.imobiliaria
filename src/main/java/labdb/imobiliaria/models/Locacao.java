package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "locacao")
public @Data class Locacao implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Imovel imovel;

    @ManyToOne
    @JoinColumn(name = "id_inquilino")
    private Cliente inquilino;

    private double valorAluguel;
    private double percentualMulta;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    private int diaVencimento;
    private boolean ativo;
    private String obs;
}
