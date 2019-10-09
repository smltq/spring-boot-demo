# Java命令

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

## 返回上一级目录

cd ..

## 切换目录

cd tomcat/logs



