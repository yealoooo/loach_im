package cn.loach.server.message.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthRequestMessage extends RequestMessage implements Serializable {

    /**
     * token
     */
    private String authToken;

    public LoginAuthRequestMessage() {
        setChatType(AUTH);
        setMessageType(MESSAGE_REQUEST_TYPE);
    }
}
