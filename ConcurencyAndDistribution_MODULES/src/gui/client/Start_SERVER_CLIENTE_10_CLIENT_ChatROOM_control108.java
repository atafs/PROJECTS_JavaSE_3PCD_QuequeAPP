/*    */ package gui.client;
/*    */ 
/*    */ import java.awt.EventQueue;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;

/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.UIManager.LookAndFeelInfo;
/*    */ import javax.swing.UnsupportedLookAndFeelException;

import pt.progJava.americoLib.control107.frame.ClientFrame;
/*    */ 
/*    */ 
/*    */ public class Start_SERVER_CLIENTE_10_CLIENT_ChatROOM_control108
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
	
			new ClientFrame().setVisible(true);
	
///*    */     try
///*    */     {
///* 26 */       for (UIManager.LookAndFeelInfo info = null; ; ) {
///* 27 */         if ("Nimbus".equals(info.getName())) {
///* 28 */           UIManager.setLookAndFeel(info.getClassName());
///* 29 */           break;
///*    */         }
///*    */       }
///*    */     } catch (ClassNotFoundException ex) {
///* 33 */       Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
///*    */     } catch (InstantiationException ex) {
///* 35 */       Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
///*    */     } catch (IllegalAccessException ex) {
///* 37 */       Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
///*    */     } catch (UnsupportedLookAndFeelException ex) {
///* 39 */       Logger.getLogger(ClienteFrame.class.getName()).log(Level.SEVERE, null, ex);
///*    */     }
/*    */     
/*    */ 
/*    */ 
/* 44 */     EventQueue.invokeLater(new Runnable() {
/*    */       public void run() {
/* 46 */         new ClientFrame().setVisible(true);
/*    */       }
/*    */     });
/*    */   }
/*    */ }


/* Location:              D:\clouds\Drive Ilimitado\BackEnd_JavaSE_PCD\LinguagemJAVA_super\src\control_3CD\Start_serverClient_11_CLIENT_chatROOM_control108.jar!\pt\progJava\americoLib\control108\Start_SERVER_CLIENTE_10_CLIENT_ChatROOM_control108.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */