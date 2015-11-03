/*     */ package pt.progJava.americoLib.control107.frame;
/*     */ 
/*     */ import java.awt.event.ActionEvent;

/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;

import pt.progJava.americoLib.control107.service.ClienteService;
/*     */ import pt.progJava.americoLib.control108.bean.ChatMessage;
/*     */ import pt.progJava.americoLib.control108.bean.ChatMessage.Action;
/*     */ 
/*     */ public class ClientFrame extends javax.swing.JFrame
/*     */ {
/*     */   private java.net.Socket socket;
/*     */   private ChatMessage message;
/*     */   private JButton btnConectar;
/*     */   private JButton btnEnviar;
/*     */   private JButton btnLimpar;
/*     */   private JButton btnSair;
/*     */   private JPanel jPanel1;
/*     */   private JPanel jPanel2;
/*     */   private JPanel jPanel3;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JScrollPane jScrollPane2;
/*     */   private JScrollPane jScrollPane3;
/*     */   private javax.swing.JList listOnlines;
/*     */   private JTextArea txtAreaReceive;
/*     */   private JTextArea txtAreaSend;
/*     */   private javax.swing.JTextField txtName;
private ClienteService service;
/*     */   
/*     */   public ClientFrame()
/*     */   {
/*  37 */     initComponents();
/*     */   }
/*     */   
/*     */   private class ListenerSocket
/*     */     implements Runnable
/*     */   {
/*     */     private java.io.ObjectInputStream input;
/*     */     
/*     */     public ListenerSocket(java.net.Socket socket)
/*     */     {
/*     */       try
/*     */       {
/*  49 */         this.input = new java.io.ObjectInputStream(socket.getInputStream());
/*     */       } catch (java.io.IOException ex) {
/*  51 */         java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */     
/*     */     public void run()
/*     */     {
/*  57 */       ChatMessage message = null;
/*     */       try
/*     */       {
/*  60 */         while ((message = (ChatMessage)this.input.readObject()) != null) {
/*  61 */           ChatMessage.Action action = message.getAction();
/*     */           
/*  63 */           if (action.equals(ChatMessage.Action.CONNECT)) {
/*  64 */             ClientFrame.this.connect(message);
/*  65 */           } else if (action.equals(ChatMessage.Action.DISCONNECT)) {
/*  66 */             ClientFrame.this.disconnected();
/*  67 */             ClientFrame.this.socket.close();
/*  68 */           } else if (action.equals(ChatMessage.Action.SEND_ONE)) {
/*  69 */             ClientFrame.this.receive(message);
/*  70 */           } else if (action.equals(ChatMessage.Action.USERS_ONLINE)) {
/*  71 */             ClientFrame.this.refreshOnlines(message);
/*     */           }
/*     */         }
/*     */       } catch (java.io.IOException ex) {
/*  75 */         java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
/*     */       } catch (ClassNotFoundException ex) {
/*  77 */         java.util.logging.Logger.getLogger(ClientFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void connect(ChatMessage message)
/*     */   {
/*  86 */     if (message.getText().equals("NO")) {
/*  87 */       this.txtName.setText("");
/*  88 */       javax.swing.JOptionPane.showMessageDialog(this, "CONNECTION FAILERD.\n TRY AGAIN WITH A DIFERENT NAME");
/*  89 */       return;
/*     */     }
/*     */     
/*  92 */     this.message = message;
/*     */     
/*  94 */     this.btnConectar.setEnabled(false);
/*  95 */     this.txtName.setEditable(false);
/*     */     
/*     */ 
/*  98 */     this.btnSair.setEnabled(true);
/*  99 */     this.txtAreaSend.setEnabled(true);
/* 100 */     this.txtAreaReceive.setEnabled(true);
/*     */     
/* 102 */     this.btnEnviar.setEnabled(true);
/* 103 */     this.btnLimpar.setEnabled(true);
/*     */     
/* 105 */     javax.swing.JOptionPane.showMessageDialog(this, "CONNECTION SUCCEDED.\n YOU ARE CONNECTED IN CHATROOM");
/*     */   }
/*     */   
/*     */ 
/*     */   private void disconnected()
/*     */   {
/* 111 */     this.btnConectar.setEnabled(true);
/* 112 */     this.txtName.setEnabled(true);
/*     */     
/* 114 */     this.btnSair.setEnabled(false);
/* 115 */     this.txtAreaSend.setEnabled(false);
/* 116 */     this.txtAreaReceive.setEnabled(false);
/* 117 */     this.btnEnviar.setEnabled(false);
/* 118 */     this.btnLimpar.setEnabled(false);
/*     */     
/* 120 */     this.txtAreaReceive.setText("");
/* 121 */     this.txtAreaSend.setText("");
/*     */     
/* 123 */     javax.swing.JOptionPane.showMessageDialog(this, "YOU HAVE LEFT THE CHATROOM");
/*     */   }
/*     */   
/*     */   private void receive(ChatMessage message) {
/* 127 */     this.txtAreaReceive.append(message.getName() + " SAID: " + message.getText() + "\n");
/*     */   }
/*     */   
/*     */   private void refreshOnlines(ChatMessage message)
/*     */   {
/* 132 */     System.out.println(message.getSetOnlines().toString());
/* 133 */     java.util.Set<String> names = message.getSetOnlines();
/*     */     
/* 135 */     names.remove(message.getName());
/*     */     
/* 137 */     String[] array = (String[])names.toArray(new String[names.size()]);
/*     */     
/* 139 */     this.listOnlines.setListData(array);
/* 140 */     this.listOnlines.setSelectionMode(0);
/* 141 */     this.listOnlines.setLayoutOrientation(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 154 */     this.jPanel1 = new JPanel();
/* 155 */     this.txtName = new javax.swing.JTextField();
/* 156 */     this.btnConectar = new JButton();
/* 157 */     this.btnSair = new JButton();
/* 158 */     this.jPanel2 = new JPanel();
/* 159 */     this.jScrollPane3 = new JScrollPane();
/* 160 */     this.listOnlines = new javax.swing.JList();
/* 161 */     this.jPanel3 = new JPanel();
/* 162 */     this.jScrollPane1 = new JScrollPane();
/* 163 */     this.txtAreaReceive = new JTextArea();
/* 164 */     this.jScrollPane2 = new JScrollPane();
/* 165 */     this.txtAreaSend = new JTextArea();
/* 166 */     this.btnEnviar = new JButton();
/* 167 */     this.btnLimpar = new JButton();
/*     */     
/* 169 */     setDefaultCloseOperation(3);
/*     */     
/* 171 */     this.jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("CONNECT"));
/*     */     
/* 173 */     this.btnConectar.setText("Connect");
/* 174 */     this.btnConectar.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 176 */         ClientFrame.this.btnConectarActionPerformed(evt);
/*     */       }
/*     */       
/* 179 */     });
/* 180 */     this.btnSair.setText("Exit");
/* 181 */     this.btnSair.setEnabled(false);
/* 182 */     this.btnSair.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 184 */         ClientFrame.this.btnSairActionPerformed(evt);
/*     */       }
/*     */       
/* 187 */     });
/* 188 */     GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
/* 189 */     this.jPanel1.setLayout(jPanel1Layout);
/* 190 */     jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.txtName).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.btnConectar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.btnSair).addContainerGap()));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 200 */     jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtName, -2, -1, -2).addComponent(this.btnConectar).addComponent(this.btnSair)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 208 */     this.jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("USERS ONLINE"));
/*     */     
/* 210 */     this.jScrollPane3.setViewportView(this.listOnlines);
/*     */     
/* 212 */     GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
/* 213 */     this.jPanel2.setLayout(jPanel2Layout);
/* 214 */     jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane3, -1, 157, 32767));
/*     */     
/*     */ 
/*     */ 
/* 218 */     jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.jScrollPane3, -2, 266, -2)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 225 */     this.jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
/*     */     
/* 227 */     this.txtAreaReceive.setEditable(false);
/* 228 */     this.txtAreaReceive.setColumns(20);
/* 229 */     this.txtAreaReceive.setRows(5);
/* 230 */     this.txtAreaReceive.setEnabled(false);
/* 231 */     this.jScrollPane1.setViewportView(this.txtAreaReceive);
/*     */     
/* 233 */     this.txtAreaSend.setColumns(20);
/* 234 */     this.txtAreaSend.setRows(5);
/* 235 */     this.txtAreaSend.setEnabled(false);
/* 236 */     this.jScrollPane2.setViewportView(this.txtAreaSend);
/*     */     
/* 238 */     this.btnEnviar.setText("Send");
/* 239 */     this.btnEnviar.setEnabled(false);
/* 240 */     this.btnEnviar.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 242 */         ClientFrame.this.btnEnviarActionPerformed(evt);
/*     */       }
/*     */       
/* 245 */     });
/* 246 */     this.btnLimpar.setText("Clear");
/* 247 */     this.btnLimpar.setEnabled(false);
/* 248 */     this.btnLimpar.addActionListener(new java.awt.event.ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 250 */         ClientFrame.this.btnLimparActionPerformed(evt);
/*     */       }
/*     */       
/* 253 */     });
/* 254 */     GroupLayout jPanel3Layout = new GroupLayout(this.jPanel3);
/* 255 */     this.jPanel3.setLayout(jPanel3Layout);
/* 256 */     jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 355, 32767).addComponent(this.jScrollPane2).addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup().addGap(0, 0, 32767).addComponent(this.btnLimpar).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.btnEnviar))).addContainerGap()));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */     jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel3Layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -2, 118, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, -1, 32767).addComponent(this.jScrollPane2, -2, 56, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.btnEnviar).addComponent(this.btnLimpar)).addContainerGap()));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 284 */     GroupLayout layout = new GroupLayout(getContentPane());
/* 285 */     getContentPane().setLayout(layout);
/* 286 */     layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jPanel1, -1, -1, 32767).addComponent(this.jPanel3, -1, -1, 32767)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel2, -1, -1, 32767)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 296 */     layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel3, -1, -1, 32767).addContainerGap()).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel2, -1, -1, 32767)));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 308 */     pack();
/*     */   }
/*     */   
/*     */   private void btnConectarActionPerformed(ActionEvent evt) {
/* 312 */     String name = this.txtName.getText();
/*     */     
/* 314 */     if (!name.isEmpty()) {
/* 315 */       this.message = new ChatMessage();
/* 316 */       this.message.setAction(ChatMessage.Action.CONNECT);
/* 317 */       this.message.setName(name);
/*     */       
/* 319 */       this.service = new ClienteService();
/* 320 */       this.socket = this.service.connect();
/*     */       
/* 322 */       new Thread(new ListenerSocket(this.socket)).start();
/* 323 */       this.service.send(this.message);
/*     */     }
/* 325 */     this.txtAreaSend.setText("");
/*     */   }
/*     */   
/*     */   private void btnSairActionPerformed(ActionEvent evt) {
/* 329 */     this.message.setAction(ChatMessage.Action.DISCONNECT);
/* 330 */     this.service.send(this.message);
/* 331 */     disconnected();
/*     */   }
/*     */   
/*     */   private void btnLimparActionPerformed(ActionEvent evt)
/*     */   {
/* 336 */     this.txtAreaSend.setText("");
/*     */   }
/*     */   
/*     */   private void btnEnviarActionPerformed(ActionEvent evt)
/*     */   {
/* 341 */     String text = this.txtAreaSend.getText();
/* 342 */     String name = this.message.getName();
/*     */     
/*     */ 
/* 345 */     this.message = new ChatMessage();
/*     */     
/*     */ 
/* 348 */     if (this.listOnlines.getSelectedIndex() > -1) {
/* 349 */       this.message.setNameReserved((String)this.listOnlines.getSelectedValue());
/* 350 */       this.message.setAction(ChatMessage.Action.SEND_ONE);
/* 351 */       this.listOnlines.clearSelection();
/*     */     }
/*     */     else {
/* 354 */       this.message.setAction(ChatMessage.Action.SEND_ALL);
/*     */     }
/*     */     
/*     */ 
/* 358 */     if (!text.isEmpty())
/*     */     {
/*     */ 
/* 361 */       this.message.setName(name);
/* 362 */       this.message.setText(text);
/*     */       
/* 364 */       this.txtAreaReceive.append("YOU SAID: " + text + "\n");
/*     */       
/* 366 */       this.service.send(this.message);
/*     */     }
/* 368 */     this.txtAreaSend.setText("");
/*     */   }
/*     */ }


/* Location:              D:\clouds\Drive Ilimitado\BackEnd_JavaSE_PCD\LinguagemJAVA_super\src\control_3CD\Start_serverClient_11_CLIENT_chatROOM_control108.jar!\pt\progJava\americoLib\control108\frame\ClienteFrame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */