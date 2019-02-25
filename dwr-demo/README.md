## DWR http 请求反推送技术
[参考网址1](https://blog.csdn.net/vtnem/article/details/80802886)

[参考网址2](https://blog.csdn.net/xyl870419/article/details/17914055)

[参考网址3-简书](https://www.jianshu.com/p/24a7e3298ed1)

## 环境配置如下
- pom 文件中添加
```xml
       <dependency>
            <groupId>org.directwebremoting</groupId>
            <artifactId>dwr</artifactId>
            <version>3.0.2-RELEASE</version>
        </dependency>
```
## 定义配置spring-boot配置文件如下

- DwrConfig 使用SpringDwrServlet
```java

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


        return servletRegister;
    }
}


```
- SpringDwrContainer 定义springDwr 容器

```java

package com.example.dwr.config;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.extend.ContainerConfigurationException;
import org.directwebremoting.spring.SpringContainer;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.ClassUtils;

import java.util.Map;

/**
 * {@link SpringContainer}
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-下午 1:01
 */
public class SpringDwrContainer extends SpringContainer {

    @Override
    public void addParameter(String askFor, Object valueParam) throws ContainerConfigurationException {

        try {
            Class<?> clz = ClassUtils.forName(askFor, ClassUtils.getDefaultClassLoader());

            @SuppressWarnings("unchecked")
            Map<String, Object> beansOfType = (Map<String, Object>) ((ListableBeanFactory) beanFactory)
                    .getBeansOfType(clz);

            if (beansOfType.isEmpty()) {
                super.addParameter(askFor, valueParam);
            } else if (beansOfType.size() > 1) {
                String key = StringUtils.uncapitalize(SpringDwrServlet.class.getSimpleName());
                if (beansOfType.containsKey(key)) {
                    beans.put(askFor, beansOfType.get(key));
                } else {
                    throw new ContainerConfigurationException("spring容器中无法找到对应servlet:" + key);
                }
            } else {
                beans.put(askFor, beansOfType.values().iterator().next());
            }
        } catch (ClassNotFoundException ex) {
            super.addParameter(askFor, valueParam);
        }


    }
}

```
- SpringDwrServlet 定义servlet
```java

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

```

- 定义远程请求类

```java
package com.example.dwr.dwrpush;


import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "DemoService")
public class DemoService {

    // 这里也可以使用 @Autowired 注入依赖的其他服务

    @RemoteMethod
    public String hello() {

        return "hello";
    }

    @RemoteMethod
    public String echo(String string) {

        return string + 1111;
    }

}

```

- 定义spring-dwr spring+dwr 集成 配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dwr="http://www.directwebremoting.org/schema/spring-dwr"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
       http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
       http://www.directwebremoting.org/schema/spring-dwr http://www.directwebremoting.org/schema/spring-dwr-3.0.xsd">

    <dwr:annotation-config/>
    <dwr:annotation-scan base-package="com.example.dwr.dwrpush" scanDataTransferObject="true" scanRemoteProxy="true"/>
    <dwr:configuration/>
</beans>

```
- 配置简单的访问页面
```html
<html>
<head>
	<title></title>
	<script type='text/javascript' src='/dwr/engine.js'></script>
	<script type='text/javascript' src='/dwr/interface/DemoService.js'></script>
</head>
hello
<script>
	DemoService.echo('回声测试', function (str) {
		alert(str);
	});

</script>
</html>
```

## 最终访问地址如下
```html
http://localhost:8081/
```