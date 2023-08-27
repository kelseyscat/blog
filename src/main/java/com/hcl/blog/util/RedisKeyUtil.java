package com.hcl.blog.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_TICKET = "ticket";
    private static final String PREFIX_READING = "readVolume";
    //保存用户点赞数据的key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    //保存用户被点赞数量的key
    public static final String MAP_KEY_USER_LIKED_COUNT = "MAP_USER_LIKED_COUNT";
    // 登录凭证
    public static String getTicketKey(String ticket){ // 字符串凭证用来标识用户
        return PREFIX_TICKET + SPLIT + ticket;
    }
    // 阅读量
    public static String getReadingVolumeKey(int articleId){
        return PREFIX_READING + SPLIT + articleId;
    }

    /**
     * 拼接被点赞的文章id和点赞的人的ip作为key。格式 222222:333333
     * @param articleId 被点赞的文章id
     * @param ip 点赞的人的ip
     * @return
     */
    public static String getLikedKey(int articleId, String ip){
        StringBuilder builder = new StringBuilder();
        builder.append(articleId);
        builder.append(SPLIT);
        builder.append(ip);
        return builder.toString();
    }

}
