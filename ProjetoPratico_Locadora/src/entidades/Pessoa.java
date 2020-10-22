
package entidades;

import java.io.IOException;
import java.util.List;

import gerenciadorArquivos.GerenciadorPessoas;
import java.io.Serializable;
import util.FuncoesUteis;

public abstract class Pessoa implements Serializable {
    private int codPessoa;
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
            this.codPessoa = FuncoesUteis.getProxCodCliente();
    }
    
    public int getCodigo(){
        return this.codPessoa;
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

    public void addLocacao(Locacao locacao) {
         this.locacoes.add(locacao);
    }
    
    public void setLocacao(List<Locacao> list) {
         this.locacoes = list;
    }

    public enmTipo getTipo() {
        return tipoPessoa;
    }
    
    public boolean salvarPessoa(String caminho) throws IOException, ClassNotFoundException {
        if(!Pessoa.existe(this, caminho) ) {
            GerenciadorPessoas.gravar(this, caminho);
            return true;
        }
        return false;
    }

    public static Pessoa getPessoa(String nome, String caminho) throws IOException, ClassNotFoundException {
        Pessoa pessoa = GerenciadorPessoas.ler(nome, caminho);

        return pessoa;
    }
        
    public static boolean existe(Pessoa p, String caminho){
        try {
            return GerenciadorPessoas.existe(p, caminho);
        } catch (IOException | ClassNotFoundException | NullPointerException ex) {
           return false;
        }
    }
}
