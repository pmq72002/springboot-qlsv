package com.pmq.spring.qlsv.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error"),
    INVALID_ARGUMENT(1000, "Invalid argument"),
    STUDENT_EXISTED(1001, "Student existed"),
    NAME_INVALID(1002, "Student's name invalid (at least 8 character)"),
    GENDER_INVALID(1003, "Student's gender invalid"),
    BIRTH_INVALID(1004, "Student's birth invalid"),
    CLASSR_INVALID(1005, "Student's class invalid (at least 4 character)"),
    COURSE_INVALID(1006, "Student's course invalid (at least 3 character)"),
    PASSWORD_INVALID(1007, "Password invalid (at least 8 character)"),
    STUDENT_NOT_EXISTED(1008, "Student not existed"),
    UNAUTHENTICATED(1009, "Unauthenticated"),
    UNREAD_TOKEN(1010, "Can't read token");



    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
