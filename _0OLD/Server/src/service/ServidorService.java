/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.ChatMessage;
import bean.ChatMessage.Action;

/**
 *
 * @author Americo
 */
public class ServidorService {

    //ATRIBUTES
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> mapOnlies = new HashMap<String, ObjectOutputStream>();

    //CONSTRUCTOR
    public ServidorService() {
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("SERVIDOR ON.");

            while (true) {
                socket = serverSocket.accept();

                new Thread(new ListenerSocket(socket)).start();

            }

        } catch (Exception e) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private class ListenerSocket implements Runnable {

        //ATTRIBUTES
        private ObjectOutputStream output;
        private ObjectInputStream input;

        //CONSTRUCTOR
        public ListenerSocket(Socket socket) {
            try {

                this.output = new ObjectOutputStream(socket.getOutputStream());
                this.input = new ObjectInputStream(socket.getInputStream());

            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {

            ChatMessage message = null;
            try {
                while ((message = (ChatMessage) input.readObject()) != null) {

                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnected = connect(message, output);
                        //WHEN CONNECTION OCCURS, ADD TO THE LIST
                        if (isConnected) {
                            mapOnlies.put(message.getName(), output);
                            sendOnlines();
                        }
                    } else if (action.equals(Action.DISCONNECT)) {
                        disconnected(message, output);
                        sendOnlines();
                        return; //FORCE WHILE TO LEAVE
                    } else if (action.equals(Action.SEND_ONE)) {
                        sendOne(message);
                        
              
                    } else if (action.equals(Action.SEND_ALL)) {
                        sendAll(message);
                       
                       

                    } 
                }
            } catch (IOException ex) {
                disconnected(message, output);
                sendOnlines();
                System.out.println(message.getName() + " LEFT THE CHAT.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean connect(ChatMessage message, ObjectOutputStream output) {

        //NO CLIENT CONNECTED YET
        if (mapOnlies.size() == 0) {
            message.setText("YES");
            System.out.println("YES. YOU ARE CONNECTED. And YOU ARE THE FIRST ONE");
            send(message, output);
            return true;
        }

        //ADD LEAST ONE CLIENT CONNECTED
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlies.entrySet()) {
            //VERIFY NAME THAT HAS TO BE DIFFERENT FROM THE ONE ON THE LIST
            if (kv.getKey().equals(message.getName())) {
                message.setText("NO");
                System.out.println("NO. NOT CONNECTED YET");
                send(message, output);
                return false;
            } else {
                message.setText("YES");
                System.out.println("YES. YOU ARE CONNECTED.");
                send(message, output);
                return true;
            }
        }

        return false;
    }

    
    private void disconnected(ChatMessage message, ObjectOutputStream output) {
        
        mapOnlies.remove(message.getName());

        message.setText("LEFT CHATROOM. SEE YOU NEXT TIME!!!");

        //CHANGE THE ACTION
        message.setAction(Action.SEND_ONE);
        sendAll(message);
        System.out.println("USER -> " + message.getName() + " LEFT THE CHATROOM");
    }
    
    
    private void send(ChatMessage message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendOne(ChatMessage message) {

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlies.entrySet()) {

            if (kv.getKey().equals(message.getNameReserved())) {
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


    private void sendAll(ChatMessage message) {

        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlies.entrySet()) {

            //AVOID THE MESSAGE TO BE RE_SENT TO HIMSELF
            if (!kv.getKey().equals(message.getName())) {
                message.setAction(Action.SEND_ONE);
                try {
                    System.out.println("USER -> " + message.getName());
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sendOnlines() {

        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlies.entrySet()) {
            setNames.add(kv.getKey());
        }

        ChatMessage message = new ChatMessage();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        //SEND TO USERS IN LIST
        for (Map.Entry<String, ObjectOutputStream> kv : mapOnlies.entrySet()) {
            message.setName(kv.getKey());
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(ServidorService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
