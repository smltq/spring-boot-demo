# Java命令

## 查看java进程id

ps -ef|grep java

## 找到java进程id，抓取dump

sudo -u lintq jmap -dump:format=b,file=myheap.hprof 27226

