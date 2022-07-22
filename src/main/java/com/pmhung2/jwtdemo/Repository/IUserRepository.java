package com.pmhung2.jwtdemo.Repository;


import com.pmhung2.jwtdemo.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository <Users, Long>{
    Optional<Users> findByUserName(String name); //Tìm kiếm User có tồn tại trong DB không
    Boolean existsByUserName(String username);
}
