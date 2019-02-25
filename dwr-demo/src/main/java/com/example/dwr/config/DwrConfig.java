package com.example.dwr.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-上午 11:47
 */
@Configuration
@ImportResource(locations = "classpath:spring.xml")
public class DwrConfig {

    /**
     *
     * @param springDwrServlet {@link SpringDwrServlet }
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(SpringDwrServlet springDwrServlet) {

        ServletRegistrationBean servletRegister = new ServletRegistrationBean(springDwrServlet, "/dwr/*");
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("debug", "true");
        servletRegister.setInitParameters(initParameters);

        //设置成true使DWR能够debug和进入测试页面。
        servletRegister.addInitParameter("debug", "true");
        //pollAndCometEnabled 设置成true能增加服务器的加载能力，尽管DWR有保护服务器过载的机制。
        servletRegister.addInitParameter("pollAndCometEnabled", "true");

        servletRegister.addInitParameter("activeReverseAjaxEnabled", "true");
        servletRegister.addInitParameter("maxWaitAfterWrite", "60");
        return servletRegister;
    }
}
