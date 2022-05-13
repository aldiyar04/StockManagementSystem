package kz.iitu.itse1910.variant2issenbayev.dto.request;

import lombok.Data;

@Data
public class UserPasswdChangeReq {
    private final String oldPassword;
    private final String newPassword;
}
