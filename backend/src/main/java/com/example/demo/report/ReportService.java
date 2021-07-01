package com.example.demo.report;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface ReportService {
    File downloadReport();

    File downloadTickets(Long id);

    ReportType getType();
}

