package project.task.manager.user_service.data.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import project.task.manager.user_service.data.entity.User;
import project.task.manager.user_service.data.mapper.decorator.UserMapperDecorator;
import project.task.manager.user_service.data.request.UserRequestDto;
import project.task.manager.user_service.data.request.UserUpdateDto;
import project.task.manager.user_service.data.response.UserResponseDto;

/**
 * Mapper-интерфейс для преобразования моделей пользователей между объектами доменного уровня и DTO-представлениями.
 * <p>
 * Поддерживаются различные сценарии преобразования:
 * <ul>
 *     <li>Преобразование запроса регистрации пользователя в сущность (с возможностью обработки изображений)</li>
 *     <li>Преобразование сущности пользователя в ответное DTO для API</li>
 *     <li>Частичное обновление данных пользователя (используются только заданные значения полей)</li>
 * </ul>
 * Интерфейс декорируется классом {@link UserMapperDecorator}, что позволяет переопределять поведение стандартных маппингов и добавлять специфичную бизнес-логику.
 *
 * @see UserMapperDecorator
 */
@Mapper(
        componentModel = "spring",
        uses = UserMapperDecorator.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    /**
     * Преобразует данные формы регистрации пользователя в объект сущности User.
     * <p>
     * Дополнительно поддерживает обработку параметра, определяющего наличие изображения пользователя.
     *
     * @param request  DTO с данными формы регистрации пользователя
     * @param hasImage Флаг, указывающий, загружал ли пользователь своё изображение
     * @return Новая сущность пользователя
     */
    User mapRequestToEntity(UserRequestDto request, boolean hasImage);

    /**
     * Преобразует сущность пользователя в представление DTO для вывода пользователю.
     *
     * @param entity Пользовательская сущность
     * @return DTO с данными пользователя
     */

    UserResponseDto mapToDto(User entity);

    /**
     * Альтернативная реализация метода маппинга сущности в DTO с поддержкой передачи изображения.
     *
     * @param entity Пользовательская сущность
     * @param image  Изображение пользователя (может быть null)
     * @return DTO с данными пользователя и изображением
     */
    UserResponseDto mapToDto(User entity, byte[] image);

    /**
     * Частично обновляет данные существующего пользователя на основании полученных значений из UpdateDTO.
     * <p>
     * Поле считается обновляемым, только если оно не null. Это полезно для частичного обновления профилей пользователей.
     *
     * @param updateDto Данные обновления пользователя
     * @param user      Текущая сущность пользователя
     * @return Обновленная сущность пользователя
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User mapUpdateToEntity(UserUpdateDto updateDto, @MappingTarget User user);
    
}