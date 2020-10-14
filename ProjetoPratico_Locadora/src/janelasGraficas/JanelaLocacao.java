package janelasGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import entidades.Fisica;
import entidades.Juridica;
import entidades.Locacao;
import entidades.Pessoa;
import gerenciadorArquivos.GerenciadorCarros;
import gerenciadorArquivos.GerenciadorLocacoes;
import gerenciadorArquivos.GerenciadorPessoas;
import janelasGraficas.JanelaCliente.RadioListener;
import util.DataUtil;

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
    //lista carros
	
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
	
    private void ajustarPropridadesJanela() {
        setVisible(true);
        setSize(500,300);
        setLocation(45, 20);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
	

	@Override
	public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if(e.getSource() == buttonLocacao) {
                realizarLocacao();
            }
	}
	
	private void realizarLocacao() {
            boolean inseriu = false;   
            Pessoa pessoa = null;
            List<Carro> carros = null;
        try{        
            //if(GerenciadorCarros.existe(fieldPlaca.getText(), Constantes.CAMINHO_LOCACAO)) {
            locacao = new Locacao(LocalDate.parse(fieldDataRealizacao.getText()), Integer.parseInt(fieldNumDiaria.getText()), LocalDate.parse(fieldDataMaxDevolucao.getText()), pessoa, carros);
            locacao.salvarLocacao(Constantes.CAMINHO_LOCACAO);	
                    inseriu = true;
            //}else {
            //	JOptionPane.showMessageDialog(this, "Loca��o j� existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            //}	
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Algum campo est� vazio, por favor preencher todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        if(inseriu){
        	limparCampos(fieldNumDiaria, fieldDataDevolucao, fieldDataMaxDevolucao);
        	fieldDataRealizacao.requestFocusInWindow();
        }else{
        	JOptionPane.showMessageDialog(this, "Loca��o n�o inserida", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	private void limparCampos(JTextField... fields) {
        for(JTextField field : fields) {
            field.setText(null);
        }
    }
	
	public class NumDiariaListener implements KeyListener {
    	@Override
        public void keyReleased(KeyEvent e) {
            if(e.getSource() == fieldNumDiaria) {
                    if(fieldNumDiaria.getText().isEmpty()) {
                            fieldDataMaxDevolucao.setText("");
                    }else if (Integer.parseInt(fieldNumDiaria.getText()) <= 0){	    			
                        JOptionPane.showMessageDialog(null,"O n�mero de di�rias deve ser maior que 0!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }else {	        	
                            String diaMaxDevolucao = DataUtil.calcularDiaMaxDevolucao(Integer.parseInt(fieldNumDiaria.getText()));
                            fieldDataMaxDevolucao.setText(diaMaxDevolucao);
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
