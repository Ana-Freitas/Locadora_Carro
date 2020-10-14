package janelasGraficas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class JanelaPrincipal extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JMenuBar menuBar;
	private JMenu menu;
	
	private JMenuItem itemInserirCliente;	
	private JMenuItem itemInserirCarro;	
	private JMenuItem itemRealizarLocacao;	
	private JMenuItem itemRealizarDevolucao;	
	private JMenuItem itemSair;
	
	private JDesktopPane desktop;
	
	private JanelaCliente janelaCliente;
	private JanelaCarro janelaCarro;
	private JanelaLocacao janelaLocacao;
	private JanelaDevolucao janelaDevolucao;
	
	public JanelaPrincipal(String titulo) {
        super(titulo);
        criarComponentes();
        ajustarPropriedadesJanela();
    }
	
	private void criarComponentes() {
		menuBar = new JMenuBar();
    	
    	menu = new JMenu("Menu");
    	
    	itemInserirCliente = new JMenuItem("Cadastrar Cliente");
    	itemInserirCliente.addActionListener(this);

    	itemInserirCarro = new JMenuItem("Cadastrar Carro");
    	itemInserirCarro.addActionListener(this);
    	
    	itemRealizarLocacao = new JMenuItem("Realizar Locação");
    	itemRealizarLocacao.addActionListener(this);
    	
    	itemRealizarDevolucao = new JMenuItem("Realizar Devolução");
    	itemRealizarDevolucao.addActionListener(this);
    	
    	itemSair = new JMenuItem("Sair");
    	itemSair.addActionListener(this);
    	
    	menu.add(itemInserirCliente);    	
    	menu.add(itemInserirCarro);    
    	menu.add(itemRealizarLocacao);    
    	menu.add(itemRealizarDevolucao);    
    	menu.addSeparator();
    	menu.add(itemSair);   	
    	
    	menuBar.add(menu);
    	setJMenuBar(menuBar);
    	
    	desktop = new JDesktopPane();
    	desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    	add(desktop);    	
	}
	
	 private void ajustarPropriedadesJanela() {
            setVisible(true);
            setSize(600, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 }
	 
	 private void addJanelaInterna(JInternalFrame janelaInterna) {
            desktop.add(janelaInterna);
            desktop.moveToFront(janelaInterna);

            janelaInterna.addInternalFrameListener(new OuvinteJanelaInterna());
            janelaInterna.requestFocusInWindow();

            try { 
                janelaInterna.setSelected(true); 
            } catch(PropertyVetoException erro) {
                System.err.println("Erro: " + erro.getMessage());
            }   	
	}
	 
	 @Override
	    public void actionPerformed(ActionEvent e) {
	    	JMenuItem itemClicado = (JMenuItem)e.getSource();
	    	if(itemClicado == itemInserirCliente) {
	    		if(janelaCliente == null) {
	    			janelaCliente = new JanelaCliente("Cadastrar Cliente", this);
	    			addJanelaInterna(janelaCliente);
	    		}
			}else if(itemClicado == itemInserirCarro){
                            if(janelaCarro == null) {
                                janelaCarro = new JanelaCarro("Cadastrar Carro", this);
                                addJanelaInterna(janelaCarro); 
                            }
			}else if(itemClicado == itemRealizarLocacao){
				if(janelaLocacao == null) {
					janelaLocacao = new JanelaLocacao("Realizar Locação", this);
					addJanelaInterna(janelaLocacao);    
				}
	    	}else if(itemClicado == itemRealizarDevolucao){
	    		if(janelaDevolucao == null) {
	    			janelaDevolucao = new JanelaDevolucao("Realizar Devolu��o", this);
	    			addJanelaInterna(janelaDevolucao);  
	    		}
	    	}else {
	    		System.exit(0);
	    	}
	    }
	 
	 private class OuvinteJanelaInterna extends InternalFrameAdapter {
	    	
	    	@Override
	    	public void internalFrameClosed(InternalFrameEvent e) {
	    		if(e.getInternalFrame() == janelaCliente) {
	    			desktop.remove(janelaCliente);
	    			janelaCliente = null;
	    		}
	    		else if(e.getInternalFrame() == janelaCarro) {
	    			desktop.remove(janelaCarro);
	    			janelaCarro = null;
	    		}
	    		else if(e.getInternalFrame() == janelaLocacao) {
	    			desktop.remove(janelaLocacao);
	    			janelaLocacao = null;
	    		}
	    		else {
	    			desktop.remove(janelaDevolucao);
	    			janelaDevolucao = null;
	    		}
	    	}
	    }
}
