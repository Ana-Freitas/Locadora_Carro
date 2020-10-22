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
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JanelaDevolucao extends JInternalFrame implements ActionListener, ListSelectionListener{
	
    private static final long serialVersionUID = 1L;

    private JanelaPrincipal janela;

    private JPanel panel;
    private JLabel labelValorTotal;
    private JTextField fieldValorTotal;		
    private JButton buttonDevolucao;
    
    //locacoes
    private JComboBox<String> cboLocacoes;

    
    //lista pessoas
    private JComboBox<String> cboClientes;
    private JLabel lblClient;
    //lista carros
    private JLabel lblCarros;
    private JList<String> lstCarros;
    List<Carro> carros;
    
	
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
        buttonDevolucao = new JButton("Realizar Devolução");
        buttonDevolucao.addActionListener(this);

        lblClient = new JLabel("Selecione o cliente:");
        
        String[] nomes = this.getPessoas();
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomes);
        cboClientes = new JComboBox<>();
        cboClientes.setModel(model);
        
        cboClientes.addActionListener(this);
        
        lblCarros = new JLabel("Escolha os carros:");
        lstCarros = new JList();
        lstCarros.setFixedCellHeight(15);
        lstCarros.setFixedCellWidth(100);
        lstCarros.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstCarros.addListSelectionListener(this);
        
        panel.add(lblClient);
        panel.add(cboClientes);
        panel.add(lblCarros);
        panel.add(new JScrollPane(lstCarros));
        panel.add(labelValorTotal);
        panel.add(fieldValorTotal);
        panel.add(buttonDevolucao);
        
    	add(panel);
        
        this.getCarrosLocacoes(cboClientes.getSelectedItem().toString());
    }
	
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonDevolucao) {
                realizarDevolucao();
                this.cboClientes.setSelectedIndex(0);
                this.fieldValorTotal.setText("");
                this.getCarrosLocacoes(this.cboClientes.getSelectedItem().toString());
        }else if(e.getSource() == cboClientes){
            String cliente = cboClientes.getSelectedItem().toString();
            this.getCarrosLocacoes(cliente);
        }
    }
	
    private void realizarDevolucao() {
        try {
            GerenciadorCarros.atualizarCarro(carros, Constantes.CAMINHO_CARRO);
            JOptionPane.showMessageDialog(null, "Devolvido com sucesso!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
        }
    }
    
    public String[] getCarros(String cliente){
        List<Carro> carros;
        String[] nomes = null;  
        try {
            carros = GerenciadorCarros.getCarros(Constantes.CAMINHO_CARRO);
            nomes = new String[carros.size()];
            
            for (int i = 0; i < carros.size(); i++) {
                nomes[i] = carros.get(i).getModelo();
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return nomes;
    }
    
    private void getCarrosLocacoes(String cliente){
        try{
            Pessoa p = GerenciadorPessoas.ler(cliente, Constantes.CAMINHO_PESSOA);
            List<Locacao> loc = p.getLocacoes();
            String car = "";
            Locacao lo;
            
            if(loc.size() > 0){
                for (int i = 0; i < loc.size(); i++) {
                    lo = loc.get(i);
                    for (int j = 0; j < lo.getCarros().size(); j++) {
                        if(!lo.getCarros().get(j).isSituacao()){
                            car = car + lo.getCarros().get(j).getModelo() + "\n";
                        }
                    }
                }
            }
           
            DefaultComboBoxModel models = new DefaultComboBoxModel(car.split("\n"));
             lstCarros.setModel(models);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
        }
    }
    
    public String[] getPessoas(){
        List<Pessoa> clientes;
        String[] nomes = null;  
        try {
            clientes = GerenciadorPessoas.getPessoas(Constantes.CAMINHO_PESSOA);
            nomes = new String[clientes.size()];
            
            for (int i = 0; i < clientes.size(); i++) {
                nomes[i] = clientes.get(i).getNome();
            }
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (NullPointerException ex){
             nomes = new String[10];
        }
        
        return nomes;
    }
    
    private void getValorTotal(String cliente){
         try{
            List<String> selCar = this.lstCarros.getSelectedValuesList();
            Pessoa p = GerenciadorPessoas.ler(cliente, Constantes.CAMINHO_PESSOA);
            List<Locacao> loc = p.getLocacoes();
            String car = "";
            Locacao lo;
            carros = new ArrayList<>();
            double valor=0;
            
            if(loc.size() > 0){
                for (int i = 0; i < loc.size(); i++) {
                    lo = loc.get(i);
                    for (Carro carro : lo.getCarros()) {
                        for (String ca: selCar) {
                            if(ca.equals(carro.getModelo())){
                                carro.setSituacao(true);
                                valor += carro.getValorDiaria()*lo.getNumDiarias();
                            }
                        }
                        carros.add(carro);
                    }
                }
            }
            
            this.fieldValorTotal.setText(Double.toString(valor));
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(this, "Ocorreu um erro ao acessar o arquivo. Erro:" + ex);
        } catch (ClassNotFoundException ex) {
           JOptionPane.showMessageDialog(this, "Não foi possível encontrar a classe. Erro:" + ex);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(e.getSource() == lstCarros){
            String cliente = cboClientes.getSelectedItem().toString();
            this.getValorTotal(cliente);
        }
    }

}
