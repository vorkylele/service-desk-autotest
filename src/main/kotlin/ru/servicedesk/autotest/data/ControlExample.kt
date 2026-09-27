package ru.servicedesk.autotest.data

import java.time.LocalDate

/**
 * Контрольный пример ВКР: исходные данные сквозного сценария и ожидаемые результаты.
 * Сценарий выполняется на чистой базе первым, часы стенда стартуют 21.09.2026 10:20 (понедельник),
 * поэтому номера заявок, участники маршрутов и сроки детерминированы.
 */
object ControlExample {
    const val ACCESS_SERVICE = "Предоставление доступа"
    const val INCIDENT_SERVICE = "Неисправность рабочей станции"

    const val PAYMENT_GATEWAY = "Платёжный шлюз: промышленный контур"
    const val VIEW_LOGS_ROLE = "Просмотр журналов"
    const val ACCESS_JUSTIFICATION = "Для анализа дефектов маршрутизации платежей при регрессионном тестировании релиза " +
        "требуется чтение журналов операций промышленного контура."
    const val DUPLICATE_JUSTIFICATION = "Повторный запрос"

    const val VCS = "Система контроля версий"
    const val DEVELOPER_ROLE = "Разработчик"
    const val VCS_JUSTIFICATION = "Необходима запись в репозитории группы платёжного шлюза."

    const val INCIDENT_SUBJECT = "Не определяется второй монитор после обновления"
    const val INCIDENT_EQUIPMENT_NO = "ИНВ-004512"
    const val INCIDENT_DESCRIPTION = "После установки обновлений операционной системы внешний монитор не определяется. " +
        "Кабель и порт проверены на другом рабочем месте — исправны."

    const val MANAGER_COMMENT = "Доступ необходим для регрессионного тестирования"
    const val OWNER_COMMENT = "Согласовано на время тестирования релиза"
    const val SECURITY_COMMENT = "Роль не даёт доступа к платёжным реквизитам"

    const val ACCESS_RESOLUTION = "Роль назначена в целевой системе, членство в группе доступа проверено."
    const val INCIDENT_RESOLUTION = "Переустановлен драйвер видеоадаптера, монитор определяется. Проверено совместно с заявителем."

    const val ACCESS_TICKET_NUMBER = "00000226"
    const val INCIDENT_TICKET_NUMBER = "00000227"
    const val DEVELOPER_TICKET_NUMBER = "00000228"
    const val ACCESS_SLA_MINUTES = 16 * 60
    const val INCIDENT_SLA_MINUTES = 8 * 60
    const val ACCESS_HISTORY_EVENTS = 8

    val PAYMENT_GATEWAY_ROUTE = listOf("Соколова Марина Игоревна", "Орлов Дмитрий Павлович", "Ершова Наталья Сергеевна")
    val VCS_ROUTE = listOf("Соколова Марина Игоревна", "Никитин Роман Евгеньевич")
    const val PENDING_APPROVALS_OF_MANAGER = 2

    const val L1_GROUP = "Первая линия поддержки"
    const val ACCESS_AGENT_NAME = "Мельникова Ольга Юрьевна"

    const val DISMISSED_EMPLOYEE = "Лебедева"
    const val DISMISSED_EMPLOYEE_FULL_NAME = "Лебедева Анна Олеговна"
    const val DISMISSAL_REASON = "Прекращение трудовых отношений"
    const val DISMISSED_EMPLOYEE_GRANTS = 1

    /** Демонстрационная история заявок: 60 дней до старта сценария. */
    val HISTORY_FROM: LocalDate = LocalDate.of(2026, 7, 23)
    val HISTORY_TO: LocalDate = LocalDate.of(2026, 9, 20)
    val SCENARIO_DATE: LocalDate = LocalDate.of(2026, 9, 21)
    const val HISTORY_TICKET_TYPES = 6
    const val HISTORY_TICKETS = 225
    const val HISTORY_BREACHED = 23

    /** Показатели отчёта за период, включающий день сценария: 225 исторических заявок и три новых. */
    val REPORT_KPI = listOf("228", "228", "89.9%", "23")
}
