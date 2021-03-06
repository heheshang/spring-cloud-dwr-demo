package com.example.dwr.config;

import org.directwebremoting.Container;
import org.directwebremoting.ServerContextFactory;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.event.ScriptSessionEvent;
import org.directwebremoting.event.ScriptSessionListener;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.DwrServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-下午 1:54
 */
public class DwrScriptSessionManagerUtil extends DwrServlet {

    private static final long serialVersionUID = -7504612622407420071L;

    public void init(final String key, final String value) throws ServletException {

        Container container = ServerContextFactory.get().getContainer();
        ScriptSessionManager manager = container.getBean(ScriptSessionManager.class);
        ScriptSessionListener listener = new ScriptSessionListener() {
            @Override
            public void sessionCreated(ScriptSessionEvent ev) {

                HttpSession session = WebContextFactory.get().getSession();
                //String userId = ((User) session.getAttribute("userinfo")).getHumanid() + "";
                System.out.println("a ScriptSession is created!");
                ev.getSession().setAttribute(key, value);
            }

            public void sessionDestroyed(ScriptSessionEvent ev) {

                System.out.println("a ScriptSession is distroyed");
            }
        };
        manager.addScriptSessionListener(listener);

    }

}
