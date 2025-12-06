package project.task.manager.user_service.data.mapper.decorator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.mapper.UserMapper;
import project.task.manager.user_service.data.request.UserRequestDto;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Декоратор для {@link UserMapper}, позволяющей расширить функциональность маппинга пользователей.
 * <p>
 * Сюда помещаются операции, которые нельзя выразить средствами простого MapStruct-mapping.
 * Например, здесь выполняется шифрование паролей и установка начальных ролей для новых пользователей.
 *
 * @author Your Name
 */
@Component
@Primary
@Setter
public abstract class UserMapperDecorator implements UserMapper {

    /**
     * Основной делегируемый маппер, осуществляющий базовые преобразования.
     */
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Реализует маппинг регистрационных данных пользователя в сущность.
     * <p>
     * По умолчанию устанавливает роль GUEST новому пользователю и
     * хэширует пароль перед сохранением.
     *
     * @param request  Форма регистрации пользователя
     * @param hasImage Наличие изображения пользователя
     * @return Подготовленная сущность пользователя
     */
    @Override
    public User mapRequestToEntity(UserRequestDto request, boolean hasImage) {
        User user = delegate.mapRequestToEntity(request, hasImage);
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
				user.setBirthDate(request.birthDate() == null ? LocalDate.now() : request.birthDate());

        return user;
    }
}