package com.yqz.count.controller;

import com.yqz.core.domain.CommonResult;
import com.yqz.count.entity.LikeBo;
import com.yqz.count.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/3/17
 * @Version V1.0
 **/
@RestController
public class CountController {

    @Autowired
    private CountService countService;

    @PostMapping("/like")
    public CommonResult<Integer> like(@RequestBody LikeBo likeBo){
        return CommonResult.ok(countService.like(likeBo));
    }
}
