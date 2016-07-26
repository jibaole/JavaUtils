package com.myutil.json;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.myutil.json.parser.JSONParser;
import com.myutil.json.parser.ParseException;


/**
 * JsonUtil工具类.
 * <br/>此类为Json对象转换的轻量级实现.可以将java对象转换为Json字符串，或将Json字符串反解析为java对象，等等常用JSON转换操作.
 */
public class JsonUtil {

	/**
     * 从指定的JSON字符串输入对象进行JSON解析.
     * 如果给定的输入不是一个有效的JSON,或其它输入错误，此方法将抛出异常.
	 * @param in in
	 * @return 此方法返回的对象实例，为如下java类型之一：
	 *	JsonObject,
	 * 	JsonArray,
	 * 	java.lang.String,
	 * 	java.lang.Number,
	 * 	java.lang.Boolean,
	 * 	null
     * @throws java.io.IOException 当发生I/O错误，将抛出此异常.
     * @throws org.jdiy.json.parser.ParseException 当给定的输入不是一个有效的JSON字符串，获其它解析错误，抛出此异常.
     * @see #parse(String)
	 * 
	 */
	public static Object parse(Reader in) throws IOException, ParseException {
			JSONParser parser=new JSONParser();
			return parser.parse(in);
	}

    /**
     * 将JSON字符串解析为java对象.
     * 如果给定的字符串不是一个有效的JSON,此方法将抛出异常.
     * @param s 要解析的JSON字符串.
     * @return 此方法返回的对象实例，为如下java类型之一：
     *	JsonObject,
     * 	JsonArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
     * @throws java.io.IOException 当发生I/O错误，将抛出此异常.
     * @throws org.jdiy.json.parser.ParseException 当给定的字符串不是一个有效的JSON，获其它解析错误，抛出此异常.
     * @see #parse(java.io.Reader)
     *
     */
	public static Object parse(String s) throws IOException, ParseException {
		StringReader in=new StringReader(s);
		return parse(in);
	}
	

    /**
     * 将给定的java对象转换成JSON并输出.
     * @param value 要输出为JSON的java对象．此对象必须是以下类型(或其子类型)之一:
     * {@link JsonObject}, {@link JsonArray}, java.lang.String, java.lang.Number, java.lang.Boolean,
     *              java.util.Date, java.util.Map, java.util.List, null
     * @param out Writer对象
     * @throws IOException
     */
	public static void outJson(Object value, Writer out) throws IOException {
		if(value == null){
			out.write("null");
			return;
		}
		
		if(value instanceof String){		
            out.write('\"');
			out.write(escape((String)value));
            out.write('\"');
			return;
		}
		
		if(value instanceof Double){
			if(((Double)value).isInfinite() || ((Double)value).isNaN())
				out.write("null");
			else
				out.write(value.toString());
			return;
		}
		
		if(value instanceof Float){
			if(((Float)value).isInfinite() || ((Float)value).isNaN())
				out.write("null");
			else
				out.write(value.toString());
			return;
		}		
		
		if(value instanceof Number){
			out.write(value.toString());
			return;
		}
		
		if(value instanceof Boolean){
			out.write(value.toString());
			return;
		}

		if((value instanceof JsonObject)){
			out.write(value.toString());
			return;
		}
		
		if(value instanceof Map){
            outJson((Map) value, out);
			return;
		}
		
		if(value instanceof List){
			outJson((List) value, out);
            return;
		}
		
		out.write(value.toString());
	}


