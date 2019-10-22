# GIT 命令
    
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



