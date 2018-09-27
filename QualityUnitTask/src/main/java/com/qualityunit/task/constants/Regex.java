package com.qualityunit.task.constants;

public class Regex {
    public static String QUERY = "D ((([\\d]){1,2}(.[\\d])?)|\\*) ((([\\d]){1,2}(.[\\d]{1,2}(.[\\d]{1})?)?)|\\*)" +
            " ([PN]) ([\\d]{0,2}.[\\d]{0,2}.[\\d]{4})(-[\\d]{0,2}.[\\d]{0,2}.[\\d]{4})?";
    public static String REPLY = "C (([\\d]){1,2}(.[\\d])?) (([\\d]){1,2}(.[\\d]{1,2}(.[\\d])?)?)" +
            " ([PN]) ([\\d]{0,2}.[\\d]{0,2}.[\\d]{4}) ([\\d]*)?";
    public static String QUERY_SERVICE_VERSION = "(([\\d]){1,2}(.[\\d]{1,2})?)|\\*";
    public static String QUERY_QUESTION_TYPE = "(([\\d]){1,2}(.[\\d]{1,2}(.[\\d]{1})?)?)|\\*";
    public static String DATE = "[\\d]{0,2}.[\\d]{0,2}.[\\d]{4}";
}
