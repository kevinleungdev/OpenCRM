package com.opencrm.app.service.user;

import com.opencrm.app.model.User;
import com.opencrm.app.service.BaseService;

public interface UserService extends BaseService<User, Long> {
    User getUserByEmail(String email);
}
