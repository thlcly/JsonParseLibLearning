package com.aaront.gson.demo;

import com.aaront.gson.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Date;

/**
 * @author tonyhui
 * @since 16/4/3
 */
public class AdvancedUsageManager {
    public static void main(String[] args) throws IOException {
        // Gson流式反序列化(手动方式)
        generatePojoFromJsonUsingStreamByManual();

        // Gson流式序列化(手动方式)
        parseJsonToPojoUsingStreamByManual();

        // 使用GsonBuilder改变Gson的默认配置
        buildGsonInstanceWithGsonBuilder();
    }

    /**
     * Gson的流式反序列化(手动方式)
     *
     * @throws IOException
     */
    public static void generatePojoFromJsonUsingStreamByManual() throws IOException {
        String json = "{\"name\":\"怪盗kidou\",\"age\":\"24\"}";
        User user = new User();
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.beginObject(); // throws IOException
        while (reader.hasNext()) {
            String s = reader.nextName();
            if ("name".equals(s)) {
                user.name = reader.nextString();
            } else if ("age".equals(s)) {
                user.age = reader.nextInt();
            } else if ("email".equals(s)) {
                user.emailAddress = reader.nextString();
            }
        }
        reader.endObject(); // throws IOException

        System.out.println(user.name);  // 怪盗kidou
        System.out.println(user.age);   // 24
        System.out.println(user.emailAddress); // ikidou@example.com
    }

    /**
     * Gson的流式序列化(手动方式)
     *
     * @throws IOException
     */
    public static void parseJsonToPojoUsingStreamByManual() throws IOException {
        // 对象
        System.out.println("对象");
        JsonWriter writer1 = new JsonWriter(new OutputStreamWriter(System.out));
        writer1.beginObject() // throws IOException
                .name("name").value("怪盗kidou")
                .name("age").value(24)
                .name("email").nullValue() //演示null
                .endObject(); // throws IOException
        writer1.flush(); // throws IOException

        System.out.println();

        // 数组
        System.out.println("数组");
        JsonWriter writer2 = new JsonWriter(new OutputStreamWriter(System.out));
        writer2.beginArray().value(1).value(2).endArray();
        writer2.flush();

        System.out.println();

        // 对象中嵌套数组
        System.out.println("对象中嵌套数组");
        JsonWriter writer3 = new JsonWriter(new OutputStreamWriter(System.out));
        writer3.beginObject()
                .name("array")
                .beginArray().value(1).value(2)
                .endArray()
                .name("age")
                .value(33)
                .endObject();
        writer3.flush();

        System.out.println();

        // 数组中嵌套对象
        System.out.println("数组中嵌套对象");
        JsonWriter writer4 = new JsonWriter(new OutputStreamWriter(System.out));
        writer4.beginArray()
                .beginObject().name("name").value("aaront").name("age").value(12).endObject()
                .beginObject().name("name").value("thlcly").endObject()
                .endArray();
        writer4.flush();

        System.out.println();
    }

    public static void buildGsonInstanceWithGsonBuilder(){
        Gson gson = new GsonBuilder()
                //序列化null
                .serializeNulls()
                // 设置日期时间格式，另有2个重载方法
                // 在序列化和反序化时均生效
                .setDateFormat("yyyy-MM-dd") // 能控制java.util.Date的格式
                // 禁此序列化内部类
                .disableInnerClassSerialization()
                //生成不可执行的Json（多了 )]}' 这4个字符）
                .generateNonExecutableJson()
                //禁止转义html标签
                .disableHtmlEscaping()
                //格式化输出
                .setPrettyPrinting()
                .create();
        User user = new User("怪盗kidou", 24);
        user.birth = new Date();
        System.out.println(gson.toJson(user)); //{"name":"怪盗kidou","age":24,"email":null}


    }
}
