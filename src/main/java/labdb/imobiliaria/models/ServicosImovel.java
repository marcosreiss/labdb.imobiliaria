package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Servicos_Imovel")
public @Data class ServicosImovel implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Imovel imovel;

    @ManyToOne
    private Profissional profissional;

    @Temporal(TemporalType.DATE)
    private Date dataServico;

    private double valorTotal;
    private String obs;
}
