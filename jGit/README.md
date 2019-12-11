# JGit--将 Git 嵌入你的应用

如果你想在一个 Java 程序中使用 Git ，有一个功能齐全的 Git 库，那就是 JGit 。 JGit 是一个用 Java 写成的功能相对健全的 Git 的实现，它在 Java 社区中被广泛使用。 JGit 项目由 Eclipse 维护，[它的主页](http://www.eclipse.org/jgit)。

## 依赖添加

有很多种方式可以将 JGit 依赖加入到你的项目，并依靠它去写代码。 最简单的方式也许就是使用 Maven 。你可以通过在你的 pom.xml 文件里的 <dependencies> 标签中增加像下面这样的片段来完成这个整合。

```xml
    <dependency>
        <groupId>org.eclipse.jgit</groupId>
        <artifactId>org.eclipse.jgit</artifactId>
        <version>5.5.1.201910021850-r</version>
    </dependency>
```

在你读到这段文字时 version 很可能已经更新了，所以请浏览 http://mvnrepository.com/artifact/org.eclipse.jgit/org.eclipse.jgit 以获取最新的仓库信息。 当这一步完成之后， Maven 就会自动获取并使用你所需要的 JGit 库。

## 项目实践

在搭建[我的博客](https://tqlin.cn/)的过程中，因为该博客是部署在自己的服务器上，需要在[ci](https://travis-ci.org/)自动编译完成后，实现自动部署到我的服务器上（该步实现的方式很多，通过开放git接口，有编译部署的时候自动拉取到我的服务器就是其中的一个方法）

以下主要使用了pull拉取方法
```java
package com.easy.jGit.controller;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@Slf4j
public class JGitController {

    /**
     * git仓路径
     */
    final String patch = "/opt/webapps/blog/.git";

    /**
     * 代码分支
     */
    final String branch = "origin/gh-pages";

    /**
     * 拉取
     *
     * @return
     */
    @RequestMapping("/pull")
    public String pull() {
        String result;
        Repository repo = null;
        try {
            repo = new FileRepository(new File(patch));
            Git git = new Git(repo);

            log.info("开始重置");
            //重置
            git.reset()
                    .setMode(ResetCommand.ResetType.HARD)
                    .setRef(branch).call();

            log.info("开始拉取");

            //拉取
            git.pull()
                    .setRemote("origin")
                    .setRemoteBranchName("gh-pages")
                    .call();
            result = "拉取成功!";
            log.info(result);
        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
        return result;
    }

    /**
     * 重置
     *
     * @return
     */
    @RequestMapping("/reset")
    public String reset() {
        String result;

        Repository repo = null;
        try {
            repo = new FileRepository(new File(patch));
            Git git = new Git(repo);
            git.reset().setMode(ResetCommand.ResetType.HARD).setRef(branch).call();
            result = "重置成功!";

        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
        return result;
    }

    /**
     * 恢复
     */
    @RequestMapping("/revert")
    public String revert() {
        String result;

        Repository repo = null;
        try {
            repo = new FileRepository(new File(patch));
            Git git = new Git(repo);
            git.revert().call();
            result = "恢复成功!";

        } catch (Exception e) {
            result = e.getMessage();
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
        return result;
    }

    /**
     * 克隆
     *
     * @return
     */
    @RequestMapping("/clone")
    public String clone() {
        String result;
        try {
            Git.cloneRepository()
                    .setURI("https://github.com/smltq/blog.git")
                    .setDirectory(new File("/blog"))
                    .call();
            result = "克隆成功了!";
        } catch (GitAPIException e) {
            result = e.getMessage();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 状态
     */
    @RequestMapping("/status")
    public static void status() {
        File RepoGitDir = new File("/blog/.git");
        Repository repo = null;
        try {
            repo = new FileRepository(RepoGitDir.getAbsolutePath());
            Git git = new Git(repo);
            Status status = git.status().call();
            log.info("Git Change: " + status.getChanged());
            log.info("Git Modified: " + status.getModified());
            log.info("Git UncommittedChanges: " + status.getUncommittedChanges());
            log.info("Git Untracked: " + status.getUntracked());
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            if (repo != null) {
                repo.close();
            }
        }
    }
}
```

[.travis.yml 源文件](https://github.com/smltq/blog/blob/master/.travis.yml)
```yaml
language: node_js # 设置语言
node_js: stable   # 设置相应版本
cache:
  apt: true
  directories:
    - node_modules # 缓存不经常更改的内容
before_install:
  - export TZ='Asia/Shanghai' # 更改时区
  - npm install hexo-cli -g
  #- chmod +x ./publish-to-gh-pages.sh  # 为shell文件添加可执行权限
install:
  - npm install # 安装hexo及插件
script:
  - hexo clean  # 清除
  - hexo g      # 生成
after_script:
  - git clone https://${GH_REF} .deploy_git
  - cd .deploy_git
  - git checkout master:gh-pages
  - cd ../
  - mv .deploy_git/.git/ ./public/
  - cd ./public
  - git config user.name  "tqlin"
  - git config user.email "smltq@126.com"
  # add commit timestamp
  - git add .
  - git commit -m "Travis CI Auto Builder at `date +"%Y-%m-%d %H:%M"`"
  - git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:gh-pages && curl http://49.235.170.100:8787/pull
  - curl http://49.235.170.100:8787/pull   #这里调用上面实现的拉取接口
branches:
  only:
    - master # 只监测master分支
env:
  global:
    - GH_REF: github.com/smltq/blog.git #设置GH_REF
```

## 基本概念

- Repository 包括所有的对象和引用，用来管理源码

- AnyObjectId 表示SHA1对象，可以获得SHA1的值，进而可以获得git对象

- Ref 引用对象，表示.git/refs下面的文件引用 Ref HEAD = repository.getRef("refs/heads/master");

- RevWalk 可以遍历提交对象，并按照顺序返回提交对象

- RevCommit 代表一个提交对象

- RevTag 代表标签对象

- RevTree 代表树对象

## 其它常用命令

大多数 JGit 会话会以 Repository 类作为起点，你首先要做的事就是创建一个它的实例。 对于一个基于文件系统的仓库来说（JGit 允许其它的存储模型），用 FileRepositoryBuilder 完成它。

```java
// 创建一个新仓库
Repository newlyCreatedRepo = FileRepositoryBuilder.create(
    new File("/tmp/new_repo/.git"));
newlyCreatedRepo.create();

// 打开一个存在的仓库
Repository existingRepo = new FileRepositoryBuilder()
    .setGitDir(new File("my_repo/.git"))
    .build();
```

当你拥有一个 Repository 实例后，你就能对它做各种各样的事。比如：

```java
// 获取引用
Ref master = repo.getRef("master");

// 获取该引用所指向的对象
ObjectId masterTip = master.getObjectId();

// Rev-parse
ObjectId obj = repo.resolve("HEAD^{tree}");

// 装载对象原始内容
ObjectLoader loader = repo.open(masterTip);
loader.copyTo(System.out);

// 创建分支
RefUpdate createBranch1 = repo.updateRef("refs/heads/branch1");
createBranch1.setNewObjectId(masterTip);
createBranch1.update();

// 删除分支
RefUpdate deleteBranch1 = repo.updateRef("refs/heads/branch1");
deleteBranch1.setForceUpdate(true);
deleteBranch1.delete();

// 配置
Config cfg = repo.getConfig();
String name = cfg.getString("user", null, "name");
```

### 提交命令

AddCommand可以把工作区的内容添加到暂存区。
```java
Git git = Git.open(new File("D:\\source-code\\temp\\.git"));
git.add().addFilepattern(".").call(); // 相当与git add -A添加所有的变更文件git.add().addFilepattern("*.java")这种形式是不支持的
git.add().addFilepattern("src/main/java/").call(); // 添加目录，可以把目录下的文件都添加到暂存区
//jgit当前还不支持模式匹配的方式，例如*.java
```

CommitCommand用于提交操作

```java
Git git =Git.open(new File("D:\\source-code\\temp\\user1\\.git"));
CommitCommand commitCommand = git.commit().setMessage("master 23 commit").setAllowEmpty(true);
commitCommand.call();
```

### status命令

```java
    Git git = Git.open(new File("D:\\source-code\\temp-1\\.git"));
    Status status = git.status().call();        //返回的值都是相对工作区的路径，而不是绝对路径
    status.getAdded().forEach(it -> System.out.println("Add File :" + it));      //git add命令后会看到变化
    status.getRemoved().forEach(it -> System.out.println("Remove File :" + it));  ///git rm命令会看到变化，从暂存区删除的文件列表
    status.getModified().forEach(it -> System.out.println("Modified File :" + it));  //修改的文件列表
    status.getUntracked().forEach(it -> System.out.println("Untracked File :" + it)); //工作区新增的文件列表
    status.getConflicting().forEach(it -> System.out.println("Conflicting File :" + it)); //冲突的文件列表
    status.getMissing().forEach(it -> System.out.println("Missing File :" + it));    //工作区删除的文件列表
```

### log命令

LogCommand相当于git log命令

```java
//提取某个作者的提交，并打印相关信息
Git git = Git.open(new File("D:\\source-code\\temp-1\\.git"));
DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Iterable<RevCommit> results = git.log().setRevFilter(new RevFilter() {
    @Override
    public boolean include(RevWalk walker, RevCommit cmit)
       throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
        return cmit.getAuthorIdent().getName().equals("xxxxx dsd");
    }

    @Override
    public RevFilter clone() {
    return this;
            }
        }).call();
results.forEach(commit -> {
    PersonIdent authoIdent = commit.getAuthorIdent();
    System.out.println("提交人：  " + authoIdent.getName() + "     <" + authoIdent.getEmailAddress() + ">");
    System.out.println("提交SHA1：  " + commit.getId().name());
    System.out.println("提交信息：  " + commit.getShortMessage());
    System.out.println("提交时间：  " + format.format(authoIdent.getWhen()));
});
```

### fetch命令

fetch命令
```java
Repository rep = new FileRepository("D:\\source-code\\temp-1\\.git");
Git git = new Git(rep);
git.pull().setRemote("origin").call();
//fetch命令提供了setRefSpecs方法，而pull命令并没有提供，所有pull命令只能fetch所有的分支
git.fetch().setRefSpecs("refs/heads/*:refs/heads/*").call();
```

### push命令

而PushCommand和git push相同，一般都需要我们提供用户名和密码，需要用到CredentialsProvider类
```java
Repository rep = new FileRepository("D:\\source-code\\temp-1\\.git");
Git git = new Git(rep);
git.push().setCredentialsProvider(new UsernamePasswordCredentialsProvider("myname", "password")).call();
```

### clone命令

CloneCommand等价与git clone命令
```java
Git.cloneRepository().setURI("https://admin@localhost:8443/r/game-of-life.git")
                .setDirectory(new File("D:\\source-code\\temp-1")).call();
```

### RevWalk API

以下代码实现这样一个功能，查找某个文件的历史记录，并把每个提交的文件内容打印出来。

```java
 DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 Repository repository = new RepositoryBuilder().setGitDir(new File("D:\\source-code\\temp-1\\.git")).build();
 try (RevWalk walk = new RevWalk(repository)) {
     Ref head = repository.findRef("HEAD");
     walk.markStart(walk.parseCommit(head.getObjectId())); // 从HEAD开始遍历，
     for (RevCommit commit : walk) {
         RevTree tree = commit.getTree();

         TreeWalk treeWalk = new TreeWalk(repository, repository.newObjectReader());
         PathFilter f = PathFilter.create("pom.xml");
         treeWalk.setFilter(f);
         treeWalk.reset(tree);
         treeWalk.setRecursive(false);
         while (treeWalk.next()) {
             PersonIdent authoIdent = commit.getAuthorIdent();
             System.out.println("提交人： " + authoIdent.getName() + " <" + authoIdent.getEmailAddress() + ">");
             System.out.println("提交SHA1： " + commit.getId().name());
             System.out.println("提交信息： " + commit.getShortMessage());
             System.out.println("提交时间： " + format.format(authoIdent.getWhen()));

             ObjectId objectId = treeWalk.getObjectId(0);
             ObjectLoader loader = repository.open(objectId);
             loader.copyTo(System.out);              //提取blob对象的内容
         }
     }
 }
```

其它更多命令参考官网

## 资料

- [JGit 示例源码](https://github.com/smltq/spring-boot-demo/blob/master/jGit)
- [JGit官方文档](https://wiki.eclipse.org/JGit/User_Guide)