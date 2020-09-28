package com.example.pandemic_monitoring.repository.geospatialdb;

import java.util.Map;

public interface GeoSpatialDb {

    void Save(Map object);
    void Query(Map query);
}
