package com.example.dwr.dwrpush;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-下午 2:04
 */
public abstract class AbstractSpringNotifyConsumer {

    public abstract void onMessage(TextNotifyMessage message) throws NotifyException;
}
