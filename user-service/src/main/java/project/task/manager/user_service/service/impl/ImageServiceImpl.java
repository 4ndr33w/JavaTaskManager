package project.task.manager.user_service.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.task.manager.user_service.data.enums.ImageContentType;
import project.task.manager.user_service.exception.ImageProcessingException;
import project.task.manager.user_service.service.GridFsService;
import project.task.manager.user_service.service.ImageService;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final GridFsService gridFsService;

    @Override
    public byte[] saveImage(UUID userId, MultipartFile image) {
        if(!isNullOrEmpty(userId, image) && isValid(userId, image)) {
            try {
                ObjectId fileId = gridFsService.storeFile(userId, image);
                log.info("Изображение успешно сохранено для пользователя с userId: {}, fileId: {}", userId, fileId);

                return getImageByUserId(userId);

            } catch (Exception e) {
                log.error("Ошибка при сохранении изображения для пользователя с userId: {}", userId, e);
                throw new ImageProcessingException("Ошибка при сохранении изображения", e);
            }
        }
        return null;
    }

    /**
     * ToDo: обеспечить компенсирующие операции на случай ошибки
     * @param userId Id пользователя, для которого требуется изменить изображение
     * @param image Объект, содержащий данные для обновления изображения
     */
    @Override
    public void updateImageByUserId(UUID userId, MultipartFile image) {
        if(!isNullOrEmpty(userId, image)) {
            try {
                deleteImageByUserId(userId);
                saveImage(userId, image);
                log.info("Успешно завершено обновление изображения для пользователя с userId: {}", userId);

            } catch (Exception e) {
                log.error("Ошибка при обновлении изображения для пользователя с userId: {}", userId, e);
                throw new ImageProcessingException("Ошибка при обновлении изображения", e);
            }
        }
    }

    @Override
    public byte[] getImageByUserId(UUID userId) {
        try {
            GridFsResource resource = gridFsService.getFileByUserId(userId);
            if (resource == null) {
                log.debug("Не найдено изображение для пользователя с userId: {}", userId);
                return null;
            }

            byte[] imageData = resource.getInputStream().readAllBytes();
            log.debug("Получено изображение для пользователя с userId: {}, size: {} bytes", userId, imageData.length);
            return imageData;

        } catch (IOException e) {
            log.error("Ошибка при поиске изображения для пользователя с userId: {}", userId, e);
            throw new ImageProcessingException("Ошибка при поиске изображения", e);
        }
    }

    @Override
    public void deleteImageByUserId(UUID userId) {
        try {
            gridFsService.deleteFileByUserId(userId);
            log.info("Удалено изображение пользователя с userId: {}", userId);
        } catch (Exception e) {
            log.error("Ошибка при удалении изображения пользователя с userId: {}", userId, e);
            throw new ImageProcessingException("Ошибка при удалении изображения", e);
        }
    }

    @Override
    public Map<UUID, byte[]> getImageListByIds(List<UUID> ids) {
				if (ids == null || ids.isEmpty()) {
						log.debug("Передан пустой список userId для поиска изображений");
						return Map.of();
				}
				try {
						log.debug("Поиск изображений для {} пользователей", ids.size());

						Map<UUID, GridFsResource>  resources = gridFsService.getFilesResourcesByUserIds(ids);

						Map<UUID, byte[]> images = resources.entrySet().stream()
										.map(x -> {
												return new AbstractMap.SimpleEntry<>(
																x.getKey(),
																convertResourceToByteArray(x.getValue()));
										})
										.filter(Objects::nonNull)
										.collect(Collectors.toMap(
														Map.Entry::getKey,
														Map.Entry::getValue
										));

						log.info("Успешно получено {} изображений для {} пользователей",
										images.size(), ids.size());

						return images;

				} catch (Exception e) {
						log.error("Ошибка при получении изображений для пользователей: {}", ids, e);
						throw new ImageProcessingException("Ошибка при получении коллекции изображений", e);
				}
    }

		/**
		 * Преобразование GridFsResource в Byte[]
		 */
		private byte[] convertResourceToByteArray(GridFsResource resource) {
				if (resource == null || !resource.exists()) {
						return null;
				}
				try {
						return resource.getInputStream().readAllBytes();
				} catch (IOException e) {
						log.error("Ошибка при чтении данных из GridFsResource", e);
						return null;
				}
		}

    @Override
    public boolean hasImage(UUID userId) {
        boolean exists = gridFsService.existsByUserId(userId);
        log.debug("Проверка наличия изображения у пользователя: {} - {}", userId, exists);
        return exists;
    }

    private boolean isNullOrEmpty(UUID userId, MultipartFile image) {
        if (image == null || image.isEmpty()) {
            log.debug("Отсутствует изображение, переданное пользователем с userId: {}", userId);
            return true;
        }
        return false;
    }

    private boolean isValid(UUID userId, MultipartFile image) {
        if (!isValidImageType(image)) {
            log.warn("Неподдерживаемый тип изображения. userId: {}. ContentType: {}", userId, image.getContentType());
            throw new IllegalArgumentException("Неподдерживаемый тип изображения. Допустимые типы: JPEG, PNG, GIF, WEBP");
        }
        if (image.getSize() > 10 * 1024 * 1024) { // 10MB
            log.warn("Превышен размер изображения для пользователя с userId: {}. Size: {} bytes", userId, image.getSize());
            throw new IllegalArgumentException("Допустимый размер изображения 10MB");
        }
        return true;
    }

    private boolean isValidImageType(MultipartFile image) {
        String contentType = image.getContentType();
        return contentType != null && (
                contentType.equals(ImageContentType.JPEG.getValue()) ||
                contentType.equals(ImageContentType.PNG.getValue()) ||
                contentType.equals(ImageContentType.GIF.getValue()) ||
                contentType.equals(ImageContentType.WEBP.getValue())
        );
    }
}