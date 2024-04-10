package com.intern.crm.service;

import com.intern.crm.entity.User;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String createUser(CreateUserRequest request);

    List<UserModel> findAllUsers();

    UserModel findUserById(String id);

    UserModel updateUser(UserModel user, String id);

    //void deleteUser(String id);

}
