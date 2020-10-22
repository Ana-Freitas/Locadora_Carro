package janelasGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import constantes.Constantes;
import entidades.Carro;
import entidades.Locacao;
import entidades.Pessoa;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorPessoas;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import util.DataUtil;
import util.FuncoesUteis;

public class JanelaLocacao extends JInternalFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    private JanelaPrincipal janela;	

    private JPanel panel;
    private JLabel labelDataRealizacao;
    private JTextField fieldDataRealizacao;
    private JLabel labelNumDiaria;
    private JTextField fieldNumDiaria;
    private JLabel labelDataDevolucao;
    private JTextField fieldDataDevolucao;
    private JLabel labelDataMaxDevolucao;
    private JTextField fieldDataMaxDevolucao;

    private JButton buttonLocacao;
    private Locacao locacao; 

    //lista pessoas
    private JComboBox<String> cboClientes;
    private JLabel lblClient;
    //lista carros
    private JLabel lblCarros;
    private JList<String> lstCarros;
	
    public JanelaLocacao(String titulo, JanelaPrincipal janela) {
        super(titulo, true, true, true, true);
        this.janela = janela;
        criarComponentes();
        ajustarPropridadesJanela();
    }
	
    private void criarComponentes() {
        panel = new JPanel();

        labelDataRealizacao = new JLabel("Data de Realização: ");        
        fieldDataRealizacao = new JTextField(7);
        fieldDataRealizacao.setText(DataUtil.getDataFormatada(LocalDate.now()));
        fieldDataRealizacao.setEditable(false);
        
        
        lblClient = new JLabel("Selecione o cliente:");
        
        String[] nomes = this.getPessoas();
        DefaultComboBoxModel model = new DefaultComboBoxModel(nomes);
        cboClientes = new JComboBox<>();
        cboClientes.setModel(model);
        
        
        lblCarros = new JLabel("Escolha os carros:");
        String[] carros = this.getCarros();
        
        lstCarros = new JList(carros);
        lstCarros.setFixedCellHeight(15);
        lstCarros.setFixedCellWidth(100);
        lstCarros.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        labelNumDiaria = new JLabel("Número de Diárias: ");        
        fieldNumDiaria = new JTextField(7);

        NumDiariaListener numDiariaListener = new NumDiariaListener();     
        fieldNumDiaria.addKeyListener(numDiariaListener);

        
        labelDataDevolucao = new JLabel("Data Devolução: ");        
        fieldDataDevolucao = new JTextField(7);
        fieldDataDevolucao.setEditable(false);

        labelDataMaxDevolucao = new JLabel("Data Máx. Devolução: ");        
        fieldDataMaxDevolucao = new JTextField(7);
        fieldDataMaxDevolucao.setEditable(false);

        buttonLocacao = new JButton("Realizar Locação");
        buttonLocacao.addActionListener(this);

        panel.add(lblClient);
        panel.add(cboClientes);
        panel.add(lblCarros);
        panel.add(new JScrollPane(lstCarros));
        panel.add(labelDataRealizacao);
        panel.add(fieldDataRealizacao);
        panel.add(labelNumDiaria);
        panel.add(fieldNumDiaria);
        panel.add(labelDataMaxDevolucao);
        panel.add(fieldDataMaxDevolucao);
        panel.add(labelDataDevolucao);
        panel.add(fieldDataDevolucao);
        panel.add(buttonLocacao);
        
    	add(panel);
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
             nomes = new String[0];
        }
        
        return nomes;
    }
    
    public String[] getCarros(){
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
        } catch(NullPointerException ex){
            nomes = new String[0];
        }
        
        return nomes;
    }
	
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonLocacao) {
            realizarLocacao();
        }
    }
	
    private void realizarLocacao() {
        boolean inseriu = false;   
        Pessoa pessoa = null;
        List<Carro> carros = null;
        try{  
            if(this.validarCampos()){
                carros = this.getCarrosSelecionados();
                if(carros.size() > 0){
                    pessoa = Pessoa.getPessoa(cboClientes.getSelectedItem().toString(), Constantes.CAMINHO_PESSOA);
                    locacao = new Locacao(DataUtil.formatToLocalDate(fieldDataRealizacao.getText()), Integer.parseInt(fieldNumDiaria.getText()), DataUtil.formatToLocalDate(fieldDataMaxDevolucao.getText()), pessoa, carros);
                    pessoa.addLocacao(locacao);
                    GerenciadorPessoas.atualizarPessoa(pessoa, Constantes.CAMINHO_PESSOA);
                    GerenciadorCarros.atualizarCarro(carros, Constantes.CAMINHO_CARRO);
                    locacao.salvarLocacao(Constantes.CAMINHO_LOCACAO);	
                    inseriu = true;
                    JOptionPane.showMessageDialog(null, "Locação feita com sucesso!");
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,ex);
        }
        
        if(inseriu){
        	limparCampos(fieldNumDiaria, fieldDataDevolucao, fieldDataMaxDevolucao);
        	fieldDataRealizacao.requestFocusInWindow();
        }else{
        	JOptionPane.showMessageDialog(this, "Locação não inserida", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
	
    private void limparCampos(JTextField... fields) {
        for(JTextField field : fields) {
            field.setText(null);
        }
    }
    
    private boolean validarCampos(){
        if(this.fieldNumDiaria.getText().trim().isEmpty() || !FuncoesUteis.isNumber(this.fieldNumDiaria.getText().trim())){
            JOptionPane.showMessageDialog(this, "Preencha o número de dias corretamente");
            return false;
        }
        
        return true;
    }
    
    private List<Carro> getCarrosSelecionados(){
        List<String> carrosSel = this.lstCarros.getSelectedValuesList();
        List<Carro> carrosSelecionados = new ArrayList<>();
        try {
            List<Carro> carros = GerenciadorCarros.getCarros(Constantes.CAMINHO_CARRO);
            
            for (Carro carro : carros) {
                for (String item : carrosSel) {
                    if(carro.getModelo().equals(item)){
                        if(carro.isSituacao()){
                            carro.setSituacao(false);
                            carrosSelecionados.add(carro);
                        }else{
                            JOptionPane.showMessageDialog(null, "O carro:" + carro.getModelo() + "já está alocado!");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
        return carrosSelecionados;
    }
	
    public class NumDiariaListener implements KeyListener {
    	@Override
        public void keyReleased(KeyEvent e) {
            if(e.getSource() == fieldNumDiaria) {
                
                if(FuncoesUteis.isNumber(fieldNumDiaria.getText())){
                    if(fieldNumDiaria.getText().isEmpty()) {
                        fieldDataMaxDevolucao.setText("");
                    }else if (Integer.parseInt(fieldNumDiaria.getText()) <= 0){	    			
                        JOptionPane.showMessageDialog(null,"O número de diárias deve ser maior que 0!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }else {	        	
                        String diaMaxDevolucao = DataUtil.calcularDiaMaxDevolucao(Integer.parseInt(fieldNumDiaria.getText()));
                        fieldDataMaxDevolucao.setText(diaMaxDevolucao);
                    }
                }
            }
    	}

        @Override
        public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

        }

        @Override
        public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub

        }
    }
}
