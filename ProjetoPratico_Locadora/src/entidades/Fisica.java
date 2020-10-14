package entidades;

import java.util.List;

public class Fisica extends Pessoa{
    private String cpf;

	public Fisica(String nome, List<Locacao> locacoes, String cpf) {
		super(nome, locacoes);
		this.cpf = cpf;
		this.tipoPessoa = enmTipo.FISICO;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
