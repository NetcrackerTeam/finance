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
                .user_id(resultSet.getBigDecimal("user_id").toBigInteger())
                .user_name(resultSet.getString("name"))
                .user_eMail(resultSet.getString("email"))
                .user_password(resultSet.getString("password"))
                .userActive(UserStatusActive.getStatusByKey( resultSet.getBigDecimal("is_active").toBigInteger()))
                .personalDebit(resultSet.getBigDecimal("per_deb_acc1").toBigInteger())
                .familyDebit(resultSet.getBigDecimal("fam_deb_acc1").toBigInteger())
                .build();
    }
}
