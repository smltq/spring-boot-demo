# 使用 Docker 部署 Spring Boot 项目

## Docker 介绍

Docker 属于 Linux 容器的一种封装，提供简单易用的容器使用接口。它是目前最流行的 Linux 容器解决方案。

Docker 将应用程序与该程序的依赖，打包在一个文件里面。运行这个文件，就会生成一个虚拟容器。程序在这个虚拟容器里运行，就好像在真实的物理机上运行一样。有了 Docker，就不用担心环境问题。

总体来说，Docker 的接口相当简单，用户可以方便地创建和使用容器，把自己的应用放入容器。容器还可以进行版本管理、复制、分享、修改，就像管理普通的代码一样。

### Docker 的主要用途

- （1）提供一次性的环境。比如，本地测试他人的软件、持续集成的时候提供单元测试和构建的环境。

- （2）提供弹性的云服务。因为 Docker 容器可以随开随关，很适合动态扩容和缩容。

- （3）组建微服务架构。通过多个容器，一台机器可以跑多个服务，因此在本机就可以模拟出微服务架构。

### Docker 的安装（CentOS环境）

- 安装命令

```cfml
yum install docker
```

- 安装完成后，使用下面的命令来启动 docker 服务，并将其设置为开机启动

```cfml
service docker start
chkconfig docker on

#LCTT 译注：此处采用了旧式的 sysv 语法，如采用CentOS 7中支持的新式 systemd 语法，如下：
systemctl  start docker.service
systemctl  enable docker.service
```

- 使用Docker 中国加速器

```cfml
vi  /etc/docker/daemon.json

#添加后：
{
    "registry-mirrors": ["https://registry.docker-cn.com"],
    "live-restore": true
}
```

- 重新启动docker

```cfml
systemctl restart docker
```

输入 docker version 返回版本信息则安装正常。

## 安装JDK

```cfml
yum -y install java-1.8.0-openjdk*
```

配置环境变量 打开 vim /etc/profile 添加一下内容

```cfml
export JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk-1.8.0.242.b08-0.el7_7.x86_64
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

修改完成之后，使其生效

```cfml
source /etc/profile
```

输入java -version 返回版本信息则安装正常。

## 安装 MAVEN

下载：https://mirror.bit.edu.cn/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz

```cfml
## 解压
tar vxf apache-maven-3.6.3-bin.tar.gz
## 移动
mv apache-maven-3.6.3 /usr/local/maven3
```

修改环境变量， 在/etc/profile

```cfml
MAVEN_HOME=/usr/local/maven3
export MAVEN_HOME
export PATH=${PATH}:${MAVEN_HOME}/bin

```

执行source /etc/profile使环境变量生效。

输入mvn -version 返回版本信息则安装正常。

    到止，通过docker,jdk,maven的安装，整个构建环境算配置完成了。
    

## 创建 spring boot 项目

### pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.4.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.easy</groupId>
    <artifactId>spring-boot-docker</artifactId>
    <version>1.0</version>
    <packaging>jar</packaging>

    <name>spring-boot-docker</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>1.8</java.version>
        <docker.image.prefix>springboot</docker.image.prefix>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <!-- Docker maven plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
                    <dockerDirectory>src/main/docker</dockerDirectory>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>
            <!-- Docker maven plugin -->
        </plugins>
    </build>

</project>

```

### Dockerfile 配置

```cfml
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8282
ADD spring-boot-docker-1.0.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
```

Dockerfile 文件介绍，构建 Jdk 基础环境，添加 Spring Boot Jar 到镜像中:

- FROM，表示使用 Jdk8 环境 为基础镜像，如果镜像不是本地的会从 DockerHub 进行下载
- VOLUME，VOLUME 指向了一个/tmp的目录，由于 Spring Boot 使用内置的Tomcat容器，Tomcat 默认使用/tmp作为工作目录。这个命令的效果是：在宿主机的/var/lib/docker目录下创建一个临时文件并把它链接到容器中的/tmp目录
- EXPOSE，EXPOSE 指令是声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务。在 Dockerfile 中写入这样的声明有两个好处，一个是帮助镜像使用者理解这个镜像服务的守护端口，以方便配置映射；另一个用处则是在运行时使用随机端口映射时，也就是 docker run -P 时，会自动随机映射 EXPOSE 的端口。
- ADD，拷贝文件并且重命名
- ENTRYPOINT，为了缩短 Tomcat 的启动时间，添加java.security.egd的系统属性指向/dev/urandom作为 ENTRYPOINT

### 其它示例代码

DockerController.java
```java
@RestController
public class DockerController {

    @RequestMapping("/")
    public String index() {
        return "Hello Docker!";
    }
}
```

DockerApplication.java
```java
@SpringBootApplication
public class DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }
}

```

application.properties
```properties
server.port=8282
```

## 使用 Docker 部署 Spring Boot 项目

将项目 docker 拷贝至服务器中，进入项目路径下进行打包测试。

```cfml
#打包
mvn package
#启动
java -jar target/spring-boot-docker-1.0.jar
```

看到 Spring Boot 的启动日志后表明环境配置没有问题，接下来我们使用 DockerFile 构建镜像。

```cfml
mvn package docker:build
```

第一次构建可能有点慢，当看到以下内容的时候表明构建成功：

```cfml
Step 1/5 : FROM openjdk:8-jdk-alpine
 ---> a3562aa0b991
Step 2/5 : VOLUME /tmp
 ---> Using cache
 ---> d070c927d0a7
Step 3/5 : EXPOSE 8282
 ---> Using cache
 ---> b16d14267527
Step 4/5 : ADD spring-boot-docker-1.0.jar app.jar
 ---> c4ddc409b458
Removing intermediate container c58c986e6b9a
Step 5/5 : ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app.jar
 ---> Running in d2b61fddd616
 ---> 13c600d3f625
Removing intermediate container d2b61fddd616
Successfully built 13c600d3f625
[INFO] Built springboot/spring-boot-docker
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 7.273 s
[INFO] Finished at: 2020-03-25T10:05:04+08:00
[INFO] ------------------------------------------------------------------------

```

使用docker images命令查看构建好的镜像：

```cfml
# docker images
REPOSITORY                      TAG                 IMAGE ID            CREATED             SIZE
springboot/spring-boot-docker   latest              13c600d3f625        18 minutes ago      122 MB
docker.io/openjdk               8-jdk-alpine        a3562aa0b991        10 months ago       105 MB

```

springboot/spring-boot-docker 就是我们构建好的镜像，下一步就是运行该镜像

```cfml
docker run -p 8282:8282 -t springboot/spring-boot-docker
```

启动完成之后我们使用docker ps查看正在运行的镜像：

```cfml
# docker ps
CONTAINER ID        IMAGE                           COMMAND                  CREATED             STATUS              PORTS                    NAMES
a626c3dbdb1b        springboot/spring-boot-docker   "java -Djava.secur..."   34 seconds ago      Up 34 seconds       0.0.0.0:8282->8282/tcp   suspicious_murdock

```

可以看到我们构建的容器正在在运行，访问浏览器：http://192.168.0.x:8282/,返回

```cfml
Hello Docker!
```

    说明使用 Docker 部署 Spring Boot 项目成功！

## 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/docker)
- [Docker 入门教程](https://www.ruanyifeng.com/blog/2018/02/docker-tutorial.html)
- [Spring Boot 2 (四)：使用 Docker 部署 Spring Boot](http://www.ityouknow.com/springboot/2018/03/19/spring-boot-docker.html)