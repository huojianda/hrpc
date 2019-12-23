package com.hrpc;

import com.hrpc.api.IHelloHrpc;

/**
 * @author huoji
 *
 */
public class Runtest {

    public static void main(String[] args) {
        IHelloHrpc hrpc = new HelloHrpcImpl();

        HrpcProxyServer proxyServer = new HrpcProxyServer();

        proxyServer.publish(hrpc,8080);

    }
}
