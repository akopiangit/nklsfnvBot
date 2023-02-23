package ru.nklsfnv.nklsfnvbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.nklsfnv.nklsfnvbot.model.entity.User;
import ru.nklsfnv.nklsfnvbot.repository.UserRepository;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class MessageGate {

    private final UserRepository userRepository;

    public SendMessage resolveMessage(Message inMessage) {
        String chatId = inMessage.getChatId().toString();
        return SendMessage.builder()
                    .chatId(chatId)
                    .text(getAnswer(inMessage))
                    .build();
    }

    public String getAnswer(Message inMessage) {
        log.info(
                "user [{} {} {}] sent message : \n [{}]",
                inMessage.getChat().getFirstName(),
                inMessage.getChat().getLastName(),
                inMessage.getChat().getUserName(),
                inMessage.getText()
        );
        String text = inMessage.getText();
        saveNewUserOrGetExisted(
                inMessage.getChat().getFirstName() + " " + inMessage.getChat().getLastName(),
                inMessage.getChat().getUserName()
        );
        return "Уже зарегистрированы: " + userRepository.findAll().stream().map(User::getUserName).filter(Objects::nonNull).collect(Collectors.joining(", " ));
    }

    public void saveNewUserOrGetExisted(String name, String userName) {
        User user = userRepository.findByUserName(userName);
        if (user != null) {
            return;
        }
        user = new User();
        user.setName(name);
        user.setUserName(userName);
        userRepository.save(user);
    }

    @Scheduled(fixedDelay = 10000L)
    public void logAllUsers() {
        log.info("Current users : " + userRepository.findAll());
    }

}
