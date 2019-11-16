# Nginx

## 安装 Nginx

    # 添加 Nginx 源
    sudo rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
    
    # 安装 Nginx
    sudo yum install -y nginx
    
    # 启动 Nginx
    sudo systemctl start nginx.service
    
    # 设置开机自启 Nginx
    sudo systemctl enable nginx.service

## 常用命令

    # 检查配置是否有误
    sudo nginx -t
    
    # 重载 Nginx 配置
    sudo nginx -s reload
    
## 配置 SSL 证书
    
    # 安装 certbot 以及 certbot nginx 插件
    sudo yum install certbot python2-certbot-nginx -y
    
    # 执行配置，中途会询问你的邮箱，如实填写即可
    sudo certbot --nginx
    
    # 自动续约
    sudo certbot renew --dry-run

## 静态网站配置

```cfml
server {
    listen       8091;                      #端口
    server_name  tqlin.cn www.tqlin.cn;     #域名
    location / {
        root   /opt/webapps/leaf/public;    #网站目录
        index  index.html index.htm;
    }
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
```

## 资料

- [Java问题收集](https://github.com/smltq/spring-boot-demo/tree/master/java-gather)