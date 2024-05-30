package com.example.oopsem3lab1.Core.Models;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter @Setter
public class TripDescription {
    private int id;
    private String title;
    private String description;
    private float baseCost;
    private boolean isHot;
    private float salesCost;
    private int capacity;

    @Override
    public String toString() {
        return "TripDescription{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", baseCost=" + baseCost +
                ", isHot=" + isHot +
                ", salesCost=" + salesCost +
                ", capacity=" + capacity +
                '}';
    }

    public static String getCreationTableCreationSql() {
        return "CREATE TABLE IF NOT EXISTS trips_description (" +
                "id SERIAL PRIMARY KEY, " +
                "title varchar(255), " +
                "description varchar(1024)," +
                "baseCost FLOAT," +
                "isHot BOOLEAN," +
                "salesCost FLOAT," +
                "capacity INTEGER)";
    }
}
