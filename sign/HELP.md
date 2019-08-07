# Spring Boot 防篡改、防重放攻击

## 本示例主要内容

- 请求参数防止篡改攻击
- 基于timestamp方案，防止重放攻击
- 使用swagger接口文档自动生成

## API接口设计

API接口由于需要供第三方服务调用，所以必须暴露到外网，并提供了具体请求地址和请求参数，为了防止被别有用心之人获取到真实请求参数后再次发起请求获取信息，需要采取很多安全机制。

- 需要采用https方式对第三方提供接口，数据的加密传输会更安全，即便是被破解，也需要耗费更多时间
- 需要有安全的后台验证机制，达到防参数篡改+防二次请求（本示例内容）

防止重放攻击必须要保证请求只在限定的时间内有效，需要通过在请求体中携带当前请求的唯一标识，并且进行签名防止被篡改，所以防止重放攻击需要建立在防止签名被串改的基础之上

### 防止篡改

- 客户端使用约定好的秘钥对传输参数进行加密，得到签名值sign1，并且将签名值存入headers，发送请求给服务端
- 服务端接收客户端的请求，通过过滤器使用约定好的秘钥对请求的参数（headers除外）再次进行签名，得到签名值sign2。
- 服务端对比sign1和sign2的值，如果对比一致，认定为合法请求。如果对比不一致，说明参数被篡改，认定为非法请求

### 基于timestamp的方案，防止重放

每次HTTP请求，headers都需要加上timestamp参数，并且timestamp和请求的参数一起进行数字签名。因为一次正常的HTTP请求，从发出到达服务器一般都不会超过60s，所以服务器收到HTTP请求之后，首先判断时间戳参数与当前时间相比较，是否超过了60s，如果超过了则提示签名过期（这个过期时间最好做成配置）。

一般情况下，黑客从抓包重放请求耗时远远超过了60s，所以此时请求中的timestamp参数已经失效了。
如果黑客修改timestamp参数为当前的时间戳，则sign参数对应的数字签名就会失效，因为黑客不知道签名秘钥，没有办法生成新的数字签名（前端一定要保护好秘钥和加密算法）。

## 相关核心思路代码

### 过滤器

```java
@Slf4j
@Component
/**
 * 防篡改、防重放攻击过滤器
 */
public class SignAuthFilter implements Filter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化 SignAuthFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletRequest requestWrapper = new RequestWrapper(httpRequest);

        Set<String> uriSet = new HashSet<>(securityProperties.getIgnoreSignUri());
        String requestUri = httpRequest.getRequestURI();
        boolean isMatch = false;
        for (String uri : uriSet) {
            isMatch = requestUri.contains(uri);
            if (isMatch) {
                break;
            }
        }
        log.info("当前请求的URI是==>{},isMatch==>{}", httpRequest.getRequestURI(), isMatch);
        if (isMatch) {
            filterChain.doFilter(requestWrapper, response);
            return;
        }

        String sign = requestWrapper.getHeader("Sign");
        Long timestamp = Convert.toLong(requestWrapper.getHeader("Timestamp"));

        if (StrUtil.isEmpty(sign)) {
            returnFail("签名不允许为空", response);
            return;
        }

        if (timestamp == null) {
            returnFail("时间戳不允许为空", response);
            return;
        }

        //重放时间限制（单位分）
        Long difference = DateUtil.between(DateUtil.date(), DateUtil.date(timestamp * 1000), DateUnit.MINUTE);
        if (difference > securityProperties.getSignTimeout()) {
            returnFail("已过期的签名", response);
            log.info("前端时间戳：{},服务端时间戳：{}", DateUtil.date(timestamp * 1000), DateUtil.date());
            return;
        }

        boolean accept = true;
        SortedMap<String, String> paramMap;
        switch (requestWrapper.getMethod()) {
            case "GET":
                paramMap = HttpUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            case "POST":
            case "PUT":
            case "DELETE":
                paramMap = HttpUtil.getBodyParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            default:
                accept = true;
                break;
        }
        if (accept) {
            filterChain.doFilter(requestWrapper, response);
        } else {
            returnFail("签名验证不通过", response);
        }
    }

    private void returnFail(String msg, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String result = JSONObject.toJSONString(AjaxResult.fail(msg));
        out.println(result);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        log.info("销毁 SignAuthFilter");
    }
}
```

