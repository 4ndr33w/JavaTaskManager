package project.task.manager.user_service.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с изображениями пользователей.
 * Позволяет сохранять, получать, обновлять и удалять изображения, ассоциированные с пользователями.
 */
public interface ImageService {

		/**
		 * Сохраняет новое изображение для указанного пользователя.
		 *
		 * @param userId Идентификатор пользователя, для которого нужно сохранить изображение.
		 * @param image  Объект с данными изображения.
		 * @return Массив байтов, представляющих сохранённое изображение.
		 */
    byte[] saveImage(UUID userId, MultipartFile image);

		/**
		 * Обновляет существующее изображение пользователя.
		 *
		 * @param userId Идентификатор пользователя, для которого нужно обновить изображение.
		 * @param image  Объект с новыми данными изображения.
		 */
    void updateImageByUserId(UUID userId, MultipartFile image);

		/**
		 * Получает изображение конкретного пользователя.
		 *
		 * @param userId Идентификатор пользователя, чьё изображение нужно получить.
		 * @return Массив байтов, представляющих изображение пользователя.
		 */
    byte[] getImageByUserId(UUID userId);

		/**
		 * Удаляет изображение, связанное с указанным пользователем.
		 *
		 * @param userId Идентификатор пользователя, чьё изображение нужно удалить.
		 */
    void deleteImageByUserId(UUID userId);

		/**
		 * Получает коллекцию изображений по списку идентификаторов пользователей.
		 *
		 * @param ids Список идентификаторов пользователей.
		 * @return Коллекция массивов байтов, представляющих изображения соответствующих пользователей.
		 */
		Map<UUID, byte[]> getImageListByIds(List<UUID> ids);

		/**
		 * Проверяет, существует ли изображение для указанного пользователя.
		 *
		 * @param userId Идентификатор пользователя, для которого нужно проверить наличие изображения.
		 * @return {@code true}, если изображение найдено, иначе {@code false}.
		 */
    boolean hasImage(UUID userId);
}