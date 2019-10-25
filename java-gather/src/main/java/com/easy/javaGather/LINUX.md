# Linux 命令(CentOS 系统)
    
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
    - 新建目录 mkdir 目录名
    - 删除已建立的目录 rmdir 目录名
    - 删除文件 rm 文件名
        -f, --force    忽略不存在的文件，从不给出提示。
        -i, --interactive 进行交互式删除
        -r, -R, --recursive   指示rm将参数中列出的全部目录和子目录均递归地删除。
        -v, --verbose    详细显示进行的步骤
    - 文件改名、移动 mv 
    这个命令的功能是移动目录或文件，引申的功能是给目录或文件重命名。当使用该命令来移动目录时，他会连同该目录下面的子目录也一同移走。
        -b ：若需覆盖文件，则覆盖前先行备份。 
        -f ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
        -i ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
        -u ：若目标文件已经存在，且 source 比较新，才会更新(update)
        -t  ： --target-directory=DIRECTORY move all SOURCE arguments into DIRECTORY，即指定mv的目标目录，该选项适用于移动多个源文件到一个目录的情况，此时目标目录在前，源文件在后。
    - 更改文件权限 chmod [可选项] <mode> <file...>
        - 数字权限使用格式 r=4，w=2，x=1; 每个文件都可以针对三个粒度，设置不同的rwx(读写执行)权限,拥有者 、群组 、其它组( u、 g 、o)的权限
        - 设置所有人可以读写及执行   chmod 777 file  (等价于  chmod u=rwx,g=rwx,o=rwx file 或  chmod a=rwx file)
        - 设置拥有者可读写，其他人不可读写执行 chmod 600 file (等价于  chmod u=rw,g=---,o=--- file 或 chmod u=rw,go-rwx file)
    - 更改文件拥有者 chown [可选项] user[:group] file...
        - 设置文件 d.key、e.scrt的拥有者设为 users 群体的tom==>chown tom:users file d.key e.scrt
        - 设置当前目录下与子目录下的所有文件的拥有者为 users 群体的James==>chown -R James:users  *         
    
## 用户操作

    - 普通用户切换为root用户：sudo su
    - root 用户切换为普通用户 ：用 login -f username （加 -f 不用输入密码）例如普通用户的用户名为hadoop，这里就是 login -f hadoop
    - 查看用户登陆历史记录：last
    - 查看系统中有哪些用户：cut -d : -f 1 /etc/passwd
    - 查看用户组：sudo cat /etc/group
    - 创建新用户：useradd centos
    - 设置密码：passwd centos
    
## 系统操作
    
    - shutdown -h now  --立即关机  
    - shutdown -h 10:53  --到10:53关机，如果该时间小于当前时间，则到隔天  
    - shutdown -r +30 'The System Will Reboot in 30 Mins'   --30分钟后重启并并发送通知给其它在线用户
    - shutdown -h +10  --10分钟后自动关机  
    - shutdown -r now  --立即重启  
    - 查看IP：ip addr 或：ifconfig
    
## 开端口相关
    
    - 查询防火墙状态   service iptables status
    - 停止防火墙 service iptables stop
    - 启动防火墙 service iptables start
    - 重启防火墙 service iptables restart
    - 永久关闭防火墙   chkconfig   iptables off
    - 永久关闭后启用   chkconfig   iptables on 
    - 清空防火墙规则   service iptables -F
    - 查看端口占用情况  netstat -pan|grep 8787
    - 查看防火墙 iptables -L
    - 保存防火墙设置   service iptables save
    - 查看端口是否连通  telnet ip地址 端口号
    - 开8787端口，-p表示协议，示例
        - iptables -I INPUT 3 -p tcp -m tcp --dport 8787 -j ACCEPT
        - iptables -A INPUT -p tcp --dport 8787 -j ACCEPT
        - iptables -A OUTPUT -p tcp --sport 8787 -j ACCEPT
    - 查看当前所有tcp端口   netstat -ntlp
    - 查看所有80端口使用情况  netstat -ntulp |grep 80
    - 查看所有3306端口使用情况    netstat -ntulp | grep 3306
    
## 开放端口

    #添加可访问端口
    sudo firewall-cmd --add-port=8787/tcp --permanent
    
    #重新加载防火墙策略    
    sudo firewall-cmd --reload
    
## 目录操作
 
### 返回上一级目录

    cd ..

### 切换目录

    cd tomcat/logs

## VIM安装

    yum -y install vim*
    
## Maven安装

    wget http://repos.fedorapeople.org/repos/dchen/apache-maven/epel-apache-maven.repo -O /etc/yum.repos.d/epel-apache-maven.repo
    yum -y install apache-maven

## 安装及启动mysql指令

    - yum install mysql mysql-server
    - /etc/init.d/mysqld start