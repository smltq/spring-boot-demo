# Linux

## 安装JDK

### 方法一

    - 验证Linux系统的位数  uname -a
    - 安装上传下载插件  yum install lrzsz
    - 打开上传文件对话框   rz
    
### 方法二

    - 查看yum库中都有哪些jdk版本  yum search java|grep jdk
    - 选择版本，进行安装 yum install java-1.8.0-openjdk  安装完之后，默认的安装目录是在: /usr/lib/jvm/java-1.8.0-openjdk.x86_64
    - 设置环境变量    vi /etc/profile
    - 进入编辑模式    i
    
## JDK常用命令

    - 查看JDK版本：java -version
    - 查看java执行路径：which java
    - 查看JAVA_HOME路径：echo $JAVA_HOME
    
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