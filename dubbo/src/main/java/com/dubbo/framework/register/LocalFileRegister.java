package com.dubbo.framework.register;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.dubbo.framework.Url;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地文件注册，服务调用这和消费者必须在同一台机器
 *
 * @author telzhou
 * @since 2021/6/11
 */
public class LocalFileRegister implements Register {

    /**
     * 本地注册需要的持久化文件
     */
    private File file;
    /**
     * 服务和地址端口映射关系，一个服务可以由多个IP和端口
     */
    private Map<String, List<Url>> serviceUriMap;

    public LocalFileRegister() {
        file = new File("/tmp/register_file.db");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void register(String serviceName, Url url) {
        loadMap();
        if (serviceUriMap.containsKey(serviceName)) {
            List<Url> list = serviceUriMap.get(serviceName);
            if (list != null && !list.contains(url)) {
                list.add(url);
                serviceUriMap.put(serviceName, list);
            }
        } else {
            serviceUriMap.put(serviceName, Lists.newArrayList(url));
        }
        // 持久化
        saveMap();
    }


    @Override
    public List<Url> getService(String serviceName) {
        loadMap();
        return serviceUriMap.get(serviceName);
    }

    private void saveMap() {
        try {
            String jsonString = JSON.toJSONString(serviceUriMap);
            FileUtils.writeStringToFile(file, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMap() {
        try {
            String jsonString = FileUtils.readFileToString(file);
            serviceUriMap = JSON.parseObject(jsonString, new TypeReference<LinkedHashMap<String, List<Url>>>() {
            }, Feature.OrderedField);
            if (serviceUriMap == null) {
                serviceUriMap = new LinkedHashMap<>(256);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
