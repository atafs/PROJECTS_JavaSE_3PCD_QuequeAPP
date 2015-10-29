package gui.backup;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GUI_tabs_v0_0 extends JFrame{
	
	JTextField textoParaEnviar;
	Socket socket;
	PrintWriter escritor;
	String nome;
	JTextArea textoRecebido;
	Scanner leitor;
	
	//interface grafica GUI
	public GUI_tabs_v0_0(String nome) throws Exception {
		super("Chat: " + nome);
		this.nome = nome;
		
		//fonte e botoes/texto
		Font fonte = new Font("Serif", Font.BOLD, 20);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setFont(fonte);
		JButton botao = new JButton("Enviar");
		botao.setFont(fonte);
		botao.addActionListener(new EnviarListener());
		
		//JPanel
		Container envio = new JPanel();
		envio.setLayout(new BorderLayout());
		envio.add(BorderLayout.CENTER, textoParaEnviar);
		envio.add(BorderLayout.EAST, botao);
		
		//area de recepcao de mensagens
		textoRecebido = new JTextArea();
		textoRecebido.setFont(fonte);
		JScrollPane scroll = new JScrollPane(textoRecebido);
		
		//container principal
		getContentPane().add(BorderLayout.CENTER, scroll);
		getContentPane().add(BorderLayout.SOUTH, envio);

		//configuracao de rede
		configurarRede();
		
		//instrucoes finais da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLocationRelativeTo(null); 
		setVisible(true);
		
		//System.out.println("Executei todas as instrucoes");
	}
	
	//listener
	private class EnviarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			escritor.println(nome + " -> " + textoParaEnviar.getText());
			escritor.flush();//garantir que foi enviado
			textoParaEnviar.setText("");//limpar campo de texto
			textoParaEnviar.requestFocus();//colocar cursor dentro do campo
		}
	}

	//ligacao com o servidor
	private void configurarRede() throws Exception {
	
		try {
			socket = new Socket("127.0.0.1", 6002);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new EscutaServidor()).start();
		} catch (Exception e) {}
	}
		
	//class de escuta do servidor
	private class EscutaServidor implements Runnable {

		@Override
		public void run() {
			try {
				String texto;
				while((texto = leitor.nextLine()) != null) {
					//adiciona no final de todo o texto o novo texto
					textoRecebido.append(texto + "\n");
				}
			} catch(Exception x) {}
		}
		
	}
	
	//main
	public static void main(String[] args) throws Exception {
		new GUI_tabs_v0_0("Americo");
		new GUI_tabs_v0_0("Tomas");
		new GUI_tabs_v0_0("Ana");
	}
}
