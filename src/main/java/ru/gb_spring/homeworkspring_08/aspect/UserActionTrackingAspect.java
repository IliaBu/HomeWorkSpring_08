package ru.gb_spring.homeworkspring_08.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * Класс для отслеживания действий пользователя и вывод результатов в консоль
 */
@Aspect
@Component
public class UserActionTrackingAspect {

    /**
     * Список действий
     */
    private final Map<String, String> mapAction = Map.of(
            "getAllTask", "Запрос всех записей",
            "saveTask", "Сохранение записи",
            "addTask", "Добавление записи",
            "delTask", "Удаление записи по ID",
            "getTaskByStatus", "Получение списка задач по их статусу"
    );

    /**
     * Функция замера времени выполнения метода, аннотированного @TrackUserAction
     * @param joinPoint точка соединения
     * @return результат в виде текста в консоль
     * @throws Throwable исключение
     */
    @Around("@annotation(TrackUserAction)")
    public Object trackUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = mapAction.get(joinPoint.getSignature().getName());
        String arguments = Arrays.toString(args);
        println("Время начала: " + LocalDateTime.now(), Colors.YELLOW);
        print(methodName + ", аргументы: " + arguments, Colors.BLUE);

        long runtime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        runtime = System.currentTimeMillis() - runtime;

        printf(", выполнено за: %d мс", runtime, Colors.GREEN);
        println("Время окончания: " + LocalDateTime.now(), Colors.YELLOW);
        println("=====================================================================================================", Colors.MAGENTA);
        return result;
    }

    /**
     * Функция вывода сообщения в консоль с переносом строки с цветом
     * @param message сообщение
     * @param displayColor цвет
     */
    public static void println(String message, Colors displayColor) {
        System.out.println(displayColor + message + Colors.RESET);
    }

    /**
     * Функция вывода сообщения в консоль без переноса строки с цветом
     * @param message сообщение
     * @param displayColor цвет
     */
    public static void print(String message, Colors displayColor) {
        System.out.print(displayColor + message + Colors.RESET);
    }

    /**
     * Функция вывода сообщения в консоль с параметрами и цветом
     * @param message сообщение
     * @param object объект
     * @param displayColor цвет
     */
    public static void printf(String message, Object object, Colors displayColor) {
        System.out.printf(displayColor + message, object, Colors.RESET);
    }
}
