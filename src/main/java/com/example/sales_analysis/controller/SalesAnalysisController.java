package com.example.sales_analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sales_analysis.service.ReportService;

@RestController
public class SalesAnalysisController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/generate-report")
    public String generateReport() {
        reportService.generateReport();
        return "Report generated successfully";
    }
}

