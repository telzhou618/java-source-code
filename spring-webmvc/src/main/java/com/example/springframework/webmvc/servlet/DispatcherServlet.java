package com.example.springframework.webmvc.servlet;

import com.example.springframework.context.AnnotationConfigApplicationContext;
import com.example.springframework.util.Resources;
import com.example.springframework.util.XmlUtil;
import com.example.springframework.webmvc.adapter.HandlerAdapter;
import com.example.springframework.webmvc.adapter.RequestMappingHandlerAdapter;
import com.example.springframework.webmvc.mapping.HandlerExecutionChain;
import com.example.springframework.webmvc.mapping.HandlerMapping;
import com.example.springframework.webmvc.mapping.RequestMappingHandlerMapping;
import com.example.springframework.webmvc.videw.ModelAndView;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhougaojun
 */
@Slf4j
public class DispatcherServlet extends FrameworkServlet {

    private AnnotationConfigApplicationContext applicationContext;
    /**
     * HandlerAdapter
     */
    private final List<HandlerAdapter> handlerAdapters = new LinkedList<>();
    /**
     * HandlerMapping
     */
    private final List<HandlerMapping> handlerMappings = new LinkedList<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        long start = System.currentTimeMillis();
        // 初始化Spring容器
        initContext(config);
        // 初始化 HandlerMapping
        initHandlerMapping(applicationContext);
        // 初始化 HandlerAdapters
        initHandlerAdapters(applicationContext);
        log.info("DispatcherServlet in {}ms started!", System.currentTimeMillis() - start);
    }

    private void initContext(ServletConfig config) {
        try {
            // 实例化Spring 容器，扫描包，实例化bean,获取扫描包路径
            String configureLocation = config.getInitParameter("configureLocation");
            if (configureLocation == null) {
                throw new RuntimeException("spring.xml文件路径不合法！");
            }
            Document document = XmlUtil.parse(Resources.getResourceURL(configureLocation));
            String aPackage = Optional.of(document)
                    .map(Document::getRootElement)
                    .map(element -> element.element("component-scan"))
                    .map(element -> element.attributeValue("package"))
                    .orElseThrow(() -> new RuntimeException("spring.xml中文未找到扫描路径节点"));
            this.applicationContext = new AnnotationConfigApplicationContext(aPackage);
            this.applicationContext.onRefresh();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initHandlerMapping(AnnotationConfigApplicationContext context) {
        handlerMappings.add(new RequestMappingHandlerMapping(context));
    }

    private void initHandlerAdapters(AnnotationConfigApplicationContext context) {
        handlerAdapters.add(new RequestMappingHandlerAdapter());
    }

    protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        for (HandlerMapping mapping : this.handlerMappings) {
            HandlerExecutionChain handler = mapping.getHandler(request);
            if (handler != null) {
                return handler;
            }
        }
        return null;
    }

    protected HandlerAdapter getHandlerAdapter(Object handler) throws Exception {
        for (HandlerAdapter adapter : this.handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new RuntimeException("无法找到[" + handler + "]对应的处理器适配器");
    }


    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 1. 根据 URI从handlerMapping 中获取 HandlerExecutionChain（持有handler对象，拦截器链）
        ModelAndView mv = null;
        HandlerExecutionChain handlerExecutionChain = null;
        try {
            handlerExecutionChain = getHandler(request);
            if (handlerExecutionChain == null) {
                response.setStatus(404);
                response.sendError(404);
                return;
            }
            // 2. 根据 handler 从 HandlerAdapter中获取一个 Adapter
            HandlerAdapter ha = getHandlerAdapter(handlerExecutionChain.getHandler());
            // 3. 调通 Adapter 的 handler 方法，返回 modelView
            mv = ha.handle(request, response, handlerExecutionChain.getHandler());
            // 4. 根据视图解析器返回结果
            render(request, response, handlerExecutionChain.getHandler(), mv);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("request URI: {}", request.getRequestURI());
                log.debug("Response Status: {}!", response.getStatus());
            }
        }
    }

    /**
     * 渲染视图
     */
    private void render(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mv) throws Exception {
        try {
            if (null == mv) {
                return;
            }
            // TODO 渲染视图
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("视图渲染失败", e);
        }
    }
}
