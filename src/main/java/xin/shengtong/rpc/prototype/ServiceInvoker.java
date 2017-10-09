package xin.shengtong.rpc.prototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceInvoker implements InvocationHandler {

    private AtomicLong sequenceGenerator;
    private Class serviceInterface;
    private String ip;
    private int port;

    public <T> ServiceInvoker(Class<T> serviceInterface, String ip, int port) {
        this.sequenceGenerator = new AtomicLong(0);
        this.serviceInterface = serviceInterface;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] parameters) throws Throwable {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        try {
            Request request = new Request(serviceInterface.getName(), method.getName(), method.getParameterTypes(), parameters);
            request.setSequence(sequenceGenerator.getAndIncrement());
            socket = new Socket(ip, port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(request);
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
            Response response = (Response) inputStream.readObject();
            return response.getResult();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
