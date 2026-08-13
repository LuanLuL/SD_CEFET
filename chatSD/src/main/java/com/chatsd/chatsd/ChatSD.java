package com.chatsd.chatsd;

public class ChatSD {

    public static void main(String[] args) {
        ServidorTCP server = new ServidorTCP();
        int qClient = 2;
        
        for(int i = 0;i<qClient;i++) {
            server.execute();
        }
    }
}
