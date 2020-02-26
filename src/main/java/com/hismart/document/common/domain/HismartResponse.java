package com.hismart.document.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;


public class HismartResponse<T> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回描述信息
     */
    private String msg;

    /**
     * 返回内容的签名数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String sign;

    /**
     * 返回内容
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T content;

    /**
     * 附件（满足不同场景需求返回数据）
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object attachment;


    public HismartResponse() {
    }

    public HismartResponse(String code) {
        this.code = code;
    }

    public HismartResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public HismartResponse(String code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
