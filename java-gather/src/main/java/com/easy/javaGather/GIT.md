# GIT 命令

## 基础命令

### 用户设置

    git config --global user.name "Your Name"
    git config --global user.email "email@example.com"
 
### 初始化仓库

    git init
 
### 把文件添加到仓库

    git add filename
 
### 把文件提交到仓库

    git commit -m "comment message"

初始化一个Git仓库，使用git init命令。

添加文件到Git仓库，分两步：
- 第一步，使用命令git add ，注意，可反复多次使用，添加多个文件；
- 第二步，使用命令git commit，完成。
 
### 运行git status命令看看结果

    git status
 
### 看看具体修改了什么内容

    git diff filename
 
- 要随时掌握工作区的状态，使用git status命令。
- 如果git status告诉你有文件被修改过，用git diff可以查看修改内容
    
## 多远程仓一次提交支持

### 1.添加远程仓
    
    git remote add gitee 你的gitee项目地址


查看远程仓，发现两个远程仓了

    git remote


此时推送代码需要推送两次

    git push gitRepo
    git push giteeRepo


### 2.添加推送url地址

    git remote set-url --add origin 你的gitee项目地址
    
添加完成好，只要git push就能提交到两个仓库了，比如github和gitee
## Git 回滚代码

### 日志查看

    git reflog或git log
    
### 代码回退

    git reset --hard HEAD        回退到上个版本
    git reset --hard commit_id    退到/进到 指定commit_id
    
默认参数 -soft,所有commit的修改都会退回到git缓冲区
参数--hard，所有commit的修改直接丢弃

### 推送到远程

    git push origin HEAD --force
    
## 资料

- [阮一峰常用 Git 命令清单](https://www.ruanyifeng.com/blog/2015/12/git-cheat-sheet.html)