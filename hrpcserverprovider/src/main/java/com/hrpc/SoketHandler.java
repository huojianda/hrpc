package com.hrpc;

import com.hrpc.api.HrpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class SoketHandler implements Runnable {

    private Socket socket;
    private Object service;

    public SoketHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try{
            inputStream = new ObjectInputStream(socket.getInputStream());

            HrpcRequest request = (HrpcRequest)inputStream.readObject();
            Object result = invoke(request);//反射

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(HrpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] parameters = request.getParameters();
        Class<?>[] types = new Class[parameters.length];
        for (int i = 0; i< parameters.length;i++) {
            types[i] = parameters[i].getClass();
        }
        Class clazz = Class.forName(request.getClassName());
        Method method = clazz.getMethod(request.getMethodName(),types);
        Object result = method.invoke(service,parameters);
        return result;
    }
}
