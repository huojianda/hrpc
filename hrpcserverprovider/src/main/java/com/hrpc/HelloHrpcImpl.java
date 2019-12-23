package com.hrpc;

import com.hrpc.api.IHelloHrpc;

/**
 * @author huoji
 * api 服务提供
 */
public class HelloHrpcImpl implements IHelloHrpc{
    @Override
    public String helloHrpc(String str) {
        System.out.println(" hello , this is hrpc server :"+str);
        return "success";
    }
}
