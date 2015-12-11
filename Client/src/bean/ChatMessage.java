/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Americo Tomas: 54149
 */
@SuppressWarnings("serial")
public class ChatMessage implements Serializable{
    
    //ATRIBUTES
    private String name;
    private String text;
    private String nameReserved; //armazenar nome de mensagem do tipo reservada
    private Set<String> setOnlines = new HashSet<String>();
    private Action action;

    //GETTERS AND SETTERS
    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getNameReserved() {
        return nameReserved;
    }

    public Set<String> getSetOnlines() {
        return setOnlines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setNameReserved(String nameReserved) {
        this.nameReserved = nameReserved;
    }

    public void setSetOnlines(Set<String> setOnlines) {
        this.setOnlines = setOnlines;
    }
    
    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
    
    public enum Action {
    CONNECT, DISCONNECT, SEND_ONE, SEND_ALL, USERS_ONLINE
    
    }
    
    
}
