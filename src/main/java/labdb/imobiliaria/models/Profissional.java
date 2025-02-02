package labdb.imobiliaria.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Profissionais")
public @Data class Profissional implements EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String profissao;
    private String telefone1;
    private String telefone2;
    private String email;
    private double valorHora;
    private String obs;
}
