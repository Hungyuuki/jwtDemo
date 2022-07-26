package com.pmhung2.jwtdemo.Controller;


import com.pmhung2.jwtdemo.Service.role.RoleService;
import com.pmhung2.jwtdemo.Service.user.UserService;
import com.pmhung2.jwtdemo.domain.Role;
import com.pmhung2.jwtdemo.domain.RoleName;
import com.pmhung2.jwtdemo.domain.Users;
import com.pmhung2.jwtdemo.dto.request.SignInForm;
import com.pmhung2.jwtdemo.dto.request.SignUpForm;
import com.pmhung2.jwtdemo.dto.request.SignupFormImage;
import com.pmhung2.jwtdemo.dto.response.JwtResponse;
import com.pmhung2.jwtdemo.dto.response.ResponseMessage;
import com.pmhung2.jwtdemo.security.jwt.JwtProvider;
import com.pmhung2.jwtdemo.security.userPrincipal.UserPrinciple;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@RequestMapping("/auth")
@RestController
@NoArgsConstructor
@AllArgsConstructor
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;
    @Value("${D:/IMG}")
    private String uploadPath;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
        if (userService.existByUsername(signUpForm.getUserName())) {
            return new ResponseEntity<>(new ResponseMessage("The Username is existed"), HttpStatus.OK);
        }
        Users users = new Users(
                signUpForm.getName(),
                signUpForm.getUserName(),
                passwordEncoder.encode(
                        signUpForm.getPassword()),
                        signUpForm.getRole()
                               );
        Set<String> strRole = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                case "user":
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
            }
        });
        users.setRoles(roles);
        userService.save(users);
        return new ResponseEntity<>(new ResponseMessage("Created success"), HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUserName(), signInForm.getPassword())
                //Nếu đúng thì sẽ lưu trữ user đang đặng nhập (user principal vào securitycontextholder
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication); //tạo token jwt
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getAuthorities()));
    }

    @PostMapping("/signup2")
    public ResponseEntity<?> register2(@Valid @RequestBody SignupFormImage signUpFormImage) {
        MultipartFile userImage = signUpFormImage.getImage();
        String imageName="";
        if (userService.existByUsername(signUpFormImage.getUserName())) {
            return new ResponseEntity<>(new ResponseMessage("The Username is existed"), HttpStatus.OK);
        }
        if(userImage.getSize()!=0){
            imageName = signUpFormImage.getImage().getOriginalFilename();
            imageName = System.currentTimeMillis() + imageName;
            try {
                FileCopyUtils.copy(signUpFormImage.getImage().getBytes(), new File(uploadPath + imageName));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Users users = new Users(
                signUpFormImage.getName(),
                signUpFormImage.getUserName(),
                passwordEncoder.encode(signUpFormImage.getPassword()),
                signUpFormImage.getRole(),
                imageName);

        Set<String> strRole = signUpFormImage.getRoles();
        Set<Role> roles = new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(pmRole);
                    break;
                case "user":
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
                    roles.add(userRole);
                    break;
            }
        });
        users.setRoles(roles);
        userService.save(users);
        return new ResponseEntity<>(new ResponseMessage("Created success"), HttpStatus.OK);
    }
}
