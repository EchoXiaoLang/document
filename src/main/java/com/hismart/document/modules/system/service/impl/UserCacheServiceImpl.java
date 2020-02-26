package com.hismart.document.modules.system.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hismart.document.modules.system.service.RoleService;
import com.hismart.document.common.domain.HismartConstant;
import com.hismart.document.common.service.CacheService;
import com.hismart.document.modules.system.entity.SinkMenu;
import com.hismart.document.modules.system.entity.SinkRole;
import com.hismart.document.modules.system.entity.SinkUser;
import com.hismart.document.modules.system.mapper.SinkUserMapper;
import com.hismart.document.modules.system.service.MenuService;
import com.hismart.document.modules.system.service.UserCacheService;
import org.apache.catalina.Role;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Echo_Sxl on 2019/8/9 10:35
 * @version 1.0
 */
@Service
public class UserCacheServiceImpl implements UserCacheService {
    private final CacheService redisService;

    private final ObjectMapper mapper;
    private final SinkUserMapper sinkUserMapper;
    private final RoleService roleService;
    private final MenuService menuService;

    public UserCacheServiceImpl(CacheService redisService, ObjectMapper mapper, SinkUserMapper sinkUserMapper, RoleService roleService, MenuService menuService) {
        this.redisService = redisService;
        this.mapper = mapper;
        this.sinkUserMapper = sinkUserMapper;
        this.roleService = roleService;
        this.menuService = menuService;
    }

    @Override
    public SinkUser getUser(String username) throws Exception {
        String userString = this.redisService.get(HismartConstant.USER_CACHE_PREFIX + username);
        if (StringUtils.isBlank(userString)) {
            throw new Exception();
        } else {
            return this.mapper.readValue(userString, SinkUser.class);
        }
    }

    @Override
    public List<SinkRole> getRoles(String username) throws Exception {
        String roleListString = this.redisService.get(HismartConstant.USER_ROLE_CACHE_PREFIX + username);
        if (StringUtils.isBlank(roleListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, Role.class);
            return this.mapper.readValue(roleListString, type);
        }
    }

    @Override
    public List<SinkMenu> getPermissions(String username) throws Exception {
        String permissionListString = this.redisService.get(HismartConstant.USER_PERMISSION_CACHE_PREFIX + username);
        if (StringUtils.isBlank(permissionListString)) {
            throw new Exception();
        } else {
            JavaType type = mapper.getTypeFactory().constructParametricType(List.class, SinkMenu.class);
            return this.mapper.readValue(permissionListString, type);
        }
    }


    @Override
    public void saveUser(SinkUser user) throws Exception {
        String username = user.getUsername();
        this.deleteUser(username);
        redisService.set(HismartConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveUser(String username) throws Exception {
        SinkUser user = sinkUserMapper.findDetail(username);
        this.deleteUser(username);
        redisService.set(HismartConstant.USER_CACHE_PREFIX + username, mapper.writeValueAsString(user));
    }

    @Override
    public void saveRoles(String username) throws Exception {
        List<SinkRole> roleList = this.roleService.findUserRole(username);
        if (!roleList.isEmpty()) {
            this.deleteRoles(username);
            redisService.set(HismartConstant.USER_ROLE_CACHE_PREFIX + username, mapper.writeValueAsString(roleList));
        }

    }

    @Override
    public void savePermissions(String username) throws Exception {
        List<SinkMenu> permissionList = this.menuService.findUserPermissions(username);
        if (!permissionList.isEmpty()) {
            this.deletePermissions(username);
            redisService.set(HismartConstant.USER_PERMISSION_CACHE_PREFIX + username, mapper.writeValueAsString(permissionList));
        }
    }


    @Override
    public void deleteUser(String username) throws Exception {
        username = username.toLowerCase();
        redisService.remove(HismartConstant.USER_CACHE_PREFIX + username);
    }

    @Override
    public void deleteRoles(String username) throws Exception {
        username = username.toLowerCase();
        redisService.remove(HismartConstant.USER_ROLE_CACHE_PREFIX + username);
    }

    @Override
    public void deletePermissions(String username) throws Exception {
        username = username.toLowerCase();
        redisService.remove(HismartConstant.USER_PERMISSION_CACHE_PREFIX + username);
    }

}
