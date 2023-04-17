package com.generation.clicksolucao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "tb_categorias")
public class Categoria {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O tipo de serviço é obrigatório")
    private String tipoServico;

    @NotBlank(message = "Profissão é obrigatória")
    private String profissao;

    @NotBlank(message = "Regime de contratação é obrigatório")
    private String contratacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getContratacao() {
        return contratacao;
    }

    public void setContratacao(String contratacao) {
        this.contratacao = contratacao;
    }
}
