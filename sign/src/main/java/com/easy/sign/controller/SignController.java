package com.easy.sign.controller;

import com.easy.sign.model.AjaxResult;
import com.easy.sign.model.TestVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sign")
public class SignController {
    @GetMapping("/testGet")
    public AjaxResult testGet(String username, String password) {
        log.info("username：{},password：{}", username, password);
        return AjaxResult.success("GET参数检验成功");
    }

    @PostMapping("/testPost")
    public AjaxResult testPost(@Valid @RequestBody TestVo data) {
        return AjaxResult.success("POST参数检验成功", data);
    }

    @PutMapping("/testPut/{id}")
    public AjaxResult testPutJson(@PathVariable Integer id, @RequestBody TestVo data) {
        data.setId(id);
        return AjaxResult.success("PUT参数检验成功", data);
    }

    @DeleteMapping("/testDelete")
    public AjaxResult testDelete(@RequestBody List<Integer> idList) {
        return AjaxResult.success("DELETE参数检验成功", idList);
    }
}