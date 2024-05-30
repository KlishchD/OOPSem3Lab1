package com.example.oopsem3lab1.Controllers;

import com.example.oopsem3lab1.Core.DBSetup;
import com.example.oopsem3lab1.Core.Repository.Repository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "api/buyTrip", urlPatterns = "/api/buyTrip")
public class BuyTripController extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int id = Integer.parseInt(request.getParameter("id"));

        Repository repository = DBSetup.getInstance().getRepository();

        repository.buyTrip(username, id);
    }
}
