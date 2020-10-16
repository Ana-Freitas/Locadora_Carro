package entidades;

import java.io.IOException;
import java.io.Serializable;

import gerenciadorArquivos.GerenciadorCarros;

public class Carro implements Serializable{
	
    private static final long serialVersionUID = 1L;
	
    private static int codigo;
    private String marca;
    private String modelo;
    private String estado;
    private int ano;
    private String placa;
    private short qtdPorta;
    private boolean hasArCond;
    private double valorDiaria;
    private boolean situacao;   
    
    public Carro(String marca, String modelo, int ano, String placa, short qtdPorta, boolean hasArCond, double valorDiaria, String estado, boolean situacao) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.placa = placa;
        this.qtdPorta = qtdPorta;
        this.hasArCond = hasArCond;
        this.valorDiaria = valorDiaria;
        this.estado = estado;
        this.situacao = situacao;
        Carro.codigo++;
    }	

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
            this.marca = marca;
    }

    public String getModelo() {
            return modelo;
    }

    public void setModelo(String modelo) {
            this.modelo = modelo;
    }

    public int getAno() {
            return ano;
    }

    public void setAno(int ano) {
            this.ano = ano;
    }

    public String getPlaca() {
            return placa;
    }

    public void setPlaca(String placa) {
            this.placa = placa;
    }

    public short getQtdPorta() {
            return qtdPorta;
    }

    public void setQtdPorta(short qtdPorta) {
            this.qtdPorta = qtdPorta;
    }

    public boolean isHasArCond() {
            return hasArCond;
    }

    public void setHasArCond(boolean hasArCond) {
            this.hasArCond = hasArCond;
    }

    public double getValorDiaria() {
            return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
            this.valorDiaria = valorDiaria;
    }

    public String getEstado() {
            return estado;
    }

    public void setEstado(String estado) {
            this.estado = estado;
    }

    public boolean isSituacao() {
            return situacao;
    }

    public void setSituacao(boolean situacao) {
            this.situacao = situacao;
    }

    public static int getCodigo() {
            return codigo;
    }	

    public boolean salvarCarro(String caminho) throws IOException, ClassNotFoundException {
            if(Carro.existe(this.getPlaca(), caminho) ) {
                return false;
            }

            GerenciadorCarros.gravar(this, caminho);
            return true;
    }

    public static Carro getCarro(String placa, String caminho) throws IOException, ClassNotFoundException {
            Carro carro = GerenciadorCarros.ler(placa, caminho);

            return carro;
    }
        
    public static boolean existe(String identificacao, String caminho){
        try {
            return GerenciadorCarros.existe(identificacao, caminho);
        } catch (IOException | ClassNotFoundException | NullPointerException ex) {
           return false;
        }
    }
}
