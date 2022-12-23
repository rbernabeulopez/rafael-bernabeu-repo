package org.example.backend.application;


import org.example.backend.domain.entity.Client;
import org.example.backend.domain.entity.Trip;
import org.example.backend.domain.exception.EntityNotFoundException;
import org.example.backend.domain.exception.InvalidDataException;
import org.example.backend.domain.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TripServiceImplTest {

    @InjectMocks
    private TripServiceImpl tripService;

    @Mock
    private TripRepository tripRepository;


    @Test
    void saveTripOK() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
            .arrivalDate(LocalDate.now()).status("OK").build();
        when(tripRepository.save(trip)).thenReturn(trip);

        // WHEN
        assertDoesNotThrow(() -> tripService.saveTrip(trip));

        // THEN
        verify(tripRepository, times(1)).save(trip);
    }

    @Test
    void saveTripWithNullOrigin() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin(null).destination("Madrid").departureDate(LocalDate.EPOCH)
            .arrivalDate(LocalDate.now()).status("OK").build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> tripService.saveTrip(trip));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Origin cannot be null");
        verify(tripRepository, never()).save(any());
    }

    @Test
    void saveTripWithNullDestination() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin("Alicante").destination(null).departureDate(LocalDate.EPOCH)
            .arrivalDate(LocalDate.now()).status("OK").build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> tripService.saveTrip(trip));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Destination cannot be null");
        verify(tripRepository, never()).save(any());
    }

    @Test
    void saveTripWithNullDepartureDate() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin("Alicante").destination("Madrid").departureDate(null)
            .arrivalDate(LocalDate.now()).status("OK").build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> tripService.saveTrip(trip));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Departure date cannot be null");
        verify(tripRepository, never()).save(any());
    }

    @Test
    void saveTripWithNullArrivalDate() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
            .arrivalDate(null).status("OK").build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> tripService.saveTrip(trip));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Arrival date cannot be null");
        verify(tripRepository, never()).save(any());
    }

    @Test
    void saveTripWithNullStatus() {
        // GIVEN
        Trip trip = Trip.builder()
            .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
            .arrivalDate(LocalDate.now()).status(null).build();

        // WHEN
        InvalidDataException invalidDataException =
                assertThrows(InvalidDataException.class, () -> tripService.saveTrip(trip));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Status cannot be null");
        verify(tripRepository, never()).save(any());
    }


    @Test
    void removeTripByIdOK() {
        // GIVEN
        long tripId = 1;
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(new Trip()));
        doNothing().when(tripRepository).deleteById(tripId);

        // WHEN
        assertDoesNotThrow(() -> tripService.removeTripById(tripId));

        // THEN
        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).deleteById(tripId);
    }

    @Test
    void updateTripByIdOK() {
        // GIVEN
        long tripId = 1;
        Trip trip = Trip.builder()
                .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").build();
        trip.setId(tripId);
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(tripRepository.save(trip)).thenReturn(trip);
        trip.setId(0);


        // WHEN
        assertDoesNotThrow(() -> tripService.updateTripById(tripId, trip));

        // THEN
        trip.setId(tripId);
        verify(tripRepository, times(1)).findById(tripId);
        verify(tripRepository, times(1)).save(trip);

    }

    @Test
    void retrieveTripByIdOK() {
        // GIVEN
        long tripId = 1;
        Trip expectedTrip = Trip.builder()
                .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").build();
        when(tripRepository.findById(tripId)).thenReturn(Optional.of(expectedTrip));

        // WHEN
        Trip actualTrip =
            assertDoesNotThrow(() -> tripService.retrieveTripById(tripId));

        // THEN
        assertThat(actualTrip).isEqualTo(expectedTrip);
        verify(tripRepository, times(1)).findById(tripId);
    }

    @Test
    void retrieveTripByIdNotExists() {
        // GIVEN
        long tripId = 1;
        when(tripRepository.findById(tripId)).thenReturn(Optional.empty());

        // WHEN
        EntityNotFoundException entityNotFoundException =
                assertThrows(EntityNotFoundException.class, () -> tripService.retrieveTripById(tripId));

        // THEN
        assertThat(entityNotFoundException.getMessage()).isEqualTo("Trip with id '"+ tripId +"' not found");
        verify(tripRepository, times(1)).findById(tripId);
    }

    @Test
    void retrieveAllTripsOK() {
        // GIVEN
        Trip expectedTrip = Trip.builder()
                .origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").build();
        when(tripRepository.findAll()).thenReturn(List.of(expectedTrip));

        // WHEN
        List<Trip> actualTrips = tripService.retrieveAllTrips();

        // THEN
        assertThat(actualTrips).isEqualTo(List.of(expectedTrip));
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void countPassengersOK() {
        // GIVEN
        List<Client> clients = List.of(Client.builder()
            .id(1).name("Rafa").surname("Barnabe").age(30)
            .email("rafa@gmail.com").phoneNumber(12345678).build());

        Trip trip = Trip.builder()
                .id(1).origin("Alicante").destination("Madrid").passengers(clients)
                .departureDate(LocalDate.EPOCH).arrivalDate(LocalDate.now()).status("OK").build();

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));

        // WHEN

        int numPassengers =
            assertDoesNotThrow(() -> tripService.countPassengers(trip.getId()));

        // THEN
        assertThat(numPassengers).isEqualTo(trip.getPassengers().size());
        verify(tripRepository, times(1)).findById(trip.getId());
    }

    @Test
    void updateStatusOK() {
        // GIVEN
        String expectedStatus = "FINISHED";
        Trip trip = Trip.builder()
                .id(1).origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").build();

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));

        // WHEN

        assertDoesNotThrow(() -> tripService.updateStatus(trip.getId(), expectedStatus));

        // THEN
        verify(tripRepository, times(1)).findById(trip.getId());
    }

    @Test
    void updateStatusWithNullStatus() {
        // GIVEN
        Trip trip = Trip.builder()
                .id(1).origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
                .arrivalDate(LocalDate.now()).status("OK").build();
        long tripId = trip.getId();

        // WHEN

        InvalidDataException invalidDataException =
            assertThrows(InvalidDataException.class, () -> tripService.updateStatus(tripId, null));

        // THEN
        assertThat(invalidDataException.getMessage()).isEqualTo("Status cannot be null");
        verify(tripRepository, never()).findById(tripId);
    }

    @Test
    void getStatusOK() {
        // GIVEN
        Trip trip = Trip.builder()
            .id(1).origin("Alicante").destination("Madrid").departureDate(LocalDate.EPOCH)
            .arrivalDate(LocalDate.now()).status("FINISHED").build();

        when(tripRepository.findById(trip.getId())).thenReturn(Optional.of(trip));

        // WHEN

        String actualStatus =
            assertDoesNotThrow(() -> tripService.getStatus(trip.getId()));

        // THEN
        assertThat(actualStatus).isEqualTo(trip.getStatus());
        verify(tripRepository, times(1)).findById(trip.getId());
    }
}