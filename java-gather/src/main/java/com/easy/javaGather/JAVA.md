# Java命令

## 安装JDK

### yum安装方法

    - 验证Linux系统的位数  uname -a
    - 查看yum库中都有哪些jdk版本  yum search java|grep jdk
    - 选择版本，进行安装 yum install java-1.8.0-openjdk  安装完之后，默认的安装目录是在: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64
    - 设置环境变量    vi /etc/profile
    - 进入编辑模式    i
    - 复制以下三行到文件中，按esc退出编辑模式，输入:wq保存退出（这里的JAVA_HOME以自己实际的目录为准）
        export JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64
        export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
        export PATH=$PATH:$JAVA_HOME/bin
    - 设置环境变量立即生效  source /etc/profile
    
## 运行java项目
  
    - java -jar helloworld-0.0.1.jar
    
### JDK常用命令

    - 查看JDK版本   java -version
    - 查看java执行路径    which java
    - 查看JAVA_HOME路径 echo $JAVA_HOME
    - 查看PATH路径  echo $PATH

## 查看java进程

    ps -ef|grep java

此条命令具体含义

    ps:将某个进程显示出来
    -A 显示所有程序。 
    -e 此参数的效果和指定"A"参数相同。
    -f 显示UID,PPIP,C与STIME栏位。 
    grep命令是查找

## 找到java进程id，抓取dump

    sudo -u lintq jmap -dump:format=b,file=myheap.hprof 27226

## 有时关闭服务操作不成功，可以使用kill强杀

    kill -9 7010
 
 来杀死进程，需要注意的是7010是当前tomcat进程的id 

## 打开控制台,查看日志

    tail -f catalina.out

按Ctrl+c退出