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
Шлюз приложения. Работает по адресу http://localhost:901<br>
Доступен `Swagger`: http://localhost:901/swagger-ui/index.html
<hr/>

### Discovery-Server
Регистрирует сервисы, предоставляет доступ по именам.
Работает по адресу http://localhost:900
<hr/>

### User-Service
Сервис администрирования пользователей, так же осуществляет авторизацию и аутентификацию.<br>

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
Работает по адресу http://localhost:903/project-service<br>
(Контроллер менеджмента проектов http://localhost:901/project-service/api/v1/projects через `API-Gateway`)

Сервис отвечает за:
* создание проекта,
* создание доски с задачами,
* создание задачи,
* управление досками, проектами и задачами.
<hr/>

### Notification-Service
Сервис уведомлений.<br>
Работает по адресу http://localhost:904

Сервис отвечает за отправку уведомлений.<hr/>