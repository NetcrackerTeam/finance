package com.netcracker.dao;

import java.math.BigInteger;

public interface TemplatesDao {

    String sendMassageById(BigInteger id);

    String sendNameMessageById(BigInteger id);

    String MESSAGE = "SELECT MESSAGE FROM TEMPLATE WHERE TEMPLATE_ID = ? ";
    String MESSAGE_NAME = "SELECT NAME FROM TEMPLATE WHERE TEMPLATE_ID = ? ";
}
