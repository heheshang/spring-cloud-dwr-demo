package com.example.dwr.dwrpush;

import com.example.dwr.config.DwrScriptSessionManagerUtil;
import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-下午 1:55
 */
@Service
@RemoteProxy
public class Demo2Consumer extends AbstractSpringNotifyConsumer  {
    //保存scriptSession ， 这个方法需要在页面刚已加载的时候调用，为了前端和后端建立连接。
    @RemoteMethod
    public void onPageLoad(String tag) {
        //获取当前的ScriptSession
        try {
            ScriptSession scriptSession =  WebContextFactory.get().getScriptSession();
            if(scriptSession != null){
                scriptSession.setAttribute("tag", tag);
            }
            DwrScriptSessionManagerUtil dwrScriptSessionManagerUtil = new DwrScriptSessionManagerUtil() ;
            dwrScriptSessionManagerUtil.init("tag",tag);
        } catch (Exception e) {

        }
        System.out.println("onPageLoad 被调用 ：" + tag);
    }

    // mq接受到消息会触发这个方法
    @Override
    public void onMessage(TextNotifyMessage message) throws NotifyException {

        System.out.println(" 收到消息  " + message.getData());
        System.err.println(" 收到消息  " + message.getData());
        // 下面代码变化不大，主要是给前端推送
        Browser.withAllSessionsFiltered(new ScriptSessionFilter() {

            public boolean match(ScriptSession session) {
                /* 这块判断是否合法 ，可以在这块验证用户的合法性，为了简单我直接返回true*/
                return true;
                  /*  if (session.getAttribute("userId") == null) {
                        return false;
                    } else {
                        return (session.getAttribute("userId")).equals(userId);
                    }*/
            }

        }, new Runnable() {
            private ScriptBuffer script = new ScriptBuffer();

            public void run() {
                //设定前台接收消息的方法和参数  在前台js里定义getmessage (data) 的方法，就会自动被调用
                script.appendCall("getmessage", message.getData());
                Collection<ScriptSession> sessions = Browser.getTargetSessions();
                for (ScriptSession scriptSession : sessions) {
                    scriptSession.addScript(script);
                }
                System.out.println("dwrtool  showmessage 调用 ");
            }
        });
    }
}
