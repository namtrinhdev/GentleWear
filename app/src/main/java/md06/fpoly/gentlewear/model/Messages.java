package md06.fpoly.gentlewear.model;

import java.util.List;

public class Messages {
    private String msg;
    private int status;
    private List<Users> data;

    public Messages() {
    }

    public Messages(String msg, int status, List<Users> data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Users> getData() {
        return data;
    }

    public void setData(List<Users> data) {
        this.data = data;
    }
}
