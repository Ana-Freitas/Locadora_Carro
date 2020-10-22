package gerenciadorArquivos;

import constantes.Constantes;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entidades.Fisica;
import entidades.Juridica;
import entidades.Locacao;
import entidades.Pessoa;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorPessoas {
    
    public static void gravar(Pessoa pessoa, String caminho) throws IOException{
        FileOutputStream arquivoSaida = null;
        ObjectOutputStream objetoSaida = null;
        try{
            arquivoSaida = new FileOutputStream(caminho, true);
            objetoSaida = new ObjectOutputStream(arquivoSaida);
            objetoSaida.writeObject(pessoa);
            objetoSaida.flush();
        }finally{
            objetoSaida.close();
            arquivoSaida.close();
        }
    }
    
      public static Pessoa ler(String nome, String caminho) throws IOException, ClassNotFoundException{
        Pessoa pessoa = null;
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            
            while(arquivoEntrada.available() > 0){
                objetoEntrada = new ObjectInputStream(arquivoEntrada);
                pessoa = (Pessoa)objetoEntrada.readObject();
                if(pessoa.getNome().equalsIgnoreCase(nome)){
                    return pessoa;
                }
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return pessoa;
    }
    
    public static boolean existe(Pessoa p, String caminho) throws IOException, ClassNotFoundException{
        Pessoa pessoa = null;
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            while(arquivoEntrada.available() > 0){
                objetoEntrada = new ObjectInputStream(arquivoEntrada);
                pessoa = (Pessoa)objetoEntrada.readObject();
                if(pessoa.getTipo() == Pessoa.enmTipo.FISICO && p.getTipo() == Pessoa.enmTipo.FISICO) {
                    if(((Fisica) pessoa).getCpf().equals(((Fisica) p).getCpf())){
                            return true;
                    }
                }else if(pessoa.getTipo() == Pessoa.enmTipo.JURIDICO && p.getTipo() == Pessoa.enmTipo.JURIDICO) {
                    if(((Juridica) pessoa).getCnpj().equals(((Juridica) p).getCnpj())){
                        return true;
                    }
                }  
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return false;
    }
    
    public static List<Pessoa> getPessoas(String caminho) throws IOException, ClassNotFoundException{
        Pessoa pessoa = null;
        List<Pessoa> pessoas = new ArrayList<>();
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            
            while(arquivoEntrada.available() > 0){
                objetoEntrada = new ObjectInputStream(arquivoEntrada);
                pessoa = (Pessoa)objetoEntrada.readObject();
                pessoas.add(pessoa);
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return pessoas;
    }
    
    public static boolean atualizarPessoa(Pessoa pessoa, String caminho) throws FileNotFoundException, IOException, ClassNotFoundException{
            List<Pessoa> pessoas = GerenciadorPessoas.getPessoas(caminho);
            
            for (Pessoa p : pessoas) {
                if(p.getNome().equals(pessoa.getNome())){
                    p.setLocacao(pessoa.getLocacoes());
                }
            }
            
            boolean success = (new File(caminho)).delete();
            if(success){
                for (Pessoa p : pessoas) {
                    GerenciadorPessoas.gravar(p, caminho);
                }
                
                return true;
            }
        return false;
    }
}
