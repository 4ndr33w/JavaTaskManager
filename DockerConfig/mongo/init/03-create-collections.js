// Создание дополнительных коллекций с проверками
db = db.getSiblingDB('userdb');

print('Setting up additional collections...');

// Проверяем существование коллекции user_metadata
var metadataCollectionExists = db.getCollectionNames().includes('user_metadata');

if (metadataCollectionExists) {
    print('✅ user_metadata collection already exists');
} else {
    // Создаем коллекцию для метаданных пользователей
    db.createCollection('user_metadata', {
        validator: {
            $jsonSchema: {
                bsonType: "object",
                required: ["userId", "createdAt"],
                properties: {
                    userId: {
                        bsonType: "string",
                        description: "must be a string and is required"
                    },
                    createdAt: {
                        bsonType: "date",
                        description: "must be a date and is required"
                    },
                    lastLogin: {
                        bsonType: "date"
                    },
                    loginCount: {
                        bsonType: "int",
                        minimum: 0
                    }
                }
            }
        }
    });
    print('✅ user_metadata collection created');
}

// Создаем индекс только если он не существует
try {
    db.user_metadata.createIndex({ "userId": 1 }, { unique: true });
    print('✅ Created user_metadata userId index');
} catch (e) {
    if (e.codeName === 'IndexKeySpecsConflict' || e.codeName === 'IndexOptionsConflict') {
        print('✅ user_metadata userId index already exists');
    } else {
        print('❌ Error creating user_metadata index: ' + e.message);
    }
}

print('✅ Additional collections setup completed');