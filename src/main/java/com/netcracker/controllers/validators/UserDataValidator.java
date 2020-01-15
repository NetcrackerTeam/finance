package com.netcracker.controllers.validators;

import com.netcracker.exception.FamilyDebitAccountException;
import com.netcracker.exception.UserException;
import com.netcracker.models.AbstractAutoOperation;
import com.netcracker.models.AutoOperationExpense;
import com.netcracker.models.AutoOperationIncome;
import com.netcracker.services.utils.ExceptionMessages;
import com.netcracker.services.utils.ObjectsCheckUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserDataValidator {
    private final static Pattern hasUppercase = Pattern.compile("[A-Z]");
    private final static Pattern hasLowercase = Pattern.compile("[a-z]");
    private final static Pattern hasNumber = Pattern.compile("\\d");
    private final static Pattern onlyLatinAlphabet = Pattern.compile("^[a-zA-Z0-9\\S]+$");
    private final static Pattern onlyLatinLetters = Pattern.compile("^[a-zA-Z\\s]+$");
    private final static Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    private static boolean isEmptyString(String string) {
        ObjectsCheckUtils.isNotNull(string);
        return string.trim().length() == 0;
    }

    public static void isValidEmail(String email) {
        if (!isEmptyString(email)) {
            if (!emailPattern.matcher(email).find()) throw new UserException(ExceptionMessages.INVALID_EMAIL);
        } else throw new UserException(ExceptionMessages.EMPTY_FIELD);
    }

    public static void isValidPassword(String password) {
        if (!isEmptyString(password)) {
            if (password.length() < 8) throw new UserException(ExceptionMessages.PASS_SHORT);
            if (!onlyLatinAlphabet.matcher(password).find()) throw new UserException(ExceptionMessages.LATIN_CHAR);
            if (!hasUppercase.matcher(password).find()) throw new UserException(ExceptionMessages.PASS_UPPER);
            if (!hasLowercase.matcher(password).find()) throw new UserException(ExceptionMessages.PASS_LOWER);
            if (!hasNumber.matcher(password).find()) throw new UserException(ExceptionMessages.PASS_NUM);
        } else throw new UserException(ExceptionMessages.EMPTY_FIELD);
    }

    public static void isValidUsername(String username) {
        if (!isEmptyString(username)) {
            if (!onlyLatinLetters.matcher(username).find()) throw new UserException(ExceptionMessages.LATIN_LETTERS);
            if (username.length() < 2) throw new UserException(ExceptionMessages.NAME_SHORT);
        } else throw new UserException(ExceptionMessages.EMPTY_FIELD);
    }

    public static void isValidNameFamily(String nameAccount) {
        if (!isEmptyString(nameAccount)) {
            if (!onlyLatinLetters.matcher(nameAccount).find())
                throw new FamilyDebitAccountException(ExceptionMessages.LATIN_LETTERS);
            if (nameAccount.length() < 2) throw new FamilyDebitAccountException(ExceptionMessages.NAME_FAMILY_SHORT);
        } else throw new FamilyDebitAccountException(ExceptionMessages.EMPTY_FIELD);
    }

    public static boolean isValidDateForAutoOperation(AbstractAutoOperation abstractAutoOperation) {
        boolean validDayOfMonth = (abstractAutoOperation.getDayOfMonth() <= 31) && (abstractAutoOperation.getDayOfMonth() >= 1);
        if (validDayOfMonth) {
            return true;
        } else
            return false;
    }
}
