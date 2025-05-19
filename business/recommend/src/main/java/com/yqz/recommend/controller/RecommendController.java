package com.yqz.recommend.controller;

import com.yqz.recommend.entity.FeedItemDTO;
import com.yqz.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {
    @Autowired
    private RecommendService recommendService;

    @GetMapping("/feed")
    public List<FeedItemDTO> recommendFeed(@RequestParam Long userId) {
        return recommendService.recommend(userId);
    }
}
