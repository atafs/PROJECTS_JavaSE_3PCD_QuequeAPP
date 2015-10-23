package gui.swing.icon_and_text.old;

import gui.listener.Send;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BoundedRangeModel;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
public class Client extends JFrame {
     
	//ATTRIBUTES
	private static final long serialVersionUID = 1L;
	
	private String nomeClient;
	private JTextField textoParaEnviar;
	private Socket socket;
	private PrintWriter escritor;
	private JTextArea textoRecebido;
	private Scanner leitor;
	
	//CONSTRUCTOR
	public Client(String nomeClient) {
		super("Chat: " + nomeClient);
		this.nomeClient = nomeClient;
		
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
        
        //TEXTFIELD COMPONENT
		Font fonteTextfield = new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT, 20);
		textoParaEnviar = new JTextField();
		textoParaEnviar.setBackground(Color.BLUE);
		textoParaEnviar.setForeground(Color.WHITE);
		textoParaEnviar.setSize(200, 200);
		textoParaEnviar.setFont(fonteTextfield);
		
        //BUTTON COMPONENT
		JButton send = new JButton("Send");
		Font fonteButton = new Font("Serif", Font.BOLD, 30);
		send.setBackground(Color.WHITE);
		send.setForeground(Color.GREEN);
		send.setFont(fonteButton);
		  try {
		    Image img = ImageIO.read(getClass().getResource("/gui/listener/img/send.jpg"));
		    send.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		
		//TEXTAREA COMPONENT
		textoRecebido = new JTextArea();
		textoRecebido.setFont(fonteTextfield);
		JScrollPane scroll = new JScrollPane(textoRecebido);
		
  		//DISTRIBUITON
  		try {
			configurarRede();
		} catch (Exception e) {
			e.printStackTrace();
		}
  		
  		//LISTENER
		Send sendListener = new Send(send, textoParaEnviar, escritor, nomeClient);
		send.addActionListener(sendListener);
		textoParaEnviar.addKeyListener(sendListener);
		
		//PANEL3
		JPanel jp3 = new JPanel();
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		jp3.setLayout(new BoxLayout(jp3, BoxLayout.X_AXIS));

	    BoundedRangeModel brm = textoParaEnviar.getHorizontalVisibility();
	    scrollBar.setModel(brm);
	    jp3.add(textoParaEnviar);
	    jp3.add(send);
	    jp3.add(scrollBar);

		jp2.setLayout(new BorderLayout());
		jp2.add(BorderLayout.SOUTH, jp3);
		jp2.add(BorderLayout.CENTER, scroll);
        
        //ADD PANELS TO TABS
		jtp.addTab("CHAT", jp2);
        jtp.addTab("CONTACTS", envio);
      
        //LANCH GUI
        init();
        start();   
    }
	
	//GETTERS
    public String getNomeClient() {
		return nomeClient;
	}
	
	/** MAIN */
    public static void main(String[] args) {   	
    	//INITIALIZE
    	new Client("Americo");
    	new Client("Tomas");
    }
	
    /** INIT: */
    private void init() {
    	//setDefaultLookAndFeelDecorated(true);
     	setSize(700, 500);
    	setLocation(200, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /** START: */
    private void start() {
    	setVisible(true);
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