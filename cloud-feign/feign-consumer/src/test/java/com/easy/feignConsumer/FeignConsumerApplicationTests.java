package com.easy.feignConsumer;

import com.easy.helloServiceApi.model.Order;
import com.easy.helloServiceApi.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FeignConsumerApplicationTests {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFeignConsumer() {
        Order order = new Order();
        order.setGoodsId("1");
        Result result = this.restTemplate.getForObject("http://HELLO-SERVER/goods/goodsInfo/" + order.getGoodsId(), Result.class);
        log.info("成功调用了服务，返回结果==>{}", ToStringBuilder.reflectionToString(result));
    }
}
