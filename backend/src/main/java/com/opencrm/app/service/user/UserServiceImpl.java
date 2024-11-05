package com.opencrm.app.service.user;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.opencrm.app.model.User;
import com.opencrm.app.repository.UserRepository;
import com.opencrm.app.service.BaseServiceImpl;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public User getUserByEmail(String email) {
        return findOne(Example.of(User.builder().email(email).build())).orElse(null);
    }
}
