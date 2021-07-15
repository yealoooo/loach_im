package cn.loach.server.message.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LoginAuthRequestMessage extends RequestMessage implements Serializable {

    public LoginAuthRequestMessage() {
        setChatType(AUTH);
        setMessageType(MESSAGE_REQUEST_TYPE);
    }
}
