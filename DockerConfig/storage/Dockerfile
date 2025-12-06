FROM mongo:6.0.8

# Устанавливаем дополнительные утилиты
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Сохраняем стандартный порт MongoDB
#EXPOSE 27017

# Используем стандартную команду запуска MongoDB
CMD ["mongod", "--bind_ip_all"]