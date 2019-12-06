package com.netcracker.services.validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
abstract  class AbstractValidation {


    protected JdbcTemplate template;


    @Autowired
    AbstractValidation(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    void validateId(String id) {
    }


    public Boolean saveNameByPreparedStatement(Object name){
        String query="INSERT INTO OBJECTS (OBJECT_ID,PARENT_ID,OBJECT_TYPE_ID,NAME,DESCRIPTION) VALUES (objects_id_s.NEXTVAL,NULL,1,?,NULL)";
        return template.execute(query, new PreparedStatementCallback<Boolean>(){
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {

                //  ps.setBigDecimal(1, new BigDecimal(user.getId()));
                ps.setString(1, (String) name);
                return ps.execute();
            }
        });
    }
}
