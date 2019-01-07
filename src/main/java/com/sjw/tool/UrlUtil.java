package com.sjw.tool; /**
 * Created by Administrator on 2018/8/15.
 */

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.URLUtil;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Author 史佳伟
 * @date 2018/8/15
 */
public class UrlUtil extends URLUtil {

    public UrlUtil() {
        super();
    }

    /**
     * 将以get请求发送出去的url来进行一下拆解，然后获取他里面的那些Data
     *
     * @param url
     * @return
     */
    public static HashMap<String, Object> getData(String url) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        URL url1 = url(url);
        String[] split = {url1.getQuery()};
        if (url.contains("&")) {
            split = url1.getQuery().split("&");
        }
        if (url1.getQuery() != null) {
            for (int i = 0; i < split.length; i++) {
                String[] split1 = split[i].split("=");
                if (split1.length == 1) {
                    map.put(split1[0], null);
                } else {
                    map.put(split1[0], split1[1]);
                }
            }
        }
        return map;
    }

    /**
     * 获取一个url 里面的所有paths
     *
     * @param url
     * @return
     */
    public static String[] getPaths(String url) {
        URL url1 = url(url);
        String[] split = url1.getPath().split("/");
        return split;
    }

    /**
     * 移除一个url以get请求发送的所有的data
     *
     * @param url
     * @return
     */
    public static String removeData(String url) {
        URL url1 = url(url);
        return url1.getProtocol() + "://" + url1.getHost() + url1.getPath();
    }

    /**
     * 想一个url里面以get请求添加数据--- 大批量
     *
     * @param map
     * @param url
     * @return
     */
    public static String addDatas(HashMap<String, Object> map, String url) {
        //防止url,上面已经有数据，所以先取上面的数据,然后用以 传进来的为主
        HashMap<String, Object> data = getData(url);
        data.putAll(map);
        url = removeData(url);
        Iterator<String> iterator = data.keySet().iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            Object val = map.get(k);
            url = addData(url, k, val);
        }
        return url;

    }

    /**
     * 向一个url里面添加数据  ---一个一个添加
     *
     * @param url
     * @param key
     * @param value
     * @return
     */
    public static String addData(String url, String key, Object value) {
        HashMap<String, Object> map = getData(url);
        url = removeData(url);
        map.put(key, value);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String k = iterator.next();
            Object val = map.get(k);
            if (!url.contains("?")) {
                url += "?";
            } else {
                url += "&";
            }
            if (val == null) {
                url += k;
            } else {
                url += k + "=" + val;
            }
        }
        return url;
    }

    /**
     * 无脑往后面加
     *
     * @param url
     * @param paths
     * @return
     */
    public static String addPath(String url, String... paths) {
        String[] paths1 = getPaths(url);
        URL urls = url(url);
        String basicUrl = urls.getProtocol()+"://" + urls.getHost();
        StringBuffer str = new StringBuffer(basicUrl);
        for (String path : paths1) {
            str.append("/" + path);
        }
        for (String path : paths) {
            str.append("/" + path);
        }
        return str.toString();
    }

    public static void main(String[] args) {
        System.out.println(addPath("https://www.baseballfantshirts.com", "123MySQL - baseball@120.77.52.157 [2]"));
    }
}
