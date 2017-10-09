package xin.shengtong.rpc.demos;

public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String message) {
        return message;
    }
}
