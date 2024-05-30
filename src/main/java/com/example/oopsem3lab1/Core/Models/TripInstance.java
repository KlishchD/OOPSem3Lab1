package com.example.oopsem3lab1.Core.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class TripInstance {
    private TripDescription trip;
    private int id;
    private LocalDate time;
    private User buyer;
    private TripPaymentStatus status;

    @Override
    public String toString() {
        return "TripInstance{" +
                "trip=" + trip +
                ", id=" + id +
                ", time=" + time +
                '}';
    }

    public static String getCreationTableCreationSql() {
        String tripInstanceCreationQuery = "CREATE TABLE IF NOT EXISTS trips_instances (" +
                "id SERIAL PRIMARY KEY, " +
                "tripId INTEGER, " +
                "time DATE);";

        String tripCustomersCreationQuery = "CREATE TABLE IF NOT EXISTS trip_customers (id INTEGER, " +
                "username VARCHAR(36), " +
                "status INTEGER);";

        return tripInstanceCreationQuery + tripCustomersCreationQuery;
    }
}
