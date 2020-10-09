package com.iec61850bean.app;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class simpleThread extends Thread{
    private String path_file;
    private int port;

    private static void sendFile(String path_file, int port) throws IOException {
        ServerSocket receiver = null;
        OutputStream out = null;
        Socket socket = null;
        File myFile = new File(path_file);
        byte[] buffer = new byte[(int) myFile.length()];

        receiver = new ServerSocket(port);
        socket = receiver.accept();
        System.out.println("Accepted connection from : " + socket);
        FileInputStream fis = new FileInputStream(myFile);
        BufferedInputStream in = new BufferedInputStream(fis);
        in.read(buffer, 0, buffer.length);
        out = socket.getOutputStream();
        System.out.println("Sending files");
        out.write(buffer, 0, buffer.length);
        out.flush();
        out.close();
        in.close();
        socket.close();
        System.out.println("Finished sending");
    }

    public simpleThread(String path_file, int port) {
        this.path_file=path_file;
        this.port=port;
    }
    public void run(){
        while(true){
            try {
                sendFile(path_file, port);
                sleep(500);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
