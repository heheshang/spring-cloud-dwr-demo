package com.example.dwr.reactor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-02-25-上午 10:56
 */
@RestController
public class ExampleController {

    private final MyReactiveLibrary reactiveLibrary;

    //Note Spring Boot 4.3+ autowires single constructors now
    public ExampleController(MyReactiveLibrary reactiveLibrary) {
        this.reactiveLibrary = reactiveLibrary;
    }

    @GetMapping("hello/{who}")
    public Mono<String> hello(@PathVariable String who) {
        return Mono.just(who)
                .map(w -> "Hello " + w + "!");
    }

    @GetMapping("helloDelay/{who}")
    public Mono<String> helloDelay(@PathVariable String who) {
        return reactiveLibrary.withDelay("Hello " + who + "!!", 2);
    }

  /*  @PostMapping("heyMister")
    public Flux<String> hey(@RequestBody Mono<Sir> body) {
        return Mono.just("Hey mister ")
                .concatWith(body
                        .flatMap(sir -> Flux.fromArray(sir.getLastName().split("")))
                        .map(String::toUpperCase)
                        .take(1)
                ).concatWith(Mono.just(". how are you?"));
    }*/
}