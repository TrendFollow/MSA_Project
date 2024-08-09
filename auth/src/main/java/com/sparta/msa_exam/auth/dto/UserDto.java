package com.sparta.msa_exam.auth.dto;

import com.sparta.msa_exam.auth.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"login_id", "password", "userRole", "token"})
public class UserDto {

    private Long user_id;

    @NotBlank(message = "ID 입력은 필수입니다.")
    private String login_id;

    @NotBlank(message = "Password 입력은 필수입니다.")
    private String password;

    private String userRole;
    private String token;

    public static UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUser_id(user.getId());
        userDto.setLogin_id(user.getLoginId());
        userDto.setPassword(user.getPassword());
        userDto.setUserRole(user.getUserRole().name());
        return userDto;
    }


}
