package com.aaront.gson.demo;

import com.aaront.gson.pojo.Category;
import com.aaront.gson.pojo.ModifierSample;
import com.aaront.gson.pojo.SinceUntilSample;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Modifier;

/**
 * @author tonyhui
 * @since 16/4/3
 */
public class ExpertUsageManager {
    public static void main(String[] args){
        // 使用@Expose注解决定哪些字段需要序列化和反序列化
        annotationExposeUsage();
        // 使用@Since&@Until主角决定哪些字段需要进行序列化
        basedVersionAnnotationUsage();
        // 基于访问修饰符决定哪些字段需要进行序列化
        basedModifierUsage();
    }


    /**
     * @Expose 决定字段的序列化和反序列化
     */
    public static void annotationExposeUsage(){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Category category = new Category();
        gson.toJson(category);
    }

    /**
     * @Since & @Until 根据版本决定字段是否进行序列化
     */
    public static void basedVersionAnnotationUsage(){
        Integer version = 4;
        SinceUntilSample sinceUntilSample = new SinceUntilSample();
        sinceUntilSample.since = "since";
        sinceUntilSample.until = "until";
        Gson gson = new GsonBuilder().setVersion(version).create();
        System.out.println(gson.toJson(sinceUntilSample));

        //当version <4时，结果：{"until":"until"}
        //当version >=4 && version <5时，结果：{"since":"since","until":"until"}
        //当version >=5时，结果：{"since":"since"}
    }

    /**
     * 根据字段的修饰符决定是否进行序列化
     */
    public static void basedModifierUsage(){
        ModifierSample modifierSample = new ModifierSample();
        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL,Modifier.PRIVATE)
                .create();
        System.out.println(gson.toJson(modifierSample));
        // 结果：{"publicField":"public","protectedField":"protected","defaultField":"default"}
        // static 会自动被排除。
    }

    /**
     * 根据自定义的策略决定字段是否要进行序列化和反序列化
     */
    public static void basedStrategyUsage(){
        // 序列化的策略
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(new ExclusionStrategy() { // 序列化策略
                    public boolean shouldSkipField(FieldAttributes f) {
                        // 这里作判断，决定要不要排除该字段,return true为排除
                        if ("finalField".equals(f.getName())) return true; //按字段名排除
                        Expose expose = f.getAnnotation(Expose.class);
                        if (expose != null && !expose.deserialize()) return true; //按注解排除
                        return false;
                    }

                    public boolean shouldSkipClass(Class<?> clazz) {
                        // 直接排除某个类 ，return true为排除
                        return (clazz == int.class || clazz == Integer.class);
                    }
                })
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {  // 反序列化策略
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false;
                    }

                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }
}
