package com.netcracker.dao.impl.mapper;
import com.netcracker.models.User;
import com.netcracker.models.enums.UserStatusActive;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDaoMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User.Builder()
                .user_id(resultSet.getBigDecimal("USER_ID").toBigInteger())
                .user_name(resultSet.getString("NAME"))
                .user_eMail(resultSet.getString("EMAIL"))
                .user_password(resultSet.getString("PASSWORD"))
                .userActive(UserStatusActive.getStatusByKey( resultSet.getBigDecimal("IS_ACTIVE").toBigInteger()))
                .personalDebit(resultSet.getBigDecimal("PER_DEB_ACC1").toBigInteger())
                .familyDebit(resultSet.getBigDecimal("FAM_DEB_ACC1").toBigInteger())
                .build();
    }
}
