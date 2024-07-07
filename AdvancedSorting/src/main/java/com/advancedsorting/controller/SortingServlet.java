package com.advancedsorting.controller;

import com.advancedsorting.service.DataEntryService;
import com.advancedsorting.service.SortingService;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sort")
public class SortingServlet extends HttpServlet {
    @Inject
    private DataEntryService dataEntryService;

    @Inject
    private SortingService sortingService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int index = Integer.parseInt(request.getParameter("index"));
        String algorithm = request.getParameter("algorithm");

        int[] unsortedData = dataEntryService.getAllDataEntries().get(index).getData();
        int[] sortedData = sortingService.sort(unsortedData, algorithm);

        request.setAttribute("unsortedData", unsortedData);
        request.setAttribute("sortedData", sortedData);
        request.setAttribute("algorithm", algorithm);

        request.getRequestDispatcher("/sort-result.jsp").forward(request, response);
    }
}
