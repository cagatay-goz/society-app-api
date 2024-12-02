package com.metuncc.society_app_api.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDTO {
    private String email;
    private String password;
}
