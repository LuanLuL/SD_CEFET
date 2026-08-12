package com.chatsd.chatsd;

public class ChatSD {

    public static void main(String[] args) {
        ServidorTCP server = new ServidorTCP();
        
        server.execute();
    }
}
