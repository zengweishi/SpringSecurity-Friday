package com.sxbang.friday;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxbang.friday.dao.UserDao;
import com.sxbang.friday.model.SysPermission;
import com.sxbang.friday.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FridayApplicationTests {

    @Resource
    UserDao dao ;
    @Test
    public void contextLoads() {
//        SysUser user = new SysUser();
//        user.setUsername("alex-s");
//        user.setPassword(new BCryptPasswordEncoder().encode("alex-s"));
//        user.setStatus(1);
//        dao.save(user);
        dao.changePassword(new Long(1), new BCryptPasswordEncoder().encode("admin"));
    }

    @Test
    public void testJson() {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setParentId(1);
        sysPermission.setCss("css");
        sysPermission.setHref("href");
        sysPermission.setName("name");
        sysPermission.setSort(1);
        sysPermission.setType(1);
        JSONArray array = new JSONArray();
        String string = JSONObject.toJSONString(sysPermission);
        JSONObject parent = (JSONObject) JSONObject.parse(string);
        array.add(parent);

        System.out.println(array+"---------");
        JSONArray child = new JSONArray();
        parent.put("child", child);
        SysPermission sysPermission1 = new SysPermission();
        sysPermission1.setParentId(2);
        sysPermission1.setCss("css2");
        sysPermission1.setHref("href2");
        sysPermission1.setName("name2");
        sysPermission1.setSort(2);
        sysPermission1.setType(2);
        JSONArray array1 = new JSONArray();
        String string1 = JSONObject.toJSONString(sysPermission1);
        JSONObject parent1 = (JSONObject) JSONObject.parse(string1);
        child.add(parent1);

        JSONArray child1 = new JSONArray();
        parent.put("child", child1);

        SysPermission sysPermission2 = new SysPermission();
        sysPermission2.setParentId(3);
        sysPermission2.setCss("css3");
        sysPermission2.setHref("href3");
        sysPermission2.setName("name3");
        sysPermission2.setSort(3);
        sysPermission2.setType(3);
        JSONArray array2 = new JSONArray();
        String string2 = JSONObject.toJSONString(sysPermission2);
        JSONObject parent2 = (JSONObject) JSONObject.parse(string2);
        child1.add(parent2);

        System.out.println(array+"========");
    }

}
