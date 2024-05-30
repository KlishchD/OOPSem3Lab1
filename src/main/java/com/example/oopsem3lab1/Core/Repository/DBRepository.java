package com.example.oopsem3lab1.Core.Repository;

import com.example.oopsem3lab1.Core.Models.TripDescription;
import com.example.oopsem3lab1.Core.Models.TripInstance;
import com.example.oopsem3lab1.Core.Models.TripPaymentStatus;
import com.example.oopsem3lab1.Core.Models.User;
import org.apache.james.mime4j.dom.datetime.DateTime;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBRepository implements Repository {
    private Connection connection;

    private String url;
    private String user;
    private String password;

    public DBRepository(String url, String user, String password) {
        try {
            this.url = url;
            this.user = user;
            this.password = password;

            recreateConnection();

            List<String> tableCreationTablesQueries = new ArrayList<>();
            tableCreationTablesQueries.add(TripInstance.getCreationTableCreationSql());
            tableCreationTablesQueries.add(TripDescription.getCreationTableCreationSql());

            Statement statement = connection.createStatement();

            for (String tableCreationQuery : tableCreationTablesQueries) {
                statement.execute(tableCreationQuery);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void recreateConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TripInstance findTripInstanceById(int id) {
        try {
            Statement statement = connection.createStatement();

            String tripsDescriptionSelect = "select id, title, description, baseCost, isHot, salesCost, capacity from trips_description";

            String tripInstancesSelect = "select id as instanceId, tripId as id, time from trips_instances where id = " + id;

            String tripCustomersSelect = "select id as instanceId, username, status from trip_customers";

            String userSelect = "select username, CONCAT(first_name, ' ', last_name) as name from user_entity";

            String tripInstancesWithDescription = "((" + tripsDescriptionSelect + ") JOIN (" +  tripInstancesSelect + ") USING(id))";

            String query = "select id, instanceId, title, description, baseCost, isHot, salesCost, capacity, time, status, username, name from (" + tripInstancesWithDescription + " LEFT JOIN (" + tripCustomersSelect + ") USING(instanceId)) LEFT JOIN (" + userSelect + ") USING(username)";

            ResultSet resultSet = statement.executeQuery(query);

            TripInstance instance = null;

            if (resultSet.next()) {
                instance = new TripInstance();
                parseTripInstance(resultSet, instance);
            }

            return instance;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TripInstance> findTripInstancesByPrompt(String prompt) {
        try {
            Statement statement = connection.createStatement();

            String lowerPrompt = prompt.toLowerCase();

            String tripsDescriptionSelect;
            if (prompt.isEmpty()) {
                tripsDescriptionSelect = "select id, " +
                        "title, " +
                        "description, " +
                        "baseCost, " +
                        "isHot, " +
                        "salesCost, " +
                        "capacity " +
                        "from trips_description";
            } else {
                tripsDescriptionSelect = "select id, " +
                        "title, " +
                        "description, " +
                        "baseCost, " +
                        "isHot, " +
                        "salesCost, " +
                        "capacity " +
                        "from trips_description " +
                        "where strpos(lower(title), '" + lowerPrompt + "') > 0 or strpos(lower(description), '" + lowerPrompt + "') > 0";
            }

            String tripInstancesSelect = "select id as instanceId, tripId as id, time from trips_instances";

            String tripCustomersSelect = "select id as instanceId, username, status from trip_customers";

            String userSelect = "select username, CONCAT(first_name, ' ', last_name) as name from user_entity";

            String tripInstancesWithDescription = "((" + tripsDescriptionSelect + ") JOIN (" +  tripInstancesSelect + ") USING(id))";

            String query = "select id, instanceId, title, description, baseCost, isHot, salesCost, capacity, time, status, username, name from (" + tripInstancesWithDescription + " LEFT JOIN (" + tripCustomersSelect + ") USING(instanceId)) LEFT JOIN (" + userSelect + ") USING(username)";

            ResultSet resultSet = statement.executeQuery(query);

            List<TripInstance> instances = new ArrayList<>();

            while (resultSet.next()) {
                TripInstance instance = new TripInstance();

                parseTripInstance(resultSet, instance);

                instances.add(instance);
            }

            return instances;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void buyTrip(String username, int tripId) {
        try {
            Statement statement = connection.createStatement();

            String removeTripCustomer = "delete from trip_customers where id = " + tripId + " and username = '" + username + "'";
            statement.execute(removeTripCustomer);

            String addTripCustomer = "insert into trip_customers VALUES(" + tripId + ", '" + username + "', 2)";
            statement.execute(addTripCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refundTrip(String username, int tripId) {
        try {
            Statement statement = connection.createStatement();

            String removeTripCustomer = "delete from trip_customers where id = " + tripId + " and username = '" + username + "'";
            statement.execute(removeTripCustomer);

            String addTripCustomer = "insert into trip_customers VALUES(" + tripId + ", '" + username + "', 0)";
            statement.execute(addTripCustomer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseTripInstance(ResultSet resultSet, TripInstance instance) throws SQLException {
        instance.setId(resultSet.getInt("instanceId"));
        instance.setTime(LocalDate.parse(resultSet.getString("time")));

        TripDescription trip = new TripDescription();

        parseTrip(resultSet, trip);
        instance.setTrip(trip);

        String username = resultSet.getString("username");
        if (!resultSet.wasNull()) {
            User user = new User();
            user.setName(username);

            String name = resultSet.getString("name");
            user.setName(name);

            instance.setBuyer(user);
        }

        TripPaymentStatus status = TripPaymentStatus.parse(resultSet.getInt("status"));

        instance.setStatus(status);
    }

    @Override
    public void addTrip(String title, String description, float baseCost, boolean isHot, float salesCost, int capacity) {
        try {
            Statement statement = connection.createStatement();

            String addTrip = "INSERT INTO trips_description(title, description, baseCost, isHot, salesCost, capacity) " +
                    "values('" + title + "', '" + description + "'," + baseCost + "," + isHot + "," + salesCost + "," + capacity + ")";

            statement.execute(addTrip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTripInstance(int tripId, LocalDate time) {
        try {
            Statement statement = connection.createStatement();

            String addTrip = "INSERT INTO trips_instances(tripid, time) " +
                    "values(" + tripId + ", '" + time.toString() + "')";

            statement.execute(addTrip);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TripDescription> findTripsByPrompt(String prompt) {
        try {
            Statement statement = connection.createStatement();

            String lowerPrompt = prompt.toLowerCase();

            String tripsDescriptionSelect;
            if (prompt.isEmpty()) {
                tripsDescriptionSelect = "select id, " +
                        "title, " +
                        "description, " +
                        "baseCost, " +
                        "isHot, " +
                        "salesCost, " +
                        "capacity " +
                        "from trips_description";
            } else {
                tripsDescriptionSelect = "select id, " +
                        "title, " +
                        "description, " +
                        "baseCost, " +
                        "isHot, " +
                        "salesCost, " +
                        "capacity " +
                        "from trips_description " +
                        "where strpos(lower(title), '" + lowerPrompt + "') > 0 or strpos(lower(description), '" + lowerPrompt + "') > 0";
            }

            ResultSet resultSet = statement.executeQuery(tripsDescriptionSelect);

            List<TripDescription> trips = new ArrayList<>();

            while (resultSet.next()) {
                TripDescription trip = new TripDescription();

                parseTrip(resultSet, trip);

                trips.add(trip);
            }

            return trips;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void parseTrip(ResultSet resultSet, TripDescription trip) throws SQLException {
        trip.setId(resultSet.getInt("id"));
        trip.setTitle(resultSet.getString("title"));
        trip.setDescription(resultSet.getString("description"));
        trip.setBaseCost(resultSet.getFloat("baseCost"));
        trip.setHot(resultSet.getBoolean("isHot"));
        trip.setSalesCost(resultSet.getFloat("salesCost"));
        trip.setCapacity(resultSet.getInt("capacity"));
    }
}
