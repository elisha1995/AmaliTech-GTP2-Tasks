package com.advancedsorting.controller;

import com.advancedsorting.model.DataEntry;
import com.advancedsorting.service.DataEntryService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet implementation class DataEntryServlet.
 * This servlet handles HTTP requests for adding, updating, and deleting data entries.
 */
@WebServlet("/dataEntry")
public class DataEntryServlet extends HttpServlet {

    // Injecting the DataEntryService to handle business logic.
    @Inject
    private DataEntryService dataEntryService;

    /**
     * Handles the HTTP GET request.
     * Retrieves all data entries and forwards the request to the index.jsp page.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<DataEntry> dataEntries = dataEntryService.getAllDataEntries();
        request.setAttribute("dataEntries", dataEntries);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST request.
     * Delegates the request to add, update, or delete data entries based on the action parameter.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addDataEntry(request, response);
                break;
            case "update":
                updateDataEntry(request, response);
                break;
            case "delete":
                deleteDataEntry(request, response);
                break;
        }

        response.sendRedirect(request.getContextPath() + "/dataEntry");
    }

    private void addDataEntry(HttpServletRequest request, HttpServletResponse response) {
        String dataString = request.getParameter("data");
        try {
            int[] data = Arrays.stream(dataString.split(",")).mapToInt(Integer::parseInt).toArray();
            dataEntryService.addDataEntry(data);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Please enter only integers separated by commas.");
        }
    }

    private void updateDataEntry(HttpServletRequest request, HttpServletResponse response) {
        int index = Integer.parseInt(request.getParameter("index"));
        String dataString = request.getParameter("data");
        try {
            int[] data = Arrays.stream(dataString.split(",")).mapToInt(Integer::parseInt).toArray();
            dataEntryService.updateDataEntry(index, data);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Please enter only integers separated by commas.");
        }
    }

    private void deleteDataEntry(HttpServletRequest request, HttpServletResponse response) {
        int index = Integer.parseInt(request.getParameter("index"));
        dataEntryService.deleteDataEntry(index);
    }
}
