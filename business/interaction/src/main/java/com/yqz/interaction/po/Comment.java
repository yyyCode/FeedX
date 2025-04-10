package com.yqz.interaction.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @TableName comment
 */
@TableName(value ="comment")
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    @TableField(value = "item_id")
    private Long itemId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 
     */
    @TableField(value = "content")
    private String content;

    /**
     * 
     */
    @TableField(value = "created_at",fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 
     */
    @TableField(value = "updated_at",fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

    /**
     * 
     */
    @TableField(value = "deleted")
    @TableLogic(value = "0", delval = "1")
    private Byte deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}