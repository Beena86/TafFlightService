package com.techarch.TafFlightService.Controller;

import com.techarch.TafFlightService.DTO.Flights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/flights")

public class FlightController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${taf.datastore.service.url}")
    private String datastoreServiceUrl;

    @GetMapping
    public ResponseEntity<?> getAllFlights() {
        ResponseEntity<?> response = restTemplate.exchange(datastoreServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Flights>>() {});
        return ResponseEntity.ok(response.getBody());
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<?> getFlightById(@PathVariable Long flightId) {
        String url = datastoreServiceUrl + "/" + flightId;
        ResponseEntity<Flights> response = restTemplate.getForEntity(url, Flights.class);
        return ResponseEntity.ok(response.getBody());
    }

    @PostMapping
    public ResponseEntity<?> addFlight(@RequestBody Flights flight) {
        ResponseEntity<Flights> response = restTemplate.postForEntity(datastoreServiceUrl, flight, Flights.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<?> updateFlight(@PathVariable Long flightId, @RequestBody Flights flight) {
        String url = datastoreServiceUrl + "/" + flightId;
        restTemplate.put(url, flight);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity<?> deleteFlight(@PathVariable Long flightId) {
        String url = datastoreServiceUrl + "/" + flightId;
        restTemplate.delete(url);
        return ResponseEntity.noContent().build();
    }
}
