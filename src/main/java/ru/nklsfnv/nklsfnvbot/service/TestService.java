package ru.nklsfnv.nklsfnvbot.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nklsfnv.nklsfnvbot.model.entity.User;
import ru.nklsfnv.nklsfnvbot.repository.UserRepository;

@Service
public class TestService {

    private final UserRepository userRepository;

    @Autowired
    public TestService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    @PostConstruct
//    @Transactional
//    public void test() {
//        User user = new User();
//        user.setName("Artak Akopyan");
//        userRepository.save(user);
//    }

}
