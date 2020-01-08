package com.alibaba.datax.common.util;

import com.alibaba.datax.common.constant.CommonConstant;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghaijun
 * @description:
 * @date 2020-01-06 16:22
 * @version:
 */
public class DDConfig {
    private static final Logger LOG = LoggerFactory.getLogger(DDConfig.class);
    private HashMap<String, Object> ddConfigMap = new HashMap<>();

    private DDConfig() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(CommonConstant.DD_CONF_URL).get().build();
        String response = null;
        try {
            response = Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string();
            JSONObject responseObj = Objects.requireNonNull(JSON.parseArray(response)).getJSONObject(0);
            String config = new String(Base64.getDecoder().decode(responseObj.getString("Value")), Charset.defaultCharset());
            for (String s : config.split("\n")) {
                if (s.isEmpty() || s.trim().startsWith("#")) continue;
                int index = s.indexOf("=");
                ddConfigMap.put(s.substring(0, index).trim(), s.substring(index + 1).trim());
            }
        } catch (IOException e) {
            LOG.error("初始化DDConfig失败 -> ", e.getMessage());
        }
    }

    public static DDConfig getSingleton() {
        return Inner.instance;
    }

    private static class Inner {
        private static final DDConfig instance = new DDConfig();
    }

    public String getUserInfoSetting(String key) {

        if (key.isEmpty()) return key;
        String result = key;
        if (key.startsWith("ddconfig.")) {
            LOG.info("使用自编译的插件，调用DDConfig获取远端配置：" + key);
            Object ddconfig = ddConfigMap.get(key.replace("ddconfig.", ""));
            result = ddconfig == null ? key : ddconfig.toString();
        }
        return result;
    }

}
