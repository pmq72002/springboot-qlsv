package com.pmq.spring.qlsv.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error"),
    INVALID_ARGUMENT(1000, "Invalid argument"),
    STUDENT_EXISTED(1001, "Sinh viên đã tồn tại"),
    NAME_INVALID(1002, "Tên sinh viên không hợp lệ (ít nhất 8 ký tự)"),
    GENDER_INVALID(1003, "Giới tính không hợp lệ "),
    BIRTH_INVALID(1004, "Ngày sinh không hợp lệ"),
    CLASSR_INVALID(1005, "Lớp không hợp lệ"),
    COURSE_INVALID(1006, "Khóa không hợp lệ"),
    PASSWORD_INVALID(1007, "Mật khẩu không hợp lệ (ít nhất 8 ký tự)"),
    STUDENT_NOT_EXISTED(1008, "Sinh viên không tồn tại"),
    UNAUTHENTICATED(1009, "Unauthenticated"),
    UNREAD_TOKEN(1010, "Can't read token"),
    OLD_PASSWORD_INCORRECT(1011, "Sai mật khẩu cũ"),
    PASSWORD_NOT_MATCH(1012, "Xác nhận mật khẩu không khớp"),
    STUDENT_NOT_FOUND(1013, "Không tìm thấy sinh viên"),
    SUBJECT_EXISTED(1014, "Môn học đã tồn tại"),
    STUCODE_INVALID(1015, "Mã sinh viên ít nhất 4 ký tự"),
    SUBCODE_INVALID(1016, "Mã môn học ít nhất 3 ký tự"),
    SUBJECT_NOT_EXISTED(1014, "Môn học không tồn tại"),
    ALREADY_REGISTER(1015, "Sinh viên đã đăng ký môn học này rồi"),
    PASSWORD_REQUIRED(1016,"Mật khẩu không được để trống "),
    CONFIRM_PASSWORD_REQUIRED(1017,"Xác nhận mật khẩu không được để trống "),
    TOKEN_EXPIRED(1018,"Phiên đăng nhập kết thúc");

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
