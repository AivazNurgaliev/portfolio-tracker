# 1. Цель проекта
<p>Цель проекта - разработать систему анализа данных инвестиционного портфеля. Изначально пользователь указывает свои данные (не обязательно паспортные) и данные своего брокерского счёта/ИИС (номер счёта). Пользователь самостоятельно (вручную) указывает данные сделки (или неторговых поручений (ввод/ввывод средств/перевод на другой счёт)) по ценным бумагам или иным активам (валюта, драгоценные металлы), а именно:</p>
<ul>
    <li>тикер (может вручную указать название) эмитента/ценной бумаги или актива</li>
    <li>время операции</li>
    <li>сумму операции</li>
    <li>(возможно что-то ещё, например страну актива)</li>
</ul>
<p>и получает в наглядном виде данные о своём портфеле (диаграммы, гистограммы и прочее).</p>
<p>В системе есть разные типы подписок (уточнить какие). С разным уровнем подписки доступен разный функционал приложения (чем дороже подписка, тем больший функционал доступен). В Telegram боте с определённой периодичностью будет приходить аналитика портфеля (как дополнительная опция (в зависимости от подписки))</p>

# 2. Описание системы
Системы состоит из следующих функциональных блоков:
<ol>
    <li>Регистрация, аутентификация и авторизация (с целью возможности переноса данных на другое устройство и/или открытием приложения на другой платформе (например web application)). В том числе указание данных счёта (номер брокерского счёта/ИИС например)</li>
    <li>Функционал для ввода вручную всех данных по сделкам</li>
    <li>Функционал оплаты и выбора подписки</li>
    <li>Функционал просмотра аналитики портфеля (стоимость портфеля, доход (за разные промежутки времени), пополнения, выводы и т. д.)</li>
    <li>Функционал интеграции с Telegram (нужен?) (на будущее или как дополнительная опция?)</li>
    <li>Функционал просмотра аналитики баланса портфеля (страны, валюта, диаграммы и пр.)</li>
    <li>Функционал для работы с <a href="https://tinkoff.github.io/investAPI/">TINKOFF INVEST API</a> (на будущее) </li>
    <li></li>
    <li></li>
</ol>

## 2.1 Типы пользователей
<p>Система предусматривает 2 типа пользователей (как минимум): "стандарт" и "премиум". Пакет "премиум" дороже, чем "стандарт", следовательно в "премиуме" максимальный функционал, который может предоставить система.</p>
<p>В начале пользователь может единоразово (с одного устройства и надо будет это отслеживать) воспользоваться бесплатно полным функционалом приложения в течение 14 дней с момента регистрации.</p>

### 2.2 Регистрация
///
