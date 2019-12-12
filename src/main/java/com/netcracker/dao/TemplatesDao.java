package com.netcracker.dao;

import java.math.BigInteger;

public interface TemplatesDao {
    String sendMassageById(BigInteger id);

    String MESSAGE = "SELECT MESSAGE FROM TEMPLATE WHERE template_id = ? ";
}
