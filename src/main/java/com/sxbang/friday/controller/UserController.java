package com.sxbang.friday.controller;

import com.sxbang.friday.base.result.PageTableRequest;
import com.sxbang.friday.base.result.ResponseCode;
import com.sxbang.friday.base.result.Results;
import com.sxbang.friday.dto.UserDto;
import com.sxbang.friday.model.SysUser;
import com.sxbang.friday.service.UserService;
import com.sxbang.friday.util.MD5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
//import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{username}")
    @ResponseBody
	public SysUser user(@PathVariable String username) {
	    log.info("UserController.user(): param ( username = " + username +" )");
		return userService.getUser(username);
	}

	@GetMapping("/list")
	@ResponseBody
	@PreAuthorize("hasAuthority('sys:user:query')")
	public Results<SysUser> getUsers(PageTableRequest request) {
		request.countOffset();
		return userService.getAllUsersByPage(request.getOffset(),request.getLimit());
	}

	@GetMapping(value = "/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
	public String addUser(Model model) {
		model.addAttribute("sysUser",new SysUser());
		return "user/user-add";
	}

	@PostMapping(value = "/add")
	@ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
	public Results<SysUser> saveUser(UserDto userDto, Integer roleId) {
		SysUser sysUser = null;
		sysUser = userService.getUser(userDto.getUsername());
		if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
			return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(),ResponseCode.USERNAME_REPEAT.getMessage());
		}
		sysUser = userService.getUserByPhone(userDto.getTelephone());
		if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
			return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
		}
		sysUser = userService.getUserByEmail(userDto.getEmail());
		if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
			return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(),ResponseCode.EMAIL_REPEAT.getMessage());
		}

		userDto.setStatus(1);
		userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
		//userDto.setPassword(MD5.crypt(userDto.getPassword()));
		return userService.save(userDto,roleId);
	}

	String pattern = "yyyy-MM-dd";

	//只需要加上下面这段即可，注意不能忘记注解
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));// CustomDateEditor为自定义日期编辑器
	}

    @GetMapping(value = "/edit")
    public String editUser(Model model, SysUser user) {
        model.addAttribute("sysUser",userService.getUserById(user.getId()));
        return "user/user-edit";
    }

    @PostMapping(value = "/edit")
    @ResponseBody
	@PreAuthorize("hasAuthority('sys:user:edit')")
    public Results<SysUser> updateUser( UserDto userDto,Integer roleId) {
        SysUser sysUser = null;
        sysUser = userService.getUser(userDto.getUsername());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.USERNAME_REPEAT.getCode(),ResponseCode.USERNAME_REPEAT.getMessage());
        }
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }
        sysUser = userService.getUserByEmail(userDto.getEmail());
        if(sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.EMAIL_REPEAT.getCode(),ResponseCode.EMAIL_REPEAT.getMessage());
        }
        return userService.updateUser(userDto,roleId);
    }

    @GetMapping(value = "/delete")
    @ResponseBody
	@PreAuthorize("hasAuthority('sys:user:del')")
    public Results<SysUser> deleteUser( UserDto userDto) {
        userService.deleteUser(userDto.getId());
        return Results.success();
    }

    @GetMapping("/findUserByFuzzyUserName")
	@ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")
	public Results<SysUser> getUserByFuzzyUserName(PageTableRequest requests, String username) {
		requests.countOffset();
		return userService.getUserByFuzzyUserNamePage(username,requests.getOffset(),requests.getLimit());
	}


}
