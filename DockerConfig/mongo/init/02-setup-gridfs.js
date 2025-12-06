// Настройка GridFS бакета и индексов с проверками
db = db.getSiblingDB('userdb');

print('Setting up GridFS bucket...');

// Проверяем существование коллекций
var filesCollectionExists = db.getCollectionNames().includes('users.files');
var chunksCollectionExists = db.getCollectionNames().includes('users.chunks');

if (filesCollectionExists && chunksCollectionExists) {
    print('✅ GridFS collections already exist');
} else {
    // Создаем кастомный бакет для изображений пользователей
    db.createCollection('users.files');
    db.createCollection('users.chunks');
    print('✅ GridFS collections created');
}

// Создаем индексы только если они не существуют
try {
    db.getCollection('users.files').createIndex({ "metadata.userId": 1 }, {
        unique: true,
        background: true,
        name: "userId_index"
    });
    print('✅ Created userId_index');
} catch (e) {
    if (e.codeName === 'IndexKeySpecsConflict' || e.codeName === 'IndexOptionsConflict') {
        print('✅ userId_index already exists');
    } else {
        print('❌ Error creating userId_index: ' + e.message);
    }
}

try {
    db.getCollection('users.files').createIndex({ "uploadDate": 1 }, {
        background: true,
        name: "uploadDate_index"
    });
    print('✅ Created uploadDate_index');
} catch (e) {
    if (e.codeName === 'IndexKeySpecsConflict' || e.codeName === 'IndexOptionsConflict') {
        print('✅ uploadDate_index already exists');
    } else {
        print('❌ Error creating uploadDate_index: ' + e.message);
    }
}

try {
    db.getCollection('users.files').createIndex({ "filename": 1 }, {
        background: true,
        name: "filename_index"
    });
    print('✅ Created filename_index');
} catch (e) {
    if (e.codeName === 'IndexKeySpecsConflict' || e.codeName === 'IndexOptionsConflict') {
        print('✅ filename_index already exists');
    } else {
        print('❌ Error creating filename_index: ' + e.message);
    }
}

try {
    db.getCollection('users.chunks').createIndex({ "files_id": 1, "n": 1 }, {
        unique: true,
        background: true,
        name: "chunks_composite_index"
    });
    print('✅ Created chunks_composite_index');
} catch (e) {
    if (e.codeName === 'IndexKeySpecsConflict' || e.codeName === 'IndexOptionsConflict') {
        print('✅ chunks_composite_index already exists');
    } else {
        print('❌ Error creating chunks_composite_index: ' + e.message);
    }
}

print('✅ GridFS bucket setup completed');