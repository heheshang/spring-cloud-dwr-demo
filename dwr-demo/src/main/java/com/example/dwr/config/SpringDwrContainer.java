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
