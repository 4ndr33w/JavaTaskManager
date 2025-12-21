## README

Билдим каждый сервис:
```bash
./gradlew clean build
```

Запускаем докер-компост в корневом каталоге:

```bash
 docker-compose up --build
 ```

В каталоге `./DockerConfig` содержатся докер-компосты инфраструктуры:<br>
`grafana`, `prometheus`, `loki`, `kafka`, и т.д.<br>
Дашборды ставить отдельно.

## Сервисы:
<hr/>

### API-Gateway
Шлюз приложения. <br> 
`url`: http://localhost:901 <br>
`Swagger`: http://localhost:901/swagger-ui/index.html
<hr/>

### Discovery-Server
Регистрирует сервисы, предоставляет доступ по именам. <br>
`url`: http://localhost:900
<hr/>

### User-Service
Сервис администрирования пользователей, так же осуществляет авторизацию и аутентификацию.<br>
`url`: http://localhost:902/user-service <br>
`Swagger`: http://localhost:901/user-service/swagger-ui/index.html<br>

Контроллер пользователей:<br>
http://localhost:902/user-service/api/v1/users<br>
(http://localhost:901/user-service/api/v1/users через `API-Gateway`)<br>

Контроллер адвинистрирования:<br>
http://localhost:902/user-service/api/v1/admin<br>
(http://localhost:901/user-service/api/v1/admin через `API-Gateway`)<br>

Контроллер обновления токена:<br>
http://localhost:902/user-service/api/v1/auth<br>
(http://localhost:901/user-service/api/v1/auth через `API-Gateway`)<br>

Ресурс авторизации по логину и паролю для получения токена.<br>
http://localhost:902/user-service/api/v1/login<br>
(http://localhost:901/user-service/api/v1/login через `API-Gateway`)<br>

Сервис отвечает за:
* создание пользователя,
* редактирование пользователя и удваление,
* выдачу токена
* обновление токена
* Осуществляет доступ к ресурсам по токену.
<hr/>

### Product-Service
Сервис управления проектами.<br>
`url`: http://localhost:903/project-service <br>
`Swagger`: http://localhost:903/project-service/swagger-ui/index.html<br>

Контроллер менеджмента проектов:<br> 
http://localhost:903/project-service/api/v1/projects <br>
(http://localhost:901/project-service/api/v1/projects через `API-Gateway`)

Сервис отвечает за:
* создание проекта,
* создание доски с задачами,
* создание задачи,
* управление досками, проектами и задачами.
<hr/>

### Notification-Service
Сервис уведомлений.<br>
`url`: http://localhost:904 <br>

Сервис отвечает за отправку уведомлений.<hr/>

### Desktop-Client
Сервис предоставляет GUI-интерфейс для работы с проектом <hr/>

Для запуска проекта:

#### 1)
`File` -> `Project Structure` -> `Libraries` -> `+` -> `Java` -> указываем путь к `\path\to\javafx-sdk\lib`

#### 2)
`File` -> `Settings` -> `Appearance & Behavior` -> `Path Variables` -> `+`<br>
`Name`: `PATH_TO_FX`<br>
`Value`: `\path\to\javafx-sdk\lib`<br>

#### 3)
`RUN -> Edit Configuration`: `--module-path ${PATH_TO_FX} --add-modules javafx.controls,javafx.fxml` <br>
(`C:\Users\rx081\Downloads\openjfx-21.0.2_windows-x64_bin-sdk\javafx-sdk-21.0.2\lib`)

### Нюансы запуска:
!) При создании проекта через `GigaIDE` пришлось повозиться, чтобы запустить проект.<br>
!) При создании проекта через `IntelliJ Idea` пришлось только добавить зависимость `org.jetbrains.kotlin:kotlin-stdlib`.<br>
(В `module-info` так же пришлось добавить `requires kotlin.stdlib`)

