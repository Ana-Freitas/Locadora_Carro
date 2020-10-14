package janelasGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JanelaDevolucao extends JInternalFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;

	private JanelaPrincipal janela;
	
	private JPanel panel;
	private JLabel labelValorTotal;
	private JTextField fieldValorTotal;		
	private JLabel labelEstadoFinal;
	private JTextField fieldEstadoFinal;	
	
	private JButton buttonDevolucao;
	
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
}
