package ru.servicedesk.autotest.config

/** Демонстрационные учётные записи стенда и их роли в контрольном примере. */
object Users {
    const val APPLICANT = "kravtsov@vbtech.example"      // тестировщик, заявитель
    const val DEVELOPER = "lebedeva@vbtech.example"      // разработчик, увольняется по ходу сценария
    const val TECH_LEAD = "nikitin@vbtech.example"       // владелец системы контроля версий
    const val MANAGER = "sokolova@vbtech.example"        // руководитель группы
    const val OWNER = "orlov@vbtech.example"             // владелец платёжного контура
    const val SECURITY = "ershova@vbtech.example"        // информационная безопасность
    const val ACCESS_AGENT = "melnikova@vbtech.example"  // исполнитель, управление доступом
    const val L1_AGENT = "zaitsev@vbtech.example"        // исполнитель, первая линия
    const val SUPPORT_HEAD = "gromov@vbtech.example"     // руководитель службы поддержки
    const val ADMIN = "frolova@vbtech.example"           // администратор, кадровые события
}
