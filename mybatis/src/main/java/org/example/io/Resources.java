/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.example.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * 资源管理工具类
 *
 * @author zhou1
 */
public class Resources {

    private static ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

    public static URL getResourceURL(String resource) throws IOException {
        URL url = systemClassLoader.getResource(resource);
        if (null == url) {
            url = systemClassLoader.getResource("/" + resource);
        }
        return url;
    }

    public static InputStream getResourceAsStream(String resource) throws IOException {
        InputStream returnValue = systemClassLoader.getResourceAsStream(resource);
        if (null == returnValue) {
            returnValue = systemClassLoader.getResourceAsStream("/" + resource);
        }

        if (null != returnValue) {
            return returnValue;
        }
        return null;
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(resource);
        props.load(in);
        in.close();
        return props;
    }

    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        InputStream in = getUrlAsStream(urlString);
        props.load(in);
        in.close();
        return props;
    }

    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return Class.forName(className, true, systemClassLoader);
    }
}
