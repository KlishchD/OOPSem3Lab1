package com.example.oopsem3lab1.Controllers;

import com.example.oopsem3lab1.Core.DBSetup;
import com.example.oopsem3lab1.Core.Models.TripInstance;
import com.example.oopsem3lab1.Core.Repository.Repository;
import com.example.oopsem3lab1.Utils.LocalDateTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "api/findTripInstanceByID", urlPatterns = "/api/findTripInstanceByID")
public class FindTripInstanceByID extends HttpServlet {
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter()).create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Repository repository = DBSetup.getInstance().getRepository();
        TripInstance trip = repository.findTripInstanceById(id);

        String json = gson.toJson(trip);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
