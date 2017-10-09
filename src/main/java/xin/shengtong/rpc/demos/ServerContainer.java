package xin.shengtong.rpc.demos;

import xin.shengtong.rpc.prototype.ServiceFactory;

/**
 * Created by shentong.zhou on 2017/10/9.
 */
public class ServerContainer {

    public static void main(String[] args) {
        EchoService echoService = new EchoServiceImpl();
        String message = ServiceFactory.exportService(echoService, 2048);
    }
}
