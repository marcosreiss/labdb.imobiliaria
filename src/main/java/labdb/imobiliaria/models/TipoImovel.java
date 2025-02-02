package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tipo_Imovel")
public @Data class TipoImovel implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;
}
