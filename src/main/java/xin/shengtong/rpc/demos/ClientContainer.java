package xin.shengtong.rpc.demos;

import xin.shengtong.rpc.prototype.ServiceFactory;

public class ClientContainer {

    public static void main(String[] args) {
        EchoService echoService = ServiceFactory.importService(EchoService.class, "127.0.0.1", 2048);
        String message = echoService.echo("shengtong");
        System.out.println(message);
    }
}
