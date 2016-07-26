package com.myutil.json;
import java.util.ArrayList;
import java.util.List;

/**
 * JDiy的Json列表集合的轻量级实现.<br/>
 * 　　仅管此类实现了List接口，也可以将任意类型的对象(Object)添加到此集合.
 * 但并不是任意类型的对象都可以转换为JSON的.
 * 通常情况下，可以直接完成JSON转换的对象类型如下（或如下类型的子类型）：
 * <ul>
 * 	<li>java.lang.String </li>
 * 	<li>java.lang.Boolean</li>
 * 	<li>java.util.Map - 程序将遍历呈递Map的每个K/V键值对，并根据其实际类型完成转换.</li>
 * 	<li>java.lang.Number  包括Double, Float, Integer等Number类的子类, </li>
 * 	<li>java.util.Date 　日期时间对象在JSON中表示为long型(即毫秒值).用户可以通过客户端js的new Date(long)将其还原．</li>
 *	<li>JsonObject - JDiy的JsonArray对象</li>
 * 	<li>JsonArray - JDiy的JsonArray对象</li>
 * 	<li>null </li>
 * 	</ul>
 *  　　除上面列出的类型以外，其它任意类型的对象，都将直接调用默认的toString方法完成转换．
 *  @see JsonObject
 */
public class JsonArray extends ArrayList<Object> implements List<Object> {
	private static final long serialVersionUID = 3957988303675231981L;

    /**
     * 返回对象的JSON-String表示.
     * @return JSON-String.
     */
    public String toString() {
        return JsonUtil.toJsonString(this);
	}

}
