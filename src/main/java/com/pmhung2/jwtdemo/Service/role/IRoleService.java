package com.pmhung2.jwtdemo.Service.role;


import com.pmhung2.jwtdemo.Service.IGeneralService;
import com.pmhung2.jwtdemo.domain.Role;
import com.pmhung2.jwtdemo.domain.RoleName;

import java.util.Optional;

public interface IRoleService extends IGeneralService<Role> {
    Optional<Role> findByName(RoleName name);
}
