package project.task.manager.user_service.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Сервис для работы с файлами, хранящимися в GridFS MongoDB.
 * <p>
 * Предоставляет возможности сохранения, извлечения, удаления и проверки существования файлов.
 */
public interface GridFsService {

		/**
		 * Загружает ресурс файла из GridFS по идентификатору пользователя.
		 *
		 * @param userId Уникальный идентификатор пользователя, чьего файла требуется вернуть.
		 * @return Ресурс файла в виде {@link GridFsResource}. Может быть null, если файл не найден.
		 */
    ObjectId storeFile(UUID userId, MultipartFile file);

		/**
		 * Загружает ресурс файла из GridFS по идентификатору пользователя.
		 *
		 * @param userId Уникальный идентификатор пользователя, чьего файла требуется вернуть.
		 * @return Ресурс файла в виде {@link GridFsResource}. Может быть null, если файл не найден.
		 */
    GridFsResource getFileByUserId(UUID userId);

		/**
		 * Удаляет файл из GridFS по идентификатору пользователя.
		 *
		 * @param userId Уникальный идентификатор пользователя, файл которого подлежит удалению.
		 */
    void deleteFileByUserId(UUID userId);

		/**
		 * Проверяет, существует ли файл в GridFS, связанный с указанным пользователем.
		 *
		 * @param userId Уникальный идентификатор пользователя.
		 * @return {@code true}, если файл найден, иначе {@code false}.
		 */
    boolean existsByUserId(UUID userId);

		/**
		 * Возвращает мета-данные файла из GridFS по идентификатору пользователя.
		 *
		 * @param userId Уникальный идентификатор пользователя, чьих мета-данных требуется вернуть.
		 * @return Объект {@link GridFSFile}, содержащий мета-данные файла. Может быть null, если файл не найден.
		 */
    GridFSFile getFileMetadataByUserId(UUID userId);

		/**
		 * Возвращает ресурс файла из GridFS по уникальному идентификатору файла.
		 *
		 * @param fileId Уникальный идентификатор файла в GridFS.
		 * @return Ресурс файла в виде {@link GridFsResource}. Может быть null, если файл не найден.
		 */
    GridFsResource getFile(ObjectId fileId);

		Map<UUID, GridFsResource> getFilesResourcesByUserIds(List<UUID> userIds);

		/**
		 * Обновить файл пользователя - удалить старый и сохранить новый
		 * @param userId ID пользователя
		 * @param file новый файл для сохранения
		 * @return ObjectId нового сохраненного файла
		 */
		ObjectId updateFile(UUID userId, MultipartFile file);
}