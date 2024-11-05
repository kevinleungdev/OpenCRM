package com.opencrm.app.service.user;

import com.opencrm.app.model.User;

public interface UserService {
    User getUserByEmail(String email);
}
