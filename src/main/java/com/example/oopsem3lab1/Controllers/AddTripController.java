package com.example.oopsem3lab1.Controllers;

import com.example.oopsem3lab1.Core.DBSetup;
import com.example.oopsem3lab1.Core.Repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "api/addTrip", urlPatterns = "/api/addTrip")
public class AddTripController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        float baseCost = Float.parseFloat(request.getParameter("baseCost"));
        boolean isHot = Boolean.parseBoolean(request.getParameter("isHot"));
        float salesCost = Float.parseFloat(request.getParameter("salesCost"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));

        Repository repository = DBSetup.getInstance().getRepository();

        repository.addTrip(title, description, baseCost, isHot, salesCost, capacity);
    }
}
