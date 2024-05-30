package com.example.oopsem3lab1.Controllers;

import com.example.oopsem3lab1.Core.DBSetup;
import com.example.oopsem3lab1.Core.Repository.Repository;
import lombok.SneakyThrows;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "api/addTripInstance", urlPatterns = "/api/addTripInstance")
public class AddTripInstanceController extends HttpServlet {
    @SneakyThrows
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        LocalDate time = LocalDate.parse(request.getParameter("time"));

        Repository repository = DBSetup.getInstance().getRepository();

        repository.addTripInstance(id, time);
    }
}
