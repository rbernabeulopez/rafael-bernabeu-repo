package org.example.backend.application;

import org.example.backend.domain.entity.Trip;

import java.util.List;

public interface TripService {
    void saveTrip(Trip trip);

    void removeTripById(long id);

    void updateTripById(long id, Trip trip);

    Trip retrieveTripById(long id);

    List<Trip> retrieveAllTrips();

    void addPassenger(long tripId, long clientId);

    int countPassengers(long tripId);

    void updateStatus(long tripId, String status);

    String getStatus(long tripId);
}
