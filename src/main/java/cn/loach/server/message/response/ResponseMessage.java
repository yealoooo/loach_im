package cn.loach.server.message.response;

import cn.loach.server.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage extends Message {
    /**
     * 返回请求结果码
     */
    private int code;

    /**
     * 请求结果说明
     */
    private String responseMessage;

    /**
     * 请求成功与否表示
     */
    private boolean requestFlag;
    /**
     * 数据类型
     */
    private String contentType;

    private ResponseMessage(int code, String responseMessage, boolean requestFlag) {
        this.code = code;
        this.responseMessage = responseMessage;
        this.requestFlag = requestFlag;
    }


}
