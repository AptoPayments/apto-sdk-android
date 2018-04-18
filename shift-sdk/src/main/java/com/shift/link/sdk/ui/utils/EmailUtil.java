package com.shift.link.sdk.ui.utils;

import ru.lanwen.verbalregex.VerbalExpression;

/**
 * Created by pauteruel on 22/02/2018.
 */

public class EmailUtil {
    /**
     * Validation of email.
     * @return The corresponding boolean.
     */
    public static boolean isValidEmail(String email){
        VerbalExpression emailRegex = VerbalExpression.regex()
                .startOfLine()
                .anythingBut(" ")
                .then("@")
                .anythingBut(" ")
                .then(".")
                .anythingBut(" ")
                .endOfLine()
                .build();

        return emailRegex.testExact(email);
    }

}