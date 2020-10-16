package gerenciadorArquivos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import entidades.Locacao;

public class GerenciadorLocacoes {
    public static void gravar(Locacao locacao, String caminho) throws IOException{
        FileOutputStream arquivoSaida = null;
        ObjectOutputStream objetoSaida = null;
        try{
            arquivoSaida = new FileOutputStream(caminho, true);
            objetoSaida = new ObjectOutputStream(arquivoSaida);
            objetoSaida.writeObject(locacao);
            objetoSaida.flush();
        }finally{
            objetoSaida.close();
            arquivoSaida.close();
        }
    }
        
        
}
