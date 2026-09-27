# service-desk-autotest

Автотесты портала внутренних заявок: интерфейс, REST API и база данных — учебный проект
к выпускной квалификационной работе «Автоматизация обработки внутренних заявок сотрудников в организации
на примере ООО «ВБ ТЕХ»».

Связанные репозитории:

| Репозиторий | Назначение |
|---|---|
| [service-desk-backend](https://github.com/vorkylele/service-desk-backend) | REST API, прикладная логика, схема базы данных |
| [service-desk-frontend](https://github.com/vorkylele/service-desk-frontend) | портал самообслуживания (React) |
| **service-desk-autotest** (этот) | автотесты API, базы данных и интерфейса, полный стенд в Docker |

## Что проверяется

| Тесты | Слой | Содержание |
|---|---|---|
| `ui.ControlExampleUiTests` (9) | интерфейс, Playwright | контрольный пример ВКР: заявка на доступ к платёжному контуру, маршрут из трёх согласований, автоматическая маршрутизация инцидента, решение, реестр прав, отказ при повторном запросе, отчёт, увольнение с отзывом прав; 17 снимков экранов |
| `api.*ApiTests` (13) | REST API, REST Assured | аутентификация, разграничение прав по ролям, каталог услуг, расчёт контрольного срока, согласование и отказ, бизнес-правила, отчёт |
| `db.*DbTests` (6) | база данных, JDBC | запись реестра прав со ссылкой на заявку, отзыв прав при увольнении, уникальность действующего права, журнал событий, хеширование паролей |

Контрольный срок сверяется с независимым эталоном `SlaOracle`: он считает срок арифметикой по рабочим дням,
а не обходом рабочих интервалов, как приложение. Классы выполняются по порядку `@Order`: сценарий интерфейса
требует чистой базы, проверки API и базы данных опираются на оставленное им состояние.

## Стек

Kotlin 2.0 · JUnit 5 · Playwright (интерфейс) · REST Assured (API) · JDBC + PostgreSQL (база данных) · AssertJ · Allure

## Запуск

Три репозитория должны лежать в одной папке:

```bash
git clone https://github.com/vorkylele/service-desk-backend
git clone https://github.com/vorkylele/service-desk-frontend
git clone https://github.com/vorkylele/service-desk-autotest
cd service-desk-autotest
```

```bash
docker compose -f docker-compose.full.yml up -d --build   # база, backend (8080), frontend (5173)
./gradlew test                                            # первый запуск скачает браузер Playwright
./gradlew allureServe                                     # отчёт Allure
docker compose -f docker-compose.full.yml down -v         # чистая база для следующего прогона
```

Параметры: `-Dbrowser.channel=chrome` — использовать установленный Chrome; `-Dheadless=false` — видимый браузер;
`-Dui.url`, `-Dapi.url`, `-Ddb.url` — адреса другого стенда. `./gradlew publishScreenshots` копирует снимки экранов
в `../service-desk-frontend/docs/screenshots`.

Часы стенда стартуют 21.09.2026 (понедельник) в 10:19:56 — сценарии зависят от рабочего календаря, поэтому результат
не зависит от дня и времени запуска. Чтобы снимки совпали с приведёнными в ВКР минута в минуту, тесты запускают сразу после старта стенда.

## Структура

```
src/main/kotlin/ru/servicedesk/autotest/
  config/   Config — адреса стенда, Users — демонстрационные учётные записи
  data/     ControlExample — исходные данные и ожидаемые результаты контрольного примера, TicketStatus
  api/      Endpoints — адреса REST-интерфейса; specs/SpecManager — спецификации запросов и токены;
            AbstractApiClient и services/*Client — вызовы API; models/ — запросы и ответы;
            models/builders/ — тестовые данные; helpers/ReferenceIds — коды справочников → идентификаторы; assertions/
  ui/       Browser — Playwright, Portal — вход и переходы; locators/ — локаторы страниц;
            pages/ — действия на страницах (наследуют локаторы); assertions/
  db/       DatabaseClient — JDBC; queries/Sql — текст запросов; services/*Queries — выборки; models/; assertions/
  utils/    SlaOracle — независимый эталон расчёта контрольного срока
src/test/kotlin/ru/servicedesk/autotest/
  ui/ api/ db/   тестовые классы: только вызовы шагов и проверок
```
