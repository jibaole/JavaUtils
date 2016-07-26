package com.myutil.json;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * JDiy的Json交换对象轻量级实现.<br/>
 * 使用此类，可以非常方便的实现java对象到JSON之间的转换.<br/> <br/>
 * <strong>使用示例：</strong><br/>
 * <code><pre>
 *     JsonObject json = new JsonObject();
 *     json.set("name","张三");
 *     json.set("age",30);
 *     json.set("enable",true).set("addDate",new Date()); //可以set连着写(继承了JDiy的链式编程风格).
 *     System.out.print(json);
 * </pre></code>
 * <strong>输出结果：</strong>
 * <code>{"name":"张三","age":30,"enable":true,"addDate":1386163884794}</code>
 * 　
 */
public class JsonObject {
    private Map<String, Object> map;

    /**
     * 构造一个JsonObject.
     */
    public JsonObject() {
        map = new HashMap<String, Object>();
    }

    /**
     * 根据给定的Map对象构造为一个JsonObject.
     * 程序将根据传入的Map的每个K/V键值对自动完成JSON转换.
     * @param map Map对象.
     */
    public JsonObject(Map map) {
        this();
        this.setAll(map);
    }

    //todo 添加直接传pojo对象的构造方法.

    /**
     * 向当前的JsonObject添加JSON字段/值.
     * @param name 字段名称
     * @param value 字段值.
     * @return 当前的JsonObject.
     */
    public JsonObject set(String name, Object value) {
        map.put(name, value);
        return this;
    }

    /**
     * 将给定的Map对象添加到当前的JsonObject.
     * 程序将根据传入的Map的每个K/V键值对自动完成添加.
     * @param map Map对象.
     * @return 当前的JsonObject对象本身.
     */
    public JsonObject setAll(Map map) {
        if (map != null)
            for (Object eo : map.entrySet()) {
                Map.Entry e = (Map.Entry) eo;
                this.map.put(String.valueOf(e.getKey()), e.getValue());
            }
        return this;
    }

    /**
     * 获取指定名称的数据值.
     * @param name 指定的键名称.
     * @return 该名称对应的数据值．如果不存在，则返回null.
     */
    public Object get(String name) {
        return map.get(name);
    }


    /**
     * 返回对象的JSON-String表示.
     * @return JSON-String.
     */
    public String toString() {
        if (map == null)
            return "null";

        StringBuffer sb = new StringBuffer();
        boolean first = true;
        Iterator iter = map.entrySet().iterator();

        sb.append('{');
        while (iter.hasNext()) {
            if (first)
                first = false;
            else
                sb.append(',');

            Map.Entry entry = (Map.Entry) iter.next();
            JsonUtil.toJsonString(String.valueOf(entry.getKey()), entry.getValue(), sb);
        }
        sb.append('}');
        return sb.toString();
    }

}
