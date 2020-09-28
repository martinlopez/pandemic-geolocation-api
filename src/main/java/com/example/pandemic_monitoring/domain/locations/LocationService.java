package com.example.pandemic_monitoring.domain.locations;

import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface LocationService {
     Mono<LocationEvent> publish(LocationEvent locationEvent);
     Function<Object, Void> receive();
}
