package com.green.greengram4.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSigninDto {
    @Schema(title = "아이디",example = "mmm123")
    private String uid;
    @Schema(title = "비번",example = "mmm123")
    private String upw;
}
