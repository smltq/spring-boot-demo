# Spring Boot Mail 实现邮件发送

> 此 demo 主要演示了 Spring Boot 如何整合邮件功能，包括发送简单文本邮件。

邮件服务在开发中非常常见，比如用邮件注册账号、邮件作为找回密码的途径、用于订阅内容定期邮件推送等等，下面就简单的介绍下邮件实现方式。

## 代码实现

1.pom.xml依赖

```xml
    <dependencies>
        <!-- Spring Boot 邮件依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

2.application.yml配置

```yaml
spring:
  mail:
    host: smtp.qq.com
    username: 271657370@qq.com
    password: #*****  //授权码，注意这里不是直接填密码
    default-encoding: UTF-8
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
      mail.smtp.starttls.required: true
      mail.smtp.ssl.enable: true
      mail.display.sendmail: spring-boot-demo
```

注意

- spring.mail.username指的是发送邮件的QQ邮箱
- spring.mail.password指的是该邮箱的授权码，而不是该邮箱的密码

3.编写简单邮件发送接口

```java
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送文本邮件
     *
     * @param to      收件人地址
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param cc      抄送地址
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setCc(cc);

        mailSender.send(message);
    }
}
```

4.编写测试用例

```java
public class MailServiceTest extends EmailApplicationTests {
    @Autowired
    private MailService mailService;

    /**
     * 测试简单邮件
     */
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("smltq@126.com", "这是一封简单邮件", "这是一封普通的SpringBoot测试邮件");
    }
}
```
运行测试用例,发现成功收到邮件了

## 可能会遇到的异常

```cfml

org.springframework.mail.MailAuthenticationException: Authentication failed; nested exception is javax.mail.AuthenticationFailedException: [EOF]

	at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:440)
	at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:323)
	at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:312)
	at com.easy.email.service.impl.MailServiceImpl.sendSimpleMail(MailServiceImpl.java:35)
	at com.easy.email.service.MailServiceTest.sendSimpleMail(MailServiceTest.java:16)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.springframework.test.context.junit4.statements.RunBeforeTestExecutionCallbacks.evaluate(RunBeforeTestExecutionCallbacks.java:74)
	at org.springframework.test.context.junit4.statements.RunAfterTestExecutionCallbacks.evaluate(RunAfterTestExecutionCallbacks.java:84)
	at org.springframework.test.context.junit4.statements.RunBeforeTestMethodCallbacks.evaluate(RunBeforeTestMethodCallbacks.java:75)
	at org.springframework.test.context.junit4.statements.RunAfterTestMethodCallbacks.evaluate(RunAfterTestMethodCallbacks.java:86)
	at org.springframework.test.context.junit4.statements.SpringRepeat.evaluate(SpringRepeat.java:84)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:251)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.runChild(SpringJUnit4ClassRunner.java:97)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.springframework.test.context.junit4.statements.RunBeforeTestClassCallbacks.evaluate(RunBeforeTestClassCallbacks.java:61)
	at org.springframework.test.context.junit4.statements.RunAfterTestClassCallbacks.evaluate(RunAfterTestClassCallbacks.java:70)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.springframework.test.context.junit4.SpringJUnit4ClassRunner.run(SpringJUnit4ClassRunner.java:190)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
Caused by: javax.mail.AuthenticationFailedException: [EOF]
	at com.sun.mail.smtp.SMTPTransport$Authenticator.authenticate(SMTPTransport.java:965)
	at com.sun.mail.smtp.SMTPTransport.authenticate(SMTPTransport.java:876)
	at com.sun.mail.smtp.SMTPTransport.protocolConnect(SMTPTransport.java:780)
	at javax.mail.Service.connect(Service.java:366)
	at org.springframework.mail.javamail.JavaMailSenderImpl.connectTransport(JavaMailSenderImpl.java:518)
	at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:437)
	... 34 more

```

这是因为还没有去邮箱那里设置开启POP3/SMTP服务，操作步骤如下：

- 邮箱—>设置—>账户，然后拉下来，找到POP3/IMAP/SMTP/Exchange/CardDAV/CalDAV服务

- 开启服务：POP3/SMTP服务

- 开启好了POP3/SMTP服务之后，会有一个授权码，这个授权码就是application.yml的spring.mail.password

## 参考

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/email)
- [Spring Boot Email 官方文档](https://docs.spring.io/spring-boot/docs/2.1.9.RELEASE/reference/htmlsingle/#boot-features-email)
- [JavaMail官方文档](https://docs.spring.io/spring/docs/5.1.10.RELEASE/spring-framework-reference/integration.html#mail)