/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entidades.Carro;
import entidades.Pessoa;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorPessoas;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anevnessa
 */
public class FuncoesUteis {
    public static boolean isLetra(String nome){
        return nome.matches("[A-Za-z]");
    }
    
    public static boolean isNumber(String text){
        return text.matches("[0-9]*");
    }
    
    public static int getProxCodCliente(){
       int codigo = 0;
        try {
            List<Pessoa> pessoas = GerenciadorPessoas.getPessoas(constantes.Constantes.CAMINHO_PESSOA);
            for (Pessoa i : pessoas) {
                if(i.getCodigo() > codigo){
                    codigo = i.getCodigo();
                }
            }
            
            return ++codigo;
        } catch (IOException ex) {
            Logger.getLogger(FuncoesUteis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncoesUteis.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            codigo = 1;
        }
        return codigo;
    }
    
        public static int getProxCodCarro(){
       int codigo = 0;
        try {
            List<Carro> carros = GerenciadorCarros.getCarros(constantes.Constantes.CAMINHO_CARRO);
            for (Carro i : carros) {
                if(i.getCodigo() > codigo){
                    codigo = i.getCodigo();
                }
            }
            
            return ++codigo;
        } catch (IOException ex) {
            Logger.getLogger(FuncoesUteis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FuncoesUteis.class.getName()).log(Level.SEVERE, null, ex);
        } catch(NullPointerException ex){
            codigo = 1;
        }
        return codigo;
    }
}
