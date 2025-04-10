package com.yqz.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yqz.item.mapper.CategoryMapper;
import com.yqz.item.po.Category;
import com.yqz.item.service.CategoryService;
import org.springframework.stereotype.Service;

/**
* @author 86139
* @description 针对表【category】的数据库操作Service实现
* @createDate 2023-11-02 11:39:42
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

}