### 签名验证
```java
@Slf4j
public class SignUtil {

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(SortedMap<String, String> params, String sign, Long timestamp) {
        String paramsJsonStr = "Timestamp" + timestamp + JSONObject.toJSONString(params);
        return verifySign(paramsJsonStr, sign);
    }

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(String params, String sign) {
        log.info("Header Sign : {}", sign);
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("Param : {}", params);
        String paramsSign = getParamsSign(params);
        log.info("Param Sign : {}", paramsSign);
        return sign.equals(paramsSign);
    }

    /**
     * @return 得到签名
     */
    public static String getParamsSign(String params) {
        return DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
    }
}
```

### 不做签名验证的接口做成配置(application.yml)

```yaml
spring:
  security:
    # 签名验证超时时间
    signTimeout: 300
    # 允许未签名访问的url地址
    ignoreSignUri:
      - /swagger-ui.html
      - /swagger-resources
      - /v2/api-docs
      - /webjars/springfox-swagger-ui
      - /csrf
```

### 属性代码（SecurityProperties.java)

```java
@Component
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityProperties {

    /**
     * 允许忽略签名地址
     */
    List<String> ignoreSignUri;

    /**
     * 签名超时时间(分)
     */
    Integer signTimeout;
}
```

### 签名测试控制器

````java
@RestController
@Slf4j
@RequestMapping(encoder)
@Api(value = "签名controller", tags = {"签名测试接口"})
public class SignController {

    @ApiOperation("get测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @GetMapping("/testGet")
    public AjaxResult testGet(String username, String password) {
        log.info("username：{},password：{}", username, password);
        return AjaxResult.success("GET参数检验成功");
    }

    @ApiOperation("post测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "测试实体", required = true, dataType = "TestVo")
    })
    @PostMapping("/testPost")
    public AjaxResult<TestVo> testPost(@Valid @RequestBody TestVo data) {
        return AjaxResult.success("POST参数检验成功", data);
    }

    @ApiOperation("put测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "编号", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "data", value = "测试实体", required = true, dataType = "TestVo")
    })
    @PutMapping("/testPut/{id}")
    public AjaxResult testPut(@PathVariable Integer id, @RequestBody TestVo data) {
        data.setId(id);
        return AjaxResult.success("PUT参数检验成功", data);
    }

    @ApiOperation("delete测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "编号列表", required = true, dataType = "List<Integer> ")
    })
    @DeleteMapping("/testDelete")
    public AjaxResult testDelete(@RequestBody List<Integer> idList) {
        return AjaxResult.success("DELETE参数检验成功", idList);
    }
}
````

### 前端js请求示例

```js
var settings = {
  "async": true,
  "crossDomain": true,
  "url": "http://localhost:8080/sign/testGet?username=abc&password=123",
  "method": "GET",
  "headers": {
    "Sign": "46B1990701BCF090E3E6E517751DB02F",
    "Timestamp": "1564126422",
    "User-Agent": "PostmanRuntime/7.15.2",
    "Accept": "*/*",
    "Cache-Control": "no-cache",
    "Postman-Token": "a9d10ef5-283b-4ed3-8856-72d4589fb61d,6e7fa816-000a-4b29-9882-56d6ae0f33fb",
    "Host": "localhost:8080",
    "Cookie": "SESSION=OWYyYzFmMDMtODkyOC00NDg5LTk4ZTYtODNhYzcwYjQ5Zjg2",
    "Accept-Encoding": "gzip, deflate",
    "Connection": "keep-alive",
    "cache-control": "no-cache"
  }
}

$.ajax(settings).done(function (response) {
  console.log(response);
});
```

## 注意事项

- 该示例没有设置秘钥,只做了参数升排然后创建md5签名
- 示例请求的参数md5原文本为:Timestamp1564126422{"password":"123","username":"abc"}
- 注意headers请求头带上了Sign和Timestamp参数
- js读取的Timestamp必须要在服务端获取
- 该示例不包括分布试环境下,多台服务器时间同步问题

## 自动生成接口文档

- 配置代码

```java
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(ccom.easy.easyeasy))
                .paths(PathSelectors.any())
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("签名示例")
                .contact(new Contact("签名示例网站", "http://www.baidu.com", "test@qq.com"))
                .version("1.0.0")
                .description("签名示例接口描述")
                .build();
    }
}
```

 - 自动生成文档地址：http://localhost:8080/swagger-ui.html

## 资料

- [示例代码-github](https://github.com/smltq/spring-boot-demo/blob/master/sign/HELP.md)
- [md5在线加密](https://md5jiami.51240.com/)
- [在线时间戳](https://tool.chinaz.com/Tools/unixtime.aspx)