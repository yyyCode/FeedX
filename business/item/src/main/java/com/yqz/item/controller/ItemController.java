package com.yqz.item.controller;

import com.alibaba.fastjson2.JSON;

import com.yqz.core.domain.CommonResult;
import com.yqz.item.po.Item;
import com.yqz.item.service.ItemService;
import com.yqz.minio.MinioUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2024/2/25
 * @Version V1.0
 **/
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private ItemService itemService;


    @PostMapping(value = "/post")
    public CommonResult<String> item(@RequestPart("file") MultipartFile file, @RequestPart("itemBo") String itemBo) throws Exception {
        itemService.postItem(file,JSON.parseObject(itemBo, Item.class));
        return CommonResult.ok("发布成功");
    }

    @PostMapping("/upload")
    public CommonResult<String> uploadFileTest(@RequestParam("file") MultipartFile file) throws Exception {
        String filePath = minioUtil.uploadFile(file, file.getOriginalFilename(), file.getContentType());
        return CommonResult.ok(filePath);
    }

    @GetMapping("/download")
    public CommonResult<String> downloadFileTest(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        return minioUtil.downloadFile(fileName, response) ?
                CommonResult.ok("下载成功") : CommonResult.fail("下载失败");
    }

    @GetMapping("/list/{targetId}")
    public CommonResult<List<Item>> listItem(@PathVariable String targetId){
        return CommonResult.ok(itemService.listItem(targetId));
    }


}
