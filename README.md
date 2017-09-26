# SpringMVC 概要和环境配置

### 一、MVC
MVC是Model(模型)、View(视图)、控制器(Controller)的缩写。是一种软件的架构模式，使业务逻辑、数据、界面显示分离的方法组织代码，将业务逻辑聚集到一个部件里面，在改进和个性化定制界面及用户交互的同时，不需要重新编写业务逻辑。

![MVC架构图](http://ou6wcfapi.bkt.clouddn.com/17-9-26/65272154.jpg)

### 二、SpringMVC
SpringMVC是Spring Framework的一部分，是基于Java实现的MVC的轻量级Web框架
SpringMVC的总结起来：
1. 简洁、高效
2. 清晰的角色划分
3. 强大的配置等等

### 三、环境配置和使用

#### 3.1使用IDEA 新建一个Maven项目
选中webapp的模板，如图所示

![Maven模板](http://ou6wcfapi.bkt.clouddn.com/17-9-26/57809499.jpg)

下一步后 填写一些[mavan](https://maven.apache.org/)相关的信息,然后一直next。

![mavan相关的信息](http://ou6wcfapi.bkt.clouddn.com/17-9-26/80085687.jpg)

#### 3.2 导入相关依赖

修改pom.xml依赖，如下
```
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.you</groupId>
    <artifactId>spring-mvc2</artifactId>
    <packaging>war</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>spring-mvc2 Maven Webapp</name>
    <url>http://maven.apache.org</url>
    <build>
        <finalName>spring-mvc2</finalName>
    </build>

    <properties>
        <project.build.sourceEncodeing>UTF-8</project.build.sourceEncodeing>
        <spring.version>4.2.5.RELEASE</spring.version>
    </properties>
    <dependencies>

        <!--Spring框架核心库-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!--Spring-MVC-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!--JSTL-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```
然后点击软件右下角的import Changes,导入相关依赖。第一次导入要花不少时间，耐心等待。

#### 3.3 修改web.xml配置文件,注册中心控制器DispatcherServlet
Spring MVC框架像许多其他MVC框架一样, 请求驱动,围绕一个中心Servlet分派请求及提供其他功能，DispatcherServlet是一个实际的Servlet (它继承自HttpServlet 基类)。当发起请求时被前置的控制器拦截到请求，根据请求参数生成代理请求，找到请求对应的实际控制器，控制器处理请求，创建数据模型，访问数据库，将模型响应给中心控制器，控制器使用模型与视图渲染视图结果，将结果返回给中心控制器，再将结果返回给请求者。如下配置：
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
		  http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"
         version="3.0">
    <display-name>Archetype Created Web Application</display-name>
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>

        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--默认名称为ServletName-servlet.xml -->
            <param-value>WEB-INF/spring/springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动顺序，数字越小，启动越早-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!--默认拦截所有请求-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

其中contextConfigLocation对用的属性中指定SpringMVC配置文件的所在路径
我们可以把它放在src文件的Resources文件夹下，这样配置`<param-value>classpath*:springmvc-servlet.xml</param-value>`
也可以放在WEB-INF目录下。

#### 3.4、添加Spring MVC配置文件
如下所示：
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-4.0.xsd
                        http://www.springframework.org/schema/mvc
                        http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
                        http://www.springframework.org/schema/aop
                        http://www.springframework.org/schema/aop/spring-aop-4.2.xsd">

    <!--自动扫描包，实现支持注解的IOC-->
    <context:component-scan base-package="com.you.springmvc2"/>
    <context:annotation-config/>

    <!--SpringMVC不处理静态资源-->
    <mvc:default-servlet-handler/>

    <!--支持MVC注解驱动-->
    <mvc:annotation-driven/>


    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver"
          id="internalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/view/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

</beans>

```
其中视图配置我放在了WEB-INF/view(要新建)目录下，这样视图不会被外部访问，你也可以放在其他目录下，改下配置即可。

#### 3.5、创建控制器
在src/main 文件夹下新建java文件夹，指定java为Sources文件(在project structure中，快捷键ctrl+Alt+shift+S)。其中Model是视图模型 ,返回字符串 home 为去掉后缀的jsp文件名

```
@Controller
@RequestMapping("/view")
public class Test {

    @RequestMapping("/test")
    public String view1(Model model){
        model.addAttribute("message","你好");
        return "home";
    }
}

```
#### 3.6、创建视图
在view文件下新建home.jsp文件，内容如下
```
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
${message}
</body>
</html>
```
#### 3.7、测试内容
启动Tomcat，在浏览器中输入http://localhost:8080/view/test ，如成功，如图所示

![success](http://ou6wcfapi.bkt.clouddn.com/17-9-26/29122044.jpg)


