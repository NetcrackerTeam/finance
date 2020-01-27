package com.netcracker.services.impl;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.netcracker.dao.*;
import com.netcracker.exception.MonthReportException;
import com.netcracker.exception.UserException;
import com.netcracker.models.*;
import com.netcracker.services.MonthReportService;
import com.netcracker.services.utils.DateUtils;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class MonthReportServiceImpl implements MonthReportService {

    private static final Logger logger = Logger.getLogger(MonthReportServiceImpl.class);

    @Autowired
    MonthReportDao monthReportDao;

    @Autowired
    OperationDao operationDao;

    @Autowired
    FamilyAccountDebitDao familyAccountDebitDao;

    @Autowired
    PersonalDebitAccountDao personalDebitAccountDao;

    @Autowired
    UserDao userDao;

    @Override
    public void formMonthFamilyReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        logger.debug("Insert formFamilyReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id);


        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesFamilyGroupByCategories(id, dateFrom);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesFamilyGroupByCategories(id, dateFrom);

        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);

        double totalIncome = incomeReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        FamilyDebitAccount debitAccount = familyAccountDebitDao.getFamilyAccountById(id);

        ObjectsCheckUtils.isNotNull(debitAccount);

        double sum = familyAccountDebitDao.getFamilyAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .dateTo(dateTo)
                .dateFrom(dateFrom)
                .build();

        monthReportDao.createFamilyMonthReport(monthReport, id);


        BigInteger idOfRecentMonth = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom,
                dateTo).getId();

        ObjectsCheckUtils.isNotNull(idOfRecentMonth);

        expenseReports.forEach(exp -> {
            monthReportDao.createCategoryExpenseFamilyReport(idOfRecentMonth, exp.getUserReference(), exp);
        });
        incomeReports.forEach(inc -> {
            monthReportDao.createCategoryIncomeFamilyReport(idOfRecentMonth, inc.getUserReference(), inc);
        });
    }

    @Override
    public void formMonthPersonalReportFromDb(BigInteger id, LocalDateTime dateFrom, LocalDateTime dateTo) {
        logger.debug("Insert formPersonalReportFromDb " + id);

        ObjectsCheckUtils.isNotNull(id, dateTo);

        Collection<CategoryExpenseReport> expenseReports = operationDao.getExpensesPersonalGroupByCategories(id, dateFrom);
        Collection<CategoryIncomeReport> incomeReports = operationDao.getIncomesPersonalGroupByCategories(id, dateFrom);

        double totalIncome = incomeReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();
        double totalExpense = expenseReports.stream().mapToDouble(AbstractCategoryReport::getAmount).sum();

        double sum = personalDebitAccountDao.getPersonalAccountById(id).getAmount();

        MonthReport monthReport = new MonthReport.Builder()
                .balance(sum)
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .build();

        monthReportDao.createPersonalMonthReport(monthReport, id);

        BigInteger idOfRecentReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom,
                dateTo).getId();

        ObjectsCheckUtils.isNotNull(idOfRecentReport);

        expenseReports.forEach(exp -> {
            monthReportDao.createCategoryExpensePersonalReport(idOfRecentReport, exp);
        });
        incomeReports.forEach(inc -> {
            monthReportDao.createCategoryIncomePersonalReport(idOfRecentReport, inc);
        });
    }

    @Override
    public Path convertToTxt(MonthReport monthReport) {
        logger.debug("Convertation " + monthReport);

        ObjectsCheckUtils.isNotNull(monthReport);

        Path path = Paths.get( "report.pdf");

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("report.pdf"));
        } catch (FileNotFoundException | DocumentException e) {
            logger.debug(e.getMessage(), e);
            throw new MonthReportException(ExceptionMessages.ERROR_MESSAGE_MONTH_REPORT_WRITE);
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Chunk header = new Chunk(MONTH_REPORT_FROM +
                + monthReport.getDateFrom().getYear() +
                HYPHEN + monthReport.getDateFrom().getMonth().getValue() + HYPHEN +
                monthReport.getDateFrom().getDayOfMonth() + MONTH_REPORT_TO + monthReport.getDateTo().getYear() + HYPHEN +
                monthReport.getDateTo().getMonth().getValue() +
                HYPHEN + monthReport.getDateTo().getDayOfMonth() +  NEW_LINE, font);

        Chunk balance = new Chunk(ACTUAL_BALANCE, font);

        Paragraph head = new Paragraph();

        head.add(header);

        head.setSpacingAfter(10f);

        Paragraph balanceP = new Paragraph();

        balanceP.setSpacingBefore(10f);

        balanceP.add(balance);

        balanceP.add(Chunk.TABBING);

        balanceP.add(new Chunk(Double.toString(monthReport.getBalance()), font));


        Paragraph expenseP = new Paragraph();

        expenseP.add(new Chunk(TOTAL_EXPENSE, font));

        expenseP.add(Chunk.TABBING);

        expenseP.add(new Chunk(Double.toString(monthReport.getTotalExpense()), font));

        Paragraph incomeP = new Paragraph();

        incomeP.add(new Chunk(TOTAL_INCOME, font));

        incomeP.add(Chunk.TABBING);

        incomeP.add(new Chunk(Double.toString(monthReport.getTotalIncome()), font));

        incomeP.setSpacingAfter(20f);

        LineSeparator lineSeparator = new LineSeparator(1,100,BaseColor.BLUE,Element.ALIGN_CENTER,-2);

        Paragraph expensesByCategories = new Paragraph();

        expensesByCategories.add(new Chunk(EXPENSES_BY_CATEGORIES));

        PdfPTable table = new PdfPTable(3);

        table.setSpacingAfter(50f);

        Image img;
//        try {
//           // Path pathImg = Paths.get(ClassLoader.getSystemResource("logo_blue_big.png").toURI());
//            img = Image.getInstance(pathImg.toAbsolutePath().toString());
//        } catch (BadElementException | IOException | URISyntaxException e) {
//            logger.debug(e.getMessage(), e);
//            throw new MonthReportException(ExceptionMessages.ERROR_MESSAGE_MONTH_REPORT_WRITE);
//        }
//
        try {
           // document.add(img);

            document.add(head);

            document.add(lineSeparator);

            document.add(balanceP);

            document.add(expenseP);

            document.add(incomeP);

            if (monthReport.getCategoryExpense().size() > 0) {
                PdfPCell cell = new PdfPCell(new Phrase(EXPENSES_BY_CATEGORIES));
                cell.setBackgroundColor(BaseColor.WHITE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(3);
                table.addCell(cell);
            }

            String compareParticipants = null;
            String name = null;
            boolean named = false;

            for (CategoryExpenseReport exp :
                    monthReport.getCategoryExpense()) {
                try {
                    name = userDao.getUserById(exp.getUserReference()).getName();
                } catch (UserException e) {
                    logger.debug(e.getMessage(), e);
                }
                int count = 0;
                if (name != null) {
                    named = true;
                    if (!name.equals(compareParticipants)) {
                        PdfPCell cellName = new PdfPCell(new Phrase(name));
                        for (CategoryExpenseReport expense: monthReport.getCategoryExpense()) {
                            if(name.equals(userDao.getUserById(expense.getUserReference()).getName())) {
                                count++;
                            }
                        }
                        cellName.setRowspan(count);
                        table.addCell(cellName);
                        compareParticipants = name;
                    }
                }
                PdfPCell cellCategoryExp = new PdfPCell(new Phrase(exp.getCategoryExpense().toString()));
                if(named) {
                    cellCategoryExp.setColspan(1);
                } else {
                    cellCategoryExp.setColspan(2);
                }
                table.addCell(cellCategoryExp);
                PdfPCell cellSumExp = new PdfPCell(new Phrase(Double.toString(exp.getAmount())));
                table.addCell(cellSumExp);
        }

            document.add(table);

            PdfPTable tableInc = new PdfPTable(3);

            if (monthReport.getCategoryIncome().size() > 0) {
                PdfPCell cell = new PdfPCell(new Phrase(INCOMES_BY_CATEGORIES));
                cell.setBackgroundColor(BaseColor.WHITE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setColspan(3);
                tableInc.addCell(cell);
            }

            compareParticipants = null;
            name = null;
            named = false;

            for (CategoryIncomeReport inc :
                    monthReport.getCategoryIncome()) {
                try {
                    name = userDao.getUserById(inc.getUserReference()).getName();
                } catch (UserException e) {
                    logger.debug(e.getMessage(), e);
                }
                int count = 0;
                if (name != null) {
                    named = true;
                    if(!name.equals(compareParticipants)) {
                        PdfPCell cellNameIncome = new PdfPCell(new Phrase(name));
                        for (CategoryIncomeReport income: monthReport.getCategoryIncome()) {
                            if(name.equals(userDao.getUserById(income.getUserReference()).getName())) {
                                count++;
                            }
                        }
                        cellNameIncome.setRowspan(count);
                        tableInc.addCell(cellNameIncome);
                        compareParticipants = name;
                    }
                }
                PdfPCell cellCategoryInc = new PdfPCell(new Phrase(inc.getCategoryIncome().toString()));
                if(named) {
                    cellCategoryInc.setColspan(1);
                } else {
                    cellCategoryInc.setColspan(2);
                }
                tableInc.addCell(cellCategoryInc);
                PdfPCell cellSumExp = new PdfPCell(new Phrase(Double.toString(inc.getAmount())));
                tableInc.addCell(cellSumExp);
            }
            document.add(tableInc);

            document.close();

        } catch (DocumentException e) {
            logger.error("Error in writing to file", e);
            throw new MonthReportException(ExceptionMessages.ERROR_MESSAGE_MONTH_REPORT_WRITE);
        }
        return path;
    }


    @Override
    public MonthReport getMonthPersonalReport(BigInteger id, LocalDateTime date, boolean isJob) {
        logger.debug("Id " + id + " date " + date);

        ObjectsCheckUtils.isNotNull(id, date);

        TwoValue<LocalDateTime> dates = calculateDates(date, id, isJob, false);

        MonthReport monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dates.getValue1(), dates.getValue2());
        ObjectsCheckUtils.isNotNull(monthReport);
        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpensePersonalReport(monthReport.getId());
        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomePersonalReport(monthReport.getId());
        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public MonthReport getMonthFamilyReport(BigInteger id, LocalDateTime date, boolean isJob) {
        logger.debug("Id " + id + " dateFrom " + date);

        ObjectsCheckUtils.isNotNull(id, date);

        TwoValue<LocalDateTime> dates = calculateDates(date, id, isJob, true);

        MonthReport monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dates.getValue1(), dates.getValue2());
        ObjectsCheckUtils.isNotNull(monthReport);

        Collection<CategoryExpenseReport> expenseReports = monthReportDao.getCategoryExpenseFamilyReport(monthReport.getId());

        String compare = null;
        boolean check = false;
        for (CategoryExpenseReport c: expenseReports) {
            if(check) {
                if (compare.equals(userDao.getUserById(c.getUserReference()).getName())) {
                    c.setUserName("");
                } else {
                    compare = userDao.getUserById(c.getUserReference()).getName();
                    c.setUserName(compare);
                }
            }
            if(compare == null) {
                compare = userDao.getUserById(c.getUserReference()).getName();
                c.setUserName(compare);
                check = true;
            }
        }

        Collection<CategoryIncomeReport> incomeReports = monthReportDao.getCategoryIncomeFamilyReport(monthReport.getId());

        compare = null;
        check = false;
        for (CategoryIncomeReport c: incomeReports) {
            if(check) {
                if (compare.equals(userDao.getUserById(c.getUserReference()).getName())) {
                    c.setUserName("");
                } else {
                    compare = userDao.getUserById(c.getUserReference()).getName();
                    c.setUserName(compare);
                }
            }
            if(compare == null) {
                compare = userDao.getUserById(c.getUserReference()).getName();
                c.setUserName(compare);
                check = true;
            }
        }

        ObjectsCheckUtils.isNotNull(expenseReports, incomeReports);
        monthReport.setCategoryExpense(expenseReports);
        monthReport.setCategoryIncome(incomeReports);

        return monthReport;
    }

    @Override
    public String convertToString(Path path) {
        String report = null;
        try {
            report = new String(Files.readAllBytes(path.getFileName()));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new MonthReportException(e.getMessage());
        }
        return report;
    }

    private TwoValue<LocalDateTime> calculateDates(LocalDateTime date, BigInteger id, boolean isJob, boolean isFamily) {
        logger.debug("Date " + id + " id" + " isJob " + isJob + " isFamily " + isFamily);
        LocalDateTime dateTo;
        LocalDateTime dateFrom;
        MonthReport monthReport;

        if(isJob) {
            dateTo = date;
            dateFrom = DateUtils.addMonthsToDate(date, -1);
        } else if (date.getMonth().getValue() == LocalDateTime.now().getMonth().getValue()) {
            dateTo = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue(),
                    LocalDateTime.now().getDayOfMonth(),LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),0);
            dateFrom = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(), 1, 0, 0);

            try {
                if(isFamily){
                    monthReport = monthReportDao.getMonthReportByFamilyAccountId(id, dateFrom, dateTo);
                } else {
                    monthReport = monthReportDao.getMonthReportByPersonalAccountId(id, dateFrom, dateTo);
                }
            } catch (EmptyResultDataAccessException e) {
                if(isFamily) {
                    formMonthFamilyReportFromDb(id, dateFrom, dateTo);
                } else {
                    formMonthPersonalReportFromDb(id, dateFrom, dateTo);
                }
            }
        } else {
            dateFrom = date;
            dateTo = DateUtils.addMonthsToDate(date, 1);
        }
        logger.debug("DateFrom " + dateFrom + " dateTo" + dateTo);
        return new TwoValue<>(dateFrom, dateTo);
    }
}
