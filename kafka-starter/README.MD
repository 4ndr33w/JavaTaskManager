# Read Me First
Kafka-starter. С конвертером

### Чтобы использовать требуется:

## Настроить порт
Стартер настроен на подклчение к кафке по
```code
    http://localhost:29092
```
Так что нужно либо указать в конфиге стартера свой порт, либо запустить кафку локально на этом порте

## опубликовать стартер в локальный или удаленный репозиторий
Если в локальном, то необходимо в нашем приложении указать, что требуется
брать пакеты из локального репозитория
и, соответственно, подключить пакет со стартером

```gradle
    // build.gradle
    repositories {
        mavenCentral()
        mavenLocal()
    }
    
	implementation("planner.project:spring-boot-starter-kafka:1.0.0")
```

## Далее:
## или:
#### Разрешить переопределение бинов в конфигурации:
```properties
    #application.properties
    spring.main.allow-bean-definition-overriding=true
```

## или:
#### отключаем автоконфигурацию бинов в приложении для используемого стартера
```java
    @SpringBootApplication(exclude = {
    org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.class
    })
    public class Application {

	    public static void main(String[] args) {
		    SpringApplication.run(Application.class, args);
	    }
    }
```

## Далее можем использовать:

# ProducerService

```java
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Autowired
    private KafkaMessageConverter kafkaMessageConverter;

    public void publish() {

        User user = new User();
        user.setEmail("testEmail");
        user.setId((long)12345131);
        user.setName("testName");

        // toKafkaMessage("topicName", "key", object)
        var message = kafkaMessageConverter.toKafkaMessage("test-topic", user.getId().toString(), user);
        kafkaTemplate.send(message);
    }
```

# ConsumerService

```java
    @Autowired
    private KafkaMessageConverter kafkaMessageConverter;

    @KafkaHandler
    @KafkaListener(
            topics = "${properties.kafka.consumer.topic}",
            groupId = "${properties.kafka.consumer.group-id}")
    public User listen(Message<byte[]> message) throws Exception {

        // передаем помимо самого собщения сам класс
        User user = kafkaMessageConverter.fromKafkaMessage(message, User.class);

        return user;
    }
```

#### Все готово к использованию

#### ToDo:
На будущее: добавить возможность добавлять заголовки в требуемом формате

Также, возможно, нужно будет настроить возможность из приложения, где используется кафка
задавать URL и порт