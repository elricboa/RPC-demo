package xin.shengtong.rpc.prototype;

import java.io.Serializable;

public class Response implements Serializable {

    private long sequence;
    private Object result;

    public Response(Object result) {
        this.result = result;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
