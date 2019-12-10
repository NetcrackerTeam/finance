package com.netcracker.services;

import com.netcracker.dao.MonthReportDao;
import com.netcracker.models.MonthReport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

public interface MonthReportService {


    void formMonthPersonalReportFromDb(BigInteger id, Date date);

    void formMonthFamilyReportFromDb(BigInteger id, Date date);

    FileOutputStream convertToTxt(MonthReport monthReport);

    MonthReport getMonthPersonalReport(BigInteger id, Date dateFrom, Date dateTo);

    MonthReport getMonthFamilyReport(BigInteger id, Date dateFrom, Date dateTo);

}
