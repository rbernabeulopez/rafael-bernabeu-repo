package org.example.backend.application;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.domain.entity.Client;
import org.example.backend.domain.entity.Trip;
import org.example.backend.domain.exception.EntityNotFoundException;
import org.example.backend.domain.exception.InvalidDataException;
import org.example.backend.domain.repository.TripRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class TripServiceImpl implements TripService {

    private TripRepository tripRepository;

    private ClientService clientService;

    private void checkTripData(Trip trip) {
        try {
            Objects.requireNonNull(trip.getOrigin(), "Origin cannot be null");
            Objects.requireNonNull(trip.getDestination(), "Destination cannot be null");
            Objects.requireNonNull(trip.getDepartureDate(), "Departure date cannot be null");
            Objects.requireNonNull(trip.getArrivalDate(), "Arrival date cannot be null");
            Objects.requireNonNull(trip.getStatus(), "Status cannot be null");
        } catch (NullPointerException nullPointerException) {
            throw new InvalidDataException(nullPointerException.getMessage());
        }
    }

    @Override
    public void saveTrip(Trip trip) {
        log.info("Creating trip with credentials {}", trip);
        checkTripData(trip);
        tripRepository.save(trip);
    }

    @Override
    public void removeTripById(long id) {
        log.info("Removing trip with id {}", id);
        Trip trip = retrieveTripById(id);

        trip.setPassengers(new ArrayList<>());
        tripRepository.save(trip);

        tripRepository.deleteById(id);
    }

    @Override
    public void updateTripById(long id, Trip trip) {
        log.info("Updating trip with id {} with data {}", id, trip);
        checkTripData(trip);
        retrieveTripById(id);

        trip.setId(id);
        tripRepository.save(trip);
    }

    @Override
    public Trip retrieveTripById(long id) {
        log.info("Retrieving trip with id {}", id);
        return tripRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Trip with id '"+ id +"' not found")
        );
    }

    @Override
    public List<Trip> retrieveAllTrips() {
        log.info("Retrieving all trip");
        return tripRepository.findAll();
    }

    @Override
    public void addPassenger(long tripId, long clientId) {
        Trip trip = retrieveTripById(tripId);
        Client nextClient = clientService.retrieveClientById(clientId);

        List<Client> clients = trip.getPassengers();
        clients.add(nextClient);
        trip.setPassengers(clients);

        tripRepository.save(trip);
    }

    @Override
    public int countPassengers(long tripId) {
        Trip trip = retrieveTripById(tripId);
        return trip.getPassengers().size();
    }

    @Override
    public void updateStatus(long tripId, String status) {
        if(status == null) {
            throw new InvalidDataException("Status cannot be null");
        }
        Trip trip = retrieveTripById(tripId);
        trip.setStatus(status);
        tripRepository.save(trip);
    }

    @Override
    public String getStatus(long tripId) {
        Trip trip = retrieveTripById(tripId);
        return trip.getStatus();
    }
}
