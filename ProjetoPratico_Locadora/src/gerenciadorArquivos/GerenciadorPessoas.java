package gerenciadorArquivos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entidades.Fisica;
import entidades.Juridica;
import entidades.Pessoa;
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
            objetoEntrada = new ObjectInputStream(arquivoEntrada);
            while(arquivoEntrada.available() > 0){
                pessoa = (Pessoa)objetoEntrada.readObject();
                if(pessoa.getNome().equals(nome)) {
                	return pessoa;
                }
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return null;
    }
    
    public static boolean existe(String identificacao, String caminho) throws IOException, ClassNotFoundException{
        Pessoa pessoa = null;
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            objetoEntrada = new ObjectInputStream(arquivoEntrada);
            while(arquivoEntrada.available() > 0){
                pessoa = (Pessoa)objetoEntrada.readObject();
                if(pessoa.getTipo() == Pessoa.enmTipo.FISICO) {
    				if(((Fisica) pessoa).getCpf().equals(identificacao)){
    					return true;
    				}
    			}else if(pessoa.getTipo() == Pessoa.enmTipo.JURIDICO) {
    				if(((Juridica) pessoa).getCnpj().equals(identificacao)) {
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
            objetoEntrada = new ObjectInputStream(arquivoEntrada);
            while(arquivoEntrada.available() > 0){
                pessoa = (Pessoa)objetoEntrada.readObject();
                pessoas.add(pessoa);
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return pessoas;
    }
}
