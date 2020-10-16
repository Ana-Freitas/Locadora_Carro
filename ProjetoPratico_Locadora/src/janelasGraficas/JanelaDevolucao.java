package janelasGraficas;

import constantes.Constantes;
import entidades.Carro;
import entidades.Locacao;
import entidades.Pessoa;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorPessoas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class JanelaDevolucao extends JInternalFrame implements ActionListener{
	
    private static final long serialVersionUID = 1L;

    private JanelaPrincipal janela;

    private JPanel panel;
    private JLabel labelValorTotal;
    private JTextField fieldValorTotal;		
    private JLabel labelEstadoFinal;
    private JTextField fieldEstadoFinal;	

    private JButton buttonDevolucao;
    
    //lista pessoas
    private JComboBox<String> cboClientes;
    private JLabel lblClient;
    //lista carros
    private JLabel lblCarros;
    private JList<String> lstCarros;
	
    public JanelaDevolucao(String titulo, JanelaPrincipal janela) {
        super(titulo, true, true, true, true);
        this.janela = janela;
        criarComponentes();
        ajustarPropridadesJanela();
    }
	
    private void criarComponentes() {
        panel = new JPanel();

        labelValorTotal = new JLabel("Valor total da locação: ");        
        fieldValorTotal = new JTextField(10);
        labelEstadoFinal = new JLabel("Estado:");        
        fieldEstadoFinal = new JTextField(10);
        buttonDevolucao = new JButton("Realizar Devolução");
        buttonDevolucao.addActionListener(this);
        
        lblClient = new JLabel("Selecione o cliente:");
        
        String[] nomes = this.getPessoasComLocacoes();
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomes);
        cboClientes = new JComboBox<>();
        cboClientes.setModel(model);
        
        
        lblCarros = new JLabel("Escolha os carros:");
        String[] carros = this.getCarrosLocados();
        
        lstCarros = new JList(carros);
        lstCarros.setFixedCellHeight(15);
        lstCarros.setFixedCellWidth(100);
        lstCarros.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        panel.add(labelValorTotal);
        panel.add(fieldValorTotal);
        panel.add(labelEstadoFinal);
        panel.add(fieldEstadoFinal);
        panel.add(buttonDevolucao);
        
    	add(panel);
    }
	
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == buttonDevolucao) {
                realizarDevolucao();
        }
    }
	
    private void realizarDevolucao() {

    }
    
    /*private String[] getClientes(String cliente){
        try{
            List<Pessoa> pessoas;
            
            Locacao loc;

            for(int i = 0; i<p.getLocacoes().size(); i++){
                loc = p.getLocacoes().get(i);
                locacoes[i] = loc.getNumero() + "-" + loc.getDataRealizacao();
            } 
            
            return locacoes;
        } catch (IOException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }*/
    
    private String[] getLocacoes(String cliente){
        try{
            Pessoa p = GerenciadorPessoas.ler(cliente, Constantes.CAMINHO_PESSOA);
            String[] locacoes = new String[p.getLocacoes().size()];
            Locacao loc;

            for(int i = 0; i<p.getLocacoes().size(); i++){
                loc= p.getLocacoes().get(i);
                locacoes[i] = loc.getNumero() + "-" + loc.getDataRealizacao();
            } 
            
            return locacoes;
        } catch (IOException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    /*private List<Carro> getCarrosCliente(String cliente){
        try {
            Pessoa p = GerenciadorPessoas.ler(cliente, Constantes.CAMINHO_PESSOA);
            List<Carro> carrosDoCliente = p.getLocacoes()
            
            for (Carro c  :carros) {
                if()
            }
            
        } catch (IOException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JanelaDevolucao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    */
}
