package com.netcracker.controllers;

import com.netcracker.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class checksAutoOperationController {

    private final String currentPage = "testAutoOperation";
    @Autowired
    private JobService jobService;


    @RequestMapping(value = "/testAutoOperation", method = RequestMethod.GET)
    public String getStartPage(Model model) {
        model.addAttribute("page", "get page");

        return currentPage;
    }

    @RequestMapping(value = "/executeRemindAutoIncomePersonalJob", method = RequestMethod.GET)
    public String AutoIncPers(Model model ) {
        jobService.executeRemindAutoIncomePersonalJob();
        model.addAttribute("AutoIncMSg", "please check you acc after test ");
        return currentPage;
    }

    @RequestMapping(value = "/executeRemindAutoExpensePersonalJob", method = RequestMethod.GET)
    public String AutoExcPers(Model model, String error) {
        jobService.executeRemindAutoExpensePersonalJob();
        model.addAttribute("AutoExcMsg", "please check you acc after test");
        return currentPage;
    }

    @RequestMapping(value = "/executeRemindAutoIncomeFamilyJob", method = RequestMethod.GET)
    public String AutoIncFam(Model model, String error) {
        jobService.executeRemindAutoIncomeFamilyJob();
        model.addAttribute("AutoIncMSgFam", "please check you acc after test");
        return currentPage;
    }

    @RequestMapping(value = "/executeRemindAutoExpenseFamilyJob", method = RequestMethod.GET)
    public String AutoExpFam(Model model, String error) {
        jobService.executeRemindAutoExpenseFamilyJob();
        model.addAttribute("AutoExcMsgFam", "please check you acc after test");
        return currentPage;
    }

    @RequestMapping(value = "/executeAutoCreditExpenseFamily", method = RequestMethod.GET)
    public String AutoCreditFam(Model model, String error) {
        jobService.executeAutoCreditExpenseFamily();
        model.addAttribute("AutoCreditMSgFam", "please check you acc after test");
        return currentPage;
    }

    @RequestMapping(value = "/executeAutoCreditExpensePersonal", method = RequestMethod.GET)
    public String AutoCreditPer(Model model, String error) {
        jobService.executeAutoCreditExpensePersonal();
        model.addAttribute("AutoCreditMsgPersonal", "please check you acc after test");
        return currentPage;
    }



}

