/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import gerenciadorArquivos.GerenciadorLocacoes;
import util.DataUtil;


public class Locacao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private static int codigo;
    private int numero;
    private LocalDate dataRealizacao;
    private int numDiarias;
    private LocalDate dataDevolucao;
    private LocalDate dataMaxDevolucao;
    private Pessoa cliente;
    private List<Carro> carros;
    
    public Locacao(LocalDate dataRealizacao, int numDiarias, LocalDate dataMaxDevolucao, Pessoa cliente, List<Carro> carros) {
    	this.dataRealizacao = dataRealizacao;
		this.numDiarias = numDiarias;
		this.dataDevolucao = null;
		this.dataMaxDevolucao = dataMaxDevolucao;
		this.cliente = cliente;
		this.carros = carros;
		this.numero = ++Locacao.codigo;
	}

	public String getDataRealizacao() {
		return DataUtil.getDataFormatada(dataRealizacao);
	}

	public void setDataRealizacao(LocalDate dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}	

	public int getNumDiarias() {
		return numDiarias;
	}

	public void setNumDiarias(int numDiarias) {
		this.numDiarias = numDiarias;
	}

	public String getDataDevolucao() {
		return DataUtil.getDataFormatada(dataDevolucao);
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public String getDataMaxDevolucao() {
		return DataUtil.getDataFormatada(dataMaxDevolucao);
	}

	public void setDataMaxDevolucao(LocalDate dataMaxDevolucao) {
		this.dataMaxDevolucao = dataMaxDevolucao;
	}

	public Pessoa getCliente() {
		return cliente;
	}

	public void setCliente(Pessoa cliente) {
		this.cliente = cliente;
	}

	public List<Carro> getCarros() {
		return carros;
	}
	
	public void setCarros(List<Carro> carros) {
		this.carros = carros;
	}

	public void addCarros(Carro carro) {
		this.carros.add(carro);
	}

	public int getNumero() {
		return this.numero;
	}
	
	public void salvarLocacao(String caminho) throws IOException {
		GerenciadorLocacoes.gravar(this, caminho);
	}
	
	public static Locacao getLocacao(String nome, String caminho) throws IOException, ClassNotFoundException {
		Locacao locacao = null; //= GerenciadorLocacoes.ler(nome, caminho);

		return locacao;
	}
}
