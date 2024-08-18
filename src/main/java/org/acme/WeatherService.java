package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class WeatherService {

    @Inject
    private WeatherRepository weatherRepository;

    public List<Weather> getAllWeathers() {
        return weatherRepository.listAll();
    }

    public List<BigDecimal> getTemperaturesByCity(String city) {
        return weatherRepository.findByCityIgnoreCase(city).stream()
                .map(Weather::getTemperature)
                .collect(Collectors.toList());
    }

    // Get specific stat (min, max, mean) for a city
    public BigDecimal getStatByCity(String city, String stat) {
        List<BigDecimal> temperatures = getTemperaturesByCity(city);
        BigDecimal result;
        switch (stat.toLowerCase()) {
            case "min":
                result = temperatures.stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                break;
            case "max":
                result = temperatures.stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO);
                break;
            case "mean":
                result = temperatures.stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(new BigDecimal(temperatures.size()), 1, RoundingMode.HALF_UP);
                break;
            default:
                throw new IllegalArgumentException("Invalid stat: " + stat);
        }
        return result;
    }

    // Get weather stats (min, max, mean) for all cities
    public List<WeatherStats> getWeatherStats() {
        Map<String, List<BigDecimal>> cityTemps = weatherRepository.listAll().stream()
                .collect(Collectors.groupingBy(
                        Weather::getCity,
                        Collectors.mapping(Weather::getTemperature, Collectors.toList())
                ));

        return cityTemps.entrySet().stream()
                .map(entry -> new WeatherStats(
                        entry.getKey(),
                        entry.getValue().stream().min(BigDecimal::compareTo).orElse(BigDecimal.ZERO),
                        entry.getValue().stream().max(BigDecimal::compareTo).orElse(BigDecimal.ZERO),
                        entry.getValue().stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(entry.getValue().size()), 1, RoundingMode.HALF_UP)
                ))
                .sorted((a, b) -> a.getCity().compareToIgnoreCase(b.getCity()))
                .collect(Collectors.toList());
    }
}
