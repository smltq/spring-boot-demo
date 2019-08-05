package com.easy.easy.sign.controller;

import com.easy.easy.sign.model.AjaxResult;
import com.easy.easy.sign.model.TestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/sign")
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