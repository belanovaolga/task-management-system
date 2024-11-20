# Task Management System

Система управления задачами, предоставляет возможность организации и планированирования рабочего процесса.\
Сотрудники получают возможность работы с задачами, а именно создания, редактирования, комментирования и некоторых других
действий.
В зависимости от статуса (роли) сотрудник получает доступ к необходимым действиям.\
Такая система поможет оптимизировать рабочий процесс и обеспечить дополнительное удобство для сотрудников при выполнении
их обязанностей.

## Стек:

Java 17\
Gradle\
PostgreSQL\
Liquibase\
Spring Boot 3.3.5\
Spring-Boot-Web\
Spring-Boot-Data-JPA\
Spring-Boot-Security\
JWT\
OpenAPI (Swagger)\
Project Lombok

## Локальный запуск проекта:

1. Развернуть базу данные в docker с помощью файла docker/docker-compose.yaml\
   Необходимо запустить его, перейдя в сам файл или использовать команду docker-compose up -d в директории с файлом
   docker-compose.yaml

![Docker-Compose .png](src%2Fmain%2Fresources%2Fimages%2Fdocker-compose.png)

2. Сгенерировать классы OpenAPI, описанные в файле openapi/openapi.yaml\
   Для этого нужно в терминале вызвать команду ./gradlew openApiGenerate

![OpenAPI .png](src%2Fmain%2Fresources%2Fimages%2Fopenapi.png)

3. Запустить приложение с помощью класса Application.class\
   В классе Application находится метод main(String[] args), который является точкой входа в приложение

![Started .png](src%2Fmain%2Fresources%2Fimages%started.png)

Приложение запустилось на порту 8080
