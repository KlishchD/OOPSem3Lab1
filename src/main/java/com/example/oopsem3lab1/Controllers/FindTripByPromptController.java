package com.example.oopsem3lab1.Controllers;

import com.example.oopsem3lab1.Core.DBSetup;
import com.example.oopsem3lab1.Core.Models.TripDescription;
import com.example.oopsem3lab1.Core.Repository.Repository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "api/findTripByPrompt", urlPatterns = "/api/findTripByPrompt")
public class FindTripByPromptController extends HttpServlet {
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prompt = request.getParameter("prompt");

        Repository repository = DBSetup.getInstance().getRepository();
        List<TripDescription> trips = repository.findTripsByPrompt(prompt);

        String json = gson.toJson(trips);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(json);
        out.flush();
    }
}
