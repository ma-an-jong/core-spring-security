package corespringsecurity.corespringsecurity.service;

import corespringsecurity.corespringsecurity.domain.Account;
import corespringsecurity.corespringsecurity.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private final AccountRepository userRepository;


    @Override
    public void createUser(Account user) {
        userRepository.save(user);
    }
}
