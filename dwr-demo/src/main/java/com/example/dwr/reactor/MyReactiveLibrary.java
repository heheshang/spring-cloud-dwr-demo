package com.example.dwr.reactor;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-上午 11:02
 */
@Component
public class MyReactiveLibrary {

    public Flux<String> alphabet5(char from) {
        return Flux.range((int) from, 5)
                .map(i -> "" + (char) i.intValue());
    }

    public Mono<String> withDelay(String value, int delaySeconds) {
        return Mono.just(value)
                .delaySubscription(Duration.ofSeconds(delaySeconds));
    }
}