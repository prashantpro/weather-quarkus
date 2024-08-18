package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Path("weathers")
public class WeatherResource {

    @Inject
    private WeatherService weatherService;

    // Endpoint to get all weather data
    @GET
    public List<Weather> getAllWeathers() {
        return weatherService.getAllWeathers();
    }

    // Endpoint to get weather data by city and optional stat (min, mean, max)
    @Path("/city")
    @GET
    public Object getWeatherByCity(@QueryParam("name") String name, @QueryParam("stat") String stat) {
        if (stat == null) {
            // Return all temperatures for the city
            return weatherService.getTemperaturesByCity(name);
        } else {
            // Return the requested stat for the city
            BigDecimal result = weatherService.getStatByCity(name, stat);
            return Map.of(stat, result);
        }
    }

    // Endpoint to get weather statistics (min, max, mean) for all cities
    @Path("/stats")
    @GET
    public List<WeatherStats> getWeatherStats() {
        return weatherService.getWeatherStats();
    }
}
