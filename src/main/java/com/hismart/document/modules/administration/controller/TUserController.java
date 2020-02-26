package com.hismart.document.modules.administration.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.hismart.document.common.authentication.JWTToken;
import com.hismart.document.common.authentication.JWTUtil;
import com.hismart.document.common.domain.ActiveUser;
import com.hismart.document.common.domain.HismartConstant;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.exception.HismartException;
import com.hismart.document.common.properties.HismartProperties;
import com.hismart.document.common.service.CacheService;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.common.service.StringCode;
import com.hismart.document.common.utils.AddressUtil;
import com.hismart.document.common.utils.DateUtil;
import com.hismart.document.common.utils.HismartUtil;
import com.hismart.document.common.utils.IPUtil;
import com.hismart.document.modules.system.entity.SinkUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.common.domain.HismartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.administration.service.ITUserService;
import com.hismart.document.modules.administration.entity.TUser;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author Echo_Sxl
 * @since 2020-02-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/administration/tuser/api/v1")

public class TUserController extends BaseController {
    private final ITUserService iITUserService;
    private final HismartProperties properties;
    private final CacheService redisService;

    @Autowired
    public TUserController(ITUserService iITUserService, HismartProperties properties, CacheService redisService) {
        this.iITUserService = iITUserService;
        this.properties = properties;
        this.redisService = redisService;
    }

    /**
     * TUser 分页查询数据
     *
     * @param request request
     * @param iTUser  iTUser
     * @return HismartResponse
     */
    @GetMapping("/get/page")
    public HismartResponse getTUserPageList(QueryRequest request, TUser iTUser) {
        try {
            IPage<TUser> page = this.getPage(request);
            return success(this.iITUserService.getTUserPageList(page, iTUser));
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TUser sql分页查询数据
     *
     * @param request request
     * @param iTUser  iTUser
     * @return HismartResponse
     */
    @GetMapping("/get/sql/page")
    public HismartResponse getTUserSqlPageList(QueryRequest request, TUser iTUser) {
        try {
            IPage<TUser> page = this.getPage(request);
            return success(this.iITUserService.getTUserSqlPageList(page, iTUser));
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * TUser 新增
     *
     * @param iTUser        iTUser
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    public HismartResponse addTUser(@Valid TUser iTUser, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            Date date = DateUtil.strToDateLong(iTUser.getBirthdayStr());
            iTUser.setBirthday(date);
            return success(this.iITUserService.save(iTUser));
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }


    /**
     * TUser 修改
     *
     * @param iTUser        iTUser
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid TUser iTUser, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            return success(this.iITUserService.updateById(iTUser));
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }


    /**
     * TUser 根据id删除
     *
     * @param id 主键Id
     * @return HismartResponse
     */
    @DeleteMapping("/delete/{id}")
    public HismartResponse deleteTUserById(@PathVariable("id") String id) {
        try {
            return success(this.iITUserService.removeById(id));
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1/delete/{}  error,[{}]", id, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

    /**
     * 微信是否已经绑定
     *
     * @param request request
     * @param iTUser  iTUser
     * @return HismartResponse
     */
    @GetMapping("/is/binding")
    public HismartResponse isBinding(TUser iTUser,HttpServletRequest request) {
        try {
            TUser byId = iITUserService.getById(iTUser.getWxNumber());
            if (byId != null) {
                TUser user = iITUserService.getById(iTUser.getWxNumber());
                //TODO 保存登录记录
                String token = HismartUtil.encryptToken(JWTUtil.sign(iTUser.getWxNumber(), iTUser.getWxNumber()));
                LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
                String expireTimeStr = DateUtil.formatFullTime(expireTime);
                JWTToken jwtToken = new JWTToken(token, expireTimeStr);
                this.saveTokenToRedis(user, jwtToken, request);
                return success(byId);
            } else {
                return fail(StatusCode.CODE_500, "用户未绑定");
            }
        } catch (Exception e) {
            log.error("/administration/tuser/api/v1//is/binding {}", e.getMessage());
            return fail(StatusCode.CODE_500, "用户未绑定");
        }
    }

    /**
     * 微信是否已经绑定
     *
     * @return HismartResponse
     */
  /*  @GetMapping("/athorized_landing")
    public HismartResponse athorizedLanding(TUser iTUser,HttpServletRequest request) {
        try {



        } catch (Exception e) {
            log.error("/administration/tuser/api/v1//is/binding {}", e.getMessage());
            return fail(StatusCode.CODE_500, "用户未绑定");
        }
    }*/

    private String saveTokenToRedis(TUser user, JWTToken token, HttpServletRequest request)   {
        String ip = IPUtil.getIpAddr(request);
        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getWxNumber());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(StringCode.ONE, ip));
        this.redisService.set(HismartConstant.TOKEN_CACHE_PREFIX + token.getToken() + StringPool.DOT + ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);
        return activeUser.getId();
    }

}

