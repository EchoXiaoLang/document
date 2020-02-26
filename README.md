

### 使用教程

#####前端代码


#### 后端

1. IDEA 或者 Eclipse安装lombok插件


2. 修改数据库配置，redis配置，kafka配置等待Maven下载依赖


3.创建数据库，有基础sink.sql  和log表.sql   和函数和事件表在，函数.sql里面

4.本地启动redis服务

5.先run ogg_control程序

6,然后在run  脚本程序

注释：control程序与脚本程序的关系是1对多的关系。



mvn clean package -Dmaven.test.skip=true