    /**
     * 将List集合对象转换为JSON并输出.
     * <br/>List集合的下标元素应该是如下java类型之一：
     *	JsonObject,
     * 	JsonArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
     * @see JsonUtil#outJson(Object, java.io.Writer)
     *
     * @param list List集合元素
     * @param out Writer对象.
     */
    public static void outJson(List list, Writer out) throws IOException{
        if(list == null){
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator iter=list.iterator();

        out.write('[');
        while(iter.hasNext()){
            if(first)
                first = false;
            else
                out.write(',');

            Object value=iter.next();
            if(value == null){
                out.write("null");
                continue;
            }

            JsonUtil.outJson(value, out);
        }
        out.write(']');
    }


    /**
     * 将Map集合对象转换为JSON并输出.
     * <br/>Map的value键值应该是如下java类型之一：
     *	JsonObject,
     * 	JsonArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
     * @see JsonUtil#outJson(Object, java.io.Writer)
     *
     * @param map Map集合元素
     * @param out Writer对象.
     */
    public static void outJson(Map map, Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator iter = map.entrySet().iterator();

        out.write('{');
        while (iter.hasNext()) {
            if (first)
                first = false;
            else
                out.write(',');
            Map.Entry entry = (Map.Entry) iter.next();
            out.write('\"');
            out.write(JsonUtil.escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JsonUtil.outJson(entry.getValue(), out);
        }
        out.write('}');
    }

	/**
	 * 将给定的java对象转换为JSON字符串.
	 * <p>
	 * 所传入的对象必须是以下类型之一：
     *	JsonObject,
     * 	JsonArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
	 * <p>
	 *
	 * @see #toJsonString(java.util.List)
	 * 
	 * @param value 要转换成JSON字符串的java对象.
	 * @return JSON 字符串, 或null
	 */
	public static String toJsonString(Object value){
		if(value == null)
			return "null";
		
		if(value instanceof String)
			return "\""+escape((String)value)+"\"";
		
		if(value instanceof Double){
			if(((Double)value).isInfinite() || ((Double)value).isNaN())
				return "null";
			else
				return value.toString();
		}
		
		if(value instanceof Float){
			if(((Float)value).isInfinite() || ((Float)value).isNaN())
				return "null";
			else
				return value.toString();
		}		
		
		if(value instanceof Number)
			return value.toString();
		
		if(value instanceof Boolean)
			return value.toString();
		if (value instanceof Date)
            return Long.toString(((Date) value).getTime());
		if((value instanceof JsonObject))
			return value.toString();
		
		if(value instanceof Map)
			return new JsonObject((Map)value).toString();
		
		if(value instanceof List)
			return JsonUtil.toJsonString((List) value);
		
		return "\""+escape(value.toString())+"\"";
	}

    /**
     * 将给定的List集合对象转换为JSON字符串.
     * <p>
     * List的下标元素必须是以下类型之一：
     *	JsonObject,
     * 	JsonArray,
     * 	java.lang.String,
     * 	java.lang.Number,
     * 	java.lang.Boolean,
     * 	null
     * <p>
     *
     * @see #toJsonString(java.util.List)
     *
     * @param list 要转换成JSON字符串的List集合.
     * @return JSON 字符串, 或null
     */
    public static String toJsonString(List list){
        if(list == null)
            return "null";

        boolean first = true;
        StringBuilder sb = new StringBuilder();
        Iterator iter=list.iterator();

        sb.append('[');
        while(iter.hasNext()){
            if(first)
                first = false;
            else
                sb.append(',');

            Object value=iter.next();
            if(value == null){
                sb.append("null");
                continue;
            }
            sb.append(JsonUtil.toJsonString(value));
        }
        sb.append(']');
        return sb.toString();
    }

    static String toJsonString(String key, Object value, StringBuffer sb) {
        sb.append('\"');
        if (key == null)
            sb.append("null");
        else
            JsonUtil.escape(key, sb);
        sb.append('\"').append(':');

        sb.append(JsonUtil.toJsonString(value));

        return sb.toString();
    }


    /**
     * 编码 引号, \, /, \r, \n, \b, \f, \t 或其它控制字符 (U+0000 through U+001F).
     * @param s  s
     * @return  s
     */
    static String escape(String s){
        if(s==null)
            return null;
        StringBuffer sb = new StringBuffer();
        escape(s, sb);
        return sb.toString();
    }
    /**
     * @param s - 注意不能为null.
     * @param sb sb
     */
    static void escape(String s, StringBuffer sb) {
		for(int i=0;i<s.length();i++){
			char ch=s.charAt(i);
			switch(ch){
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
                //Reference: http://www.unicode.org/versions/Unicode5.1.0/
				if((ch>='\u0000' && ch<='\u001F') || (ch>='\u007F' && ch<='\u009F') || (ch>='\u2000' && ch<='\u20FF')){
					String ss=Integer.toHexString(ch);
					sb.append("\\u");
					for(int k=0;k<4-ss.length();k++){
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				}
				else{
					sb.append(ch);
				}
			}
		}//for
	}

}
