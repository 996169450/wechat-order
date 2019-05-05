package com.hnu.wechatorder.model.message;

import lombok.Data;

@Data
public class TextMessage extends RespMessage{

    // 文本内容
    private String Content;

}
