# Exchange-rate
## Сервис обращается к сервису курсов валют и отображает GIF
• если курс по отношению к USD за сегодня стал выше вчерашнего, то выводится случайная GIF отсюда https://giphy.com/search/rich <br />
• если ниже - отсюда https://giphy.com/search/broke <br />
• если не изменился - отсюда https://www.choicemarket.ru/nochange.gif
## Ссылки на внешние REST API
• REST API курсов валют - https://exchangerate.host/#/#docs <br />
• REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide <br />
## Технологии
• Запросы приходят на HTTP endpoint. Туда передается код валюты по отношению с которой сравнивается базовая валюта <br />
• Для взаимодействия с внешними сервисами используется Feign <br />
• Все настройки (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в файл application.properties <br />
• На сервис написаны тесты (для мока внешних сервисов используется @mockbean) <br />
• Сборка и запуск Docker контейнера с этим сервисом <br />
## Параметры сервиса:
Параметры запроса: http://localhost:8080/rate-change?currencyCode=ХХХ (где ХХХ - код валюты) <br />
Запуск контейнера: docker run --name exchange-rate -p 8080:8080 exchange-rate
