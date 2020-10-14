
package entidades;

import java.io.IOException;
import java.lang.NullPointerException;
import java.util.List;

import gerenciadorArquivos.GerenciadorPessoas;
import java.io.Serializable;

public abstract class Pessoa implements Serializable {
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
        if(!Pessoa.existe(this.getNome(), caminho) ) {
            GerenciadorPessoas.gravar(this, caminho);
            return true;
        }
        return false;
    }

    public static Pessoa getPessoa(String nome, String caminho) throws IOException, ClassNotFoundException {
        Pessoa pessoa = GerenciadorPessoas.ler(nome, caminho);

        return pessoa;
    }
        
    public static boolean existe(String identificacao, String caminho){
        try {
            return GerenciadorPessoas.existe(identificacao, caminho);
        } catch (IOException | ClassNotFoundException | NullPointerException ex) {
           return false;
        }
    }
}
