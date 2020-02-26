package com.hismart.document.modules.system.controller;


import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hismart.document.common.annotation.Log;
import com.hismart.document.common.controller.BaseController;
import com.hismart.document.common.domain.ActiveUser;
import com.hismart.document.common.domain.HismartConstant;
import com.hismart.document.common.domain.HismartResponse;
import com.hismart.document.common.domain.QueryRequest;
import com.hismart.document.common.exception.HismartException;
import com.hismart.document.common.utils.*;
import com.hismart.document.modules.system.manager.UserManager;
import com.hismart.document.modules.system.service.UserService;
import com.hismart.document.common.authentication.JWTToken;
import com.hismart.document.common.authentication.JWTUtil;
import com.hismart.document.common.properties.HismartProperties;
import com.hismart.document.common.service.CacheService;
import com.hismart.document.common.service.StatusCode;
import com.hismart.document.common.service.StringCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hismart.document.modules.system.entity.SinkUser;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Echo_Sxl
 * @since 2019-08-08
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/system/sink_user/api/v1")

public class UserController extends BaseController {
    private final UserService iUserService;
    private final UserManager userManager;
    private final UserService userService;
    private final HismartProperties properties;
    private final CacheService redisService;
    private final ObjectMapper mapper;

    @Autowired
    public UserController(UserService iUserService, UserManager userManager, UserService userService, HismartProperties properties, CacheService redisService, ObjectMapper mapper) {
        this.iUserService = iUserService;
        this.userManager = userManager;
        this.userService = userService;
        this.properties = properties;
        this.redisService = redisService;
        this.mapper = mapper;
    }

    private String errorMessage = "用户名或密码错误";



    /**
     * SinkUser 分页查询数据
     *
     * @param request   request
     * @param iSinkUser iSinkUser
     * @return HismartResponse
     */

