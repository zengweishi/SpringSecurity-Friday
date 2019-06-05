# friday

#### 介绍
基于Spring Boot2.X的后台权限管理系统，适合于学习Spring Boot开发、项目毕业设计以及研发团队快速搭建项目，提供纯净的权限管理功能，可作为开发项目的脚手架，作为基础项目。

#### 软件技术
1. Spring Boot 2.1.4
2. Spring Security 5.1.5
3. Mybatis 3.5.1
4. Logback 1.2.3
5. Druid 1.1.10
6. Swagger 2.9.2
7. Lombok 1.18.6
8. X-admin 2.2
9. Thymeleaf 3.0.11
10. Layui 2.5.3
11. MySQL 5.6
12. Docker(用于提供MySQL服务) 

#### 内置功能
用户管理：用户查询、添加用户、修改用户、用户角色设置、删除用户；

角色管理：角色查询、添加角色、修改角色、角色菜单权限配置、删除角色；

菜单管理：菜单查询、添加菜单、修改菜单、删除菜单；

登录、登出：基于Spring Security的认证和授权；

Druid数据源监控功能；

Swagger接口文档功能；

修改密码；

代码自动生成：根据数据表以及自定义模板自动生成html、controller、service、serviceImpl、dao、mapper.xml文件；

#### 安装教程

1. 使用Intelij IDEA导入项目代码；
2. 将'/resources'目录下的'friday.sql'导入MySQL数据库；
3. 修改'/resources'目录下的'application.yml'文件中的数据源配置:

```
url: jdbc:mysql://localhost:12345/sxb-base?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false

username: root

password: my-secret-pw
```

4. 启动项目，访问"http://localhost:8080",输入admin/admin即可登陆成功。

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)