package com.yqz.user.api;

import com.yqz.user.dto.UserDTO;

/**
 * @Description: TODO
 * @Author MiSinG
 * @Date 2023/11/8
 * @Version V1.0
 **/
public interface UserServiceI {


    UserDTO getUserDTOById(Long id);

    UserDTO getUserDTOByLogin(String username,String password);
}
