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

package xyz.cofe.shtrihm.hu;

import com.jacob.com.Currency;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.shtrihm.Driver;
import xyz.cofe.shtrihm.jacob.ShtrihMJacobDriver;

/**
 * Драйвер с использованием функций Shrih M
 * @author nt.gocha@gmail.com
 */
public class ShtrihMFun
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(ShtrihMFun.class.getName());
    
    private static Level logLevel(){ return logger.getLevel(); }
    private static boolean isLogSevere(){
        Level ll = logLevel();
        return ll == null
            ? true
            : ll.intValue() <= Level.SEVERE.intValue();
    }
    private static boolean isLogWarning(){
        Level ll = logLevel();
        return ll == null
            ? true
            : ll.intValue() <= Level.WARNING.intValue();
    }
    private static boolean isLogInfo(){
        Level ll = logLevel();
        return ll == null
            ? true
            : ll.intValue() <= Level.INFO.intValue();
    }
    private static boolean isLogFine(){
        Level ll = logLevel();
        return ll == null
            ? true
            : ll.intValue() <= Level.FINE.intValue();
    }
    private static boolean isLogFiner(){
        Level ll = logLevel();
        return ll == null
            ? false
            : ll.intValue() <= Level.FINER.intValue();
    }
    private static boolean isLogFinest(){
        Level ll = logLevel();
        return ll == null
            ? false
            : ll.intValue() <= Level.FINEST.intValue();
    }
    
    private static void logEntering(String method,Object ... args){
        logger.entering(ShtrihMFun.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(ShtrihMFun.class.getName(), method, result);
    }
    
    private static void logFine(String message,Object ... args){
        logger.log(Level.FINE, message, args);
    }
    private static void logFiner(String message,Object ... args){
        logger.log(Level.FINER, message, args);
    }
    private static void logFinest(String message,Object ... args){
        logger.log(Level.FINEST, message, args);
    }
    private static void logInfo(String message,Object ... args){
        logger.log(Level.INFO, message, args);
    }
    private static void logWarning(String message,Object ... args){
        logger.log(Level.WARNING, message, args);
    }
    private static void logSevere(String message,Object ... args){
        logger.log(Level.SEVERE, message, args);
    }
    private static void logException(Throwable ex){
        logger.log(Level.SEVERE, null, ex);
    }    
    //</editor-fold>
    
    /**
     * Ссылка на драйвер
     */
    public final Driver driver;
    
    /**
     * Конструктор
     * @param driver драйвер
     */
    public ShtrihMFun( Driver driver ){
        if( driver==null )throw new IllegalArgumentException( "driver==null" );
        this.driver = driver;
    }
    
    /**
     * Установить связь. <p>
     * Перед вызовом метода в свойстве ComputerName указать имя компьютера, к которому подключена ККТ. <p>
     * Метод выполняет следующие действия: <p>
     * 1. Занимает COM порт с номером ComNumber; <p>
     * 2. Устанавливает скорость порта BaudRate; <p>
     * 3. Устанавливает таймаут приёма байта порта Timeout; <p>
     * 4. Запрашивает состояние устройства путём выполнения метода GetECRStatus. <p>
     * 5. Запрашивает параметры устройства путём выполнения метода GetDeviceMetrics. <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора <p>
     * 
     * @param comport (возможно null) 0..255 Номер Com-порта ПК к которому подсоединена ККМ
     * @param baudRate (возможно null) Скорость обмена между ККМ и подключенным к ней устройством
     * @param timeout (возможно null)
     *       Тайм-аут приема байта нелинейный. <p>
     * Диапазон допустимых значений [0…255] распадается на три диапазона: <p>
     * в диапазоне [0…150] каждая единица соответствует 1 мс, т.е. данным диапазоном задаются значения тайм-аута от 0 до 150 мс;
     *  <p>
     * в диапазоне [151…249] каждая единица соответствует 150 мс,
     * т.е. данным диапазоном задаются значения тайм-аута от 300 мс до 15 сек; <p>
     * в диапазоне [250…255] каждая единица соответствует 15 сек,
     * т.е. данным диапазоном задаются значения тайм-аута от 30 сек до 105 сек. 

     * @param computerName (возможно null) Имя компьютера, к которому подключена ККТ
     * @param protocolType (возможно null) Тип протокола
     * @param protocolType (возможно null) Тип подключения
     * @param password (возможно null)  Пароль
     * @return Ответ есть/нет ошибки
     */
    public synchronized ShtrihMFunRes connect( 
        Integer comport, 
        BaudRate baudRate, 
        Integer timeout,
        String computerName,
        ProtocolType protocolType,
        ConnectionType connType,
        Integer password
    ){
        synchronized( driver ){
            if( password!=null )driver.setPassword(password);
            if( comport!=null )driver.setComNumber(comport);
            if( baudRate!=null )driver.setBaudRate(baudRate.number());
            if( timeout!=null )driver.setTimeout(timeout);
            if( computerName!=null )driver.setComputerName(computerName);
            if( protocolType!=null )driver.setProtocolType(protocolType.number());
            if( connType!=null )driver.setConnectionType(connType.number());
            return new ShtrihMFunRes(driver.connect(), driver.getResultCodeDescription());
        }        
//        return ShtrihMFunRes.develDummy;
    }
    
    /**
     * Освобождает COM-порт ПК, занятый под драйвер методом Connect
     * @return Ответ есть/нет ошибки
     */
    public synchronized ShtrihMFunRes disconnect(){
        synchronized( driver ){
            return new ShtrihMFunRes(driver.disconnect(), driver.getResultCodeDescription());
        }
    }
    
    /**
     * Проверить связь.
     * @param checkFMConnection (возможно null)
     * @param checkEJConnection (возможно null)
     * @param password (возможно null) Пароль для исполнения метода драйвера.
     * @return Ответ есть/нет ошибки
     */
    public synchronized ShtrihMFunRes checkConnection(Boolean checkFMConnection, Boolean checkEJConnection, Integer password){
        synchronized( driver ){
            if( checkFMConnection!=null )driver.setCheckFMConnection(checkFMConnection);
            if( checkEJConnection!=null )driver.setCheckEJConnection(checkEJConnection);
            if( password!=null )driver.setPassword(password);
            return new ShtrihMFunRes(driver.checkConnection(),driver.getResultCodeDescription());
        }
    }
    
    /**
     * Режим ККМ – одно из состояний ККМ, в котором она может находиться. <p>
     * Переход от режима к режиму производится автоматически при вызове того или иного метода (см. описания методов).
     *  <p>
     * Номера и назначение режимов: <p>
     *  <p>
     * Режим ККМ Описание режима ККМ <p>
     * 0 Принтер в рабочем режиме <p>
     * 1 Выдача данных <p>
     * 2 Открытая смена, 24 часа не кончились <p>
     * 3 Открытая смена, 24 часа кончились <p>
     * 4 Закрытая смена <p>
     * 5 Блокировка по неправильному паролю налогового инспектора <p>
     * 6 Ожидание подтверждения ввода даты <p>
     * 7 Разрешение изменения положения десятичной точки <p>
     * 8 Открытый документ <p>
     * 9 Режим разрешения технологического обнуления <p>
     * 10 Тестовый прогон <p>
     * 11 Печать полного фискального отчета <p>
     * 12 Печать длинного отчета ЭКЛЗ <p>
     * 13 Работа с фискальным подкладным документом <p>
     * 14 Печать подкладного документа <p>
     * 15 Фискальный подкладной документ сформирован <p>
     * @return Результат вызова
     */
    public synchronized ShtrihMFunRes<ECRMode> getECRMode(){
        synchronized( driver ){
            int code = driver.getShortECRStatus();
            if( code!=0 ){
                return new ShtrihMFunRes(code, driver.getResultCodeDescription());
            }
            
            int mode = driver.getECRMode();
            int stat = driver.getECRMode8Status();
            String desc = driver.getECRModeDescription();
            int amode = driver.getECRAdvancedMode();
            String adesc = driver.getECRAdvancedModeDescription();
            
            return new ShtrihMFunRes<ECRMode>(
                new ECRMode(mode, stat, desc, amode, adesc), 
                code, 
                driver.getResultCodeDescription());
        }
    }

    /**
     * Метод открывает документ (чек) определённого типа (продажа, покупка, возврат продажи, возврат
     * покупки).
     * <p>
     * Отличается от других методов регистрации (Sale, Buy, ReturnSale и ReturnBuy) тем,
     * что сама операция регистрации не осуществляется. Используется для формирования чека печатью
     * строк.  
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * В свойстве CheckType  <p>
     * указывается тип документа.  <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * <p> <p>
     * Работает в режимах 2, 4, 7 и 9 (см. свойство ECRMode).
     * <p> <p>
     * Переводит ККМ в режим 8 или 3 (см. свойство ECRMode).  
     * <p>
     * Используемые свойства  <p>
     * <b>Password</b> Пароль для исполнения метода драйвера.  <p>
     * <b>CheckType</b> Тип открываемого документа/чека
     * («0» - продажа, «1» - покупка, «2» - возврат продажи, «3» - возврат покупки).
     * <p>
     * Модифицируемые свойства - none
     * @param ctype Тип открываемого документа/чека
     * @param password  (возможно null) Тип открываемого документа/чека
     * @return 
     */
    public synchronized ShtrihMFunRes openCheck(CheckType ctype, Integer password){
        if( ctype==null )throw new IllegalArgumentException( "ctype==null" );
        synchronized( driver ){
            if( password!=null )driver.setPassword(password);
            driver.setCheckType(ctype.number());
            int code = driver.openCheck();
            return new ShtrihMFunRes(code, driver);
        }
    }
    
    /**
     * Метод  производит  закрытие  чека  комбинированным  типом  оплаты  с  вычислением  налогов  и
     * суммы сдачи. <p> <p>
     *
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.  <p> <p>
     *
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p>
     * В свойстве Change возвращается сумма сдачи.  <p>
     * Работает в режиме 8 (см. свойство ECRMode).  <p>
     * Переводит ККМ в режим 2 или 3 (см. свойство ECRMode).  <p> <p>
     *
     * Используемые свойства  <p> <p>
     * <ul>
     * <li>Password - Пароль для исполнения метода драйвера
     * <li>Summ1   - Свойство, используемое для хранения суммы наличных клиента.
     * <li>Summ2   - Свойство, используемое для хранения суммы клиента типа оплаты 2.
     * <li>Summ3   - Свойство, используемое для хранения суммы клиента типа оплаты 3.
     * <li>Summ4   - Свойство, используемое для хранения суммы клиента типа оплаты 4.
     * <li>DiscountOnCheck   - Скидка на чек.
     * <li>Tax1   - 1-ый номер налоговой группы.
     * <li>Tax2   - 2-ой номер налоговой группы.
     * <li>Tax3   - 3-ий номер налоговой группы.
     * <li>Tax4   - 4-ый номер налоговой группы.
     * <li>StringForPrinting   Строка не более 40 символов -
     * Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей
     * цену(сумму) и/или количество).
     * </ul>
     *
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber   - 1..30  Порядковый номер оператора, чей пароль был введен.
     * <li>Change   - Свойство, в котором хранится сумма сдачи.
     * </ul>
     * 
     * @param stringForPrinting40chars (возможно null)
     * @param sum1 (возможно null)
     * @param sum2 (возможно null)
     * @param sum3 (возможно null)
     * @param sum4 (возможно null)
     * @param discountOnCheck (возможно null)
     * @param tax1 (возможно null)
     * @param tax2 (возможно null)
     * @param tax3 (возможно null)
     * @param tax4 (возможно null)
     * @param password (возможно null) Пароль для исполнения метода драйвера
     * @return Результат (сдача или ошибка закрытия)
     */
    public synchronized ShtrihMFunRes<CloseCheckResult> closeCheck(
        String stringForPrinting40chars,
        Long sum1,
        Long sum2,
        Long sum3,
        Long sum4,
        Double discountOnCheck,
        Boolean tax1,
        Boolean tax2,
        Boolean tax3,
        Boolean tax4,
        Integer password
    ){
        synchronized( driver ){
            if( password!=null )driver.setPassword(password);
            if( stringForPrinting40chars!=null )driver.setStringForPrinting(stringForPrinting40chars);
            
            if( sum1!=null )driver.setSumm1(new Currency(sum1));
            if( sum2!=null )driver.setSumm1(new Currency(sum2));
            if( sum3!=null )driver.setSumm1(new Currency(sum3));
            if( sum4!=null )driver.setSumm1(new Currency(sum4));
            
            if( discountOnCheck!=null )
                driver.setDiscountOnCheck(discountOnCheck);
            
            if( tax1!=null )driver.setTax1( tax1 ? 1 : 0 );
            if( tax2!=null )driver.setTax2( tax2 ? 1 : 0 );
            if( tax3!=null )driver.setTax3( tax3 ? 1 : 0 );
            if( tax4!=null )driver.setTax4( tax4 ? 1 : 0 );
            
            int code = driver.closeCheck();
            CloseCheckResult cc = code==0 ?
                            new CloseCheckResult(driver.getOperatorNumber(), driver.getChange().longValue()) :
                            null;
            return new ShtrihMFunRes(cc, code, driver);
        }
    }
    
    /**
     * Продажа. <p> <p>
     *
     * Продажа – торговая операция, при которой товар перемещается от оператора к клиенту,
     * а деньги – в обратном направлении: от клиента к оператору.  <p> <p>
     * Команда  производит  регистрацию  продажи  определенного  количества  товара  в  определенную
     * секцию  с  вычислением  налогов  (см.  «Инструкцию  по  эксплуатации»/«Руководство оператора»)
     * без закрытия чека.  <p> <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.  <p> <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p> <p>
     *
     * Работает  в  режимах  2  (проверка  на  окончание  24  часов  производится  запросом  из  ФП  до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=0) и 9 (см. свойства ECRMode и
     * ECRMode8Status).  <p> <p>
     * Переводит ККМ в режим 8 подрежим 0, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).  <p> <p>
     *
     * <b>Используемые свойства</b><p>
     * <ul>
     * <li>Password   - Пароль для исполнения метода драйвера
     * <li>Quantity  - Количество товара
     * <li>Price - Цена за единицу товара
     * <li>Department   - Номер отдела (секции)
     * <li>Tax1 - 1-ый номер налоговой группы.
     * <li>Tax2 - 2-ой номер налоговой группы.
     * <li>Tax3 - 3-ий номер налоговой группы.
     * <li>Tax4 - 4-ый номер налоговой группы.
     * <li>StringForPrinting - Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * </ul>
     *
     * <b>Модифицируемые свойства</b> <p>
     * <ul>
     * <li>OperatorNumber  - Порядковый номер оператора, чей пароль был введен.
     * </ul>
     * @param stringForPrinting (возможно null) Строка символов для печати (печатается на чеке в строке,
     * идущей перед строкой, содержащей цену(сумму) и/или количество).
     * @param quantity (возможно null) Количество товара
     * @param price (возможно null) Цена за единицу товара
     * @param department (возможно null) Номер отдела (секции)
     * @param tax1 (возможно null) 1-ый номер налоговой группы.
     * @param tax2 (возможно null) 2-ый номер налоговой группы.
     * @param tax3 (возможно null) 3-ый номер налоговой группы.
     * @param tax4 (возможно null) 4-ый номер налоговой группы.
     * @param password (возможно null) Пароль для исполнения метода драйвера
     * @return Результат
     */
    public synchronized ShtrihMFunRes<SaleResult> sale(String stringForPrinting, 
                           Double quantity, 
                           Long price, 
                           Integer department, 
                           Boolean tax1,
                           Boolean tax2,
                           Boolean tax3,
                           Boolean tax4,
                           Integer password)
    {
        synchronized( driver ){
            if( password!=null )driver.setPassword(password);
            if( stringForPrinting!=null )driver.setStringForPrinting(stringForPrinting);

            if( quantity!=null )driver.setQuantity(quantity);
            if( price!=null )driver.setPrice(new Currency(price));
            if( department!=null )driver.setDepartment(department);
            
            if( tax1!=null )driver.setTax1( tax1 ? 1 : 0 );
            if( tax2!=null )driver.setTax2( tax2 ? 1 : 0 );
            if( tax3!=null )driver.setTax3( tax3 ? 1 : 0 );
            if( tax4!=null )driver.setTax4( tax4 ? 1 : 0 );
            
            int code = driver.sale();
            SaleResult sr = code==0 ?
                            new SaleResult(driver.getOperatorNumber()) :
                            null;
            
            return new ShtrihMFunRes<SaleResult>( sr, code, driver );
        }
    }
}
