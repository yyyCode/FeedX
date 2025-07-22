package com.yqz.recommend.controller;

import com.yqz.core.domain.CommonResult;
import com.yqz.recommend.service.RecommendService;
import com.yqz.recommend.vo.RecommendVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;


    // 这里可以添加推荐相关的API接口方法
    // 例如：获取推荐列表、获取推荐详情等

    // 示例方法
    @GetMapping("/list")
    public CommonResult<List<RecommendVo>> getRecommendations(@RequestParam Long userId) {
    List<RecommendVo> recommendations = recommendService.getRecommendations(userId);
    return CommonResult.ok(recommendations);
    }
}
