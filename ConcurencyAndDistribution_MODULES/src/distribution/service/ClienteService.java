/*    */ package distribution.service;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.net.Socket;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;

import distribution.serializable.ChatMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClienteService
/*    */ {
/*    */   private Socket socket;
/*    */   private ObjectOutputStream output;
/*    */   
/*    */   public Socket connect()
/*    */   {
/*    */     try
/*    */     {
/* 28 */       this.socket = new Socket("localhost", 5555);
/* 29 */       this.output = new ObjectOutputStream(this.socket.getOutputStream());
/*    */     } catch (IOException ex) {
/* 31 */       Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/* 33 */     return this.socket;
/*    */   }
/*    */   
/*    */   public void send(ChatMessage message) {
/*    */     try {
/* 38 */       this.output.writeObject(message);
/*    */     } catch (IOException ex) {
/* 40 */       Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, ex);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\clouds\Drive Ilimitado\BackEnd_JavaSE_PCD\LinguagemJAVA_super\src\control_3CD\Start_serverClient_11_CLIENT_chatROOM_control108.jar!\pt\progJava\americoLib\control108\service\ClienteService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */