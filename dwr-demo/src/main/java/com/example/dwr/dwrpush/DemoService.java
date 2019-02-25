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
