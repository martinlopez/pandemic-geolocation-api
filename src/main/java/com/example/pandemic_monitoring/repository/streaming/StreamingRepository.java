package com.example.pandemic_monitoring.repository.streaming;

import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface StreamingRepository {
    Mono<Void> publish(Object event);
    void receive(Function<Object, Void> fn);
}
