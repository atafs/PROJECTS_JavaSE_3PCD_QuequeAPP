/*    */ package com.iscte.queque.server.bean;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatMessage
/*    */   implements Serializable
/*    */ {
/*    */   private String name;
/*    */   private String text;
/*    */   private String nameReserved;
/*    */   private Set<String> setOnlines;
/*    */   private Action action;
/*    */   
/*    */   public ChatMessage()
/*    */   {
/* 22 */     this.setOnlines = new HashSet();
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 27 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 31 */     return this.text;
/*    */   }
/*    */   
/*    */   public String getNameReserved() {
/* 35 */     return this.nameReserved;
/*    */   }
/*    */   
/*    */   public Set<String> getSetOnlines() {
/* 39 */     return this.setOnlines;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 43 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void setText(String text) {
/* 47 */     this.text = text;
/*    */   }
/*    */   
/*    */   public void setNameReserved(String nameReserved) {
/* 51 */     this.nameReserved = nameReserved;
/*    */   }
/*    */   
/*    */   public void setSetOnlines(Set<String> setOnlines) {
/* 55 */     this.setOnlines = setOnlines;
/*    */   }
/*    */   
/*    */   public Action getAction() {
/* 59 */     return this.action;
/*    */   }
/*    */   
/*    */   public void setAction(Action action) {
/* 63 */     this.action = action;
/*    */   }
/*    */   
/*    */ 
/*    */   public static enum Action
/*    */   {
/* 69 */     CONNECT,  DISCONNECT,  SEND_ONE,  SEND_ALL,  USERS_ONLINE;
/*    */     
/*    */     private Action() {}
/*    */   }
/*    */ }


/* Location:              D:\clouds\Drive Ilimitado\BackEnd_JavaSE_PCD\LinguagemJAVA_super\src\control_3CD\Start_serverClient_11_SERVER_chatROOM_control107.jar!\pt\progJava\americoLib\control108\bean\ChatMessage.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */