/*
 * The MIT License
 *
 * Copyright 2017 Kamnev Georgiy <nt.gocha@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package xyz.cofe.shtrihm;

import com.jacob.com.Currency;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Драйвер Штрих-М.
 *
 * @author Kamnev Georgiy <nt.gocha@gmail.com>
 */
public interface Driver {
    //<editor-fold defaultstate="collapsed" desc="SetDate() - Устанавливает  дату">
    /**
     * SetDate.
     * <p>
     * Устанавливает дату во внутренних часах ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password
     * указать пароль системного администратора и заполнить свойство Date, в котором указать
     * текущую дату.
     * <p>
     * Работает только в режимах 4, 7 и 9 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 6 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.
     * <p>
     * Date - Внутренняя дата ККМ.
     * <p>
     * [id(0x00000056), helpstring("УстановитьДату")] long SetDate();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "date")
        , @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")})
    @CallState(state = "4|7|9")
    @TargetState(state = "6")
    int SetDate();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SetPointPosition() - установки положения десятичной точки">
    /**
     * Установить Положение Точки.
     * <p>
     * Команда установки положения десятичной точки (опция предназначена только для ККМ без
     * ЭКДЗ).
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора и
     * заполнить свойство PointPosition, в котором указать положение десятичной точки.
     * <p>
     * Работает только в режиме 7 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * PointPosition (Логич.) Признак положения десятичной точки.<p>
     * FALSE – десятичная точка отделяет 0 разрядов,
     * <p>
     * TRUE – десятичная точка отделяет 2 разряда.
     * <p>
     * [id(0x0000005b), helpstring("УстановитьПоложенияТочки")] long SetPointPosition();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
            @DriverProperty(name = "password")
        ,   @DriverProperty(name = "pointPosition")})
    @OutputProperties(properties = {})
    @CallState(state = "7")
    int SetPointPosition();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SetTime() - Устанавливает время во внутренних часах">
    /**
     * Устанавливает время во внутренних часах ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора и
     * заполнить свойство Time, в котором указать текущее время.
     * <p>
     * Работает в режимах 4, 7 и 9 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства:
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * Time – Внутреннее время ККМ.
     * <p>
     * TimeStr - Строковое представление свойства Time.
     * <p>
     * [id(0x0000005e), helpstring("УстановитьВремя")] long SetTime();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
            @DriverProperty(name = "time")
        ,   @DriverProperty(name = "timeStr")
        ,   @DriverProperty(name = "password")})
    @OutputProperties(properties = {
            @DriverProperty(name = "resultCode")
        ,   @DriverProperty(name = "resultCodeDescription")})
    @CallState(state = "4|7|9")
    int SetTime();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="beginDocument() - Включает рижим буферизации команд.">
    /**
     * Включает рижим буферизации команд.
     * <p>
     * Все последующие команды будут вноситься в буфер, и выполнены только после команды EndDocument.
     * <p>
     * [id(0x0000079f), helpstring("НачатьДокумент")]
     * long BeginDocument();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {})
    @OutputProperties(properties = {})
    @CallState
    int beginDocument();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="buy() - Покупка">
    /**
     * Покупка – торговая операция, при которой товар перемещается от клиента к оператору, а деньги –
     * в обратном направлении: от оператора к клиенту.
     * <p>
     * Команда производит регистрацию покупки определенного количества товара в определенную
     * секцию с вычислением налогов (см. «Инструкцию по эксплуатации»/«Руководство
     * оператора») без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * Работает в режимах 2 (проверка на окончание 24 часов производится запросом из ФП до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=1) и 9 (см. свойства ECRMode и
     * ECRMode8Status).
     * <p>
     * Переводит ККМ в режим 8 подрежим 1, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Quantity (0,001.. 9999999,999) Количество товара
     * <li> Price (0.. 99999999,99) Цена за единицу товара.
     * <li> Department (0..16) Номер отдела (секции).
     * <li> Tax1 (0..4) 1-ый номер налоговой группы.
     * <li> Tax2 (0..4) 2-ой номер налоговой группы.
     * <li> Tax3 (0..4) 3-ий номер налоговой группы.
     * <li> Tax4 (0..4) 4-ый номер налоговой группы.
     * <li> StringForPrinting (не более 40 символов) для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x00000003), helpstring("Покупка")] long Buy();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
          @DriverProperty(name = "password")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "2|4|7|8.1|9")
    @TargetState(state = "8.1|3")
    int buy();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="buyReturn() - Возврат покупки">
    /**
     * Возврат покупки – торговая операция, при которой товар возвращается обратно клиенту, а деньги
     * перемещаются в направлении от клиента к оператору.
     * <p>
     * Команда производит регистрацию возврата покупки определенного количества товара из
     * определенной секции с вычислением налогов (см. «Инструкцию по
     * эксплуатации»/«Руководство оператора») без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 (проверка на окончание 24 часов производится запросом из ФП до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=3) и 9 (см. свойства ECRMode и
     * ECRMode8Status).
     * <p>
     * Переводит ККМ в режим 8 подрежим 3, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).
     * <p>
     * Используемые свойства
     * <ul>
     * <li>
     * Password Пароль для исполнения метода драйвера.
     * <li>
     * Quantity (0,001..9999999,999) Количество товара
     * <li>
     * Price Цена за единицу товара.
     * <li>
     * Department Номер отдела (секции).
     * <li>
     * Tax1 1-ый номер налоговой группы.
     * <li>
     * Tax2 2-ой номер налоговой группы.
     * <li>
     * Tax3 3-ий номер налоговой группы.
     * <li>
     * Tax4 4-ый номер налоговой группы.
     * <li> StringForPrinting Строка для печати (печатается на чеке в строки,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Целое Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x0000004f), helpstring("ВозвратПокупки")]
     * long ReturnBuy();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
          @DriverProperty(name = "password")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "2|4|7|8.3|9")
    @TargetState(state = "8.3|3")
    int buyReturn();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cancelCheck() - Операция  производит  аннулирование  (отмену)  всего  чека ">
    /**
     * Операция производит аннулирование (отмену) всего чека.
     * <p>
     * При этом на чеке печатается «ЧЕК АННУЛИРОВАН».
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим, в котором ККМ была до открытия чека, или в режим 3 (см. свойство
     * ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства:
     * <p>
     * OperatorNumber - Порядковый номер оператора, чей пароль был введен.
     * <p>
     * [id(0x00000005), helpstring("АннулироватьЧек")]
     * long CancelCheck();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "8")
    @TargetState(state = "?")
    int cancelCheck();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cashIncome() - регистрирует внесение денежной суммы в кассу">
    /**
     * Метод регистрирует внесение денежной суммы в кассу.
     * <p>
     * В свойстве Summ1 задается вносимая сумма.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * В свойстве OpenDocumentNumber возвращается сквозной порядковый номер документа.
     * <p>
     * Работает в режимах 2, 3, 4, 7 и 9 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Summ1 Свойство, используемое для хранения различных значений денежных сумм.
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * <li> OpenDocumentNumber Целое 0..9999 R Сквозной номер последнего документа ККМ.
     * </ul>
     * [id(0x00000006), helpstring("Внесение")]
     * long CashIncome();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "openDocumentNumber")})
    @CallState(state = "2|3|4|7|9")
    int cashIncome();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="cashOutcome() - регистрирует выплату денежной суммы из кассы">
    /**
     * Метод регистрирует выплату денежной суммы из кассы.
     * <p>
     * В свойстве Summ1 задается выплачиваемая сумма.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * В свойстве OpenDocumentNumber возвращается сквозной порядковый номер документа.
     * <p>
     * Работает в режимах 2, 3, 4, 7 и 9 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера. 248
     * <li> Summ1
     * Свойство, используемое для хранения
     * различных значений денежных сумм.
     * </ul>
     * <p>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был
     * введен.
     * <li> OpenDocumentNumber Сквозной номер последнего документа ККМ.
     * </ul>
     * [id(0x00000007), helpstring("Выплата")]
     * long CashOutcome();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "openDocumentNumber")})
    @CallState(state = "2|3|4|7|9")
    int cashOutcome();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="charge() - Метод регистрирует надбавку на сумму">
    /**
     * Метод регистрирует надбавку на сумму, задаваемую в свойстве Summ1, с вычислением налогов.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера. 248
     * <li> Summ1 Свойство, используемое для хранения различных значений денежных сумм.
     * <li> Tax1 Целое 0..4 RW 1-ый номер налоговой группы. 279
     * <li> Tax2 Целое 0..4 RW 2-ой номер налоговой группы. 282
     * <li> Tax3 Целое 0..4 RW 3-ий номер налоговой группы. 284
     * <li> Tax4 Целое 0..4 RW 4-ый номер налоговой группы. 287
     * <li> StringForPrinting Строка символов для печати (печатается на чеке в строке, идущей перед строкой, содержащей
     * цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x00000008), helpstring("Надбавка")]
     * long Charge();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "8")
    int charge();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkConnection() - Проверить связь.">
    /**
     * Проверить связь.
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.<p>
     * CheckFMConnection Проверить связь с ФП<p>
     * CheckEJConnection Проверить связь с ЭКЛЗ<p>
     * <p>
     * [id(0x000007f1), helpstring("ПроверитьСвязь")] long CheckConnection();
     *
     * @return код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "checkFMConnection")
        , @DriverProperty(name = "checkEJConnection")
        , @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    @CallState
    int checkConnection();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkSubTotal() - Метод возвращает в свойство Summ1 подытог текущего чека">
    /**
     * Метод возвращает в свойство Summ1 подытог текущего чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера. 248
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * <li> Summ1 Свойство, используемое для хранения различных значений денежных сумм.
     * </ul>
     * [id(0x00000009), helpstring("ПодытогЧека")]
     * long CheckSubTotal();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "summ1")})
    @CallState(state = "8")
    int checkSubTotal();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="closeCheck() - закрытие  чека">
    /**
     * Метод производит закрытие чека комбинированным типом оплаты с вычислением налогов и
     * суммы сдачи.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * В свойстве Change возвращается сумма сдачи.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 2 или 3 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <ul>
     * <li>Password - Пароль для исполнения метода драйвера
     * <li>Summ1 - Свойство, используемое для хранения суммы наличных клиента.
     * <li>Summ2 - Свойство, используемое для хранения суммы клиента типа оплаты 2.
     * <li>Summ3 - Свойство, используемое для хранения суммы клиента типа оплаты 3.
     * <li>Summ4 - Свойство, используемое для хранения суммы клиента типа оплаты 4.
     * <li>DiscountOnCheck - Скидка на чек.
     * <li>Tax1 - 1-ый номер налоговой группы.
     * <li>Tax2 - 2-ой номер налоговой группы.
     * <li>Tax3 - 3-ий номер налоговой группы.
     * <li>Tax4 - 4-ый номер налоговой группы.
     * <li>StringForPrinting Строка не более 40 символов -
     * Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей
     * цену(сумму) и/или количество).
     * </ul>
     * <p>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber - 1..30 Порядковый номер оператора, чей пароль был введен.
     * <li>Change - Свойство, в котором хранится сумма сдачи.
     * </ul>
     * <p>
     * [id(0x0000000a), helpstring("ЗакрытьЧек")] long CloseCheck();
     *
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")
        , @DriverProperty(name = "summ2")
        , @DriverProperty(name = "summ3")
        , @DriverProperty(name = "summ4")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "discountOnCheck")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "change")})
    @CallState(state = "8")
    @TargetState(state = "2|3")
    int closeCheck();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="closeCheckWithKPK() - Метод производит закрытие чека с КПК.">
    /**
     * Метод производит закрытие чека с КПК.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * В свойстве Change возвращается сумма сдачи.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 2 или 3 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Summ1 Свойство, используемое для хранения суммы наличных клиента.
     * <li> Summ2 Свойство, используемое для хранения суммы клиента типа оплаты 2.
     * <li> Summ3 Свойство, используемое для хранения суммы клиента типа оплаты 3.
     * <li> Summ4 Свойство, используемое для хранения суммы клиента типа оплаты 4.
     * <li> DiscountOnCheck (0..99,99 ) Скидка на чек.
     * <li> Tax1 (0..4) 1-ый номер налоговой группы.
     * <li> Tax2 (0..4) 2-ой номер налоговой группы.
     * <li> Tax3 (0..4) 3-ий номер налоговой группы.
     * <li> Tax4 (0..4) 4-ый номер налоговой группы.
     * <li> StringForPrinting Строка (не более 40) символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей
     * цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * <li> Change Свойство, в котором хранится сумма сдачи.
     * <li> KPKStr Строка (до 40 сим ) КПК
     * </ul>
     * [id(0x0000078f), helpstring("ЗакрытьЧекСКПК")]
     * long CloseCheckWithKPK();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")
        , @DriverProperty(name = "summ2")
        , @DriverProperty(name = "summ3")
        , @DriverProperty(name = "summ4")
        , @DriverProperty(name = "discountOnCheck")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "change")
        , @DriverProperty(name = "KPKStr")})
    @CallState(state = "8")
    @TargetState(state = "2|3")
    int closeCheckWithKPK();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="confirmDate() - подтверждения  программирования  даты ">
    /**
     * Команда подтверждения программирования даты во внутренних часах ККМ.
     * <p>
     * Перед вызовом
     * метода в свойстве Password указать пароль системного администратора и заполнить свойство
     * Date, в котором указать текущую дату.
     * <p>
     * Работает только в режиме 6 (см. свойство ECRMode).
     * <p>
     * При успешном выполнении команды переводит ККМ в режим 4 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.
     * <p>
     * Date - Внутренняя дата ККМ.
     * <p>
     * [id(0x0000000c), helpstring("ПодтвердитьДату")] long ConfirmDate();
     *
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "date")
        , @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")})
    @CallState(state = "6")
    int confirmDate();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="connect() - Установить связь">
    // [id(0x0000000d), helpstring("УстановитьСвязь")] long Connect();
    /**
     * Установить связь.
     * <p>
     * Перед вызовом метода в свойстве ComputerName указать имя компьютера, к которому подключена ККТ.
     * <p>
     * Метод выполняет следующие действия:
     * <p>
     * 1. Занимает COM порт с номером ComNumber;
     * <p>
     * 2. Устанавливает скорость порта BaudRate;
     * <p>
     * 3. Устанавливает таймаут приёма байта порта Timeout;
     * <p>
     * 4. Запрашивает состояние устройства путём выполнения метода GetECRStatus.
     * <p>
     * 5. Запрашивает параметры устройства путём выполнения метода GetDeviceMetrics.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера.
     * <li> ComNumber Целое 0..255 RW Номер Com-порта ПК к которому подсоединена ККМ (0 – порт 1, 1 – порт 2, 2 – порт 3
     * и т.д.).
     * <li> BaudRate Целое 0..6 RW Скорость обмена между ККМ и подключенным к ней устройством.
     * <li> Timeout Целое 0..255 RW Таймаут приема байта (см. описание свойства).
     * <li> ComputerName Строка RW Имя компьютера, к которому подключена ККТ.
     * <li> ProtocolType Целое 0..1 Тип протокола (0-стандартный, 1-протокол ККТ 2.0).
     * <li> ConnectionType Целое 0..6 RW Тип подключения.
     * </ul>
     *
     * @return код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "comNumber")
        , @DriverProperty(name = "baudRate")
        , @DriverProperty(name = "timeout")
        , @DriverProperty(name = "computerName")
        , @DriverProperty(name = "protocolType")
        , @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    @CallState
    int connect();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="continuePrint()">
    /**
     * Команда возобновления печати после заправки в ККМ бумаги.
     * <p>
     * После заправки бумаги ККМ
     * находится в подрежиме 3 (см. свойство ECRAdvancedMode) до тех пор, пока не будет вызван
     * данный метод.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * Работает в любом режиме, но только в подрежиме 3 (см. свойства ECRMode,
     * ECRAdvancedMode).
     * <p>
     * Не меняет режима ККМ, но выводит из подрежима 3 (см. свойства ECRMode,
     * ECRAdvancedMode).
     * <p>
     * Используемые свойства
     * <p>
     * <b>Password</b> Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства
     * <p>
     * <b>OperatorNumber</b> Порядковый номер оператора, чей пароль был введен.
     * <p>
     * [id(0x0000000e), helpstring("ПродолжитьПечать")]
     * long ContinuePrint();
     * <p>
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")
        , @DriverProperty(name = "operatorNumber")})
    @CallState(state = "*.*.3")
    @TargetState(state = "!*.*.3")
    int continuePrint();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="disconnect() : int - РазорватьСвязь">
    /**
     * Освобождает COM-порт ПК, занятый под драйвер методом Connect.
     * <p>
     * [id(0x00000013), helpstring("РазорватьСвязь")] long Disconnect();
     *
     * @return код ошибки или 0
     */
    @CallState
    int disconnect();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="discount() - Метод регистрирует скидку на сумму">
    /**
     * Метод регистрирует скидку на сумму.
     * <p>
     * Метод регистрирует скидку на сумму, задаваемую в свойстве Summ1, с вычислением налогов.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Summ1 Свойство, используемое для хранения различных значений денежных сумм.
     * <li> Tax1 1-ый номер налоговой группы.
     * <li> Tax2 2-ой номер налоговой группы.
     * <li> Tax3 3-ий номер налоговой группы.
     * <li> Tax4 4-ый номер налоговой группы.
     * <li> StringForPrinting Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x00000014), helpstring("Скидка")]
     * long Discount();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "summ1")
        , @DriverProperty(name = "summ2")
        , @DriverProperty(name = "summ3")
        , @DriverProperty(name = "summ4")
        , @DriverProperty(name = "discountOnCheck")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "change")
        , @DriverProperty(name = "KPKStr")})
    @CallState(state = "8")
    int discount();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="endDocument() - Выполнение всех команд, внесенных в буфер выход из режима буферизации команд">
    /**
     * Выполнение всех команд, внесенных в буфер и выход из режима буферизации команд.
     * <p>
     * [id(0x000007a0), helpstring("ЗавершитьДокумент")]
     * long EndDocument();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {})
    @OutputProperties(properties = {})
    @CallState
    int endDocument();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="feedDocument() - Продвигает документ на указанное количество строк">
    /**
     * Продвигает документ на указанное количество строк.
     * <p>
     * Продвигает документ на указанное в свойстве StringQuantity количество строк.
     * <p>
     * Продвигаемый документ задается свойствами UseSlipDocument, UseReceiptRibbon, UseJournalRibbon.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Метод может вызываться в любом режиме, кроме режимов 8, 10, 11, 12, 14 и подрежимов 4 и 5
     * (см. свойства ECRMode и ECRAdvancedMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> StringQuantity Количество строк, на которое необходимо продвинуть документ.
     * <li> UseSlipDocument Признак операции с подкладным документом.
     * FALSE – не производить операцию над подкладным документом, TRUE – производить операцию.
     * <li> UseReceiptRibbon Признак операции с чековой лентой.
     * FALSE – не производить операцию над чековой лентой, TRUE – производить операцию над чековой лентой.
     * <li> UseJournalRibbon Признак операции с лентой операционного журнала:
     * FALSE – не производить операцию над лентой операционного журнала, TRUE – производить операцию над лентой.
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x0000001d), helpstring("ПродвинутьДокумент")]
     * long FeedDocument();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "stringQuantity")
        , @DriverProperty(name = "useSlipDocument")
        , @DriverProperty(name = "useReceiptRibbon")
        , @DriverProperty(name = "useJournalRibbon")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "!8&!10&!11&!12&!14&!*.*.4&!*.*.5")
    int feedDocument();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="baudRate : int - Скорость обмена между ККМ и подключенным к ней устройством">
    //BaudRate
    //[id(0x0000006e), propget, helpstring("СкоростьОбмена")] long BaudRate();
    //[id(0x0000006e), propput, helpstring("СкоростьОбмена")] void BaudRate([in] long rhs);
    /**
     * Скорость обмена между ККМ и подключенным к ней устройством.
     * <p>
     * Методы SetExchangeParam и Connect используют данное свойство,
     * <p>
     * а метод GetExchangeParam модифицирует его.
     * <p>
     * <p>
     * Соответствие значения параметра и скорости обмена приведены в таблице:
     * <p>
     * Значение параметра BaudRate Скорость обмена, бод
     * <p>
     * 0 2400
     * <p>
     * 1 4800
     * <p>
     * 2 9600
     * <p>
     * 3 19200
     * <p>
     * 4 38400
     * <p>
     * 5 57600
     * <p>
     * 6 115200
     * <p>
     * @return Код ошибки или 0
     */
    int getBaudRate();
    
    /**
     * Скорость обмена между ККМ и подключенным к ней устройством.
     *
     * @param brate 0..6
     * @see #getBaudRate() 
     */
    void setBaudRate(int brate);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="сhange : Currency - Сдача">
    /**
     * Сдача
     * <p>
     * Тип: Currency / Денежный (свойство доступно только для чтения)
     * <p>
     * Свойство, в котором хранится сумма сдачи.
     * <p>
     * Модифицируется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument, FNCancelDocument.
     * <p>
     * [id(0x0000006f), propget, helpstring("Сдача")]
     * CY Change();
     * @return Код ошибки или 0
     */
    Currency getChange();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkType : int - Тип открываемого документа/чека">
    /**
     * Тип открываемого документа/чека.
     * <p>
     * Диапазон значений: 0…3: «0» - продажа, «1» - покупка, «2» - возврат продажи, «3» - возврат
     * покупки.
     * <p>
     * Используется методами OpenCheck, OpenFiscalSlipDocument, OpenStandardFiscalSlipDocument
     * FNBuildCorrectionReceipt, FNStorno.
     * <p>
     * [id(0x00000071), propget, helpstring("ТипЧека")] long CheckType();
     * <p>
     * [id(0x00000071), propput, helpstring("ТипЧека")] void CheckType([in] long rhs);
     * @return Код ошибки или 0
     */
    int getCheckType();
    
    /**
     * 
     * @param v 
     * @see #getCheckType() 
     */
    void setCheckType(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="comNumber : int - Номер Com-порта ПК">
    //[id(0x00000072), propget, helpstring("НомерCOMпорта")] long ComNumber();
    //[id(0x00000072), propput, helpstring("НомерCOMпорта")] void ComNumber([in] long rhs);
    /**
     * Номер Com-порта ПК к которому подсоединена ККМ.
     * <p>
     * Диапазон значений: 0…255 («0» – порт 1, «1» – порт 2, «2» – порт 3 и т.д.).
     * <p>
     * Используется методами ShowProperties, Connect, LockPort, AdminUnlockPort.
     * <p>
     * Модифицируется методом ShowProperties
     *
     * @return Код ошибки или 0
     */
    int getComNumber();

    /**
     * Номер Com-порта ПК к которому подсоединена ККМ.
     *
     * @param cport Диапазон значений: 0…255
     */
    void setComNumber(int cport);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="computerName : String - ИмяКомпьютера">
    /**
     * ComputerName ИмяКомпьютера.
     * <p>
     * Тип: WideString / Строка
     * <p>
     * Имя компьютера, к которому подключен ККТ
     * <p>
     * Используется методом ServerConnect.
     * <p>
     * [id(0x000001e7), propget, helpstring("ИмяКомпьютера")] BSTR ComputerName();
     * <p>
     * [id(0x000001e7), propput, helpstring("ИмяКомпьютера")] void ComputerName([in] BSTR rhs);
     * <p>
     * @return Код ошибки или 0
     */
    String getComputerName();
    
    /**
     * 
     * @param cname 
     * @see #getComputerName() 
     */
    void setComputerName(String cname);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="сonnectionTimeout : int - Таймаут подключения">
    /**
     * Таймаут подключения.
     * <p>
     * Используется методами WaitConnection, WaitForPrinting
     *
     * @return
     */
    int getConnectionTimeout();
    
    /**
     * 
     * @param v 
     * @see #getConnectionTimeout() 
     */
    void setConnectionTimeout(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="connectionType : int - Тип подключения к устройству">
    /**
     * Тип подключения к устройству - ConnectionType.
     * <p>
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Тип подключения к устройству.Значение по умолчанию – 0 (Локальное подключение)
     * <p>
     * Диапазон допустимых значений:
     * <p>
     * Значение / Тип подключения
     * <p>
     * 0 Локально
     * <p>
     * 1 Сервер ККМ (TCP)
     * <p>
     * 2 Сервер ККМ (DCOM)
     * <p>
     * 3 ESCAPE
     * <p>
     * 4 Не используется
     * <p>
     * 5 Эмулятор
     * <p>
     * 6 Подключение через ТСР-сокет
     * <p>
     * Используется методом Connect.
     * <p>
     * Модифицируется методом SetActiveLD.
     * <p>
     * [id(0x0000020a), propget, helpstring("ТипПодключения")] long ConnectionType();
     * <p>
     * [id(0x0000020a), propput, helpstring("ТипПодключения")] void ConnectionType([in] long rhs);
     * <p>
     */
    int getConnectionType();

    /**
     * 
     * @param type 
     * @see #getConnectionType() 
     */
    void setConnectionType(int type);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="date : Date - Дата">
    /**
     * Дата.
     * <p>
     * Внутренняя дата ККМ. В зависимости от метода может быть датой внутреннего времени ККМ,
     * датой фискализации (перерегистрации) и т.д. (см. описание методов).
     * <p>
     * Используется методами SetDate, ConfirmDate.
     * <p>
     * Модифицируется методами GetECRStatus, GetLastFMRecordDate, Fiscalization,
     * GetFiscalizationParameters, FNBuildCalculationStateReport, FNFindDocument,
     * FNGetExpirationTime, FNGetFiscalizationResult, FNGetInfoExchangeStatus,
     * FNGetOFDTicketByDocNumber, FNGetStatus.
     * <p>
     * См. также: методы MFPGetPrepareActivizationResult, MFPPrepareActivization.
     * <p>
     * [id(0x0000007a), propget, helpstring("Дата")] DATE Date();
     * <p>
     * [id(0x0000007a), propput, helpstring("Дата")] void Date([in] DATE rhs);
     * @return дата
     */
    Date getDate();
    
    /**
     * 
     * @param d 
     * @see #getDate() 
     */
    void setDate(Date d);    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="department : int -  Номер отдела (секции)">
    /**
     * Номер отдела (секции).
     * <p>
     * Диапазон значений: 0…16.
     * <p>
     * Используется методами
     * <p>
     * Sale, SaleEx, Buy, BuyEx, ReturnSale, ReturnSaleEx, ReturnBuy,
     * ReturnBuyEx, Storno, StornoEx, EKLZDepartmentReportInDatesRange,
     * EKLZDepartmentReportInSessionsRange, GetEKLZDepartmentReportInDatesRange,
     * GetEKLZDepartmentReportInSessionsRange, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, FNDiscountOperation, FNStorno.
     * <p>
     * [id(0x0000007b), propget, helpstring("Отдел")] long Department();
     * <p>
     * [id(0x0000007b), propput, helpstring("Отдел")] void Department([in] long rhs);
     *
     * @return Код ошибки или 0
     */
    int getDepartment();
    
    /**
     * 
     * @param dep 
     * @see #getDepartment() 
     */
    void setDepartment(int dep);    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="discountOnCheck : double - Скидка на чек">
    /**
     * Скидка на чек.
     * <p>
     * Диапазон значений: 0…99,99.
     * <p>
     * Используется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument FNCloseCheckEx .
     * <p>
     * [id(0x0000007e), propget, helpstring("СкидкаНаЧек")] double DiscountOnCheck();
     * <p>
     * [id(0x0000007e), propput, helpstring("СкидкаНаЧек")] void DiscountOnCheck([in] double rhs);
     *
     * @return --
     */
    double getDiscountOnCheck();
    
    /**
     * 
     * @param v 
     * @see #getDiscountOnCheck() 
     */
    void setDiscountOnCheck(double v);    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRAdvancedMode - Подрежим ККМ">
    /**
     * Подрежим ККМ – одно из подсостояний ККМ, в котором она может находиться.
     * <p>
     * Подрежимы
     * предназначены для корректного завершения операций при печати документов в случае нештатных
     * ситуаций, таких как обрыв чековой ленты или ленты операционного журнала, выключение
     * питания во время печати документа. Переход от подрежима к подрежиму производится
     * автоматически при вызове того или иного метода (см. описания методов).
     * Номера и назначение подрежимов:
     * <p>
     * Подрежим ККМ Описание
     * <ul>
     * <li> 0 Бумага есть – ККТ не в фазе печати операции – может принимать от хоста
     * команды, связанные с печатью на том ленте, датчик которой сообщает о
     * наличии бумаги.
     * <li> 1 Пассивное отсутствие бумаги – ККМ не в фазе печати операции – не
     * принимает от хоста команды, связанные с печатью на том ленте, датчик
     * которой сообщает об отсутствии бумаги.
     * <li> 2 Активное отсутствие бумаги – ККМ в фазе печати операции – принимает
     * только команды, не связанные с печатью. Переход из этого подрежима
     * только в подрежим 3.
     * <li> 3 После активного отсутствия бумаги – ККМ ждет команду продолжения
     * печати. Кроме этого принимает команды, не связанные с печатью.
     * <li> 4 Фаза печати операции длинного отчета (полные фискальные отчеты, полные
     * отчеты ЭКЛЗ, печать контрольных лент из ЭКЛЗ) – ККМ не принимает от
     * хоста команды, связанные с печатью, кроме команды прерывания печати.
     * <li> 5 Фаза печати операции – ККМ не принимает от хоста команды, связанные с
     * печатью.
     * </ul>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x00000084), propget, helpstring("ПодрежимККМ")]
     * long ECRAdvancedMode();
     */
    int getECRAdvancedMode();

    /**
     * Свойство содержит строку с описанием на русском языке подрежима ККМ.
     * <p>
     * (см. столбец «Описание подрежима ККМ» в описании свойства ECRAdvancedMode).
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x00000085), propget, helpstring("ОписаниеПодрежимаККМ")] BSTR ECRAdvancedModeDescription();
     *
     * @return
     */
    String getECRAdvancedModeDescription();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRBuild : int - СборкаККМ">
    /**
     * СборкаККМ.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Номер сборки ПО ККМ.
     * <p>
     * Диапазон значений: 0..65535.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x00000086), propget, helpstring("СборкаККМ")]
     * long ECRBuild();
     * <p>
     */
    int getECRBuild();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRMode : int - Режим ККМ">
    /**
     * Режим ККМ – одно из состояний ККМ, в котором она может находиться.
     * <p>
     * Переход от режима к режиму производится автоматически при вызове того или иного метода (см. описания методов).
     * <p>
     * Номера и назначение режимов:
     * <p>
     * Режим ККМ Описание режима ККМ
     * <p>
     * 0 Принтер в рабочем режиме
     * <p>
     * 1 Выдача данных
     * <p>
     * 2 Открытая смена, 24 часа не кончились
     * <p>
     * 3 Открытая смена, 24 часа кончились
     * <p>
     * 4 Закрытая смена
     * <p>
     * 5 Блокировка по неправильному паролю налогового инспектора
     * <p>
     * 6 Ожидание подтверждения ввода даты
     * <p>
     * 7 Разрешение изменения положения десятичной точки
     * <p>
     * 8 Открытый документ
     * <p>
     * 9 Режим разрешения технологического обнуления
     * <p>
     * 10 Тестовый прогон
     * <p>
     * 11 Печать полного фискального отчета
     * <p>
     * 12 Печать длинного отчета ЭКЛЗ
     * <p>
     * 13 Работа с фискальным подкладным документом
     * <p>
     * 14 Печать подкладного документа
     * <p>
     * 15 Фискальный подкладной документ сформирован
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x00000089), propget, helpstring("РежимККМ")] long ECRMode();
     * <p>
     * @return --
     */
    int getECRMode();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRMode8Status : int - Статус режима 8">
    /**
     * Статус режима 8.
     * <p>
     * <p>
     * <p>
     * Находясь в режиме 8, ККМ может быть в одном из состояний:
     * <p>
     * Статус режима 8 Описание статуса режима ККМ
     * <p>
     * 0 Открыт чек продажи
     * <p>
     * 1 Открыт чек покупки
     * <p>
     * 2 Открыт чек возврата продажи
     * <p>
     * 3 Открыт чек возврата покупки
     * <p>
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * См. также: свойство ECRMode.
     * <p>
     * [id(0x0000008a), propget, helpstring("Статус8Режима")] long ECRMode8Status();
     * <p>
     * @return --
     */
    int getECRMode8Status();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRModeDescription : String - Описание режима ККМ">
    /**
     * ECRModeDescription.
     * <p>
     * Свойство содержит строку с описанием на русском языке режима ККМ (см. столбцы
     * <p>
     * «Описание статуса режима ККМ» в описании свойств ECRMode и ECRModeStatus).
     * <p>
     * Может использоваться вместо свойства ECRModeDescription, так как является его
     * <p>
     * «расширенной» версией для описания статуса не только 8-го режима, но и всех остальных.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * <p>
     * [id(0x0000008b), propget, helpstring("ОписаниеРежимаККМ")] BSTR ECRModeDescription();
     * <p>
     */
    String getECRModeDescription();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ECRSoftDate : Date - Дата ПО ККМ.">
    /**
     * Дата ПО ККМ.
     * <p>
     * Тип: Date / Дата (свойство доступно только для чтения)
     * <p>
     * Дата внутреннего программного обеспечения ККМ.
     * <p>
     * [id(0x0000008d), propget, helpstring("ДатаПОККМ")]
     * DATE ECRSoftDate();
     * <p>
     */
    Date getECRSoftDate();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getECRStatus() - ПолучитьСостояниеККМ">
    /**
     * ПолучитьСостояниеККМ.
     * <p>
     * Метод запрашивает состояние ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * После успешного выполнения команды заполняются свойства, указанные в таблице
     * «Модифицируемые свойства». В свойстве OperatorNumber возвращается порядковый номер
     * оператора, чей пароль был введен.
     * <p>
     * Метод может вызываться в любом режиме, кроме режима 1 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * <li> ECRSoftVersion Версия внутреннего программного обеспечения ККМ.
     * <li> ECRBuild Номер сборки ПО ККМ
     * <li> ECRSoftDate Дата внутреннего программного обеспечения ККМ.
     * <li> LogicalNumber Логический номер ККМ в торговом зале (внутренняя таблица ККМ номер 1, ряд 1, поле 1).
     * <li> OpenDocumentNumber Сквозной номер последнего документа ККМ.
     * <li> ECRFlags Признаки (флаги) ККМ (раскладывается в следующее битовое поле)
     * <li> ReceiptRibbonIsPresent Признак наличия в ККМ рулона чековой ленты. FALSE – рулона чековой ленты нет, TRUE –
     * рулон чековой ленты есть.
     * <li> JournalRibbonIsPresent Признак наличия в ККМ рулона операционного журнала. FALSE – рулона операционного
     * журнала нет, TRUE – рулон есть
     * <li> SKNOStatus Последний статус СКНО (Для белорусских ККТ).
     * <li> SlipDocumentIsPresent Признак наличия в ККМ подкладного документа. FALSE – подкладного документа нет, TRUE –
     * подкладной документ есть.
     * <li> SlipDocumentIsMoving Признак прохождения подкладного документа под датчиком контроля подкладного документа.
     * FALSE – подкладной документ
     * отсутствует под датчиком контроля подкладного документа, TRUE – подкладной документ проходит под датчиком.
     * <li> PointPosition Признак положения десятичной точки. FALSE – десятичная точка отделяет 0 разрядов, TRUE
     * – десятичная точка отделяет 2 разряда.
     * <li> EKLZIsPresent Признак наличия в ККМ ЭКЛЗ. FALSE - ЭКЛЗ нет, TRUE – ЭКЛЗ есть.
     * <li> JournalRibbonOpticalSensor Признак прохождения ленты операционного журнала под оптическим датчиком
     * операционного журнала. FALSE – ленты операционного журнала нет под оптическим датчиком; TRUE – лента
     * операционного
     * журнала проходит под оптическим датчиком.
     * <li> ReceiptRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE –
     * чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li> JournalRibbonLever Признак положения рычага термоголовки ленты операционного журнала TRUE – рычаг
     * термоголовки ленты операционного журнала поднят; FALSE – рычаг термоголовки ленты опущен.
     * <li> ReceiptRibbonLever Признак положения рычага термоголовки чековой ленты. TRUE – рычаг термоголовки
     * чековой ленты поднят; FALSE – рычаг термоголовки чековой ленты опущен.
     * <li> LidPositionSensor Признак положения крышки корпуса. TRUE – крышка корпуса не установлена; FALSE –
     * <li> IsPrinterLeftSensorFailure Признак отказа левого датчика печатающего механизма. FALSE – отказа датчика нет,
     * TRUE
     * – имеет место отказ датчика.
     * <li> IsPrinterRightSensorFailure Признак отказа правого датчика печатающего механизма. FALSE – отказа датчика
     * нет, TRUE
     * – имеет место отказ датчика.
     * <li> IsDrawerOpen Признак состояния денежного ящика. TRUE – денежный ящик открыт; FALSE – денежный ящик закрыт
     * <li> IsEKLZOverflow Признак состояния ЭКЛЗ. TRUE – ЭКЛЗ близка к переполнению, FALSE – ЭКЛЗ ещё не близка к
     * переполнению.
     * <li> QuantityPointPosition Признак положения десятичной точки в количестве товара. TRUE – 3 знака после
     * запятой; FALSE – 6 знаков.
     * <li> ECRMode Режим ККМ, т.е. одно из состояний ККМ, в котором она может находиться (расшифровку
     * режимов смотри в описании свойства)
     * <li> ECRModeDescription Свойство содержит строку с описанием на русском языке режима ККМ (см. столбец
     * «Описание режима ККМ» в свойстве ECRMode).
     * <li> ECRMode8Status Целое Одно из состояний, когда ККМ находится в режиме 8:
     * <li> ECRModeStatus Целое Одно из состояний, когда ККМ находится в
     * режимах 13 и 14.
     * <li>
     * ECRAdvancedMode Целое
     * Подрежим ККМ – одно из подсостояний ККМ,
     * в котором она может находиться. Подрежимы
     * предназначены для корректного завершения
     * операций при печати документов в случае
     * нештатных ситуаций.
     * <li>
     * ECRAdvancedModeDescription
     * Свойство содержит строку с описанием на
     * русском языке подрежима ККМ (см. столбец
     * «Описание подрежима ККМ» в свойстве
     * ECRAdvancedMode).
     * <li>
     * PortNumber Порт ККМ, через который она подключена к
     * ПК (0 – порт 1, 1 – порт 2, 2 – порт 3 и т.д.).
     * <li>
     * FMSoftVersion Версия внутреннего программного
     * обеспечения ФП ККМ.
     * <li>
     * FMBuild Номер сборки ПО ФП ККМ. 219
     * <li>
     * FMSoftDate
     * Дата внутреннего программного обеспечения
     * ККМ.
     * <li>
     * Date Внутренняя дата ККМ. 205
     * <li>
     * Time Внутренне время ККМ. 291
     * <li>
     * TimeStr Строковое представление свойства Time. 292
     * <li>
     * FMFlags
     * Признаки (флаги) ФП ККМ (раскладывается в
     * битовое поле)
     * <li>
     * FM1IsPresent
     * Признак наличия в ККМ ФП1. FALSE – ФП1
     * нет, TRUE – ФП1 есть.
     * <li>
     * FM2IsPresent
     * Признак наличия в ККМ ФП2. FALSE – ФП2
     * нет, TRUE – ФП2 есть
     * <li>
     * LicenseIsPresent
     * Признак наличия в ККМ лицензии. FALSE –
     * лицензия не введена, TRUE – лицензия
     * введена.
     * <li>
     * FMOverflow
     * Признак переполнения ФП. FALSE –
     * переполнения ФП нет, TRUE – переполнение
     * <li>
     * IsBatteryLow
     * Признак напряжения на батарее. TRUE –
     * напряжение пониженное; FALSE – напряжение
     * нормальное.
     * <li>
     * IsLastFMRecordCorrupted
     * Признак испорченности последней записи в
     * ФП. TRUE – последняя запись в ФП
     * испорчена; FALSE – не испорчена.
     * <li>
     * IsFMSessionOpen
     * Признак открытой смены в ФП. TRUE – смена
     * в ФП открыта; FALSE – закрыта.
     * <li>
     * IsFM24HoursOver
     * Признак истечения 24 часов в ФП. TRUE – 24
     * часа в ФП истекли; FALSE – не истекли.
     * <li>
     * SerialNumber
     * Серийный номер ККМ, строка, содержащая
     * номер (WIN1251-коды цифр). Если номер на
     * ККМ не введен, то строка содержит «не
     * введен» .
     * <li>
     * SessionNumber Номер последней закрытой на ККМ смены 265
     * <li>
     * FreeRecordInFM Целое 0..2100 R Количество свободных записей в ФП. 224
     * <li>
     * RegistrationNumber Целое 0..16 RW
     * Количество перерегистраций (фискализаций),
     * проведенных на ККМ.
     * <li>
     * FreeRegistration Количество оставшихся перерегистраций
     * (фискализаций), которые можно произвести на
     * ККМ.
     * <li>
     * INN Текстовый параметр, содержащий
     * идентификационный номер
     * налогоплательщика. Допустимы только
     * символы «0», «1», «2», «3», «4», «5», «6», «7»,
     * «8» и «9».
     * </ul>
     * [id(0x00000027), helpstring("ПолучитьСостояниеККМ")] long GetECRStatus();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")
        , @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "ECRBuild")
        , @DriverProperty(name = "logicalNumber")
        , @DriverProperty(name = "openDocumentNumber")
        , @DriverProperty(name = "ECRFlags")
        , @DriverProperty(name = "receiptRibbonIsPresent")
        , @DriverProperty(name = "journalRibbonIsPresent")
        , @DriverProperty(name = "SKNOStatus")
        , @DriverProperty(name = "slipDocumentIsPresent")
        , @DriverProperty(name = "slipDocumentIsMoving")
        , @DriverProperty(name = "pointPosition")
        , @DriverProperty(name = "EKLZIsPresent")
        , @DriverProperty(name = "journalRibbonOpticalSensor")
        , @DriverProperty(name = "receiptRibbonOpticalSensor")
        , @DriverProperty(name = "journalRibbonLever")
        , @DriverProperty(name = "receiptRibbonLever")
        , @DriverProperty(name = "lidPositionSensor")
        , @DriverProperty(name = "printerLeftSensorFailure")
        , @DriverProperty(name = "printerRightSensorFailure")
        , @DriverProperty(name = "drawerOpen")
        , @DriverProperty(name = "EKLZOverflow")
        , @DriverProperty(name = "quantityPointPosition")
        , @DriverProperty(name = "ECRMode")
        , @DriverProperty(name = "ECRModeDescription")
        , @DriverProperty(name = "ECRMode8Status")
        , @DriverProperty(name = "ECRModeStatus")
        , @DriverProperty(name = "ECRAdvancedMode")
        , @DriverProperty(name = "ECRAdvancedModeDescription")
        , @DriverProperty(name = "portNumber")
        , @DriverProperty(name = "FMSoftVersion")
        , @DriverProperty(name = "FMBuild")
        , @DriverProperty(name = "FMSoftDate")
        , @DriverProperty(name = "date")
        , @DriverProperty(name = "time")
        , @DriverProperty(name = "timeStr")
        , @DriverProperty(name = "FMFlags")
        , @DriverProperty(name = "FM1IsPresent")
        , @DriverProperty(name = "FM2IsPresent")
        , @DriverProperty(name = "licenseIsPresent")
        , @DriverProperty(name = "FMOverflow")
        , @DriverProperty(name = "batteryLow")
        , @DriverProperty(name = "lastFMRecordCorrupted")
        , @DriverProperty(name = "FMSessionOpen")
        , @DriverProperty(name = "FM24HoursOver")
        , @DriverProperty(name = "serialNumber")
        , @DriverProperty(name = "sessionNumber")
        , @DriverProperty(name = "freeRecordInFM")
        , @DriverProperty(name = "registrationNumber")
        , @DriverProperty(name = "freeRegistration")})
    @CallState
    int getECRStatus();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="emergencyStopCode : int - Код аврийной остановки.">
    /**
     * Код аврийной остановки.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * Код ошибки при аварийной остановке РК:
     * <ul>
     * <li>0 аварийной остановки нет
     * <li>1 внутренняя ошибка контроллера
     * <li>2 обратное вращение датчика
     * <li>3 обрыв фаз датчика объема
     * <li>4 обрыв цепи управления пускателя
     * <li>5 обрыв цепи управления основным клапаном
     * <li>6 обрыв цепи управления клапаном снижения
     * <li>255 неисправность оборудования
     * </ul>
     * Модифицируется методом: GetRKStatus.
     * См. также: метод GetRKStatus.
     * <p>
     * [id(0x00000090), propget, helpstring("КодАврийнойОстановки")] long EmergencyStopCode();
     *
     * @return --
     */
    int getEmergencyStopCode();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="emergencyStopCodeDescription : String - Описание кода аварийонй остановки.">
    /**
     * Описание кода аварийонй остановки.
     * <p>
     * Описание кода ошибки при аварийной остановке РК. См. колонку «Описание кода ошибки при
     * аварийной остановке РК» в таблице в описании свойства EmergencyStopCode.
     * Модифицируется методом UGetRKStatusU.
     * См. также: метод GetRKStatus.
     * <p>
     * [id(0x00000091), propget, helpstring("ОписаниеКодаАварийонйОстановки")] BSTR EmergencyStopCodeDescription();
     *
     * @return
     */
    String getEmergencyStopCodeDescription();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMFlags : int - Флаги ФП.">
    /**
     * Флаги ФП.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Признаки (флаги) ФП ККМ. Раскладывается в следующее битовое поле:
     * <ul>
     * <li> 0 бит: признак наличия ФП 1 (см. свойство FM1IsPresent);
     * <li> 1 бит: признак наличия ФП 2 (см. свойство FM2IsPresent);
     * <li> 2 бит: признак введённой лицензии (см. свойство LicenseIsPresent);
     * <li> 3 бит: признак переполнения ФП (см. свойство FMOverflow).
     * <li> 4 бит: признак пониженного напряжения на батарейке ФП (см. свойство IsBatteryLow).
     * <li> 5 бит: признак испорченности последней записи ФП (см. свойство IsLastFMRecordCorrupted).
     * <li> 6 бит: признак того, что последняя запись в ФП испорчена (см. свойство IsFMSessionOpen).
     * <li> 7 бит: признак того, что 24 часа в ФП истекли (см. свойство IsFM24HoursOver).
     * </ul>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x0000009c), propget, helpstring("ФлагиФП")]
     * long FMFlags();
     */
    int getFMFlags();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMFlagsEx : int - Расширенные флаги ФП.">
    /**
     * Расширенные флагиФП.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Расширенные флагиФП.
     * <p>
     * Битовое поле.
     * <ul>
     * <li> Бит 1: АСПД режим (0 - нет, 1 - есть);
     * <li> Бит 2: Блокировка ККТ по неверному паролю НИ (0 - нет, 1 - есть);
     * <li> Бит 4: Имеется 3 или более поврежденных записей сменных итогов (0 - нет, 1 - есть);
     * <li> Бит 5: Повреждена запись фискализации, активизации ЭКЛЗ или заводского номера (0 - нет, 1 -
     * есть);
     * <li> Бит 7: Последняя запись в накопителе ФП (0 - фискализации/активизации ЭКЛЗ, 1 - сменного
     * итога);
     * </ul>
     * Модифицируется методом GetECRStatus (Для моделей, поддерживающих протокол Кассового
     * Ядра).
     * <p>
     * [id(0x000007c5), propget, helpstring("ФлагиФПДоп")]
     * long FMFlagsEx();
     */
    int getFMFlagsEx();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMMode : int - Режим ФП.">
    /**
     * Режим ФП.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Режим ФП
     * <p>
     * Возможные значения:
     * <ul>
     * <li> 1 – Выдача данных оперативной памяти ФП;
     * <li> 2 – Выдача данных накопителя ФП;
     * <li> 3 – Выдача данных полного фискального отчета;
     * <li> 4 – Нормальное состояние ФП;
     * <li> 5 – Выдача данных памяти программ ФП;
     * <li> 9 – Начальная инициализация ОЗУ ФП (тех. обнуление).
     * </ul>
     * Модифицируется методом GetECRStatus (Для моделей, поддерживающих протокол Кассового
     * Ядра).
     * <p>
     * [id(0x000007c7), propget, helpstring("РежимФП")]
     * long FMMode();
     */
    int getFMMode();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMResultCode : int - Код ошибки ФП">
    /**
     * Код ошибки ФП .
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Свойство содержит код ошибки, возвращаемый ФП в результате выполнения последней операции.
     * <p>
     * Если ошибки не произошло, то значение данного свойства устанавливается в 0 (Ошибок нет).
     * <p>
     * Модифицируется методом GetShortECRStatus.
     * <p>
     * [id(0x0000011f), propget, helpstring("КодОшибкиФП")]
     * long FMResultCode();
     */
    int getFMResultCode();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMSoftDate : Date - Дата ПО ФП.">
    /**
     * Дата ПО ФП.
     * <p>
     * Тип: Date / Дата (свойство доступно только для чтения)
     * <p>
     * Дата внутреннего программного обеспечения ККМ.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x0000009e), propget, helpstring("ДатаПОФП")]
     * DATE FMSoftDate();
     */
    Date getFMSoftDate();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMSoftVersion : String - Версия ПО ФП">
    /**
     * Версия ПО ФП.
     * <p>
     * Тип: WideString / Строка (свойство доступно только для чтения)
     * <p>
     * Версия внутреннего программного обеспечения ФП ККМ.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x0000009f), propget, helpstring("ВерсияПОФП")]
     * BSTR FMSoftVersion();
     */
    String getFMSoftVersion();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMStringNumber : int - Номер строки фиск логотипа ПД.">
    /**
     * Номер строки фиск логотипа ПД.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * В свойстве указывается номер строки подкладного документа, которой будет печататься
     * фискальный логотип ККМ.
     * <p>
     * Используется методом OpenFiscalSlipDocument.
     * <p>
     * [id(0x0000013f), propget, helpstring("НомерСтрокиФискЛоготипаПД")]
     * long FMStringNumber();
     * <p>
     * [id(0x0000013f), propput, helpstring("НомерСтрокиФискЛоготипаПД")]
     * void FMStringNumber([in] long rhs);
     */
    int getFMStringNumber();
    
    /**
     * 
     * @param v 
     * @see #getFMStringNumber() 
     */
    void setFMStringNumber(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FNCurrentDocument : int - ФН текущий документ">
    /**
     * ФН текущий документ.
     * <p>
     * Тип: Integer / Целое
     * Доступ: RW
     * Текущий документ ФН
     * Возможные значения свойства:
     * <ul>
     * <li> 00h – нет открытого документа;
     * <li> 01h – отчёт о фискализации;
     * <li> 02h – отчёт об открытии смены;
     * <li> 04h – кассовый чек;
     * <li> 08h – отчёт о закрытии смены;
     * <li> 10h – отчёт о закрытии фискального режима/
     * </ul>
     * Модифицируется методами: FNGetStatus
     * <p>
     * [id(0x00000835), propget, helpstring("ФНТекущийДокумент")]
     * long FNCurrentDocument();
     * <p>
     * [id(0x00000835), propput, helpstring("ФНТекущийДокумент")]
     * void FNCurrentDocument([in] long rhs);
     */
    int getFNCurrentDocument();
    
    /**
     * 
     * @param v 
     * @see #getFNCurrentDocument() 
     */
    void setFNCurrentDocument(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="freeRecordInFM : int - Свободных записей в ФП">
    /**
     * Свободных записей в ФП.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Количество свободных записей в ФП.
     * <p>
     * Диапазон значений: 0..2100.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000000a0), propget, helpstring("СвободныхЗаписейВФП")]
     * long FreeRecordInFM();
     */
    int getFreeRecordInFM();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="freeRegistration : int - Осталось перерегистраций">
    /**
     * Осталось перерегистраций.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Количество оставшихся перерегистраций (фискализаций), которые можно произвести на ККМ.
     * <p>
     * Диапазон значений: 0..16.
     * <p>
     * Модифицируется методами GetECRStatus, Fiscalization, FNGetExpirationTime.
     * <p>
     * [id(0x000000a1), propget, helpstring("ОсталосьПеререгистраций")]
     * long FreeRegistration();
     */
    int getFreeRegistration();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="INN : String - ИНН">
    /**
     * ИНН.
     * <p>
     * Тип: WideString / Строка
     * <p>
     * Текстовый параметр, содержащий идентификационный номер налогоплательщика. Допустимая
     * длина строки: до 12 символов. Допустимы только символы «0», «1», «2», «3», «4», «5», «6», «7»,
     * «8» и «9». Если строка короче 12 символов, то она дополняется символами «0» слева до 12
     * символов.
     * <p>
     * Используется методами Fiscalization, FNBuildRegistrationReport, FNBuildReregistrationReport.
     * Модифицируется методами GetFiscalizationParameters, GetECRStatus, FNFindDocument,
     * FNFindDocument, FNGetFiscalizationResult.
     * <p>
     * См. также: методы: MFPGetPrepareActivizationResult, MFPPrepareActivization.
     * <p>
     * [id(0x000000a2), propget, helpstring("ИНН")]
     * BSTR INN();
     * <p>
     * [id(0x000000a2), propput, helpstring("ИНН")]
     * void INN([in] BSTR rhs);
     */
    String getINN();
    
    /**
     * 
     * @param str 
     * @see #getINN() 
     */
    void setINN(String str);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="license : String - Текстовый параметр, содержащий лицензию">
    /**
     * Текстовый параметр, содержащий лицензию (см. «Инструкцию по эксплуатации»/«Руководство
     * оператора»). Допустимая длина строки: до 5 символов. Допустимы только символы «0», «1», «2»,
     * «3», «4», «5», «6», «7», «8» и «9».
     * Используется методом WriteLicense . Модифицируется методом ReadLicense.
     * <p>
     * [id(0x000000ae), propget, helpstring("Лицензия")]
     * BSTR License();
     * <p>
     * [id(0x000000ae), propput, helpstring("Лицензия")]
     * void License([in] BSTR rhs);
     *
     * @return
     */
    String getLicense();
    
    /**
     * 
     * @param str 
     * @see #getLicense() 
     */
    void setLicense(String str);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="logicalNumber : int - Номер В Зале.">
    /**
     * Номер В Зале.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Логический номер ККМ в торговом зале (внутренняя таблица ККМ номер 1, ряд 1, поле 1).
     * <p>
     * Диапазон значений: 1..99.
     * <p>
     * Модифицируется методом GetECRStatus.
     */
    int getLogicalNumber();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openDocumentNumber : int - Сквозной Номер Документа">
    /**
     * Сквозной Номер Документа.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Сквозной номер последнего документа ККМ.
     * <p>
     * Диапазон значений: 0..9999.
     * <p>
     * Используется методами CashIncome и CashOutcome.
     * Модифицируется методами GetECRStatus, OpenFiscalSlipDocument, и
     * OpenStandardFiscalSlipDocument.
     */
    int getOpenDocumentNumber();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="operatorNumber : int - Номер Оператора.">
    /**
     * Номер Оператора.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * Порядковый номер оператора, чей пароль был введен.
     * Диапазон значений: 1..30.
     * <p>
     * Модифицируется всеми методами, в которых используется пароль оператора.
     * <p>
     * [id(0x000000bb), propget, helpstring("НомерОператора")]
     * long OperatorNumber();
     */
    int getOperatorNumber();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="password : long - Пароль">
    //[id(0x000000bc), propget, helpstring("Пароль")] long Password();
    //[id(0x000000bc), propput, helpstring("Пароль")] void Password([in] long rhs);
    /**
     * Пароль.
     * Пароль для исполнения метода драйвера.
     * Допустимая длина: до 8 разрядов
     *
     * @return Пароль
     */
    int getPassword();
    
    /**
     * Пароль
     *
     * @param pswd Пароль
     * @see #getPassword() 
     */
    void setPassword(int pswd);    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="pointPosition : boolean - Положение десятичной точки">
    /**
     * ПоложениеТочки.
     * <p>
     * Признак положения десятичной точки.
     * <p>
     * FALSE – десятичная точка отделяет 0 разрядов,
     * <p>
     * TRUE –
     * десятичная точка отделяет 2 разряда.
     * <p>
     * Модифицируется методами GetECRStatus и
     * GetShortECRStatus.
     * <p>
     * Используется методом SetPointPosition.
     * <p>
     * [id(0x000000be), propget, helpstring("ПоложениеТочки")]
     * VARIANT_BOOL PointPosition();
     * <p>
     * [id(0x000000be), propput, helpstring("ПоложениеТочки")]
     * void PointPosition([in] VARIANT_BOOL rhs);
     * <p>
     */
    boolean isPointPosition();

    /**
     * 
     * @param p 
     * @see #isPointPosition() 
     */
    void setPointPosition(boolean p);
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="portNumber : int - Номер Порта.">
    /**
     * НомерПорта.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * В методах GetECRStatus, SetExchangeParam и GetExchangeParam это свойство обозначает порт ККМ, через который она
     * подключена к ПК или какому-либо другому устройству.
     * <p>
     * Диапазон значений: 0..255 (0 – порт 1, 1 – порт 2, 2 – порт 3 и т.д.).
     * <p>
     * Методы SetExchangeParam и GetExchangeParam используют данное свойство, а метод GetECRStatus модифицирует его.
     * <p>
     * [id(0x000000bf), propget, helpstring("НомерПорта")] long PortNumber();
     * <p>
     * [id(0x000000bf), propput, helpstring("НомерПорта")] void PortNumber([in] long rhs);
     */
    int getPortNumber();

    /**
     * 
     * @param v 
     * @see #getPortNumber() 
     */
    void setPortNumber(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="price : Currency - Цена за единицу товара">
    /**
     * Цена за единицу товара.
     * <p>
     * Используется методами Sale, SaleEx, Buy, BuyEx, ReturnSale, ReturnSaleEx, ReturnBuy,
     * ReturnBuyEx, Storno, StornoEx, SetRKParameters, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, FNDiscountOperation.
     * <p>
     * Модифицируется методом GetRKStatus, FNStorno.
     * <p>
     * [id(0x000000c0), propget, helpstring("Цена")] CURRENCY Price();
     * <p>
     * [id(0x000000c0), propput, helpstring("Цена")] void Price([in] CURRENCY rhs);
     *
     * @return --
     */
    Currency getPrice();

    /**
     * 
     * @param cur 
     * @see #getPrice() 
     */
    void setPrice(Currency cur);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="protocolType : int - Тип протокола">
    /**
     * ProtocolType Целое 0..1 RW Тип протокола (0-стандартный, 1-протокол ККТ 2.0).
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Используется методами ChangeProtocol, Connect.
     * <p>
     * [id(0x000007f4), propget, helpstring("ТипПротокола")] long ProtocolType();
     * <p>
     * [id(0x000007f4), propput, helpstring("ТипПротокола")] void ProtocolType([in] long rhs);
     * <p>
     */
    int getProtocolType();
    
    /**
     * 
     * @param ptype 
     * @see #getProtocolType() 
     */
    void setProtocolType(int ptype);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="quantity : double - Количество  товара">
    /**
     * Количество товара.
     * <p>
     * Используется методами Sale, Buy, ReturnSale, ReturnBuy, Storno,
     * RegistrationOnSlipDocument, StandardRegistrationOnSlipDocument, FNDiscountOperation,
     * FNStorno (диапазон значений от 0,001 до 9999999,999, то есть округляется до трёх знаков после
     * запятой), а также методами SaleEx, BuyEx, ReturnSaleEx, ReturnBuyEx, StornoEx
     * (диапазон значений от 0,000001 до 9999999,999999, то есть округляется до шести знаков после запятой).
     * <p>
     * [id(0x000000c1), propget, helpstring("Количество")] double Quantity();
     * <p>
     * [id(0x000000c1), propput, helpstring("Количество")] void Quantity([in] double rhs);
     *
     * @return --
     */
    double getQuantity();
    
    /**
     * 
     * @param v 
     * @see #getQuantity() 
     */
    void setQuantity(double v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="quantityStringNumber : int - Номер строки количества на цену ПД.">
    /**
     * Номер строки количества на цену ПД.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * В свойстве указывается номер строки в операции на подкладном документе, в которой будет
     * печататься произведение количества товара на цену за единицу товара.
     * Диапазон значений: 0..3 (если значение свойства равно «0», то текстовая строка не печатается).
     * <p>
     * Используется методом RegistrationOnSlipDocument, ChargeOnSlipDocument.
     * <p>
     * [id(0x00000149), propget, helpstring("НомерСтрокиКоличестваНаЦенуПД")]
     * long QuantityStringNumber();
     */
    int getQuantityStringNumber();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="receiptRibbonIsPresent : boolean - Рулон Чековой Ленты Есть">
    /**
     * РулонЧековойЛентыЕсть.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак наличия в ККМ рулона чековой ленты. FALSE – рулона чековой ленты нет, TRUE –
     * рулон чековой ленты есть.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     */
    boolean isReceiptRibbonIsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="registrationNumber : int - Количество перерегистраций">
    /**
     * Количество перерегистраций.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Количество перерегистраций (фискализаций), проведенных на ККМ.
     * <p>
     * Диапазон значений: 0..16.
     * <p>
     * Используется методом GetFiscalizationParameters.
     * <p>
     * Модифицируется методами GetECRStatus, Fiscalization, FNGetExpirationTime.
     * <p>
     * [id(0x000000c7), propget, helpstring("КоличествоПеререгистраций")]
     * long RegistrationNumber();
     * <p>
     * [id(0x000000c7), propput, helpstring("КоличествоПеререгистраций")]
     * void RegistrationNumber([in] long rhs);
     */
    int getRegistrationNumber();
    
    /**
     * 
     * @param v 
     * @see #getRegistrationNumber() 
     */
    void setRegistrationNumber(int v);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resultCode : int - Результат">
    /**
     * Результат
     *
     * @return Результат
     */
    int getResultCode();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resultCodeDescription : String - Описание Результата">
    /**
     * Описание Результата
     *
     * @return Описание Результата
     */
    String getResultCodeDescription();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SKNOStatus : int - Статус СКНО">
    /**
     * Статус СКНО.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Модифицируется методами GetECRStatus, GetEKLZCode1Report, GetEKLZCode2Report,
     * GetEKLZCode3Report.
     * <p>
     * Возможные значения:
     * <p>
     * 0000…0FFFh (FFFh – некорректный статус)
     * <p>
     * Битовое поле (назначение бит):
     * <ul>
     * <li> 0 – Занят «1»/свободен «0»;
     * <li> 1 – СКЗИ, е6сть «1»/нет «0»;
     * <li> 2 – Соединение с сервером, есть «1»/нет «0»;
     * <li> 3 – Запрет обслуживания по окончанию сертификата СКЗИ, есть «1»/нет «0»;
     * <li> 4 – Запрет обслуживания по не переданным суточным (сменным) отчетам (Z-отчетам), есть
     * «1»/нет «0»;
     * <li> 5 – Запрет обслуживания по переполнению памяти СКНО, есть «1»/нет «0»;
     * <li> 6 – Идентификация прошла успешно, да «1»/нет «0»;
     * <li> 7 – Смена открыта, да «1»/нет «0»;
     * <li> 8 – Не завершена процедура по переданному документу, да «1»/нет «0»;
     * <li> 9 – Наличие в памяти СКНО не переданных документов да «1»/нет «0»;
     * <li> 10 – Превышен максимальный размер электронного кассового документа да «1»/нет «0»;
     * <li> 11 – СКНО исправно да «1»/нет «0»;
     * <li> 12…15- Зарезервировано (Всегда «0»).
     * </ul>
     * [id(0x0000082d), propget, helpstring("СтатусСКНО")]
     * long SKNOStatus();
     * <p>
     * [id(0x0000082d), propput, helpstring("СтатусСКНО")]
     * void SKNOStatus([in] long rhs);
     */
    int getSKNOStatus();

    /**
     * 
     * @param v 
     * @see #getSKNOStatus() 
     */
    void setSKNOStatus(int v);
    //</editor-fold>

    
    //<editor-fold defaultstate="collapsed" desc="sessionNumber : int - Номер смены">
    /**
     * Номер смены.
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Номер последней закрытой на ККМ смены (в случае, когда свойство модифицируется методами
     * GetECRStatus, GetFiscalizationParameters, FNCloseSession, FNFindDocument,
     * FNGetCurrentSessionParams) и номер некой закрытой смены (в случае, когда используется
     * методами EKLZJournalOnSessionNumber, ReadEKLZSessionTotal).
     * <p>
     * Когда модифицируется
     * методами GetEKLZCode2Report и FNOpenSession – номер текущей смены.
     * <p>
     * Диапазон значений: 0..2100.
     * <p>
     * Примечание: всегда до фискализации ФП и до снятия первого суточного отчета с гашением
     * номер последней закрытой смены равен 0.
     * <p>
     * [id(0x000000d1), propget, helpstring("НомерСмены")]
     * long SessionNumber();
     */
    int getSessionNumber();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getShortECRStatus() - Метод запрашивает состояние ККМ">
    /**
     * GetShortECRStatus - Метод запрашивает состояние ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * После успешного выполнения команды заполняются свойства,
     * <p>
     * указанные в таблице «Модифицируемые свойства».
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Метод может вызываться в любом режиме, кроме режима 1 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * [id(0x00000028), helpstring("ПолучитьКороткийЗапросСостоянияККМ")] long GetShortECRStatus();
     * <p>
     * Используемые свойства: Password
     * <p>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber - Порядковый номер оператора, чей пароль был введен.
     * <li>ECRFlags Признаки (флаги) ККМ (раскладывается в следующее битовое поле)
     * <li>ReceiptRibbonIsPresent Признак наличия в ККМ рулона чековой ленты. FALSE – рулона чековой ленты нет, TRUE –
     * рулон чековой ленты есть.
     * <li>JournalRibbonIsPresent Признак наличия в ККМ рулона операционного журнала. FALSE – рулона операционного
     * журнала нет, TRUE – рулон
     * <li>SlipDocumentIsPresent Признак наличия в ККМ подкладного документа. FALSE – подкладного документа нет, TRUE –
     * подкладной документ есть.
     * <li>SlipDocumentIsMoving Признак прохождения подкладного документа под датчиком контроля подкладного документа.
     * FALSE – подкладной документ отсутствует под датчиком контроля подкладного документа, TRUE – подкладной документ
     * проходит под датчиком.
     * <li>PointPosition Признак положения десятичной точки. FALSE – десятичная точка отделяет 0 разрядов, TRUE –
     * десятичная точка отделяет 2 разряда.
     * <li>EKLZIsPresent Признак наличия в ККМ ЭКЛЗ. FALSE – ЭКЛЗ нет, TRUE – ЭКЛЗ есть.
     * <li>JournalRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE –
     * чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li>ReceiptRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE –
     * чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li>JournalRibbonLever Признак положения рычага термоголовки ленты операционного журнала TRUE – рычаг
     * термоголовки ленты операционного журнала поднят; FALSE – рычаг термоголовки ленты опущен.
     * <li>ReceiptRibbonLever Признак положения рычага термоголовки чековой ленты. TRUE – рычаг термоголовки чековой
     * ленты поднят; FALSE – рычаг термоголовки чековой ленты опущен.
     * <li>LidPositionSensor Признак положения крышки корпуса. TRUE – крышка корпуса не установлена; FALSE – крышка
     * корпуса установлена.
     * <li>IsPrinterLeftSensorFailure Признак отказа левого датчика печатающего механизма. FALSE – отказа датчика нет,
     * TRUE – имеет место отказ датчика.
     * <li>IsPrinterRightSensorFailure Признак отказа правого датчика печатающего механизма. FALSE – отказа датчика нет,
     * TRUE – имеет место отказ датчика.
     * <li>IsDrawerOpen Признак состояния денежного ящика. TRUE – денежный ящик открыт; FALSE – денежный ящик закрыт
     * <li>IsEKLZOverflow Признак состояния ЭКЛЗ. TRUE – ЭКЛЗ близка к переполнению, FALSE – ЭКЛЗ ещё не близка к
     * переполнению.
     * <li>QuantityPointPosition Признак положения десятичной точки в количестве товара. TRUE – 3 знака после запятой;
     * FALSE – 6 знаков.
     * <li>ECRMode Режим ККМ, т.е. одно из состояний ККМ, в котором она может находиться (расшифровку режимов смотри в
     * описании свойства)
     * <li>ECRModeDescription Свойство содержит строку с описанием на русском языке режима ККМ (см. столбец «Описание
     * режима ККМ» в свойстве
     * <li>ECRMode8Status Одно из состояний, когда ККМ находится в режиме 8:
     * <li>ECRModeStatus Одно из состояний, когда ККМ находится в режимах 13 и 14.
     * <li>ECRAdvancedMode Подрежим ККМ – одно из подсостояний ККМ, в котором она может находиться. Подрежимы
     * предназначены для корректного завершения операций при печати документов в случае нештатных ситуаций.
     * <li>ECRAdvancedModeDescription Свойство содержит строку с описанием на русском языке подрежима ККМ (см. столбец
     * «Описание подрежима ККМ» в свойстве ECRAdvancedMode).
     * <li>QuantityOfOperations Количество выполненных операций регистрации (продаж, покупок, возвратов продаж или
     * возвратов покупок) в чеке.
     * <li>BatteryVoltage Напряжение резервной батареи.
     * <li>PowerSourceVoltage Напряжение источника питания.
     * <li>FMResultCode Код ошибки ФП.
     * <li>EKLZResultCode Код ошибки ЭКЛЗ.
     * </ul>
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")
        , @DriverProperty(name = "ECRFlags")
        , @DriverProperty(name = "receiptRibbonIsPresent")
        , @DriverProperty(name = "journalRibbonIsPresent")
        , @DriverProperty(name = "slipDocumentIsPresent")
        , @DriverProperty(name = "slipDocumentIsMoving")
        , @DriverProperty(name = "pointPosition")
        , @DriverProperty(name = "EKLZIsPresent")
        , @DriverProperty(name = "journalRibbonOpticalSensor")
        , @DriverProperty(name = "receiptRibbonOpticalSensor")
        , @DriverProperty(name = "journalRibbonLever")
        , @DriverProperty(name = "receiptRibbonLever")
        , @DriverProperty(name = "lidPositionSensor")
        , @DriverProperty(name = "printerLeftSensorFailure")
        , @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")
        , @DriverProperty(name = "EKLZResultCode")
        , @DriverProperty(name = "FMResultCode")
        , @DriverProperty(name = "powerSourceVoltage")
        , @DriverProperty(name = "batteryVoltage")
        , @DriverProperty(name = "quantityOfOperations")
        , @DriverProperty(name = "ECRAdvancedModeDescription")
        , @DriverProperty(name = "ECRModeStatus")
        , @DriverProperty(name = "ECRMode8Status")
        , @DriverProperty(name = "ECRAdvancedMode")
        , @DriverProperty(name = "ECRModeDescription")
        , @DriverProperty(name = "ECRMode")
        , @DriverProperty(name = "quantityPointPosition")
        , @DriverProperty(name = "EKLZOverflow")
        , @DriverProperty(name = "drawerOpen")
        , @DriverProperty(name = "printerRightSensorFailure")})
    @CallState
    int getShortECRStatus();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="stringForPrinting">
    /**
     * Строка для печати.
     * <p>
     * Строка символов кодовой таблицы WIN1251 для печати.
     * <p>
     * В случае, когда свойство используется методами PrintString, PrintWideString,
     * PrintStringWithFont, в свойстве передается до 249, 249 и 248 символов соответственно.
     * <p>
     * Но количество символов, которые будут выведены на печать, зависит от модели ККМ, ширины
     * строки на ленте, параметров настроек ККМ (например, перенос длинных строк), шрифта, которым
     * печатается строка.
     * <p>
     * В методах Sale, SaleEx, Buy, BuyEx, ReturnSale, ReturnSaleEx, ReturnBuy,
     * ReturnBuyEx, Storno, StornoEx, CloseCheck, Discount, Charge, StornoDiscount, StornoCharge,
     * FNCloseCheckEx, FNDiscountOperation, FNStorno, длина строки не превышает 40 символов
     * (печатается на чеке в строке, идущей перед строкой, содержащей цену/сумму и/или количество).
     * <p>
     * При использовании методами RegistrationOnSlipDocument, StandardRegistrationOnSlipDocument,
     * ChargeOnSlipDocument, StandardChargeOnSlipDocument, DiscountOnSlipDocument,
     * StandardDiscountOnSlipDocument, CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument и
     * FillSlipDocumentWithUnfiscalInfo данное свойство заполняется символами для вывода на ПД
     * (не более 250 символов).
     * <p>
     * [id(0x000000d8), propget, helpstring("СтрокаДляПечати")] BSTR StringForPrinting();
     * <p>
     * [id(0x000000d8), propput, helpstring("СтрокаДляПечати")] void StringForPrinting([in] BSTR rhs);
     *
     * @return --
     */
    String getStringForPrinting();
    
    /**
     *
     * @param str
     * @see #getStringForPrinting()
     */
    void setStringForPrinting(String str);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="stringQuantity">
    /**
     * Количество строк.
     * <p>
     * Количество строк, на которое необходимо продвинуть документ.
     * <p>
     * Диапазон значений: 1..255 (максимальное количество строк определяется
     * размером буфера печати, но не превышает 255).
     * <p>
     * Используется методом FeedDocument.
     * <p>
     * [id(0x000000d9), propget, helpstring("КоличествоСтрок")] long StringQuantity();
     * <p>
     * [id(0x000000d9), propput, helpstring("КоличествоСтрок")] void StringQuantity([in] long rhs);
     *
     * @return --
     */
    int getStringQuantity();
    
    /**
     *
     * @param q
     * @see #getStringQuantity()
     */
    void setStringQuantity(int q);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="summ1">
    /**
     * Сумма1
     * <p>
     * Свойство, используемое для хранения различных значений денежных сумм.
     * <p>
     * Используется методами CashIncome, CashOutcome, CloseCheck, Discount, Charge,
     * StornoDiscount, StornoCharge, ChargeOnSlipDocument, StandardChargeOnSlipDocument,
     * DiscountOnSlipDocument, StandardDiscountOnSlipDocument, CloseCheckOnSlipDocument,
     * CloseCheckEx, FNBuildCorrectionReceipt,.FNCloseCheckEx.
     * Модифицируется методами GetFMRecordsSum, CheckSubTotal, GetEKLZCode2Report,
     * FNFindDocument, FNStorno.
     * <p>
     * [id(0x000000da), propget, helpstring("Сумма1")] CURRENCY Summ1();
     * <p>
     * [id(0x000000da), propput, helpstring("Сумма1")] void Summ1([in] CURRENCY rhs);
     *
     * @return --
     */
    Currency getSumm1();
    
    /**
     * Сумма1
     * @param cur Сумма1
     * @see #getSumm1()
     */
    void setSumm1(Currency cur);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="summ2">
    /**
     * Сумма2
     * <p>
     * Свойство, используемое для хранения различных значений денежных сумм.
     * <p>
     * Используется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument, CloseCheckEx, FNCloseCheckEx (в свойство записывается
     * сумма типа оплаты 2).
     * <p>
     * Модифицируется методами GetFMRecordsSum, GetEKLZCode2Report, FNStorno.
     * <p>
     * [id(0x000000db), propget, helpstring("Сумма2")] CURRENCY Summ2();
     * <p>
     * [id(0x000000db), propput, helpstring("Сумма2")] void Summ2([in] CURRENCY rhs);
     * <p>
     * @return --
     */
    Currency getSumm2();
    
    void setSumm2(Currency cur);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="summ3 : Currency - Сумма3">
    /**
     * Сумма3
     * <p>
     * Свойство, используемое для хранения различных значений денежных сумм.
     * Используется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument, CloseCheckEx FNCloseCheckEx (в свойство записывается
     * сумма типа оплаты 3).
     * <p>
     * Модифицируется методами GetFMRecordsSum, GetEKLZCode2Report.
     * <p>
     * [id(0x000000dc), propget, helpstring("Сумма3")] CURRENCY Summ3();
     * <p>
     * [id(0x000000dc), propput, helpstring("Сумма3")] void Summ3([in] CURRENCY rhs);
     * <p>
     * @return --
     */
    Currency getSumm3();
    
    /**
     * Сумма3
     * @param cur Сумма3
     * @see #getSumm3()
     */
    void setSumm3(Currency cur);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="summ4 : Currency - Сумма4">
    /**
     * Сумма4
     * <p>
     * Свойство, используемое для хранения различных значений денежных сумм.
     * Используется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument, CloseCheckEx, FNCloseCheckEx (в свойство записывается
     * сумма типа оплаты 4).
     * <p>
     * Модифицируется методами GetFMRecordsSum, GetEKLZCode2Report.
     * <p>
     * [id(0x000000dd), propget, helpstring("Сумма4")] CURRENCY Summ4();
     * <p>
     * [id(0x000000dd), propput, helpstring("Сумма4")] void Summ4([in] CURRENCY rhs);
     * <p>
     * @return --
     */
    Currency getSumm4();
    
    /**
     *
     * @param cur
     * @see #getSumm4()
     */
    void setSumm4(Currency cur);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="sysAdminPassword">
    /**
     * Значение пароля системного администратора.
     * <p>
     * По умолчанию свойство имеет значение 30.
     * <p>
     * Используется методами BuyEx, ReturnBuyEx, ReturnSaleEx, SaleEx, StornoEx.
     * <p>
     * Модифицируется методом SetActiveLD.
     * <p>
     * [id(0x00000202), propget, helpstring("ПарольСистемногоАдминистратора")] long SysAdminPassword();
     * <p>
     * [id(0x00000202), propput, helpstring("ПарольСистемногоАдминистратора")] void SysAdminPassword([in] long rhs);
     */
    int getSysAdminPassword();
    
    void setSysAdminPassword(int v);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="tax1 : int - 1-ый номер налоговой группы.">
    /**
     * 1-ый номер налоговой группы.
     * <p>
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx,
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge,
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument,
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument,
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx,
     * FNDiscountOperation, FNStorno, а так же всеми методами регистрации продаж нефтепродуктов и
     * методом.
     * <p>
     * [id(0x000000e0), propget, helpstring("Налог1")] long Tax1();
     * <p>
     * [id(0x000000e0), propput, helpstring("Налог1")] void Tax1([in] long rhs);
     *
     * @return --
     */
    int getTax1();
    
    /**
     *
     * @param tax
     * @see #getTax1()
     */
    void setTax1(int tax);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tax2 : int - 2-ый номер налоговой группы.">
    /**
     * 2-ой номер налоговой группы.
     * <p>
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx,
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge,
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument,
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument,
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же
     * всеми методами регистрации продаж нефтепродуктов и методом.
     * Диапазон значений: 0..4 (0 – нет налоговой группы).
     * <p>
     * [id(0x000000e1), propget, helpstring("Налог2")] long Tax2();
     * <p>
     * [id(0x000000e1), propput, helpstring("Налог2")] void Tax2([in] long rhs);
     *
     * @return --
     */
    int getTax2();
    
    /**
     *
     * @param tax
     * @see #getTax2()
     */
    void setTax2(int tax);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tax3 : int - 3-ый номер налоговой группы.">
    /**
     * 3-ой номер налоговой группы.
     * <p>
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx,
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge,
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument,
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument,
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же
     * всеми методами регистрации продаж нефтепродуктов и методом.
     * Диапазон значений: 0..4 (0 – нет налоговой группы).
     * <p>
     * [id(0x000000e2), propget, helpstring("Налог3")] long Tax3();
     * <p>
     * [id(0x000000e2), propput, helpstring("Налог3")] void Tax3([in] long rhs);
     *
     * @return --
     */
    int getTax3();
    
    /**
     *
     * @param tax
     * @see #getTax3()
     */
    void setTax3(int tax);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="tax4 : int - 4-ый номер налоговой группы.">
    /**
     * 4-ой номер налоговой группы.
     * <p>
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx,
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge,
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument,
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument,
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же
     * всеми методами регистрации продаж нефтепродуктов и методом.
     * Диапазон значений: 0..4 (0 – нет налоговой группы).
     * <p>
     * [id(0x000000e3), propget, helpstring("Налог4")] long Tax4();
     * <p>
     * [id(0x000000e3), propput, helpstring("Налог4")] void Tax4([in] long rhs);
     *
     * @return --
     */
    int getTax4();
    
    /**
     *
     * @param tax
     * @see #getTax4()
     */
    void setTax4(int tax);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="time">
    /**
     * Time
     * <p>
     * Внутреннее время ККМ.
     * <p>
     * Используется методом SetTime.
     * <p>
     * Модифицируется методом GetECRStatus, FNFindDocument, FNGetFiscalizationResult,
     * FNGetInfoExchangeStatus, FNGetOFDTicketByDocNumber, FNGetStatus.
     * <p>
     * <p>
     * [id(0x000000e4), propget, helpstring("Время")] DATE Time();
     * <p>
     * [id(0x000000e4), propput, helpstring("Время")] void Time([in] DATE rhs);
     *
     * @return --
     */
    Date getTime();
    
    /**
     *
     * @param date
     * @see #getTime()
     */
    void setTime(Date date);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="timeStr">
    /**
     * Строковое представление свойства Time.
     * <p>
     * Используется методом SetTime.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000000e6), propget, helpstring("ВремяСтрока")] BSTR TimeStr();
     * <p>
     * [id(0x000000e6), propput, helpstring("ВремяСтрока")] void TimeStr([in] BSTR rhs);
     * <p>
     */
    String getTimeStr();
    
    /**
     *
     * @param time
     * @see #getTimeStr()
     */
    void setTimeStr(String time);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="timeout">
    /**
     * Таймаут Приема Байта.
     * <p>
     * [id(0x000000e5), propget, helpstring("ТаймаутПриемаБайта")] long Timeout();
     * <p>
     * [id(0x000000e5), propput, helpstring("ТаймаутПриемаБайта")] void Timeout([in] long rhs);
     * <p>
     * <p>
     * Тип: Integer / Целое
     * <p>
     * Тайм-аут приема байта.
     * Тайм-аут приема байта нелинейный.
     * Диапазон допустимых значений [0…255] распадается на три диапазона:
     * <p>
     * в диапазоне [0…150] каждая единица соответствует 1 мс, т.е. данным диапазоном задаются значения тайм-аута от 0 до
     * 150 мс;
     * <p>
     * в диапазоне [151…249] каждая единица соответствует 150 мс,
     * т.е. данным диапазоном задаются значения тайм-аута от 300 мс до 15 сек;
     * <p>
     * в диапазоне [250…255] каждая единица соответствует 15 сек,
     * т.е. данным диапазоном задаются значения тайм-аута от 30 сек до 105 сек.
     * <p>
     * Методы SetExchangeParam и Connect используют данное свойство, а метод GetExchangeParam модифицирует его.
     *
     * @return ---
     */
    int getTimeout();
    
    /**
     *
     * @param timeout
     * @see #getTimeout()
     */
    void setTimeout(int timeout);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="waitForPrintingDelay">
    /**
     * WaitForPrintingDelay.
     * <p>
     * Задержка в мс, использующаяся в методе WaitForPrinting
     * <p>
     * Значение по умолчанию: 1000.
     * Задержка в мс, использующаяся в методе WaitForPrinting .
     * <p>
     * [id(0x00000284), propget, helpstring("ЗадержкаОжиданияПечати")]
     * long WaitForPrintingDelay();
     * <p>
     * [id(0x00000284), propput, helpstring("ЗадержкаОжиданияПечати")]
     * void WaitForPrintingDelay([in] long rhs);
     * <p>
     */
    int getWaitForPrintingDelay();
    
    /**
     *
     * @param t
     * @see #getWaitForPrintingDelay()
     */
    void setWaitForPrintingDelay(int t);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="interruptDataStream() - Прервать выдачу данных">
    /**
     * Метод прерывает выдачу данных и переводит ККМ в режим, в котором был вызван метод
     * GetData.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора.
     * <p>
     * Работает только в режиме 1 (см. свойство ECRMode).
     * Переводит ККМ в режим, в котором она была до подачи команды DampRequest.
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.
     * <p>
     * [id(0x00000036), helpstring("ПрерватьВыдачуДанных")] long InterruptDataStream();
     */
    @CallState(state = "1")
    @TargetState(state = "!1")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    int interruptDataStream();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="interruptTest() - Прервать тестовый прогон">
    /**
     * Прервать тестовый прогон
     * <p>
     * Эта команда прерывает тестовый прогон ККМ (см. метод Test).
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает только в режиме 10 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим, в котором она находилась до вызова метода Test.
     * <p>
     * Используемые свойства
     * <p>
     * Password - Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства
     * <p>
     * OperatorNumber - Порядковый номер оператора, чей пароль был
     * введен.
     * <p>
     * [id(0x00000038), helpstring("ПрерватьТестовыйПрогон")] long InterruptTest();
     */
    @CallState(state = "10")
    @TargetState(state = "!10")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int interruptTest();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="batteryLow : boolean - Низкое напряжение на батарее">
    /**
     * Низкое напряжение на батарее.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак напряжения на батарее. TRUE – напряжение пониженное; FALSE – напряжение
     * нормальное.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000001dc), propget, helpstring("НизкоеНапряжениеНаБатарее")]
     * VARIANT_BOOL IsBatteryLow();
     */
    boolean isBatteryLow();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkEJConnection">
    /**
     * Проверять связь с ЭКЛЗ
     * <p>
     * Используется методом CheckConnection.
     * <p>
     * [id(0x000007f0), propget, helpstring("ПроверятьСвязьСЭКЛЗ")]
     * VARIANT_BOOL CheckEJConnection();
     * <p>
     * [id(0x000007f0), propput, helpstring("ПроверятьСвязьСЭКЛЗ")]
     * void CheckEJConnection([in] VARIANT_BOOL rhs);
     */
    boolean isCheckEJConnection();
    
    /**
     *
     * @param v
     * @see #isCheckEJConnection()
     */
    void setCheckEJConnection(boolean v);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="checkFMConnection">
    /**
     * Проверять Связь СФП.
     * <p>
     * Используется методом CheckConnection.
     * <p>
     * [id(0x000007ef), propget, helpstring("ПроверятьСвязьСФП")]
     * VARIANT_BOOL CheckFMConnection();
     * <p>
     * [id(0x000007ef), propput, helpstring("ПроверятьСвязьСФП")]
     * void CheckFMConnection([in] VARIANT_BOOL rhs);
     * <p>
     */
    boolean isCheckFMConnection();
    
    /**
     *
     * @param v
     * @see #isCheckFMConnection()
     */
    void setCheckFMConnection(boolean v);
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="drawerOpen : boolean - Денежный ящик открыт.">
    /**
     * Денежный ящик открыт.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак состояния денежного ящика. TRUE – денежный ящик открыт; FALSE – денежный ящик
     * закрыт.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000a5), propget, helpstring("ДенежныйЯщикОткрыт")]
     * VARIANT_BOOL IsDrawerOpen();
     */
    boolean isDrawerOpen();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FM1IsPresent : boolean - ФП1Есть">
    /**
     * ФП1Есть.
     * <p>
     * Признак состояния ЭКЛЗ. TRUE – ЭКЛЗ близка к переполнению, FALSE – ЭКЛЗ ещё не близка к переполнению.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x00000121), propget, helpstring("ПереполнениеЭКЛЗ")] VARIANT_BOOL IsEKLZOverflow();
     *
     * @return subj
     */
    boolean isEKLZOverflow();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FM1IsPresent : boolean - ФП1Есть">
    /**
     * ФП1Есть.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак наличия в ККМ ФП1. FALSE – ФП1 нет, TRUE – ФП1 есть.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x00000099), propget, helpstring("ФП1Есть")] VARIANT_BOOL
     * FM1IsPresent();
     *
     * @return subj
     */
    boolean isFM1IsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FM24HoursOver : boolean - 24 Часа в ФП кончились.">
    /**
     * 24 Часа в ФП кончились.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак того, что 24 часа в ФП истекли. TRUE – 24 часа в ФП истекли; FALSE – 24 часа в ФП не
     * истекли.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000001df), propget, helpstring("24ЧасаВФПКончились")]
     * VARIANT_BOOL IsFM24HoursOver();
     */
    boolean isFM24HoursOver();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FM2IsPresent : boolean - ФП2Есть">
    /**
     * ФП2Есть.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак наличия в ККМ ФП2. FALSE – ФП2 нет, TRUE – ФП2 есть.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x0000009a), propget, helpstring("ФП2Есть")] VARIANT_BOOL FM2IsPresent();
     *
     * @return subj
     */
    boolean isFM2IsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMOverflow : boolean - Переполнение ФП.">
    /**
     * Переполнение ФП.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак переполнения ФП. FALSE – переполнения ФП нет, TRUE – переполнение ФП.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x0000009d), propget, helpstring("ПереполнениеФП")]
     * VARIANT_BOOL FMOverflow();
     */
    boolean isFMOverflow();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="FMSessionOpen : boolean - Смена в ФП открыта.">
    /**
     * Смена в ФП открыта.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак того, что смена в ФП открыта. TRUE – смена в ФП открыта; FALSE – смена в ФП
     * закрыта.
     * <p>
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000001de), propget, helpstring("СменаВФПОткрыта")]
     * VARIANT_BOOL IsFMSessionOpen();
     */
    boolean isFMSessionOpen();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="journalRibbonIsPresent : boolean - Рулон операционного журнала есть.">
    /**
     * Рулон операционного журнала есть.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак наличия в ККМ рулона операционного журнала. FALSE – рулона операционного журнала
     * нет, TRUE – рулон операционного журнала есть.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000a6), propget, helpstring("РулонОперационногоЖурналаЕсть")]
     * VARIANT_BOOL JournalRibbonIsPresent();
     */
    boolean isJournalRibbonIsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="journalRibbonLever : boolean - Рычаг термоголовки опер журнала">
    /**
     * Рычаг термоголовки опер журнала .
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак положения рычага термоголовки операционного журнала.
     * TRUE – рычаг термоголовки
     * операционного журнала поднят; FALSE – рычаг термоголовки операционного журнала опущен.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000a7), propget, helpstring("РычагТермоголовкиОперационногоЖурнала")]
     * VARIANT_BOOL JournalRibbonLever();
     */
    boolean isJournalRibbonLever();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="journalRibbonOpticalSensor : boolean - Оптич датчик операционного журнала.">
    /**
     * Оптич датчик операционного журнала.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак прохождения ленты операционного журнала под оптическим датчиком ленты
     * операционного журнала. FALSE – операционного журнала нет под оптическим датчиком; TRUE –
     * операционный журнал проходит под оптическим датчиком.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000a8), propget, helpstring("ОптичДатчикОперационогоЖурнала")]
     * VARIANT_BOOL JournalRibbonOpticalSensor();
     */
    boolean isJournalRibbonOpticalSensor();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="lastFMRecordCorrupted : boolean - Последняя Запись в ФП испорчена.">
    /**
     * Последняя Запись в ФП испорчена.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак того, что последняя запись в ФП испорчена. TRUE – последняя запись в ФП испорчена;
     * FALSE – последняя запись в ФП не испорчена. Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000001dd), propget, helpstring("ПоследняяЗаписьВФПИспорчена")]
     * VARIANT_BOOL IsLastFMRecordCorrupted();
     * <p>
     */
    boolean isLastFMRecordCorrupted();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="licenseIsPresent:boolean - Признак наличия в ККМ лицензии">
    /**
     * Признак наличия в ККМ лицензии. FALSE – лицензия не введена, TRUE – лицензия введена.
     * Модифицируется методом GetECRStatus.
     * <p>
     * [id(0x000000af), propget, helpstring("ЛицензияЕсть")]
     * VARIANT_BOOL LicenseIsPresent();
     *
     * @return
     */
    boolean isLicenseIsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="lidPositionSensor : boolean - Датчик крышки корпуса.">
    /**
     * Датчик крышки корпуса.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак положения крышки корпуса. TRUE – крышка корпуса не установлена; FALSE – крышка
     * корпуса установлена.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000b0), propget, helpstring("ДатчикКрышкиКорпуса")]
     * VARIANT_BOOL LidPositionSensor();
     */
    boolean isLidPositionSensor();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="printerLeftSensorFailure : boolean - Отказ левого датчика печатающего механизма">
    /**
     * Отказ левого датчика печатающего механизма.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак отказа левого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE –
     * имеет место отказ датчика.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000ff), propget, helpstring("ОтказЛевогоДатчикаПечМех")]
     * VARIANT_BOOL IsPrinterLeftSensorFailure();
     */
    boolean isPrinterLeftSensorFailure();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="printerRightSensorFailure : boolean - Признак отказа правого датчика печатающего механизма.">
    /**
     * Признак отказа правого датчика печатающего механизма.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак отказа правого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE –
     * имеет место отказ датчика.
     * <p>
     * [id(0x00000100), propget, helpstring("ОтказПравогоДатчикаПечМех")]
     * VARIANT_BOOL IsPrinterRightSensorFailure();
     */
    boolean isPrinterRightSensorFailure();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="quantityPointPosition : boolean - Положение точки в количестве">
    /**
     * Положение точки в количестве.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак положения десятичной точки в количестве товара. TRUE – три знака после запятой
     * (ХХХХХХХ.ХХХ); FALSE – шесть знаков после запятой (ХХХХ.ХХХХХХ, так называемый
     * режим увеличенной точности количества).
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000001d6), propget, helpstring("ПоложениеТочкиВКоличестве")]
     * VARIANT_BOOL QuantityPointPosition();
     */
    boolean isQuantityPointPosition();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="receiptRibbonLever : boolean - Рычаг термоголовки чек ленты.">
    /**
     * Рычаг термоголовки чек ленты.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак положения рычага термоголовки чековой ленты. TRUE – рычаг термоголовки чековой
     * ленты поднят; FALSE – рычаг термоголовки чековой ленты опущен.
     * <p>
     * Модифицируется методами
     * GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000c4), propget, helpstring("РычагТермоголовкиЧекЛенты")]
     * VARIANT_BOOL ReceiptRibbonLever();
     */
    boolean isReceiptRibbonLever();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="receiptRibbonOpticalSensor : boolean - Оптич датчик чековой ленты.">
    /**
     * Оптич датчик чековой ленты.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE – чековой
     * ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000c5), propget, helpstring("ОптичДатчикЧековойЛенты")]
     * VARIANT_BOOL ReceiptRibbonOpticalSensor();
     */
    boolean isReceiptRibbonOpticalSensor();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="slipDocumentIsMoving : boolean - Подкладной документ проходит.">
    /**
     * Подкладной документ проходит.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак прохождения подкладного документа под датчиком контроля подкладного документа.
     * FALSE – подкладной документ отсутствует под датчиком контроля подкладного документа, TRUE
     * – подкладной документ проходит под датчиком контроля подкладного документа.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000d2), propget, helpstring("ПодкладнойДокументПроходит")]
     * VARIANT_BOOL SlipDocumentIsMoving();
     */
    boolean isSlipDocumentIsMoving();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="slipDocumentIsPresent : boolean - Подкладной документ есть.">
    /**
     * Подкладной документ есть.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак наличия в ККМ подкладного документа. FALSE – подкладного документа нет, TRUE –
     * подкладной документ есть.
     * <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x000000d3), propget, helpstring("ПодкладнойДокументЕсть")]
     * VARIANT_BOOL SlipDocumentIsPresent();
     */
    boolean isSlipDocumentIsPresent();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="useJournalRibbon">
    /**
     * Использовать операционный журнал.
     * <p>
     * Тип: WordBool / Логическое
     * <p>
     * Признак операции с лентой операционного журнала. FALSE – не производить операцию над
     * лентой операционного журнала, TRUE – производить операцию над лентой операционного
     * журнала.
     * <p>
     * Используется методами PrintString, PrintWideString, FeedDocument.
     * <p>
     * [id(0x000000f2), propget, helpstring("ИспользоватьОперационныйЖурнал")]
     * VARIANT_BOOL UseJournalRibbon();
     * <p>
     * [id(0x000000f2), propput, helpstring("ИспользоватьОперационныйЖурнал")]
     * void UseJournalRibbon([in] VARIANT_BOOL rhs);
     */
    boolean isUseJournalRibbon();
    
    /**
     *
     * @param v
     * @see #isUseJournalRibbon()
     */
    void setUseJournalRibbon(boolean v);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="useReceiptRibbon">
    /**
     * Использовать чековую ленту.
     * <p>
     * Тип: WordBool / Логическое
     * <p>
     * Признак операции с чековой лентой. FALSE – не производить операцию над чековой лентой,
     * TRUE – производить операцию над чековой лентой.
     * <p>
     * Используется методами PrintString, PrintWideString, FeedDocument.
     * <p>
     * [id(0x000000f3), propget, helpstring("ИспользоватьЧековуюЛенту")]
     * VARIANT_BOOL UseReceiptRibbon();
     * <p>
     * [id(0x000000f3), propput, helpstring("ИспользоватьЧековуюЛенту")]
     * void UseReceiptRibbon([in] VARIANT_BOOL rhs);
     */
    boolean isUseReceiptRibbon();
    
    /**
     *
     * @param v
     * @see #isUseReceiptRibbon() ()
     */
    void setUseReceiptRibbon(boolean v);
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="useSlipDocument : boolean - Использовать подкладной документ">
    /**
     * Использовать подкладной документ.
     * <p>
     * Тип: WordBool / Логическое
     * <p>
     * Признак операции с подкладным документом. FALSE – не производить операцию над
     * подкладным документом, TRUE – производить операцию над подкладным документом.
     * Используется методом FeedDocument.
     * <p>
     * [id(0x000000f4), propget, helpstring("ИспользоватьПодкладнойДокумент")]
     * VARIANT_BOOL UseSlipDocument();
     * <p>
     * [id(0x000000f4), propput, helpstring("ИспользоватьПодкладнойДокумент")]
     * void UseSlipDocument([in] VARIANT_BOOL rhs);
     */
    boolean isUseSlipDocument();

    /**
     * 
     * @param v 
     * @see #isUseSlipDocument() 
     */
    void setUseSlipDocument(boolean v);    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openCheck() - Открыть Чек.">
    /**
     * Открыть Чек.
     * <p>
     * Метод открывает документ (чек) определённого типа (продажа, покупка, возврат продажи, возврат
     * покупки).
     * <p>
     * Отличается от других методов регистрации (Sale, Buy, ReturnSale и ReturnBuy) тем,
     * что сама операция регистрации не осуществляется. Используется для формирования чека печатью
     * строк.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве CheckType указывается тип документа.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2, 4, 7 и 9 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 8 или 3 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * <b>Password</b> Пароль для исполнения метода драйвера.
     * <p>
     * <b>CheckType</b> Тип открываемого документа/чека
     * («0» - продажа, «1» - покупка, «2» - возврат продажи, «3» - возврат покупки).
     * <p>
     * Модифицируемые свойства - none
     * <p>
     * [id(0x0000003c), helpstring("ОткрытьЧек")] long OpenCheck();
     *
     * @return Код ошибки или 0
     */
    @CallState(state = "2|4|7|9")
    @TargetState(state = "8|3")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "checkType")})
    @OutputProperties(properties = {})
    int openCheck();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openSession() - Открыть Смену">
    /**
     * Метод передает команду «E0h», при этом в ФП открывается смена, а ККТ переходит в режим
     * «Открытой смены».
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора, который открыл текущий
     * чек.
     * <p>
     * Используемые свойства
     * <p>
     * Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера.
     * <p>
     * [id(0x00000260), helpstring("ОткрытьСмену")]
     * long OpenSession();
     */
    @CallState(state = "4|?")
    @TargetState(state = "2")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    int openSession();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="printReportWithCleaning() - Метод печатает сменный отчет с гашением.">
    /**
     * Метод печатает сменный отчет с гашением.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль администратора или системного
     * администратора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 и 3 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 4 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера.
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Целое 1..30 R
     * Порядковый номер оператора, чей пароль был
     * введен.
     * </ul>
     * [id(0x00000042), helpstring("СнятьОтчётСГашением")]
     * long PrintReportWithCleaning();
     */
    @CallState(state = "2|3")
    @TargetState(state = "4")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int printReportWithCleaning();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="printString() - Метод служит для печати строки символов">
    /**
     * Метод служит для печати строки символов на чековой ленте и/или на контрольной ленте (в
     * операционном журнале).
     * <p>
     * В свойствах UseReceiptRibbon, UseJournalRibbon указывается, на какой
     * из лент будет распечатан текст: значение свойства TRUE показывает, что текст будет выведен на
     * соответствующей ленте.
     * <p>
     * Если оба значения свойств равны TRUE, то производится одновременная
     * печать на чековой и контрольной ленте (в операционном журнале).
     * <p>
     * Печатаемый текст задается в свойстве StringForPrinting. Максимальная допустимая длина
     * печатаемой строки 249 символов.
     * <p>
     * Если длина строки в свойстве StringForPrinting меньше
     * максимальной допустимой, строка дополняется пробелами справа.
     * <p>
     * Если длина строки превышает максимальное допустимое значение,
     * то оставшиеся символы на уровне драйвера игнорируются.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Метод может вызываться в любом режиме, кроме режимов 11, 12 и 14 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> UseReceiptRibbon Признак операции с чековой лентой. FALSE – не производить операцию над чековой лентой,
     * TRUE – производить операцию над чековой лентой.
     * <li> UseJournalRibbon Признак операции с лентой операционного журнала: FALSE – не производить операцию
     * над лентой операционного журнала, TRUE – производить операцию над лентой.
     * <li> StringForPrinting Строка (не более 249) символов для печати.
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * <p>
     * [id(0x00000044), helpstring("ПечатьСтроки")]
     * long PrintString();
     */
    @CallState(state = "!11&!12&!14")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "useReceiptRibbon")
        , @DriverProperty(name = "useJournalRibbon")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int printString();
    //</editor-fold>
    
    /*
    CloseNonFiscalDocument
    ЗакрытьНефискальныйДокумент
    Метод выполняет команду ККТ E3h (Закрыть нефискальный документ).
     */
 /*
    ExcisableOperation
    Название  Тип  Диапазон/длина  Доступ  Расшифровка  Стр.
    OperationType  Целое  –  RW
    Тип операции
    (  00h - Продажа
    01h - Покупка
    02h - Возврат продажи
    03h - Возврат покупки
    10h - Сторно продажи
    11h - Сторно покупки
    12h - Сторно возврата продажи
    13h - Сторно возврата покупки).
    247
    ExciseCode   Целое  –  RW  Код акциза  217
    Department  Целое  0..16  RW  Номер отдела (секции).  206
    Price  Денеж.
    0..
    99999999,99
    RW  Цена за единицу товара.  251
    Tax1   Целое  0..4  RW  1-ый номер налоговой группы.  279
    Tax2   Целое  0..4  RW  2-ой номер налоговой группы.  282
    Tax3   Целое  0..4  RW  3-ий номер налоговой группы.  284
    Tax4   Целое  0..4  RW  4-ый номер налоговой группы.  287
    StringForPrinting   Строка  –  RW
    Строка символов кодовой таблицы WIN1251
    для печати (печатается на чеке в строки,
    идущей перед строкой, содержащей
    цену(сумму) и/или количество).
    267
    BarCode         Данные штрихкода  191
    Модифицируемые свойства
    Название  Тип  Диапазон/длина  Доступ  Расшифровка  Стр.
    OperatorNumber   Целое  1..30  R
    Порядковый номер оператора, чей пароль был
    введен.
     */

    //<editor-fold defaultstate="collapsed" desc="readLicense() - Прочитать лицензию.">
    /**
     * Прочитать лицензию.
     * <p>
     * Команда чтения лицензии из ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора.
     * <p>
     * Возвращает в свойство License номер лицензии ККМ.
     * <p>
     * Работает в любом режиме, кроме режима 1 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <p>
     * Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства
     * <p>
     * License Строка до 5 символов RW Текстовый параметр, содержащий лицензию.
     * Допустимы только символы «0», «1», «2», «3»,
     * «4», «5», «6», «7», «8» и «9».
     * <p>
     * [id(0x00000048), helpstring("ПрочитатьЛицензию")] long ReadLicense();
     */
    @CallState(state = "!1")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "license")})
    int readLicense();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="repeatDocument() - Повтор документа.">
    /**
     * Повтор документа.
     * <p>
     * Метод выводит на печать копию последнего закрытого документа продажи, покупки, возврата
     * продажи и возврата покупки. Фискальный логотип на таком документе не печатается. В конце
     * документа выводится надпись «ПОВТОР ДОКУМЕНТА».
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора, который открыл тот чек,
     * который нужно повторить.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 и 3 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 3, если кончились 24 часа (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * Модифицируемые свойства
     * <p>
     * OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * <p>
     * [id(0x0000004a), helpstring("ПовторДокумента")]
     * long RepeatDocument();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    @CallState(state = "2|3")
    @TargetState(state = "2|3")
    int repeatDocument();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resetECR() - СбросККМ">
    /**
     * СбросККМ.
     * <p>
     * Метод выполняет следующую последовательность действий
     * <p>
     * <ol>
     * <li> Выполняет команду ожидания печати (WaitForPrinting ).
     * <p>
     * <li> Запрашивает состояние ККТ и анализирует режим ККТ:
     * Далее приведены значения режимов и действия программы:
     * <ul>
     * <li> 1 (Выдача данных):
     * Прерывает выдачу данных (InterruptDataStream).
     * <li> 6 (Ожидание подтверждения вводе даты):
     * Подтверждает дату (ConfirmDate).
     * <li> 8 (Открытый документ):
     * Отменяет чек (CancelCheck)
     * <li> 10 (Тестовый прогон):
     * Прерывает тестовый прогон (InterruptTest).
     * 11, 12, 14: Ничего не делает.
     * <li> Другие значения режима ККТ:
     * Выход из метода
     * </ul>
     * <li> 3) В случае вознкновения ошибки возвращает значение -35. Устанавливает значения свойств:
     * ResultCode = -35, ResultCodeDescription = «Не удалось сбросить ККМ».
     * <li> 4) Если цикл повторился менее или равно 10 раз, возвращается к пункту 1)
     * </ol>
     * <p>
     * Используемые свойства
     * <p>
     * <b>Password</b> Пароль для исполнения метода драйвера.
     * <p>
     * [id(0x00000285), helpstring("СбросККМ")] long ResetECR();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    @CallState
    @TargetState(state = "?")
    int resetECR();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resetSettings() - Технологическое обнуление">
    /**
     * Технологическое обнуление.
     * <p>
     * Метод производит операцию технологического обнуления.
     * <p>
     * Технологическое обнуление доступно только после вскрытия пломбы на кожухе ККМ и
     * выполнения последовательности действий, описанных в ремонтной документации на ККМ.
     * <p>
     * Работает в режиме 9 (см. свойство ECRMode).
     * <p>
     * [id(0x0000004d), helpstring("ТехнологическоеОбнуление")] long ResetSettings();
     */
    @CallState(state = "9")
    @TargetState(state = "?")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    int resetSettings();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="resetSummary() - Общее гашение">
    /**
     * Общее гашение.
     * <p>
     * Метод производит общее гашение регистров ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора.
     * <p>
     * Работает в режиме 4 (см. свойство ECRMode).
     * <p>
     * Переводит ККМ в режим 7 (см. свойство ECRMode).
     * <p>
     * Используемые свойства
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * [id(0x0000004e), helpstring("ОбщееГашение")] long ResetSummary();
     */
    @CallState(state = "4")
    @TargetState(state = "7")
    @InputProperties(properties = {
        @DriverProperty(name = "password")})
    @OutputProperties(properties = {})
    int resetSummary();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="restoreState() - Восстановить состояние">
    /**
     * Восстановить состояние.
     * <p>
     * Восстанавливает сохраненные ранее с помощью метода SaveState значения всех свойств драйвера.
     * <p>
     * [id(0x000002ab), helpstring("ВосстановитьСостояние")] long RestoreState();
     */
    @CallState
    @InputProperties(properties = {})
    @OutputProperties(properties = {})
    int restoreState();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="sale() - Продажа">
    /**
     * Продажа.
     * <p>
     * Продажа – торговая операция, при которой товар перемещается от оператора к клиенту,
     * а деньги – в обратном направлении: от клиента к оператору.
     * <p>
     * Команда производит регистрацию продажи определенного количества товара в определенную
     * секцию с вычислением налогов (см. «Инструкцию по эксплуатации»/«Руководство оператора»)
     * без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 (проверка на окончание 24 часов производится запросом из ФП до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=0) и 9 (см. свойства ECRMode и
     * ECRMode8Status).
     * <p>
     * Переводит ККМ в режим 8 подрежим 0, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).
     * <p>
     * <b>Используемые свойства</b>
     * <ul>
     * <li>Password - Пароль для исполнения метода драйвера
     * <li>Quantity - Количество товара
     * <li>Price - Цена за единицу товара
     * <li>Department - Номер отдела (секции)
     * <li>Tax1 - 1-ый номер налоговой группы.
     * <li>Tax2 - 2-ой номер налоговой группы.
     * <li>Tax3 - 3-ий номер налоговой группы.
     * <li>Tax4 - 4-ый номер налоговой группы.
     * <li>StringForPrinting - Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * <p>
     * <b>Модифицируемые свойства</b>
     * <ul>
     * <li>OperatorNumber - Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * <p>
     * [id(0x00000053), helpstring("Продажа")] long Sale();
     *
     * @return Код ошибки или 0
     */
    @CallState(state = "2|4|7|8.0|9")
    @TargetState(state = "8.0|3")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int sale();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saleEx() - Продажа Точно">
    /**
     * ПродажаТочно.
     * <p>
     * Продажа – торговая операция, при которой товар перемещается от оператора к клиенту, а деньги –
     * в обратном направлении: от клиента к оператору.
     * <p>
     * Команда производит регистрацию продажи определенного количества товара в определенную
     * секцию с вычислением налогов (см. «Инструкцию по эксплуатации»/«Руководство
     * оператора») без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве SysAdminPassword должен быть указан пароль
     * системного администратора.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 (проверка на окончание 24 часов производится запросом из ФП до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=0) и 9 (см. свойства ECRMode и
     * ECRMode8Status).
     * <p>
     * Переводит ККМ в режим 8 подрежим 0, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).
     * <p>
     * <b>Используемые свойства</b>
     * <ul>
     * <li>Password - Пароль для исполнения метода драйвера
     * <li>SysAdminPassword - Пароль системного администратора для исполнения метода драйвера.
     * <li>Quantity - Количество товара
     * <li>Price - Цена за единицу товара.
     * <li>Department - Номер отдела (секции).
     * <li>Tax1 - 1-ый номер налоговой группы.
     * <li>Tax2 - 2-ой номер налоговой группы.
     * <li>Tax3 - 3-ий номер налоговой группы.
     * <li>Tax4 - 4-ый номер налоговой группы.
     * <li>StringForPrinting - Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * <p>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber - Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * <p>
     * Внимание!: Данный метод SaleEx отличается от метода Sale лишь тем, что в методе SaleEx
     * округление количества (см. свойство Quantity) идёт не до трёх знаков после запятой, а до
     * шести знаков.
     * <p>
     * [id(0x00000054), helpstring("ПродажаТочно")] long SaleEx();
     *
     * @return Код ошибки или 0
     */
    @CallState(state = "2|4|7|8.0|9")
    @TargetState(state = "8.0|3")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "sysAdminPassword")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int saleEx();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saleReturn() - Возврат продажи">
    /**
     * Возврат продажи – торговая операция, при которой товар возвращается от клиента к оператору, а
     * деньги – в обратном направлении: от оператора к клиенту.
     * <p>
     * Команда производит регистрацию возврата продажи определенного количества товара в
     * определенную секцию с вычислением налогов (см. «Инструкцию по
     * эксплуатации»/«Руководство оператора») без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режимах 2 (проверка на окончание 24 часов производится запросом из ФП до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=2) и 9 (см. свойства ECRMode и
     * ECRMode8Status).
     * <p>
     * Переводит ККМ в режим 8 подрежим 2, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).
     * <p>
     * Используемые свойства
     * <ul>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Quantity Дробн. (0,001.. 9999999,999) Количество товара.
     * <li> Price (Денеж. 0.. 99999999,99) Цена за единицу товара.
     * <li> Department (0..16) Номер отдела (секции).
     * <li> Tax1 (0..4) RW 1-ый номер налоговой группы.
     * <li> Tax2 (0..4) RW 2-ой номер налоговой группы.
     * <li> Tax3 (0..4) RW 3-ий номер налоговой группы.
     * <li> Tax4 (0..4) RW 4-ый номер налоговой группы.
     * <li> StringForPrinting Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * [id(0x00000051), helpstring("ВозвратПродажи")]
     * long ReturnSale();
     * @return Код ошибки или 0
     */
    @CallState(state = "2|4|7|8.2|9")
    @TargetState(state = "8.2|3")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int saleReturn();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saveState() - Сохранить состояние.">
    /**
     * Сохранить состояние.
     * <p>
     * Сохраняет значения всех свойств драйвера, затем их можно восстановить с помощью
     * RestoreState.
     * <p>
     * [id(0x000002aa), helpstring("СохранитьСостояние")] long SaveState();
     */
    @InputProperties(properties = {})
    @OutputProperties(properties = {})
    @CallState
    int saveState();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="storno() - Сторно">
    /**
     * Сторно.
     * <p>
     * Регистрация сторно определенного количества товара в определенную секцию с вычислением
     * налогов (см. «Инструкцию по эксплуатации»/«Руководство оператора») без закрытия чека.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.
     * <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p>
     * Работает в режиме 8 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <uL>
     * <li> Password Пароль для исполнения метода драйвера.
     * <li> Quantity (0,001..9999999,999) Количество товара.
     * <li> Price (0..99999999,99) Цена за единицу товара.
     * <li> Department Целое 0..16 RW Номер отдела (секции). 206
     * <li> Tax1 Целое 0..4 RW 1-ый номер налоговой группы. 279
     * <li> Tax2 Целое 0..4 RW 2-ой номер налоговой группы. 282
     * <li> Tax3 Целое 0..4 RW 3-ий номер налоговой группы. 284
     * <li> Tax4 Целое 0..4 RW 4-ый номер налоговой группы. 287
     * <li> StringForPrinting Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber Порядковый номер оператора, чей пароль был
     * введен.
     * </ul>
     * <p>
     * [id(0x00000062), helpstring("Сторно")] long Storno();
     * @return Код ошибки или 0
     */
    @CallState(state = "8")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "quantity")
        , @DriverProperty(name = "price")
        , @DriverProperty(name = "department")
        , @DriverProperty(name = "tax1")
        , @DriverProperty(name = "tax2")
        , @DriverProperty(name = "tax3")
        , @DriverProperty(name = "tax4")
        , @DriverProperty(name = "stringForPrinting")})
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber")})
    int storno();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="waitForPrinting() - Выполняет ожидание завершения печати">
    /**
     * Выполняет ожидание завершения печати.
     * <p>
     * Метод запрашивает состояние ККТ и анализирует подрежим ККТ. В случае отсутствия связи
     * запрос состояния повторяется до истечения времени, заданного в свойстве ConnectionTimeout.
     * <p>
     * Далее приведены значения подрежимов и действия программы:
     * <ul>
     * <li> 0. Бумага есть.
     * Выход из метода.
     * <li>1. Пассивное отсутствие бумаги.
     * Выдает ошибку E_NOPAPER, -34
     * ResultCode = E_NOPAPER
     * ResultCodeDescription = "Пассивное отсутствие бумаги"
     * <li>2. Активное отсутствие бумаги.
     * Выдает ошибку E_NOPAPER, -34
     * ResultCode= E_NOPAPER
     * ResultCodeDescription= "Активное отсутствие бумаги"
     * <li>3. После активного отсутствия бумаги.
     * ККТ ждет команду продолжения печати .
     * Подает команду продолжения печати и возвращается в цикл.
     * <li>4. Фаза печати операции полных фискальных отчетов.
     * Выполняет задержку, указанную в свойстве WaitForPrintingDelay и возвращается в цикл.
     * <li>5. Фаза печати операции
     * Выполняет задержку, указанную в свойстве WaitForPrintingDelay и возвращается в цикл.
     * <li>Другие значения подрежима ФР:
     * Выход из метода.
     * </ul>
     * Используемые свойства
     * <ul>
     * <li>Password Пароль для исполнения метода драйвера.
     * <li>WaitForPrintingDelay Задержка ожидания печати
     * <li>ConnectionTimeout Таймаут подключения
     * </ul>
     * Модифицируемые свойства
     * <ul>
     * <li>ResultCode Код ошибки, возвращаемой ККМ в результате
     * выполнения последней операции
     * <li>ResultCodeDescription Строка с описанием на русском языке кода
     * ошибки, возникающей в результате
     * выполнения последней операции
     * </ul>
     * [id(0x00000265), helpstring("ОжиданиеПечати")] long WaitForPrinting();
     * @return Код ошибки или 0
     */
    @CallState
    @TargetState(state = "?")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "waitForPrintingDelay")
        , @DriverProperty(name = "connectionTimeout")})
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode")
        , @DriverProperty(name = "resultCodeDescription")})
    int waitForPrinting();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="writeLicense() - Записать лицензию">
    /**
     * Записать лицензию.
     * <p>
     * Команда записи лицензии License в ККМ.
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль системного администратора и
     * заполнить свойство License.
     * <p>
     * Работает в любом режиме, кроме режима 1 (см. свойство ECRMode).
     * <p>
     * Не меняет режима ККМ.
     * <p>
     * [id(0x0000006a), helpstring("ЗаписатьЛицензию")] long WriteLicense();
     * @return Код ошибки или 0
     */
    @CallState(state = "!1")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
        , @DriverProperty(name = "license")})
    @OutputProperties(properties = {})
    int writeLicense();
    //</editor-fold>
}
