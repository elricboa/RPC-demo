package xin.shengtong.rpc.prototype;

import java.lang.reflect.Proxy;

public class ServiceFactory {

    public static <T> T importService(Class<T> serviceInterface, String ip, int port) {
        return (T) Proxy.newProxyInstance(ServiceFactory.class.getClassLoader(), new Class[] {serviceInterface}, new ServiceInvoker(serviceInterface, ip, port));
    }

    public static void exportService(Object serviceImpl, int port) {
        ServiceProvider serviceProvider = new ServiceProvider(serviceImpl, port);
        serviceProvider.start();
        System.out.println("server start on:" + port);
    }
}
