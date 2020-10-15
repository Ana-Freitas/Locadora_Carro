/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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
}
