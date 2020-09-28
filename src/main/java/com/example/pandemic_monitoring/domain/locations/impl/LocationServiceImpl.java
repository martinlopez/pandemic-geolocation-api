package com.example.pandemic_monitoring.domain.locations.impl;

import com.example.pandemic_monitoring.domain.locations.LocationEvent;
import com.example.pandemic_monitoring.domain.locations.LocationService;
import com.example.pandemic_monitoring.repository.streaming.StreamingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Service
public class LocationServiceImpl implements LocationService {

    private final StreamingRepository locationStreamingRepository;

    LocationServiceImpl(StreamingRepository locationStreamingRepository) {
        this.locationStreamingRepository = locationStreamingRepository;
        //if scope is consumer
        locationStreamingRepository.receive(this.receive());
    }

    @Override
    public Mono<LocationEvent> publish(LocationEvent locationEvent) {
        //TODO: save in cache last geo-point to avoid sends unnecessary notifications. Think if this logic should be here.
        return locationStreamingRepository.publish( locationEvent)
                .then(Mono.just(locationEvent));
    }

    @Override
    public Function<Object,Void> receive() {
        Function<Object,Void> fn = r -> {
            //call API Calls to store in db.
            System.out.println(r);
            return null;
        };
        return fn;
    }


}
