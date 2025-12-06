#!/bin/bash

# Скрипт-обертка для инициализации MongoDB
set -e

echo "====================================="
echo "MongoDB Initialization Script"
echo "====================================="

# Функция для проверки готовности MongoDB
wait_for_mongodb() {
    local max_attempts=30
    local attempt=1

    echo "Waiting for MongoDB to be ready..."

    while [ $attempt -le $max_attempts ]; do
        if mongosh --host localhost -u admin -p admin --authenticationDatabase admin --eval "db.adminCommand('ping').ok" --quiet > /dev/null 2>&1; then
            echo "✅ MongoDB is ready!"
            return 0
        else
            echo "⏳ Attempt $attempt/$max_attempts: MongoDB not ready yet, waiting..."
            sleep 2
            ((attempt++))
        fi
    done

    echo "❌ MongoDB failed to start within $max_attempts attempts"
    return 1
}

# Функция для выполнения скриптов с обработкой ошибок
execute_script() {
    local script_name=$1
    local max_attempts=3
    local attempt=1

    echo "Executing: $script_name"

    while [ $attempt -le $max_attempts ]; do
        if mongosh --host localhost -u admin -p admin --authenticationDatabase admin /docker-entrypoint-initdb.d/$script_name --quiet; then
            echo "✅ $script_name completed successfully"
            return 0
        else
            echo "❌ Attempt $attempt failed for $script_name, retrying..."
            sleep 2
            ((attempt++))
        fi
    done

    echo "⚠️  Script $script_name had issues but continuing..."
    return 0
}

# Основной процесс инициализации
main() {
    # Ждем пока MongoDB полностью запустится
    if ! wait_for_mongodb; then
        echo "❌ Failed to connect to MongoDB, exiting..."
        exit 1
    fi

    echo "Starting database initialization..."

    # Выполняем скрипты инициализации по порядку
    execute_script "01-create-database.js"
    execute_script "02-setup-gridfs.js"
    execute_script "03-create-collections.js"

    echo "====================================="
    echo "🎉 MongoDB Initialization Completed!"
    echo "====================================="
    echo "Database: userdb"
    echo "App User: appuser/apppassword"
    echo "GridFS Bucket: users"
    echo "====================================="
}

# Запускаем основную функцию
main