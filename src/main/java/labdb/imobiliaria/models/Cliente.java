package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Table(name = "Clientes")
public @Data class Cliente implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
}
