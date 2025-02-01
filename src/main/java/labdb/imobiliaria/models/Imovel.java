package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Imoveis")
public @Data class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String logradouro;
    private String bairro;
    private String cep;
    private int metragem;
    private int dormitorios;
    private int banheiros;
    private int suites;
    private int vagas_garagem;
    private double valor_sugerido;
    private String obs;

    @ManyToOne
    private TipoImovel tipoImovel;

    @ManyToOne
    private Cliente cliente;
}
