package xin.shengtong.rpc.demos;

import xin.shengtong.rpc.prototype.ServiceFactory;

import java.io.IOException;

public class ServerContainer {

    public static void main(String[] args) throws IOException {
        EchoService echoService = new EchoServiceImpl();
        ServiceFactory.exportService(echoService, 2048);
        System.in.read();
    }
}
