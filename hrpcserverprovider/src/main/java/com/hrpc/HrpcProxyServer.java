package com.hrpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huoji
 * 服务发布  基于soket
 * 使用线程池  阻断在线程级别
 */
public class HrpcProxyServer {

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void publish(Object server,int port){
        ServerSocket socket = null;

        try{
            socket = new ServerSocket(port);
            while (true){
                Socket accept = socket.accept();
                executorService.execute(new SoketHandler(accept,server));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
