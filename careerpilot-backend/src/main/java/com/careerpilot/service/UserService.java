package com.careerpilot.service;

import com.careerpilot.dto.UserDto;

/**
 * Contract for user profile operations.
 */
public interface UserService {

    UserDto getCurrentUser(String email);

    UserDto getUserById(Long id);
}
