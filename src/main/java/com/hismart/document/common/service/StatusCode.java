package com.hismart.document.common.service;

/**
 * 返回状态码
 */
public interface StatusCode {

    /**
     * 接口调用成功，调用结果请参考具体的API文档所对应的业务返回参数
     */
    String CODE_10000 = "10000";

    /**
     * 服务不可用
     */
    String CODE_20000 = "20000";

    /**
     * 无效的请求报文，参数内容长度超过限制
     */
    String CODE_30000 = "30000";

    /**
     * 缺少client_id
     */
    String CODE_30001 = "30001";

    /**
     * 无效的缺少client_id
     */
    String CODE_30002 = "30002";

    /**
     * 缺少业务参数
     */
    String CODE_30004 = "30004";

    /**
     * 无效的业务参数
     */
    String CODE_30005 = "30005";

    /**
     * 缺少表单参数
     */
    String CODE_30006 = "30006";

    /**
     * 无效的表单参数（用于前端参数校验）
     */
    String CODE_30007 = "30007";

    /**
     * 缺少charset
     */
    String CODE_30008 = "30008";

    /**
     * 缺少签名字段
     */
    String CODE_30009 = "30009";

    /**
     * 缺少签名类型
     */
    String CODE_30010 = "30010";

    /**
     * 缺少用户授权令牌
     */
    String CODE_30011 = "30011";

    /**
     * 无效的用户授权令牌
     */
    String CODE_30012 = "30012";

    /**
     * 用户授权令牌已失效
     */
    String CODE_30013 = "30013";

    /**
     * 用私钥解密参数发生错误
     */
    String CODE_30101 = "30101";

    /**
     * 用公钥加密返回值发生错误
     */
    String CODE_30102 = "30102";

    /**
     * 用公钥验签发生错误
     */
    String CODE_30103 = "30103";

    /**
     * 用私钥加签发生错误
     */
    String CODE_30104 = "30104";

    /**
     * 未知错误
     */
    String CODE_99999 = "99999";

    /**
     * 业务请求失败
     */
    String CODE_40003 = "40003";


    /**
     * 被转授权次数达到限制
     */
    String CODE_30014 = "30014";

    /**
     * 登录IP不一致
     */
    String CODE_30015 = "30015";

    /**
     * 系统错误
     */
    String CODE_500 = "500";

    /**
     * Bad Request
     */
    String CODE_400 = "400";


    /**
     * 未授权
     */
    String CODE_401 = "401";




}
