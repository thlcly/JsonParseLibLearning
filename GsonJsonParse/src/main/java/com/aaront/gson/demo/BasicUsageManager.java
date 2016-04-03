package com.aaront.gson.demo;

import com.aaront.gson.pojo.DataResponse;
import com.aaront.gson.pojo.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author tonyhui
 * @since 16/4/3
 */
public class BasicUsageManager {
    public static void main(String[] args) {
        // 解析:json字符串->基础的数据类型
        parseJsonToBasicDataType();
        // 生成:基础的数据类型->json字符串
        generateBasicDataTypeFromJson();

        // 解析:json字符串->pojo对象
        parseJsonToPojo();
        // 生成:pojo->json字符串
        generatePojoFromJson();

        // 解析:json字符串->Array
        parseJsonToArray();
        // 解析:json字符串->List
        parseJsonToList();

        // 生成:泛型对象->json字符串
        generateGenericTypeFromJson();
    }

    /**
     * json字符串解析成基础的数据类型
     */
    public static void parseJsonToBasicDataType() {
        Gson gson = new Gson();
        int i = gson.fromJson("100", int.class);              //100
        double d = gson.fromJson("\"99.99\"", double.class);  //99.99, 支持将字符串的数字转成数字类型(例如double,int.. eg)
        boolean b = gson.fromJson("true", boolean.class);     // true
        String str = gson.fromJson("String", String.class);   // String

        System.out.println("i=" + i);
        System.out.println("d=" + d);
        System.out.println("b=" + b);
        System.out.println("str=" + str);
    }

    /**
     * 基础的数据类型转成json字符串
     */
    public static void generateBasicDataTypeFromJson() {
        Gson gson = new Gson();
        String jsonNumber = gson.toJson(100);       // 100
        String jsonBoolean = gson.toJson(false);    // false
        String jsonString = gson.toJson("String"); //"String"

        System.out.println("jsonNumber=" + jsonNumber);
        System.out.println("jsonBoolean=" + jsonBoolean);
        System.out.println("jsonString=" + jsonString);
    }

    /**
     * json字符串解析成pojo对象
     */
    public static void parseJsonToPojo(){
        Gson gson = new Gson();
        String jsonString = "{\"name\":\"怪盗kidou\",\"age\":24, \"firstName\":\"hello\", \"email_address\":\"aaront@xingren.com\"}";
        // 如果json字符串中不存在某个属性的值,那在生成的对象中改属性的值就是其类型的默认值
        User user = gson.fromJson(jsonString, User.class);
        System.out.println("user="+user);
    }

    /**
     * pojo转成json字符串
     */
    public static void generatePojoFromJson(){
        Gson gson = new Gson();
        // 为null的字段不会转成json字符串,会被过滤
        User user1 = new User("aaront",24);
        // 基本数据类型的默认值为0,false..eg,不为null,所以会被转成json
        User user2 = new User();
        String jsonObject1 = gson.toJson(user1); // {"name":"怪盗kidou","age":24}
        String jsonObject2 = gson.toJson(user2); //{"age":0}

        System.out.println(jsonObject1);
        System.out.println(jsonObject2);
    }

    /**
     * json字符串解析成array
     */
    public static void parseJsonToArray() {
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        String[] strings = gson.fromJson(jsonArray, String[].class);

        System.out.println(Arrays.toString(strings));
    }

    /**
     * json租房次解析成List
     */
    public static void parseJsonToList(){
        Gson gson = new Gson();
        String jsonArray = "[\"Android\",\"Java\",\"PHP\"]";
        // 但对于List将上面的代码中的 String[].class 直接改为 List<String>.class 是行不通的。
        // 对于Java来说List<String> 和List<User> 这俩个的字节码文件只一个那就是List.class，这是Java泛型使用时要注意的问题泛型擦除。
        // 解决办法:
        // 注：TypeToken的构造方法是protected修饰的,所以上面才会写成new TypeToken<List<String>>() {}.getType()
        // 而不是  new TypeToken<List<String>>().getType()
        List<String> stringList = gson.fromJson(jsonArray, new TypeToken<List<String>>() {}.getType());

        System.out.println(stringList);
    }

    public static void generateGenericTypeFromJson(){
        Gson gson = new Gson();
        User user1 = new User("aaront",24);
        DataResponse<User> dataResponse1 = new DataResponse<User>(200, "succes", user1);
        String jsonObject1 = gson.toJson(dataResponse1);

        List<User> users = new ArrayList<User>();
        users.add(user1);
        DataResponse<List<User>> dataResponse2 = new DataResponse<List<User>>(200, "success", users);
        String jsonObject2 = gson.toJson(dataResponse2);

        User[] userArray = new User[1];
        userArray[0] = user1;
        DataResponse<User[]> dataResponse3 = new DataResponse<User[]>(200, "success", userArray);
        String jsonObject3 = gson.toJson(dataResponse3);

        System.out.println(jsonObject1);
        System.out.println(jsonObject2);
        System.out.println(jsonObject3);
    }

}
