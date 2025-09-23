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
    UNREAD_TOKEN(1010, "Can't read token"),
    OLD_PASSWORD_INCORRECT(1011, "Old password incorrect"),
    PASSWORD_NOT_MATCH(1012, "Password not match"),
    STUDENT_NOT_FOUND(1013, "Student not found"),
    SUBJECT_EXISTED(1014, "Subject existed"),
    STUCODE_INVALID(1015, "Mã sinh viên ít nhất 4 ký tự"),
    SUBCODE_INVALID(1016, "Mã môn học ít nhất 3 ký tự"),
    SUBJECT_NOT_EXISTED(1014, "Subject not existed"),
    ALREADY_REGISTER(1015, "Sinh viên đã đăng ký môn học này rồi");


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
