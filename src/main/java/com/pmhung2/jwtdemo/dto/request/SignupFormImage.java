package com.pmhung2.jwtdemo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupFormImage {
    private String name;
    @Size(min = 5, max = 20, message = "Username phải từ 5 -> 20 ký tự")
    @NotNull
    private String userName;
    @NotNull
    private String password;
    private String role;
    @NotNull
    private MultipartFile image;
    private Set<String> roles;
}
