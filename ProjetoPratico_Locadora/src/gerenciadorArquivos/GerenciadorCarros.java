package gerenciadorArquivos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entidades.Carro;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCarros {
	
	public static void gravar(Carro carro, String caminho) throws IOException{
        FileOutputStream arquivoSaida = null;
        ObjectOutputStream objetoSaida = null;
        try{
            arquivoSaida = new FileOutputStream(caminho, true);
            objetoSaida = new ObjectOutputStream(arquivoSaida);
            objetoSaida.writeObject(carro);
            objetoSaida.flush();
        }finally{
            objetoSaida.close();
            arquivoSaida.close();
        }
    }
    
    public static Carro ler(String placa, String caminho) throws IOException, ClassNotFoundException{
        Carro carro = null;
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            objetoEntrada = new ObjectInputStream(arquivoEntrada);
            while(arquivoEntrada.available() > 0){
            	carro = (Carro)objetoEntrada.readObject();
            	if(carro.getPlaca().equals(placa)) {
            		return carro;
            	}
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return null;
    }
    
    public static boolean existe(String placa, String caminho) throws IOException, ClassNotFoundException{
    	Carro carro = null;
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            objetoEntrada = new ObjectInputStream(arquivoEntrada);
            while(arquivoEntrada.available() > 0){
            	carro = (Carro)objetoEntrada.readObject();
            	if(carro.getPlaca().equals(placa)) {
                    return true;
            	}
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return false;
    }
    
    public static List<Carro> getCarros(String caminho) throws IOException, ClassNotFoundException{
        Carro carro = null;
        List<Carro> carros  = new ArrayList<>();
        FileInputStream arquivoEntrada = null;
        ObjectInputStream objetoEntrada = null;
        try{
            arquivoEntrada = new FileInputStream(caminho);
            
            while(arquivoEntrada.available() > 0){
                objetoEntrada = new ObjectInputStream(arquivoEntrada);
                carro = (Carro)objetoEntrada.readObject();
                carros.add(carro);
            }
        }finally{
            objetoEntrada.close();
            arquivoEntrada.close();
        }
        return carros;
    }
}
