package com.pmq.spring.qlsv.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {
    @Size(min = 8, message = "OLD_PASSWORD_INVALID")
    private String oldPassword;

    @Size(min = 8, message = "NEW_PASSWORD_INVALID")
    private String newPassword;

    @Size(min = 8, message = "CONFIRM_PASSWORD_INVALID")
    private String confirmPassword;
}
