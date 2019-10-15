# Spring Boot 复合注解

## @SpringBootApplication 复合注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    // ... 此处省略源码
}
```

查看源码可发现，@SpringBootApplication是一个复合注解，包含了@SpringBootConfiguration，@EnableAutoConfiguration，@ComponentScan这三个注解

### @SpringBootConfiguration 注解，继承@Configuration注解，主要用于加载配置文件

@SpringBootConfiguration继承自@Configuration，二者功能也一致，标注当前类是配置类， 并会将当前类内声明的一个或多个以@Bean注解标记的方法的实例纳入到spring容器中，并且实例名就是方法名。

### @EnableAutoConfiguration 注解，开启自动配置功能

@EnableAutoConfiguration可以帮助SpringBoot应用将所有符合条件的@Configuration配置都加载到当前SpringBoot创建并使用的IoC容器。借助于Spring框架原有的一个工具类：SpringFactoriesLoader的支持，@EnableAutoConfiguration可以智能的自动配置功效才得以大功告成

### @ComponentScan 注解，主要用于组件扫描和自动装配

@ComponentScan的功能其实就是自动扫描并加载符合条件的组件或bean定义，最终将这些bean定义加载到容器中。我们可以通过basePackages等属性指定@ComponentScan自动扫描的范围，如果不指定，则默认Spring框架实现从声明@ComponentScan所在类的package进行扫描，默认情况下是不指定的，所以SpringBoot的启动类最好放在root package下。

## @RestController 控制器注解

## @GetMapping