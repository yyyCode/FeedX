package com.yqz.flink;

import lombok.Data;

import java.util.List;

@Data
public class UserBehavior {
    public long userId;
    public long videoId;
    public String action; // click, like, comment
    public List<String> tags;
    public long timestamp;
}
