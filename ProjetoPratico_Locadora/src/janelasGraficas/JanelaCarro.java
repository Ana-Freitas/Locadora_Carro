package janelasGraficas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import constantes.Constantes;
import entidades.Carro;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorPessoas;

public class JanelaCarro extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JanelaPrincipal janela;
	
	private JPanel panel;
	private JLabel labelMarca;
	private JTextField fieldMarca;		
	private JLabel labelModelo;
	private JTextField fieldModelo;
	private JLabel labelAno;
	private JTextField fieldAno;
	private JLabel labelPlaca;
	private JTextField fieldPlaca;
	private JLabel labelQtdPortas;
	private JTextField fieldQtdPortas;
	private JLabel labelValorDia;
	private JTextField fieldValorDia;
	private JLabel labelEstado;
	private JTextField fieldEstado;
	
	private JRadioButton radioArCond;
	
	private JRadioButton radioDisponivel;
    private JRadioButton radioAlocado;
    private ButtonGroup group;
	
    private JButton buttonInserir;
	
	public JanelaCarro(String titulo, JanelaPrincipal janela) {
		super(titulo, true, true, true, true);
		this.janela = janela;
		criarComponentes();
		ajustarPropridadesJanela();
	}
	
	private void criarComponentes() {
		panel = new JPanel();
		
        labelMarca = new JLabel("Marca: ");  
        fieldMarca = new JTextField(10);        
        
        labelModelo = new JLabel("Modelo: "); 
        fieldModelo = new JTextField(10);
        
        labelAno = new JLabel("Ano: ");        
        fieldAno = new JTextField(4);
        
        labelPlaca = new JLabel("Placa: ");        
        fieldPlaca = new JTextField(10);
        
        labelQtdPortas = new JLabel("Quantidade de portas: ");        
        fieldQtdPortas = new JTextField(10);
        
        labelValorDia = new JLabel("Valor Diária: ");        
        fieldValorDia = new JTextField(10);
        
        labelEstado = new JLabel("Estado: ");        
        fieldEstado = new JTextField(10);
        
        radioArCond = new JRadioButton("Possui Ar Condicionado");
        radioArCond.setSelected(false);
        
        radioDisponivel = new JRadioButton("Disponível");
        radioDisponivel.setSelected(true);

        radioAlocado = new JRadioButton("Alocado");
        radioAlocado.setSelected(false);

        group = new ButtonGroup();
        group.add(radioDisponivel);
        group.add(radioAlocado);
        
        buttonInserir = new JButton("Cadastrar Carro");
        buttonInserir.addActionListener(this);
        
        panel.add(labelMarca);
        panel.add(fieldMarca);
        panel.add(labelModelo);
        panel.add(fieldModelo);
        panel.add(labelAno);
        panel.add(fieldAno);
        panel.add(labelPlaca);
        panel.add(fieldPlaca);
        panel.add(labelQtdPortas);
        panel.add(fieldQtdPortas);
        panel.add(radioArCond);
        panel.add(labelValorDia);
        panel.add(fieldValorDia);
        panel.add(labelEstado);
        panel.add(fieldEstado);
        panel.add(radioDisponivel);
        panel.add(radioAlocado);    	
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
		// TODO Auto-generated method stub
		if(event.getSource() == buttonInserir) {
			inserirCarro();
        }
	}
	
	private void inserirCarro() {
        boolean inseriu = false;        
        try{       
			if(GerenciadorCarros.existe(fieldPlaca.getText(), Constantes.CAMINHO_CARRO)) {
				Carro carro = new Carro(fieldMarca.getText(), fieldModelo.getText(), Integer.parseInt(fieldAno.getText()), fieldPlaca.getText(), Short.parseShort(fieldQtdPortas.getText()), radioArCond.isSelected(), Double.parseDouble(fieldValorDia.getText()), fieldEstado.getText(), radioDisponivel.isSelected());
				carro.salvarCarro(Constantes.CAMINHO_CARRO);	
				inseriu = true;
			}else {
				JOptionPane.showMessageDialog(this, "Cliente já está cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
			}	
		}catch (Exception e){
			JOptionPane.showMessageDialog(this, "Algum campo está vazio, por favor preencher todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
		}
        
        if(inseriu){
        	limparCampos(fieldMarca, fieldModelo, fieldAno, fieldPlaca, fieldQtdPortas, fieldValorDia, fieldEstado);
        	fieldMarca.requestFocusInWindow();
        }else{
        	JOptionPane.showMessageDialog(this, "Carro não inserido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void limparCampos(JTextField... fields) {
        for(JTextField field : fields) {
            field.setText(null);
            radioArCond.setSelected(false);
            radioDisponivel.setSelected(true);
        }
    }
}
