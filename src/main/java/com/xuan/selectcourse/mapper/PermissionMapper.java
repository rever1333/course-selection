package com.xuan.selectcourse.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuan.selectcourse.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Integer> findPermissionIdByRole(Integer rid);
}
