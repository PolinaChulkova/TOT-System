# TOT-System
Выполненное тестовое задание по Api Московской Биржи

# Архитектура
Сборщик: **Maven**

Используемые технологии:

- **Spring Boot**
- **PostgreSql**
- **Hibernate**
- **FlyWay**
- **Lombok**

**Настройки подключения к БД** находятся по пути: src/main/resources/application.properties. 
По умолчанию там выставлены настройки для подключения к локальной БД, которой можно изменить при необходимости.
Миграции накатываются на БД автоматически.

Проект содержит 6 java каталогов:

- **controller** - набор классов контроллеров;
- **DTO** - объектов, для удобства передачи данных;
- **model** - модели объектов Security, TradeHistory и TradeHistoryPK (составной PK);
- **repository** - репозитории к моделям;
- **service** - имплементация репозиториев с дополнительными методами;
- **parser** - парсеры XML для TradeHistory и Security.

В каталог storage загружаются XML файлы с информацией о ценных бумагах и историях торгов

# Примечание
Дополнительная информация также содержится в комментариях к коду.

Возникли трудности с реализацией дополнительного задания №2:
- реализовать MVC приложение, позволяющее через интерфейс импортировать файлы и работать
с таблицей п.3 и CRUD операциями. Не стоит акцентировать внимание на ux/ui, нам интересно
посмотреть на Ваш навык работы с html/js.
