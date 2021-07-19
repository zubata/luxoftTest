# luxoftTest
Необходимо разработать REST сервис-адаптер к SOAP веб-сервису.
Сервис источник: http://www.dneonline.com/calculator.asmx
Выходной частью сервиса-адаптера должен быть REST-интерфейс, который принимает значения для передачи в SOAP веб-сервис.
Необходимо предусмотреть валидацию запросов к REST-сервису на предмет их наличия, корректности формата и т.п.
Будет плюсом, если у реализованного сервиса будет спецификация в формате OpenAPI (SWAGGER), которая генерируется автоматически из кода.
Код должен работать с системой контроля версий GIT.

Фреймворки и инструменты, которые помогут реализовать задачу:
Spring, Spring Boot, Apache CXF, Apache Axis, Jackson, GSON, Apache Camel

Результат мы ожидаем в виде демонстрации работающего приложения, запущенного в minishift

Минимальный результат - синхронно работающий рест сервис, преобразующий SOAP в REST запросы.

Максимальный результат - асинхронно работающий сервис, который принимает рест запросы на расчет, возвращает correlationid, а результат расчета складывает в
Red Hat AMQ Брокер, развернутый на том же minishift. Затем результат расчета забирается вторым рест запросом по correlationId.
Также же REST сервис должен быть защищен авторизацией через Red Hat Single-sign-on (SSO) запущенным в том же minishift.
