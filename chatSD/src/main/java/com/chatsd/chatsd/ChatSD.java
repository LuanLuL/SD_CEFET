package com.chatsd.chatsd;

public class ChatSD {

    public static void main(String[] args) {
        ServidorTCP server = new ServidorTCP();
        int qClient = 2;
        server.execute(qClient);

    }
}
