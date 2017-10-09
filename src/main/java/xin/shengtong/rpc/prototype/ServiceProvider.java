package xin.shengtong.rpc.prototype;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceProvider extends Thread {

    private AtomicLong sequenceGenerator;
    private Object serviceImpl;
    private String serviceName;
    private int port;

    public ServiceProvider(Object serviceImpl, int port) {
        this.sequenceGenerator = new AtomicLong(0);
        this.serviceImpl = serviceImpl;
        this.serviceName = serviceImpl.getClass().getInterfaces()[0].getName();
        this.port = port;
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = null;
                ObjectInputStream inputStream = null;
                ObjectOutputStream outputStream = null;
                try {
                    socket = serverSocket.accept();
                    System.out.println("accept client:" + socket.getRemoteSocketAddress());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    Request request = (Request) inputStream.readObject();
                    System.out.println("receive request:" + request.getServiceName() + "." + request.getMethodName());
                    Response response = processResponse(request);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.writeObject(response);
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {

                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Response processResponse(Request request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = serviceImpl.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
        Object result = method.invoke(serviceImpl, request.getParameters());
        Response response = new Response(result);
        response.setSequence(sequenceGenerator.getAndIncrement());
        return response;
    }

}
