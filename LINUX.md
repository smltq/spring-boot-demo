# Linux

## 安装JDK

### 方法一

    - 验证Linux系统的位数  uname -a
    - 打开上传文件对话框   rz
    
### 方法二

    - 查看yum库中都有哪些jdk版本  yum search java|grep jdk
    - 选择版本，进行安装 yum install java-1.8.0-openjdk  安装完之后，默认的安装目录是在: /usr/lib/jvm/java-1.8.0-openjdk.x86_64
    - 设置环境变量    vi /etc/profile
    - 进入编辑模式    i
    
## JDK常用命令

    - 查看JDK版本   java -version
    - 查看java执行路径    which java
    - 查看JAVA_HOME路径 echo $JAVA_HOME
    - 查看PATH路径  echo $PATH
    
 ## 保存命令
      
    按ESC键 跳到命令模式，然后：
     
    :w   保存文件但不退出vi
    :w file 将修改另外保存到file中，不退出vi
    :w!   强制保存，不推出vi
    :wq  保存文件并退出vi
    :wq! 强制保存文件，并退出vi
    q:  不保存文件，退出vi
    :q! 不保存文件，强制退出vi
    :e! 放弃所有修改，从上次保存文件开始再编辑
    
  ## 文件操作
    
    - 安装上传下载插件  yum install lrzsz
    - 文件上传    rz
    - 文件覆盖上传    rz -y
    - 下载文件  sz  文件名 
    
  ## 开端口相关
    
    - 查看端口占用情况    netstat -pan|grep 8787
    - 查看防火墙 iptables -L
    - 查看端口是否连通  telnet ip地址 端口号
    - 开8787端口，-p表示协议，示例
        iptables -A INPUT -p tcp --dport 8787 -j ACCEPT
        iptables -A OUTPUT -p tcp --sport 8787 -j ACCEPT
    - 保存防火墙设置   service iptables save
    - 查看当前所有tcp端口   netstat -ntlp
    - 查看所有80端口使用情况  netstat -ntulp |grep 80
    - 查看所有3306端口使用情况    netstat -ntulp | grep 3306