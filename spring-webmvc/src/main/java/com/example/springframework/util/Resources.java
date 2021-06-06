package com.example.springframework.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @author zhougaojun
 */
public class Resources {

    private static final ClassLoader loader = ClassLoader.getSystemClassLoader();

    public static URL getResourceURL(String resource) throws IOException {
        URL url = loader.getResource(resource);
        if (null == url) {
            url = loader.getResource("/" + resource);
        }
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return url;
    }

    /**
     * 获取classpath指定目录下的文件
     */
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    /**
     * 获取包路径下的所有文件（不含含子包）
     */
    public static File[] getPackageAsFiles(String packagePath) throws IOException {
        String path = packagePath.replace(".", "/");
        return getResourceAsFile(path).listFiles();
    }

    /**
     * 转换包路径为file路径
     */
    public static File getPackageAsFile(String packagePath) throws IOException {
        String path = packagePath.replace(".", "/");
        return getResourceAsFile(path);
    }
}
