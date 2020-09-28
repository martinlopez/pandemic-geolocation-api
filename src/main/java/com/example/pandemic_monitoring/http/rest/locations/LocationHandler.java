package com.example.pandemic_monitoring.http.rest.locations;

import com.example.pandemic_monitoring.domain.geopoint.GeoPoint;
import com.example.pandemic_monitoring.domain.locations.LocationEvent;
import com.example.pandemic_monitoring.domain.locations.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Date;

@RestController
@RequestMapping("/users/{userId}")
public class LocationHandler {

    private final LocationService locationService;

    public LocationHandler(LocationService locationService) {
        this.locationService = locationService;
    }

    /**
     *
     * @param userId
     * @param geoPoint
     * @return
     */
    @PostMapping("/locations")
    public @ResponseBody  Mono<LocationEvent> handlerPostLocations(@PathVariable Long userId, @RequestBody GeoPoint geoPoint) {
        return this.enrichmentLocationEvent(geoPoint, userId)
                .flatMap(locationService::publish);
    }

    /**
     *
     * @param geoPoint
     * @param userId
     * @return
     */
    private Mono<LocationEvent> enrichmentLocationEvent(GeoPoint geoPoint, Long userId) {
        return Mono.just(new LocationEvent(userId,geoPoint,new Date()));
    }
}
