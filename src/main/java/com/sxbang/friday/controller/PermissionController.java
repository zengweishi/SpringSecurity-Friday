package com.sxbang.friday.controller;

import com.alibaba.fastjson.JSONArray;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.RoleDto;
import com.sxbang.friday.model.SysPermission;
import com.sxbang.friday.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("permission")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "/listAllPermission", method = RequestMethod.GET)
    @ResponseBody
    public Results<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }

    @RequestMapping(value = "/listAllPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> listAllPermissionByRoleId(RoleDto roleDto) {
        log.info(getClass().getName() + " : param =  " + roleDto);
        return permissionService.listByRoleId(roleDto.getId().intValue());
    }

    @GetMapping("/menuAll")
    @ResponseBody
    public Results getMenuAll(){
        return permissionService.getMenuAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addPermission(Model model) {
        model.addAttribute("sysPermission",new SysPermission());
        return "permission/permission-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Results<SysPermission> savePermission(@RequestBody SysPermission permission) {
        return permissionService.save(permission);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editPermission(Model model, SysPermission permission) {
        model.addAttribute("sysPermission",permissionService.getSysPermissionById(permission.getId()));
        return "permission/permission-add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Results updatePermission(@RequestBody  SysPermission permission) {
        return permissionService.updateSysPermission(permission);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Results deletePermission(SysPermission sysPermission) {
        return permissionService.delete(sysPermission.getId());
    }

    @GetMapping("/current")
    @ResponseBody
    public List<SysPermission> permissionsCurrent() {
        //LoginUser loginUser = UserUtil.getLoginUser();
        //List<SysPermission> list = loginUser.getPermissions();
        List<SysPermission> list = permissionService.getMenu();
        final List<SysPermission> permissions = list.stream().filter(l -> l.getType().equals(1)) .collect(Collectors.toList());
        List<SysPermission> firstLevel = permissions.stream().filter(p -> p.getParentId().equals(0)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(p -> { setChild(p, permissions);});

        return firstLevel;
    }
    /**
     * 设置子元素
     * 2018.06.09
     *
     * @param sysPermission
     * @param permissions
     */
    private void setChild(SysPermission sysPermission, List<SysPermission> permissions) {
        List<SysPermission> child = permissions.parallelStream().filter(a -> a.getParentId().equals(sysPermission.getId())).collect(Collectors.toList());
        sysPermission.setChild(child);
        if (!CollectionUtils.isEmpty(child)) {
            child.parallelStream().forEach(c -> {
                //递归设置子元素，多级菜单支持
                setChild(c, permissions);
            });
        }
    }


    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public Results<SysPermission> getMenu(Long userId) {
        return permissionService.getMenu(userId);
    }

}
