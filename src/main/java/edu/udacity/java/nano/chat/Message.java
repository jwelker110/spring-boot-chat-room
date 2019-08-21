package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;

/**
 * WebSocket message model
 */
public class Message {

    private String msg;

    private String username;

    private Integer onlineCount;

    private String type;

    public Message(String msg, String username) {
        this.msg = msg;
        this.username = username;
    }

    public Message(String msg, String username, Integer onlineCount, String type) {
        this.msg = msg;
        this.username = username;
        this.onlineCount = onlineCount;
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }
}
