package project.task.manager.user_service.service.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.service.GridFsService;

import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GridFsServiceImpl implements GridFsService {

    private final GridFsOperations gridFsOperations;
    private final GridFSBucket gridFSBucket;

    /**
     * Сохранить файл в GridFS
     * @param userId ID пользователя
     * @param file файл для сохранения
     * @return ObjectId сохраненного файла
     */
    @Override
    public ObjectId storeFile(UUID userId, MultipartFile file) {
        String filename = generateFileName(userId, file.getOriginalFilename());

        // Метаданные файла
        Document metadata = new Document()
                .append("userId", userId.toString())
                .append("contentType", file.getContentType())
                .append("uploadedAt", System.currentTimeMillis());

        // Настройки загрузки
        GridFSUploadOptions options = new GridFSUploadOptions()
                .metadata(metadata)
                .chunkSizeBytes(255 * 1024); // 255KB chunks

        try (InputStream inputStream = file.getInputStream()) {
            ObjectId fileId = gridFSBucket.uploadFromStream(filename, inputStream, options);
            log.debug("File stored in GridFS for user: {}, fileId: {}", userId, fileId);
            return fileId;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получить файл по userId
     * @param userId ID пользователя
     * @return GridFsResource или null если файл не найден
     */
    @Override
    public GridFsResource getFileByUserId(UUID userId) {
        GridFSFile gridFSFile = gridFsOperations.findOne(
                new Query(Criteria.where("metadata.userId").is(userId.toString()))
        );

        if (gridFSFile == null) {
            return null;
        }

        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        return new GridFsResource(gridFSFile, downloadStream);
    }

    /**
     * Удалить файл по userId
     * @param userId ID пользователя
     */
    @Override
    public void deleteFileByUserId(UUID userId) {
        GridFSFile gridFSFile = gridFsOperations.findOne(
                new Query(Criteria.where("metadata.userId").is(userId.toString()))
        );

        if (gridFSFile != null) {
            gridFSBucket.delete(gridFSFile.getObjectId());
            log.debug("File deleted for user: {}", userId);
        }
    }

    /**
     * Проверить существование файла для пользователя
     * @param userId ID пользователя
     * @return true если файл существует
     */
    @Override
    public boolean existsByUserId(UUID userId) {
        GridFSFile gridFSFile = gridFsOperations.findOne(
                new Query(Criteria.where("metadata.userId").is(userId.toString()))
        );
        return gridFSFile != null;
    }

    /**
     * Получить метаданные файла по userId
     * @param userId ID пользователя
     * @return GridFSFile или null
     */
    @Override
    public GridFSFile getFileMetadataByUserId(UUID userId) {
        return gridFsOperations.findOne(
                new Query(Criteria.where("metadata.userId").is(userId.toString()))
        );
    }

    /**
     * Получить файл из GridFS
     * @param fileId ObjectId файла
     * @return GridFsResource
     */
    @Override
    public GridFsResource getFile(ObjectId fileId) {
        GridFSFile gridFSFile = gridFsOperations.findOne(new Query(Criteria.where("_id").is(fileId)));
								return convertToGridFsResource(gridFSFile);
    }

		@Override
		public Map<UUID, GridFsResource> getFilesResourcesByUserIds(@NonNull List<UUID> userIds) {
				if (userIds.isEmpty()) {
						log.debug("Передан пустой список userId для поиска изображений");
						return Map.of();
				}
				try {
						log.debug("Поиск файлов в GridFS для {} пользователей", userIds.size());

						List<String> userIdStrings = userIds.stream()
										.map(UUID::toString)
										.collect(Collectors.toList());

						Query query = new Query(Criteria.where("metadata.userId").in(userIdStrings));
						List<GridFSFile> gridFSFiles = gridFsOperations.find(query).into(new ArrayList<>());

						log.debug("Найдено {} файлов в GridFS", gridFSFiles.size());

						return gridFSFiles.stream()
										.map(this::convertToGridFsResourceWithUserId)
										.filter(Objects::nonNull)
										.collect(Collectors.toMap(
														Map.Entry::getKey,
														Map.Entry::getValue
										));

				} catch (Exception e) {
						log.error("Ошибка при поиске файлов для пользователей: {}", userIds, e);
						throw new RuntimeException("Ошибка при поиске файлов в GridFS", e);
				}
		}

		/**
		 * Вспомогательный метод для преобразования GridFSFile в пару (UUID, GridFsResource)
		 */
		private Map.Entry<UUID, GridFsResource> convertToGridFsResourceWithUserId(GridFSFile gridFSFile) {
				try {
						if (gridFSFile == null) {
								return null;
						}

						// Извлекаем userId из метаданных
						String userIdStr = gridFSFile.getMetadata().getString("userId");
						if (userIdStr == null) {
								log.warn("Файл {} не содержит userId в метаданных", gridFSFile.getObjectId());
								return null;
						}

						UUID userId = UUID.fromString(userIdStr);

						// Создаем GridFsResource
						GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
						GridFsResource resource = new GridFsResource(gridFSFile, downloadStream);

						return new AbstractMap.SimpleEntry<>(userId, resource); //Pair<>(userId, resource);

				} catch (Exception e) {
						log.error("Ошибка при преобразовании GridFSFile для userId: {}", e.getMessage());
						return null;
				}
		}

		/**
		 * Преобразование GridFSFile в GridFsResource
		 */
		private GridFsResource convertToGridFsResource(GridFSFile gridFSFile) {
				try {
						if (gridFSFile == null) {
								// Если изображения не нашлось, то просто вернём пользователю ответ без изображения
								// не выкидывая ошибку
								return null;
						}
						GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
						return new GridFsResource(gridFSFile, downloadStream);

				} catch (Exception e) {
						log.error("Ошибка при создании GridFsResource для файла: {}", gridFSFile.getObjectId(), e);
						return null;
				}
		}

    private String generateFileName(UUID userId, String originalFilename) {
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return "user_" + userId + "_avatar" + extension;
    }

		@Override
		public ObjectId updateFile(UUID userId, MultipartFile file) {
				try {
						log.debug("Начало обновления файла для пользователя: {}", userId);

						deleteFileByUserId(userId);
						log.debug("Старый файл удален для пользователя: {}", userId);

						ObjectId newFileId = storeFile(userId, file);
						log.info("Файл успешно обновлен для пользователя: {}, новый fileId: {}", userId, newFileId);

						return newFileId;

				} catch (Exception e) {
						log.error("Ошибка при обновлении файла для пользователя: {}", userId, e);
						throw new RuntimeException("Ошибка при обновлении файла", e);
				}
		}
}