
package entidades;

import java.io.IOException;
import java.util.List;

import gerenciadorArquivos.GerenciadorPessoas;

public abstract class Pessoa {
    private static int codigo;
    private String nome;
    private List<Locacao> locacoes;
    protected enmTipo tipoPessoa;
    
    public static enum enmTipo{
		JURIDICO,
		FISICO,
	}
    
	public Pessoa(String nome, List<Locacao> locacoes) {
		this.nome = nome;
		this.locacoes = locacoes;
		Pessoa.codigo++;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Locacao> getLocacoes() {
		return locacoes;
	}

	public void setLocacoes(List<Locacao> locacoes) {
		this.locacoes = locacoes;
	}

	public static int getCodigo() {
		return codigo;
	}   
	
	public enmTipo getTipo() {
    	return tipoPessoa;
    }
    
	public boolean salvarPessoa(String caminho) throws IOException, ClassNotFoundException {
		if(GerenciadorPessoas.existe(this.getNome(), caminho) ) {
			return false;
		}
		
		GerenciadorPessoas.gravar(this, caminho);
		return true;
	}
	
	public static Pessoa getPessoa(String nome, String caminho) throws IOException, ClassNotFoundException {
		Pessoa pessoa = GerenciadorPessoas.ler(nome, caminho);

		return pessoa;
	}
}
