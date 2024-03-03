package md06.fpoly.gentlewear.model;

public class Messages {
    private String msg;
    private int status;
    private Users data;

    public Messages() {
    }

    public Messages(String msg, int status, Users data) {
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

    public Users getData() {
        return data;
    }

    public void setData(Users data) {
        this.data = data;
    }
}
