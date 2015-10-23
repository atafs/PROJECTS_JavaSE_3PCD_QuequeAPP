package gui.client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
public class Client extends JFrame {
     
	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private JTextField textoParaEnviar;
	private Socket socket;
	private PrintWriter escritor;
	private JTextArea textoRecebido;
	private Scanner leitor;
	
	//CONSTRUCTOR
	public Client(String nome) {
		super("Chat: " + nome);
		this.nome = nome;
		
    	//TABS
        setTitle("QuequeAPP");
        JTabbedPane jtp = new JTabbedPane();
        getContentPane().add(jtp);
        
        //PANEL1
        JPanel envio = new JPanel();
        JLabel label1 = new JLabel();
        label1.setText("MY CONTACTS");  
        envio.add(label1);
        
        //PANEL2
        JPanel jp2 = new JPanel();
        
        //fonte e botoes/texto
		Font fonte = new Font("Serif", Font.BOLD, 20);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setSize(200, 200);
		textoParaEnviar.setFont(fonte);
		JButton botao = new JButton("Enviar");
		botao.setFont(fonte);
		botao.addActionListener(new EnviarListener());
		
		//area de recepcao de mensagens
		textoRecebido = new JTextArea();
		textoRecebido.setFont(fonte);
		JScrollPane scroll = new JScrollPane(textoRecebido);
		
		//PANEL3
		JPanel jp3 = new JPanel();
		jp3.setLayout(new GridLayout(0,2));
		jp3.add(textoParaEnviar);
		jp3.add(botao);
		
		jp2.setLayout(new BorderLayout());
		jp2.add(BorderLayout.SOUTH, jp3);
		jp2.add(BorderLayout.CENTER, scroll);
        
        //ADD PANELS TO TABS
		jtp.addTab("CHAT", jp2);
        jtp.addTab("CONTACTS", envio);
      
  		//configuracao de rede
  		try {
			configurarRede();
		} catch (Exception e) {
			e.printStackTrace();
		}
  		
    	setSize(700, 500);
    	setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
         
    }
	
	//MAIN
    public static void main(String[] args) {   	
    	//INITIALIZE
    	new Client("Americo");
    	new Client("Tomas");
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
			socket = new Socket("127.0.0.2", 6002);
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
}