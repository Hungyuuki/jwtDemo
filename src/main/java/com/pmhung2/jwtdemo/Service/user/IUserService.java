package com.pmhung2.jwtdemo.Service.user;



import com.pmhung2.jwtdemo.Service.IGeneralService;
import com.pmhung2.jwtdemo.domain.Users;

import java.util.Optional;

public interface IUserService extends IGeneralService<Users> {
    Optional<Users> findByUserName(String name);
    Boolean existByUsername(String username);
    Users save (Users users);
}
