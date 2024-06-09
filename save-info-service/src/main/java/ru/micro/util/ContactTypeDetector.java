package ru.micro.util;

import ru.micro.model.ContactType;

import java.util.regex.Pattern;

public class ContactTypeDetector {
    public static ContactType detectType(String input) {
        if (Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", input)) {
            return ContactType.Email;
        } else if (Pattern.matches("^\\+?\\d{10,15}$", input)) {
            return ContactType.Sms;
        } else if (input.startsWith("@") && Pattern.matches("[a-zA-Z0-9_]{5,32}", input.substring(1))) {
            return ContactType.Tg;
        } else {
            return ContactType.Unknown;
        }
    }
}
