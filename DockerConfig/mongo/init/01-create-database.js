// Создание базы данных и пользователя с проверками
db = db.getSiblingDB('userdb');

print('=== Starting database initialization ===');

// Удаляем пользователя если существует (для пересоздания)
try {
    db.dropUser('appuser');
    print('✅ Dropped existing appuser');
} catch (e) {
    print('ℹ️ No existing appuser to drop');
}

// Создаем пользователя для приложения
print('Creating user appuser...');
db.createUser({
    user: 'appuser',
    pwd: 'apppassword',
    roles: [
        {
            role: 'readWrite',
            db: 'userdb'
        },
        {
            role: 'dbAdmin',
            db: 'userdb'
        },
        {
            role: 'readWrite',
            db: 'admin'
        }
    ]
});

print('✅ User appuser created successfully');

// Проверяем создание
var users = db.getUsers();
print('=== Current users in userdb ===');
users.forEach(user => {
    print('User: ' + user.user);
    print('Roles: ' + JSON.stringify(user.roles));
});

print('=== Database initialization completed ===');