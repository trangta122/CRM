package com.intern.crm.service;

import com.intern.crm.entity.User;
import com.intern.crm.payload.model.UserModel;
import com.intern.crm.payload.request.CreateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    //Create a user
    String createUser(CreateUserRequest request);

    //Find all users
    List<UserModel> findAllUsers();

    //Find a user by ID
    UserModel findUserById(String id);

    //Update a user by ID
    UserModel updateUser(UserModel user, String id);

    //void deleteUser(String id);

    //Paging
    Map<String, Object> pagingUser(int page, int size, String sortBy);
}
