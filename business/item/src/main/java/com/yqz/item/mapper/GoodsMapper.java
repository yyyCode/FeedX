package com.yqz.item.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yqz.item.po.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 86139
* @description 针对表【goods】的数据库操作Mapper
* @createDate 2023-11-02 11:39:42
* @Entity com.ysm.goods.po.Goods
*/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}




