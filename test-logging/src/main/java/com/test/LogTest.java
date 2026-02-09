package com.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogTest {
    // Создаем логгер
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {
        System.out.println("=== Начало теста логирования ===");

        // Тестируем разные уровни логирования
        logger.trace("Это сообщение уровня TRACE");
        logger.debug("Это сообщение уровня DEBUG");
        logger.info("Это сообщение уровня INFO");
        logger.warn("Это сообщение уровня WARN");
        logger.error("Это сообщение уровня ERROR");

        // Пример с параметрами
        String user = "Иван";
        int count = 5;
        logger.info("Пользователь {} выполнил {} действий", user, count);

        // Пример с исключением
        try {
            int result = 10 / 0;
        } catch (Exception e) {
            logger.error("Произошла ошибка при делении", e);
        }

        System.out.println("=== Тест завершен ===");
        System.out.println("Если выше видите логи - всё работает!");
    }
}  
