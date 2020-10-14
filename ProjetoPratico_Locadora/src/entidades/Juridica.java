/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.util.List;

public class Juridica extends Pessoa{
    private String cnpj;
    private String razaoSocial;
	
    public Juridica(String nome, List<Locacao> locacoes, String cnpj, String razaoSocial) {
        super(nome, locacoes);
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.tipoPessoa = enmTipo.JURIDICO;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
       return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
}
