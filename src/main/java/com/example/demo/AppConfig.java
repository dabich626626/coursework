package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TelegramBot bot(@Value("${telegram-bot.token}") String token){
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMycommands());
        return bot;
    }
}
