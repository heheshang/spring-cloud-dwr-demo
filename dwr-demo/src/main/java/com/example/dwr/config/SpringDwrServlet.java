package com.example.dwr.config;

import org.directwebremoting.impl.StartupUtil;
import org.directwebremoting.spring.DwrSpringServlet;
import org.directwebremoting.spring.SpringContainer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-下午 1:00
 */
@Component
public class SpringDwrServlet extends DwrSpringServlet {

    private static final long serialVersionUID = 1L;

    /**
     *
     * @param servletConfig {@link ServletConfig}
     * @return
     */
    @Override
    protected SpringContainer createContainer(ServletConfig servletConfig) {

        ApplicationContext appContext = getApplicationContext(servletConfig.getServletContext());

        SpringDwrContainer springContainer = new SpringDwrContainer();
        springContainer.setBeanFactory(appContext);
        StartupUtil.setupDefaultContainer(springContainer, servletConfig);
        return springContainer;
    }


}
