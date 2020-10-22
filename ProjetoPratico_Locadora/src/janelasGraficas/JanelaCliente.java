package janelasGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import constantes.Constantes;
import entidades.Fisica;
import entidades.Juridica;
import entidades.Locacao;
import entidades.Pessoa;
import java.util.ArrayList;

public class JanelaCliente extends JInternalFrame implements ActionListener{

    private static final long serialVersionUID = 1L;

    private JanelaPrincipal janela;

    private JPanel panel;
    private JLabel labelNome;
    private JTextField fieldNome;		
    private JLabel labelCPF;
    private JTextField fieldCPF;
    private JLabel labelCNPJ;
    private JTextField fieldCNPJ;
    private JLabel labelRazaoSocial;
    private JTextField fieldRazaoSocial;

    private JRadioButton radioCPF;
    private JRadioButton radioCNPJ;
    private ButtonGroup group;
    
    private JButton buttonInserir;
	
    public JanelaCliente(String titulo, JanelaPrincipal janela) {
        super(titulo, true, true, true, true);
        this.janela = janela;
        criarComponentes();
        ajustarPropridadesJanela();
    }
	
    private void criarComponentes() {
        panel = new JPanel();
		
        labelNome = new JLabel("Nome: ");        
        fieldNome = new JTextField(25);
        
        radioCPF = new JRadioButton("Pessoa Física");
        radioCPF.setSelected(true);

        radioCNPJ = new JRadioButton("Pessoa Jurídica");
        radioCNPJ.setSelected(false);
        
        group = new ButtonGroup();
        group.add(radioCPF);
        group.add(radioCNPJ);
        
        labelCPF = new JLabel("CPF: ");    
        labelCNPJ = new JLabel("CNPJ: ");  
        
        try {
            MaskFormatter mascara_cpf = new MaskFormatter("###.###.###-##");   

            fieldCPF = new javax.swing.JFormattedTextField(mascara_cpf);
            fieldCPF.setSize(2,5);
            
            MaskFormatter mascara_cnpj = new MaskFormatter("###.###.###/####-##");   

            fieldCNPJ = new javax.swing.JFormattedTextField(mascara_cnpj);
            fieldCNPJ.setSize(6,10);
        }
        catch (Exception error_mask) {
        	JOptionPane.showMessageDialog(null,"Erro: " + error_mask);
        }
        
        labelRazaoSocial = new JLabel("Razão Social: ");        
        fieldRazaoSocial = new JTextField(25);
        
        labelCNPJ.setVisible(false);
        fieldCNPJ.setVisible(false);
        labelRazaoSocial.setVisible(false);
        fieldRazaoSocial.setVisible(false);
        
        buttonInserir = new JButton("Cadastrar Cliente");
        buttonInserir.addActionListener(this);
        
        RadioListener radioListener = new RadioListener();        
        radioCPF.addActionListener(radioListener);
        radioCNPJ.addActionListener(radioListener);
        
    	panel.add(labelNome);
    	panel.add(fieldNome);
    	panel.add(radioCPF);
    	panel.add(radioCNPJ);
    	panel.add(labelCPF);
        panel.add(fieldCPF);
        panel.add(labelCNPJ);
        panel.add(fieldCNPJ);
        panel.add(labelRazaoSocial);
        panel.add(fieldRazaoSocial);
    	
    	panel.add(buttonInserir);

    	add(panel);
    }
	
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == buttonInserir) {
                inserirCliente();
        }
    }
	
    private void inserirCliente() {
    	String nome, cpfcnpj, razaoSocial;
    	boolean opcao = radioCPF.isSelected();
    	List<Locacao> locacoes;
        boolean inseriu = false;
        Pessoa pessoa;
        
        try{        	
            nome = fieldNome.getText();
            locacoes = new ArrayList<>(); //ver as locacoes

            cpfcnpj = opcao? fieldCPF.getText() : fieldCNPJ.getText();

            if(opcao) {	
                cpfcnpj = fieldCPF.getText();
                pessoa = new Fisica(nome, locacoes, cpfcnpj);
            }else {
                cpfcnpj = fieldCNPJ.getText();
                razaoSocial = fieldRazaoSocial.getText();
                pessoa = new Juridica(nome, locacoes, cpfcnpj, razaoSocial);
            }			

            if(pessoa.salvarPessoa(Constantes.CAMINHO_PESSOA)){
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                inseriu = true;
            }else {
                JOptionPane.showMessageDialog(this, "Cliente já está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
			
            }catch (Exception e){
                JOptionPane.showMessageDialog(this, "Algum campo está vazio, por favor preencher todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        
        if(inseriu){
            limparCampos(fieldNome, fieldCPF, fieldCNPJ, fieldRazaoSocial);
            fieldNome.requestFocusInWindow();
        }else{
            JOptionPane.showMessageDialog(this, "Cliente não inserido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
	
    private void limparCampos(JTextField... fields) {
        for(JTextField field : fields) {
            field.setText(null);
            radioCPF.setSelected(true);
        }
    }
	
    public class RadioListener implements ActionListener {
    	@Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == radioCPF) {
                labelCPF.setVisible(true);
                fieldCPF.setVisible(true);
                labelCNPJ.setVisible(false);
                fieldCNPJ.setVisible(false);
                labelRazaoSocial.setVisible(false);
                fieldRazaoSocial.setVisible(false);
            }else {
                labelCPF.setVisible(false);
                fieldCPF.setVisible(false);
                labelCNPJ.setVisible(true);
                fieldCNPJ.setVisible(true);
                labelRazaoSocial.setVisible(true);
                fieldRazaoSocial.setVisible(true);
            }            
        }
    }
}
