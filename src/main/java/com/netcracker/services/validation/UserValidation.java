package com.netcracker.services.validation;
import com.netcracker.models.enums.UserStatusActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class UserValidation extends AbstractValidation {

//    private static final String SQL_NAME = "select name from objects where name = ?";

    @Autowired
    public UserValidation(DataSource dataSource) {
        super(dataSource);
    }


    public void validationEmail(String email){
    }

    public void validatePassword(String password) {

    }

    private boolean statusEnumCheck(String status) {
        for (UserStatusActive enumStatus : UserStatusActive.values()) {
            if (enumStatus.name().equals(status)) {
                return true;
            }
        }
        return false;
    }
}