    @GetMapping("/get/page")
    public HismartResponse getSinkUserPageList(QueryRequest request, SinkUser iSinkUser) {
        try {
            IPage<SinkUser> page = this.getPage(request);
            return success(this.iUserService.getSinkUserPageList(page, iSinkUser));
        } catch (Exception e) {
            log.error("/center/sink_user/api/v1/get/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * SinkUser sql分页查询数据
     *
     * @param request   request
     * @param iSinkUser iSinkUser
     * @return HismartResponse
     */
    @Log("用户查询")
    @GetMapping("/get/sql/page")
    public HismartResponse getSinkUserSqlPageList(QueryRequest request, SinkUser iSinkUser) {
        try {
            IPage<SinkUser> page = this.getPage(request);
            return success(this.iUserService.getSinkUserSqlPageList(page, iSinkUser));
        } catch (Exception e) {
            log.error("/center/sink_user/api/v1/get/sql/page {}", e.getMessage());
            return fail(StatusCode.CODE_500, "分页请求异常");
        }
    }

    /**
     * SinkUser 新增
     *
     * @param iSinkUser     iSinkUser
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @PutMapping("/add")
    @Log("用户新增")

    public HismartResponse addSinkUser(@Valid SinkUser iSinkUser, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            if (iUserService.findByName(iSinkUser.getUsername()) == null) {
                this.iUserService.createUser(iSinkUser);
                return success("新增成功");
            } else {
                return fail(StatusCode.CODE_500, "用户" + iSinkUser.getUsername() + "已经存在");
            }


        } catch (Exception e) {
            log.error("/center/sink_user/api/v1/add/  error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "新增异常");
        }
    }


    /**
     * SinkUser 修改
     *
     * @param iSinkUser     iSinkUser
     * @param bindingResult 绑定检查
     * @return HismartResponse
     */
    @Log("用户修改")
    @PostMapping("/update")
    public HismartResponse updateTUserById(@Valid SinkUser iSinkUser, BindingResult bindingResult) {
        try {
            //字段要求检查
            if (bindingResult.hasErrors()) {
                return bindingResult(bindingResult);
            }
            this.iUserService.updateUser(iSinkUser);
            return success("修改成功");
        } catch (Exception e) {
            log.error("/center/sink_user/api/v1/update/ error,error,[{}]", e.getMessage());
            return fail(StatusCode.CODE_500, "修改异常");
        }
    }

    /**
     * 用户退出登录
     */
    @Log("用户退出登录")
    @GetMapping("/logout")
    public HismartResponse logout(HttpServletRequest request) {
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        // 删除对应的 token缓存
        String ip = IPUtil.getIpAddr(request);
        String encryptToken = HismartUtil.encryptToken(token);
        redisService.remove(HismartConstant.TOKEN_CACHE_PREFIX + encryptToken + StringPool.DOT + ip);
        return success("用户退出登录成功");
    }


    /**
     * SinkUser 根据id删除
     *
     * @param userIds 主键Id
     * @return HismartResponse
     */
    @Log("用户删除")
    @DeleteMapping("/delete/{userIds}")
    public HismartResponse deleteTUserById(@NotBlank(message = "{required}") @PathVariable String userIds) {
        try {
            String[] ids = userIds.split(StringPool.COMMA);
            this.iUserService.deleteUsers(ids);
            return success("删除成功");
        } catch (Exception e) {
            log.error("/center/sink_user/api/v1/delete/{}  error,[{}]", userIds, e.getMessage());
            return fail(StatusCode.CODE_500, "删除异常");
        }
    }

    @PostMapping("/login")
    public HismartResponse login(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password, HttpServletRequest request) throws Exception {
        username = StringUtils.lowerCase(username);
        password = MD5Util.encrypt(username, password);
        SinkUser user = this.userManager.getUser(username);
        if (user == null) {
            throw new HismartException(errorMessage);
        }
        if (!StringUtils.equals(user.getPassword(), password)) {
            throw new HismartException(errorMessage);
        }
        if (SinkUser.STATUS_LOCK.equals(user.getStatus())) {
            throw new HismartException("账号已被锁定,请联系管理员！");
        }
        // 更新用户登录时间
        this.userService.updateLoginTime(username);
        //TODO 保存登录记录
        String token = HismartUtil.encryptToken(JWTUtil.sign(username, password));
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(properties.getShiro().getJwtTimeOut());
        String expireTimeStr = DateUtil.formatFullTime(expireTime);
        JWTToken jwtToken = new JWTToken(token, expireTimeStr);

        String userId = this.saveTokenToRedis(user, jwtToken, request);
        user.setId(userId);

        Map<String, Object> userInfo = this.generateUserInfo(jwtToken, user);
        return success(userInfo);
    }


    private String saveTokenToRedis(SinkUser user, JWTToken token, HttpServletRequest request) throws Exception {
        String ip = IPUtil.getIpAddr(request);
        // 构建在线用户
        ActiveUser activeUser = new ActiveUser();
        activeUser.setUsername(user.getUsername());
        activeUser.setIp(ip);
        activeUser.setToken(token.getToken());
        activeUser.setLoginAddress(AddressUtil.getCityInfo(StringCode.ONE, ip));
        // zset 存储登录用户，score 为过期时间戳
        /*   this.redisService.set(HismartConstant.ACTIVE_USERS_ZSET_PREFIX, mapper.writeValueAsString(activeUser), Long.valueOf(token.getExipreAt()));*/
        // redis 中存储这个加密 token，key = 前缀 + 加密 token + .ip
        this.redisService.set(HismartConstant.TOKEN_CACHE_PREFIX + token.getToken() + StringPool.DOT + ip, token.getToken(), properties.getShiro().getJwtTimeOut() * 1000);

        return activeUser.getId();
    }

    /**
     * 生成前端需要的用户信息，包括：
     * 1. token
     * 2. Vue Router
     * 3. 用户角色
     * 4. 用户权限
     * 5. 前端系统个性化配置信息
     *
     * @param token token
     * @param user  用户信息
     * @return UserInfo
     */
    private Map<String, Object> generateUserInfo(JWTToken token, SinkUser user) {
        String username = user.getUsername();
        Map<String, Object> userInfo = new HashMap<>(5);
        userInfo.put("token", token.getToken());
        userInfo.put("exipreTime", token.getExipreAt());

        Set<String> roles = this.userManager.getUserRoles(username);
        userInfo.put("roles", roles);

        Set<String> permissions = this.userManager.getUserPermissions(username);
        userInfo.put("permissions", permissions);


        user.setPassword("it's a secret");
        userInfo.put("user", user);
        return userInfo;
    }





}

