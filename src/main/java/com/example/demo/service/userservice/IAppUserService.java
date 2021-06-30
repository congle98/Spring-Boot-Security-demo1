package com.example.demo.service.userservice;

import com.example.demo.model.AppUser;

public interface IAppUserService {
    AppUser getUserByName(String name);
}
