package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class WeatherRepository implements PanacheRepository<Weather> {
    public List<Weather> findByCityIgnoreCase(String city) {
        return find("LOWER(city) = LOWER(?1)", city).list();
    }
}
