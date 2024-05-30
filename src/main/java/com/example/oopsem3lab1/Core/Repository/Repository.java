package com.example.oopsem3lab1.Core.Repository;

import com.example.oopsem3lab1.Core.Models.TripDescription;
import com.example.oopsem3lab1.Core.Models.TripInstance;
import org.apache.james.mime4j.dom.datetime.DateTime;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public interface Repository {
    Connection getConnection();

    void recreateConnection();

    TripInstance findTripInstanceById(int id);

    List<TripInstance> findTripInstancesByPrompt(String prompt);

    void buyTrip(String username, int tripId);
    void refundTrip(String username, int tripId);

    void addTrip(String title, String description, float baseCost, boolean isHot, float salesCost, int capacity);

    void addTripInstance(int tripId, LocalDate time);

    List<TripDescription> findTripsByPrompt(String prompt);
}
