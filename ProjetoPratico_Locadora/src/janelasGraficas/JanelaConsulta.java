/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package janelasGraficas;

import constantes.Constantes;
import entidades.Carro;
import entidades.Pessoa;
import entidades.Fisica;
import entidades.Juridica;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorPessoas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Anevnessa
 */
public class JanelaConsulta extends JInternalFrame{

    public static enum TipoConsulta{
        Cliente,
        Carro,
        Locacao,
        Devolucao
    }
    
    private TipoConsulta tipo;
    private JanelaPrincipal janela;
    
     private JPanel panel;
     private JLabel labelNome;
     private JTextField fieldNome;
     private JTable tableResult;
     private DefaultTableModel modelo;
     
    public TipoConsulta getTipo(){
        return this.tipo;
    } 
    public JanelaConsulta(String titulo, JanelaPrincipal janela, TipoConsulta tipo) {
        super(titulo, true, true, true, true);
        this.tipo = tipo;
        this.janela = janela;
        criarComponentes();
        ajustarPropridadesJanela();
    }
    
    public void criarComponentes(){
        panel = new JPanel();

        modelo = new DefaultTableModel();
        this.criarModelo();
        
        tableResult = new JTable(modelo);
        JScrollPane barraRolagem = new JScrollPane();
        barraRolagem.setViewportView(tableResult);
        
        labelNome = new JLabel("Consultar " + this.tipo.name() + ":");        
        fieldNome = new JTextField(25);
        
        fieldNome.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String text = textField.getText();
                if(!text.trim().isEmpty()){
                    atualizarTabela(text);
                }else{
                    getDadosModel();
                }
            }
    });
    	panel.add(labelNome);
    	panel.add(fieldNome);
    	panel.add(barraRolagem);
        
        add(panel);
        this.getDadosModel();
    }
    
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void criarModelo(){
        if(this.tipo == TipoConsulta.Cliente){
            this.modelo.addColumn("Código");
            this.modelo.addColumn("Cliente");
            this.modelo.addColumn("CPF/CNPJ");
        }else if(this.tipo == TipoConsulta.Carro){
            this.modelo.addColumn("Código");
            this.modelo.addColumn("Marca");
            this.modelo.addColumn("Modelo");
            this.modelo.addColumn("Placa");
            this.modelo.addColumn("Valor diária");
            this.modelo.addColumn("Disponível");
        }
    }
    
    private void atualizarTabela(String texto){
        Object[] row = new Object[this.modelo.getColumnCount()];
        if(this.tipo == TipoConsulta.Cliente){
            this.modelo.setNumRows(0);
            List<Pessoa> pessoas;
            try {
                pessoas = GerenciadorPessoas.getPessoas(Constantes.CAMINHO_PESSOA);
                Pessoa p;
                for(int i=0; i<pessoas.size();i++){
                     p = pessoas.get(i);
                    if(p.getNome().toLowerCase().contains(texto.toLowerCase())){
                        row[0] = p.getCodigo();
                        row[1] = p.getNome();
                        row[2] = p.getTipo() == Pessoa.enmTipo.FISICO ? ((Fisica) p).getCpf() :  ((Juridica) p).getCnpj();
                        modelo.addRow(row);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
            }
        }else if(this.tipo == TipoConsulta.Carro){
            this.modelo.setNumRows(0);
            List<Carro> carros;
            try {
                carros = GerenciadorCarros.getCarros(Constantes.CAMINHO_CARRO);
                Carro c;
                for(int i=0; i<carros.size();i++){
                     c = carros.get(i);
                    if(c.getModelo().toLowerCase().contains(texto.toLowerCase())){
                        row[0] = c.getCodigo();
                        row[1] = c.getMarca();
                        row[2] = c.getModelo();
                        row[3] = c.getPlaca();
                        row[4] = c.getValorDiaria();
                        row[5] = c.isSituacao()? "Disponível" : "Alocado";
                        modelo.addRow(row);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
            }
        }
    }
    
    private void getDadosModel(){
        this.modelo.setNumRows(0);
        Object[] row = new Object[this.modelo.getColumnCount()];
        if(this.tipo == TipoConsulta.Cliente){
            try {
                 List<Pessoa> pessoas = GerenciadorPessoas.getPessoas(Constantes.CAMINHO_PESSOA);
                 Pessoa p;
                 for(int i=0; i<pessoas.size();i++){
                     p = pessoas.get(i);
                     row[0] = p.getCodigo();
                     row[1] = p.getNome();
                     row[2] = p.getTipo() == Pessoa.enmTipo.FISICO ? ((Fisica) p).getCpf() :  ((Juridica) p).getCnpj();
                     modelo.addRow(row);
                 }
             } catch (IOException ex) {
                 JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
             } catch (ClassNotFoundException ex) {
                 JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
             }
         } else if(this.tipo == TipoConsulta.Carro){
              try {
                 List<Carro> carros = GerenciadorCarros.getCarros(Constantes.CAMINHO_CARRO);
                 Carro c;
                 for(int i=0; i<carros.size();i++){
                     c = carros.get(i);
                     row[0] = c.getCodigo();
                     row[1] = c.getMarca();
                     row[2] = c.getModelo();
                     row[3] = c.getPlaca();
                     row[4] = c.getValorDiaria();
                     row[5] = c.isSituacao()? "Disponível" : "Alocado";
                     modelo.addRow(row);
                 }
             } catch (IOException ex) {
                 JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
             } catch (ClassNotFoundException ex) {
                 JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
             }
         }
    }
    
    
}
