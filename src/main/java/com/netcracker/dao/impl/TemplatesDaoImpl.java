package com.netcracker.dao.impl;

import com.netcracker.dao.TemplatesDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;

@Repository
public class TemplatesDaoImpl implements TemplatesDao {

    private static final Logger logger = Logger.getLogger(TemplatesDaoImpl.class);
    private JdbcTemplate template;

    @Autowired
    public TemplatesDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public String sendMassageById(BigInteger id) {
        return template.queryForObject(MESSAGE, new Object[]{id}, String.class);
    }
}
