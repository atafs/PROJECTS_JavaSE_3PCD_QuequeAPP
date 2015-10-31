/*     */ package com.iscte.queque.client.service;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;

import com.iscte.queque.server.bean.ChatMessage;
import com.iscte.queque.server.bean.ChatMessage.Action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerService
/*     */ {
/*     */   private ServerSocket serverSocket;
/*     */   private Socket socket;
/*  31 */   private Map<String, ObjectOutputStream> mapOnlies = new HashMap();
/*     */   
/*     */   public ServerService()
/*     */   {
/*     */     try {
/*  36 */       this.serverSocket = new ServerSocket(5555);
/*  37 */       System.out.println("SERVIDOR ON.");
/*     */       for (;;)
/*     */       {
/*  40 */         this.socket = this.serverSocket.accept();
/*     */         
/*  42 */         new Thread(new ListenerSocket(this.socket)).start();
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  47 */       Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ListenerSocket
/*     */     implements Runnable
/*     */   {
/*     */     private ObjectOutputStream output;
/*     */     private ObjectInputStream input;
/*     */     
/*     */     public ListenerSocket(Socket socket)
/*     */     {
/*     */       try
/*     */       {
/*  61 */         this.output = new ObjectOutputStream(socket.getOutputStream());
/*  62 */         this.input = new ObjectInputStream(socket.getInputStream());
/*     */       }
/*     */       catch (IOException ex) {
/*  65 */         Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     public void run()
/*     */     {
/*  72 */       ChatMessage message = null;
/*     */       try {
/*  74 */         while ((message = (ChatMessage)this.input.readObject()) != null)
/*     */         {
/*  76 */           ChatMessage.Action action = message.getAction();
/*     */           
/*  78 */           if (action.equals(ChatMessage.Action.CONNECT)) {
/*  79 */             boolean isConnected = ServerService.this.connect(message, this.output);
/*     */             
/*  81 */             if (isConnected) {
/*  82 */               ServerService.this.mapOnlies.put(message.getName(), this.output);
/*  83 */               ServerService.this.sendOnlines();
/*     */             }
/*  85 */           } else { if (action.equals(ChatMessage.Action.DISCONNECT)) {
/*  86 */               ServerService.this.disconnected(message, this.output);
/*  87 */               ServerService.this.sendOnlines();
/*  88 */               return; }
/*  89 */             if (action.equals(ChatMessage.Action.SEND_ONE)) {
/*  90 */               ServerService.this.sendOne(message);
/*     */ 
/*     */             }
/*  93 */             else if (action.equals(ChatMessage.Action.SEND_ALL)) {
/*  94 */               ServerService.this.sendAll(message);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (IOException ex)
/*     */       {
/* 101 */         ServerService.this.disconnected(message, this.output);
/* 102 */         ServerService.this.sendOnlines();
/* 103 */         System.out.println(message.getName() + " LEFT THE CHAT.");
/*     */       } catch (ClassNotFoundException ex) {
/* 105 */         Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean connect(ChatMessage message, ObjectOutputStream output)
/*     */   {
/* 113 */     if (this.mapOnlies.size() == 0) {
/* 114 */       message.setText("YES");
/* 115 */       System.out.println("YES. YOU ARE CONNECTED. And YOU ARE THE FIRST ONE");
/* 116 */       send(message, output);
/* 117 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 121 */     Iterator i$ = this.mapOnlies.entrySet().iterator(); if (i$.hasNext()) { Map.Entry<String, ObjectOutputStream> kv = (Map.Entry)i$.next();
/*     */       
/* 123 */       if (((String)kv.getKey()).equals(message.getName())) {
/* 124 */         message.setText("NO");
/* 125 */         System.out.println("NO. NOT CONNECTED YET");
/* 126 */         send(message, output);
/* 127 */         return false;
/*     */       }
/* 129 */       message.setText("YES");
/* 130 */       System.out.println("YES. YOU ARE CONNECTED.");
/* 131 */       send(message, output);
/* 132 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 136 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private void disconnected(ChatMessage message, ObjectOutputStream output)
/*     */   {
/* 142 */     this.mapOnlies.remove(message.getName());
/*     */     
/* 144 */     message.setText("LEFT CHATROOM. SEE YOU NEXT TIME!!!");
/*     */     
/*     */ 
/* 147 */     message.setAction(ChatMessage.Action.SEND_ONE);
/* 148 */     sendAll(message);
/* 149 */     System.out.println("USER -> " + message.getName() + " LEFT THE CHATROOM");
/*     */   }
/*     */   
/*     */   private void send(ChatMessage message, ObjectOutputStream output)
/*     */   {
/*     */     try {
/* 155 */       output.writeObject(message);
/*     */     } catch (IOException ex) {
/* 157 */       Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendOne(ChatMessage message)
/*     */   {
/* 163 */     for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet())
/*     */     {
/* 165 */       if (((String)kv.getKey()).equals(message.getNameReserved())) {
/*     */         try {
/* 167 */           ((ObjectOutputStream)kv.getValue()).writeObject(message);
/*     */         } catch (IOException ex) {
/* 169 */           Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void sendAll(ChatMessage message)
/*     */   {
/* 178 */     for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet())
/*     */     {
/*     */ 
/* 181 */       if (!((String)kv.getKey()).equals(message.getName())) {
/* 182 */         message.setAction(ChatMessage.Action.SEND_ONE);
/*     */         try {
/* 184 */           System.out.println("USER -> " + message.getName());
/* 185 */           ((ObjectOutputStream)kv.getValue()).writeObject(message);
/*     */         } catch (IOException ex) {
/* 187 */           Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void sendOnlines()
/*     */   {
/* 195 */     Set<String> setNames = new HashSet();
/* 196 */     for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
/* 197 */       setNames.add(kv.getKey());
/*     */     }
/*     */     
/* 200 */     ChatMessage message = new ChatMessage();
/* 201 */     message.setAction(ChatMessage.Action.USERS_ONLINE);
/* 202 */     message.setSetOnlines(setNames);
/*     */     
/*     */ 
/* 205 */     for (Map.Entry<String, ObjectOutputStream> kv : this.mapOnlies.entrySet()) {
/* 206 */       message.setName((String)kv.getKey());
/*     */       try {
/* 208 */         ((ObjectOutputStream)kv.getValue()).writeObject(message);
/*     */       } catch (IOException ex) {
/* 210 */         Logger.getLogger(ServerService.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\clouds\Drive Ilimitado\BackEnd_JavaSE_PCD\LinguagemJAVA_super\src\control_3CD\Start_serverClient_11_SERVER_chatROOM_control107.jar!\pt\progJava\americoLib\control108\service\ServidorService.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */