package project.task.manager.user_service.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import project.task.manager.user_service.properties.MongoProperties;

/**
 * Конфигурационный класс для интеграции с MongoDB.
 *
 * Включает поддержку механизма аудита MongoDB ({@link EnableMongoAuditing}) и конфигурирует компоненты для работы с GridFS —
 * специализированным механизмом MongoDB для хранения больших файлов.
 */
@Configuration
@EnableMongoAuditing
@RequiredArgsConstructor
public class MongoConfig {

    private final MongoProperties mongoProperties;

    /**
     * Конструирует объект {@code GridFSBucket}, предназначенный для взаимодействия с GridFS-хранилищем MongoDB.
     *
     * <p>Объект используется для операций чтения и записи больших файлов в MongoDB.</p>
     *
     * @param mongoClient Клиент MongoDB, обеспечивающий подключение к базе данных.
     * @return Экземпляр {@code GridFSBucket}, связанный с указанной базой данных и бакетом.
     */
    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient) {
        return GridFSBuckets.create(mongoClient.getDatabase(mongoProperties.getDatabase()), mongoProperties.getBucket());
    }

    /**
     * Предоставляет экземпляром {@code GridFsOperations}, реализующим операции CRUD для работы с GridFS.
     *
     * <p>Операции позволяют сохранять, удалять и извлекать файлы из MongoDB.</p>
     *
     * @param mongoClient       Клиент MongoDB, обеспечивающий подключение к базе данных.
     * @param mongoConverter    Преобразователь объектов MongoDB, преобразующий Java-классы в документы MongoDB и обратно.
     * @return Экземпляр {@code GridFsOperations}, готовый к выполнению операций с файлами в GridFS.
     */
    @Bean
    public GridFsOperations gridFsOperations(MongoClient mongoClient, MongoConverter mongoConverter) {
        return new GridFsTemplate(
                new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase()),
                mongoConverter,
                mongoProperties.getBucket()
        );
    }
}