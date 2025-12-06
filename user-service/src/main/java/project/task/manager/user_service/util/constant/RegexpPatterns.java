package project.task.manager.user_service.util.constant;

public class RegexpPatterns {

    public static final String REGEXP_NAME_PATTERN = "^[A-Za-zА-Яа-яЁё\\-\\s]+$";
    public static final String REGEXP_EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String REGEXP_PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*\\d)(?=.*[#@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?>]).{8,}$";
    public static final String REGEXP_PHONE_PATTERN = "^\\+((7[ -]?(\\d[ -]?){10,11})|(375[ -]?(29|33|44|25)[ -]?(\\d[ -]?){7}))$";

    public static final String REGEXP_NAME_MESSAGE = "Неверный формат имени. Имя должно содержать только буквы";
    public static final String REGEXP_EMAIL_MESSAGE = "Неверный формат e-mail";
    public static final String REGEXP_PHONE_MESSAGE = "Неверный формат телефонного номера";
    public static final String REGEXP_PASSWORD_MESSAGE = "Пароль должен быть не короче 8 символов и содержать хотя бы 1 заглавную букву, минимум 1 цифру и минимум 1 символ";
}