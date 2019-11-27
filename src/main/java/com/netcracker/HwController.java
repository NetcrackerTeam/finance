package com.netcracker;

import com.netcracker.dao.FamilyAccountDebitDao;
import com.netcracker.dao.PersonalDebitAccountDao;
import com.netcracker.dao.impl.FamilyAccountDebitDaoImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigInteger;
import java.util.Locale;

@Controller
public class HwController {
@Autowired
private FamilyAccountDebitDao familyAccountDebitDao;
@Autowired
private PersonalDebitAccountDao personalDebitAccountDao;
    Logger logger = Logger.getLogger(HwController.class);
    @GetMapping( "/" )
    public String welcomePage(Model model) {

        Locale.setDefault(Locale.ENGLISH);
     //   logger.debug(personalDebitAccountDao.getPersonalAccountById(BigInteger.valueOf(2)).getObjectName());
        model.addAttribute("hello", "HELLO WORLD");
        return "index";
    }
}