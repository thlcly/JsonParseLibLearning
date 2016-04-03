package com.aaront.gson.pojo;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author tonyhui
 * @since 16/4/3
 */
public class Category {
    /**
     * @Expose // 必须和GsonBuilder配合使用
     * @Expose(deserialize = true,serialize = true) //序列化和反序列化都都生效
     * @Expose(deserialize = true,serialize = false) //反序列化时生效
     * @Expose(deserialize = false,serialize = true) //序列化时生效
     * @Expose(deserialize = false,serialize = false) // 和不写一样
     *
     */
    @Expose public int id;
    @Expose public String name;
    @Expose public List<Category> children;
    //因业务需要增加，但并不需要序列化
    //不需要序列化,所以不加 @Expose 注解，
    //等价于 @Expose(deserialize = false,serialize = false)
    public Category parent;
}
