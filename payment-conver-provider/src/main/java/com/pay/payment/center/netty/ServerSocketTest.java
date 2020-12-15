package com.pay.payment.center.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {

    public static void main(String[] args) {


        try{
            ServerSocket  serverSocket = new ServerSocket(29990);
            Socket socket =  serverSocket.accept();


            System.out.println("-------"+socket.getInetAddress()+"----"+socket.getPort());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(bufferedReader.readLine());
            while (true){

            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
