package org.example.backend.infrastructure.controller;

import lombok.AllArgsConstructor;
import org.example.backend.application.TripService;
import org.example.backend.domain.entity.Trip;
import org.example.backend.infrastructure.controller.dto.TripInputDto;
import org.example.backend.infrastructure.mapper.TripMapper;
import org.example.openfeignclients.backend.dto.TripOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TripController {
    private TripService tripService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("trip")
    void save(@RequestBody TripInputDto tripInputDto) {
        Trip trip = TripMapper.INSTANCE.tripInputDtoToTrip(tripInputDto);
        tripService.saveTrip(trip);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("trip/{id}")
    void delete(@PathVariable long id) {
        tripService.removeTripById(id);
    }

    @PutMapping("trip/{id}")
    void update(@PathVariable long id, @RequestBody TripInputDto tripInputDto) {
        Trip trip = TripMapper.INSTANCE.tripInputDtoToTrip(tripInputDto);
        tripService.updateTripById(id, trip);
    }

    @GetMapping("trip")
    List<TripOutputDto> getAll() {
        return tripService.retrieveAllTrips()
                .stream().map(TripMapper.INSTANCE::tripToTripOutputDto).toList();
    }

    @GetMapping("trip/{id}")
    TripOutputDto getById(@PathVariable long id) {
        return TripMapper.INSTANCE.tripToTripOutputDto(tripService.retrieveTripById(id));
    }

    @PostMapping("trip/addPassenger/{tripId}/{passengerId}")
    void addPassenger(@PathVariable("tripId") long tripId, @PathVariable("passengerId") long passengerId) {
        tripService.addPassenger(tripId, passengerId);
    }

    @GetMapping("passenger/count/{tripId}")
    int countPassengers(@PathVariable long tripId) {
        return tripService.countPassengers(tripId);
    }

    @PutMapping("trip/{tripId}/{tripStatus}")
    void updateStatus(@PathVariable("tripId") long tripId, @PathVariable("tripStatus") String tripStatus) {
        tripService.updateStatus(tripId, tripStatus);
    }

    @GetMapping("trip/verify/{tripId}")
    String getStatus(@PathVariable long tripId) {
        return tripService.getStatus(tripId);
    }
}