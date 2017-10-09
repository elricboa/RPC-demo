package xin.shengtong.rpc.demos;

/**
 * Created by shentong.zhou on 2017/10/9.
 */
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String message) {
        return message;
    }
}
