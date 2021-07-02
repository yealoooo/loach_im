package cn.loach.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseMessage {
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
     * 返回结果体
     */
    private Message responseContent;
}
