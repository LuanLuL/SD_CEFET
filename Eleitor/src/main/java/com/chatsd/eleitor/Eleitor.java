package com.chatsd.eleitor;

public class Eleitor {
    public static void main(String[] args) {
       UrnaCliente eleitor = new UrnaCliente();
       eleitor.confirmConnection();
       eleitor.close();
    }
}
