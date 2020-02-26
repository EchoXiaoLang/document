package com.hismart.document.modules.smallprogram.entity;

import lombok.Data;

/**
 * 微信api调用回信息
 * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
 *
 * @author Echo_Sxl on 2020/2/25 16:18
 * @version 1.0
 */
@Data
public class wxUserInfo {
    /**
     * 用户唯一标识
     */
    private String openid;
    /**
     * 会话密钥
     */
    private String session_key;
    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。
     */
    private String unionid;
    /**
     * 错误码
     */
    private String errcode;
    /**
     * 错误信息
     */
    private String errmsg;
}
