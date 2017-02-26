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

package xyz.cofe.shtrihm.jacob;

import xyz.cofe.shtrihm.InputProperties;
import xyz.cofe.shtrihm.OutputProperties;
import xyz.cofe.shtrihm.DriverProperty;
import xyz.cofe.shtrihm.CallState;
import xyz.cofe.shtrihm.Driver;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.jacob.com.Currency;
import java.util.Date;

/**
 * Драйвер Штрих-М.
 * Реализация использует проект JACOB.
 * Поток опасная реализация
 * @author nt.gocha@gmail.com
 */
public class ShtrihMJacobDriver implements Driver {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(ShtrihMJacobDriver.class.getName());
    
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
        logger.entering(ShtrihMJacobDriver.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(ShtrihMJacobDriver.class.getName(), method, result);
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
    
    protected final ActiveXComponent drv;
    
    //<editor-fold defaultstate="collapsed" desc="constructors">
    /**
     * Конструктор по умолчанию
     */
    public ShtrihMJacobDriver(){
        this((String)null);
    }
    
    /**
     * Конструктор
     * @param progid progid Компонента или null (тогда Addin.DRvFR)
     */
    public ShtrihMJacobDriver(String progid){
        if( progid==null )progid = "Addin.DRvFR";
        drv = new ActiveXComponent(progid);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="resultCode : int - Результат">
    /**
     * Результат
     * @return Результат
     */
    @Override
    public synchronized int getResultCode(){
        Variant v = drv.getProperty("ResultCode");
        return v.getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="resultCodeDescription : String - Описание Результата">
    /**
     * Описание Результата
     * @return Описание Результата
     */
    @Override
    public synchronized String getResultCodeDescription(){
        Variant v = drv.getProperty("ResultCodeDescription");
        return v.getString();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="password : long - Пароль">
    //[id(0x000000bc), propget, helpstring("Пароль")] long Password();
    //[id(0x000000bc), propput, helpstring("Пароль")] void Password([in] long rhs);
    /**
     * Пароль.
     * Пароль для исполнения метода драйвера.
     * Допустимая длина: до 8 разрядов
     * @return Пароль
     */
    @Override
    public synchronized int getPassword(){
        //Variant v = drv.getProperty("Password");
        //return v.getInt();
        return drv.getPropertyAsInt("Password");
    }
    /**
     * Пароль
     * @param pswd Пароль
     */
    @Override
    public synchronized void setPassword(int pswd){        
        //drv.setProperty("Password", new Variant(pswd));
        drv.setProperty("Password", pswd);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="conntect/disconnect etc">
    //<editor-fold defaultstate="collapsed" desc="connect() - Установить связь">
// [id(0x0000000d), helpstring("УстановитьСвязь")] long Connect();
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
     *  <p>
     *
     * Используемые свойства
     * <ul>
     * <li> Password Целое до 8 разрядов RW Пароль для исполнения метода драйвера.
     * <li> ComNumber Целое 0..255 RW Номер Com-порта ПК к которому подсоединена ККМ (0 – порт 1, 1 – порт 2, 2 – порт 3 и т.д.).
     * <li> BaudRate Целое 0..6 RW Скорость обмена между ККМ и подключенным к ней устройством.
     * <li> Timeout Целое 0..255 RW Таймаут приема байта (см. описание свойства).
     * <li> ComputerName Строка RW Имя компьютера, к которому подключена ККТ.
     * <li> ProtocolType Целое 0..1 Тип протокола (0-стандартный, 1-протокол ККТ 2.0).
     * <li> ConnectionType Целое 0..6 RW Тип подключения.
     * </ul>
     * @return код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "comNumber"),
        @DriverProperty(name = "baudRate"),
        @DriverProperty(name = "timeout"),
        @DriverProperty(name = "computerName"),
        @DriverProperty(name = "protocolType"),
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "resultCode"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @CallState()
    @Override
    public synchronized int connect(){
        Variant v = drv.invoke("Connect");
        return v.getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="disconnect() : int - РазорватьСвязь">
    /**
     * Освобождает COM-порт ПК, занятый под драйвер методом Connect. <p>
     * [id(0x00000013), helpstring("РазорватьСвязь")] long Disconnect();
     * @return код ошибки или 0
     */
    @CallState()
    @Override
    public int disconnect(){
        return drv.invoke("Disconnect").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkConnection() - Проверить связь.">
    /**
     * Проверить связь.
     * <p>
     * Используемые свойства <p>
     * Password - Пароль для исполнения метода драйвера.<p>
     * CheckFMConnection Проверить связь с ФП<p>
     * CheckEJConnection Проверить связь с ЭКЛЗ<p>
     * <p>
     * [id(0x000007f1), helpstring("ПроверитьСвязь")] long CheckConnection();
     * @return код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "checkFMConnection"),
        @DriverProperty(name = "checkEJConnection"),
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "resultCode"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @CallState()
    @Override
    public synchronized int checkConnection() {
        return drv.invoke("CheckConnection").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkFMConnection : boolean - Проверять Связь СФП.">
    /**Проверять Связь СФП.
     *  <p>
     * Используется методом CheckConnection. <p>
     * [id(0x000007ef), propget, helpstring("ПроверятьСвязьСФП")]
     * VARIANT_BOOL CheckFMConnection();
     * <p>
     * [id(0x000007ef), propput, helpstring("ПроверятьСвязьСФП")]
     * void CheckFMConnection([in] VARIANT_BOOL rhs);
     * <p>
     */
    @Override
    public synchronized boolean isCheckFMConnection(){
        return drv.getPropertyAsBoolean("CheckFMConnection");
    }
    @Override
    public synchronized void setCheckFMConnection(boolean v){
        drv.setProperty("CheckFMConnection", v);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="checkEJConnection : boolean - Проверять связь с ЭКЛЗ">
    /**
     * Проверять связь с ЭКЛЗ      <p>
     * Используется методом CheckConnection.      <p>
     * [id(0x000007f0), propget, helpstring("ПроверятьСвязьСЭКЛЗ")]
     * VARIANT_BOOL CheckEJConnection();
     * <p>
     * [id(0x000007f0), propput, helpstring("ПроверятьСвязьСЭКЛЗ")]
     * void CheckEJConnection([in] VARIANT_BOOL rhs);
     */
    @Override
    public synchronized boolean isCheckEJConnection() {
        return drv.getPropertyAsBoolean("CheckEJConnection");
    }
    
    @Override
    public synchronized void setCheckEJConnection(boolean v) {
        drv.setProperty("CheckEJConnection", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="comNumber : int - Номер Com-порта ПК">
    //[id(0x00000072), propget, helpstring("НомерCOMпорта")] long ComNumber();
    //[id(0x00000072), propput, helpstring("НомерCOMпорта")] void ComNumber([in] long rhs);
    /**
     * Номер Com-порта ПК к которому подсоединена ККМ.
     * Диапазон значений: 0…255 («0» – порт 1, «1» – порт 2, «2» – порт 3 и т.д.).
     * Используется методами ShowProperties, Connect, LockPort, AdminUnlockPort.
     * Модифицируется методом ShowProperties
     * @return
     */
    @Override
    public synchronized int getComNumber(){
        Variant v = drv.getProperty("ComNumber");
        return v.getInt();
    }
    
    /**
     * Номер Com-порта ПК к которому подсоединена ККМ.
     * @param cport Диапазон значений: 0…255
     */
    @Override
    public synchronized void setComNumber( int cport ){
        drv.setProperty("ComNumber", cport);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="baudRate : int - Скорость обмена между ККМ и подключенным к ней устройством">
    //BaudRate
    //[id(0x0000006e), propget, helpstring("СкоростьОбмена")] long BaudRate();
    //[id(0x0000006e), propput, helpstring("СкоростьОбмена")] void BaudRate([in] long rhs);
    
    /**
     * Скорость обмена между ККМ и подключенным к ней устройством.  <p>
     * Методы SetExchangeParam и Connect используют данное свойство,  <p>
     * а метод GetExchangeParam модифицирует его. <p>
     *  <p>
     * Соответствие значения параметра и скорости обмена приведены в таблице: <p>
     * Значение параметра BaudRate Скорость обмена, бод <p>
     * 0 2400 <p>
     * 1 4800 <p>
     * 2 9600 <p>
     * 3 19200  <p>
     * 4 38400 <p>
     * 5 57600 <p>
     * 6 115200 <p>
     * @return ---
     */
    @Override
    public synchronized int getBaudRate(){
        return drv.getProperty("BaudRate").getInt();
    }
    
    /**
     * Скорость обмена между ККМ и подключенным к ней устройством.
     * @param brate 0..6
     */
    @Override
    public synchronized void setBaudRate( int brate ){
        drv.setProperty("BaudRate", brate);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="timeout : int - Таймаут Приема Байта">
    //Timeout
    /**
     * Таймаут Приема Байта. <p>
     *  <p>
     * [id(0x000000e5), propget, helpstring("ТаймаутПриемаБайта")] long Timeout(); <p>
     * [id(0x000000e5), propput, helpstring("ТаймаутПриемаБайта")] void Timeout([in] long rhs); <p>
     *  <p>
     * Тип: Integer / Целое <p>
     * Тайм-аут приема байта.
     * Тайм-аут приема байта нелинейный.
     * Диапазон допустимых значений [0…255] распадается на три диапазона: <p>
     * в диапазоне [0…150] каждая единица соответствует 1 мс, т.е. данным диапазоном задаются значения тайм-аута от 0 до 150 мс;
     *  <p>
     * в диапазоне [151…249] каждая единица соответствует 150 мс,
     * т.е. данным диапазоном задаются значения тайм-аута от 300 мс до 15 сек; <p>
     * в диапазоне [250…255] каждая единица соответствует 15 сек,
     * т.е. данным диапазоном задаются значения тайм-аута от 30 сек до 105 сек. <p>
     *  <p>
     * Методы SetExchangeParam и Connect используют данное свойство, а метод GetExchangeParam модифицирует его.
     * @return ---
     */
    @Override
    public synchronized int getTimeout(){
        return drv.getPropertyAsInt("Timeout");
    }
    
    @Override
    public synchronized void setTimeout( int timeout ){
        drv.setProperty("Timeout", timeout);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="computerName : String - ИмяКомпьютера">
    /**
     * ComputerName ИмяКомпьютера. <p>
     * Тип: WideString / Строка <p>
     * Имя компьютера, к которому подключен ККТ <p>
     * Используется методом ServerConnect. <p>
     * [id(0x000001e7), propget, helpstring("ИмяКомпьютера")] BSTR ComputerName(); <p>
     * [id(0x000001e7), propput, helpstring("ИмяКомпьютера")] void ComputerName([in] BSTR rhs); <p>
     * @return --
     */
    @Override
    public synchronized String getComputerName(){
        return drv.getPropertyAsString("ComputerName");
    }
    
    @Override
    public synchronized void setComputerName(String cname){
        if( cname==null )throw new IllegalArgumentException( "cname==null" );
        drv.setProperty("ComputerName", cname);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="protocolType : int - Тип протокола">
    /**
     * ProtocolType Целое 0..1 RW Тип протокола (0-стандартный, 1-протокол ККТ 2.0). <p>
     * Тип: Integer / Целое <p>
     * Используется методами ChangeProtocol, Connect. <p>
     * [id(0x000007f4), propget, helpstring("ТипПротокола")] long ProtocolType(); <p>
     * [id(0x000007f4), propput, helpstring("ТипПротокола")] void ProtocolType([in] long rhs); <p>
     */
    
    @Override
    public synchronized int getProtocolType(){
        return drv.getPropertyAsInt("ProtocolType");
    }
    
    @Override
    public synchronized void setProtocolType( int ptype ){
        drv.setProperty("ProtocolType", ptype);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="connectionType : int - Тип подключения к устройству">
    /**
     * Тип подключения к устройству - ConnectionType. <p>
     *
     * Тип: Integer / Целое <p>
     * Тип подключения к устройству.Значение по умолчанию – 0 (Локальное подключение) <p>
     * Диапазон допустимых значений: <p>
     * Значение / Тип подключения <p>
     * 0 Локально <p>
     * 1 Сервер ККМ (TCP) <p>
     * 2 Сервер ККМ (DCOM) <p>
     * 3 ESCAPE <p>
     * 4 Не используется <p>
     * 5 Эмулятор <p>
     * 6 Подключение через ТСР-сокет <p>
     * Используется методом Connect. <p>
     * Модифицируется методом SetActiveLD. <p>
     * [id(0x0000020a), propget, helpstring("ТипПодключения")] long ConnectionType(); <p>
     * [id(0x0000020a), propput, helpstring("ТипПодключения")] void ConnectionType([in] long rhs); <p>
     */
    @Override
    public synchronized int getConnectionType(){
        return drv.getPropertyAsInt("ConnectionType");
    }
    
    @Override
    public synchronized void setConnectionType( int type ){
        drv.setProperty("ConnectionType", type);
    }
//</editor-fold>
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="дата / время">
    //<editor-fold defaultstate="collapsed" desc="time : Date - Внутреннее время ККМ.">
    /**
     * Time <p> <p>
     * Внутреннее время ККМ.  <p>
     * Используется методом SetTime.  <p>
     * Модифицируется методом GetECRStatus, FNFindDocument, FNGetFiscalizationResult,
     * FNGetInfoExchangeStatus, FNGetOFDTicketByDocNumber, FNGetStatus.  <p> <p>
     * [id(0x000000e4), propget, helpstring("Время")] DATE Time(); <p>
     * [id(0x000000e4), propput, helpstring("Время")] void Time([in] DATE rhs);
     * @return --
     */
    @Override
    public synchronized Date getTime(){
        return drv.getProperty("Time").getJavaDate();
    }
    
    @Override
    public synchronized void setTime(Date date){
        if( date==null )throw new IllegalArgumentException("date==null");
        drv.setProperty("Time", new Variant(date));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="timeStr : String - Строковое представление свойства Time">
    /**
     * Строковое представление свойства Time. <p>
     * Используется методом SetTime.  <p>
     * Модифицируется методом GetECRStatus.  <p> <p>
     * [id(0x000000e6), propget, helpstring("ВремяСтрока")] BSTR TimeStr(); <p>
     * [id(0x000000e6), propput, helpstring("ВремяСтрока")] void TimeStr([in] BSTR rhs); <p>
     */
    @Override
    public synchronized String getTimeStr(){
        return drv.getProperty("TimeStr").getString();
    }
    
    @Override
    public synchronized void setTimeStr(String time){
        if( time==null )throw new IllegalArgumentException("time==null");
        drv.setProperty("TimeStr", time);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="date : Date - Дата">
    /**
     * Дата. <p><p>
     * Внутренняя  дата ККМ.  В зависимости от  метода может быть датой внутреннего времени ККМ,
     * датой фискализации (перерегистрации) и т.д. (см. описание методов).
     * <p><p>
     *
     * Используется методами SetDate, ConfirmDate.
     * Модифицируется методами GetECRStatus, GetLastFMRecordDate, Fiscalization,
     * GetFiscalizationParameters, FNBuildCalculationStateReport, FNFindDocument,
     * FNGetExpirationTime, FNGetFiscalizationResult, FNGetInfoExchangeStatus,
     * FNGetOFDTicketByDocNumber, FNGetStatus.
     * <p>
     * См. также: методы MFPGetPrepareActivizationResult, MFPPrepareActivization.
     *
     * <p><p>
     *
     * [id(0x0000007a), propget, helpstring("Дата")] DATE Date();<p>
     *
     * [id(0x0000007a), propput, helpstring("Дата")] void Date([in] DATE rhs);
     */
    
    @Override
    public synchronized Date getDate(){
        return drv.getProperty("Date").getJavaDate();
    }
    
    @Override
    public synchronized void setDate(Date d){
        if( d==null )throw new IllegalArgumentException("d==null");
        drv.setProperty("Date", new Variant(d) );
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SetDate() - Устанавливает  дату">
    /**
     * SetDate. <p> <p>
     * Устанавливает  дату  во  внутренних  часах  ККМ.   <p> <p>
     *
     * Перед  вызовом  метода  в  свойстве  Password
     * указать  пароль  системного  администратора  и  заполнить  свойство  Date,  в  котором  указать
     * текущую дату.  <p> <p>
     *
     * Работает только в режимах 4, 7 и 9 (см. свойство ECRMode).  <p>
     * Переводит ККМ в режим 6 (см. свойство ECRMode).  <p> <p>
     *
     * Используемые свойства  <p>
     * Password   - Пароль для исполнения метода драйвера. <p>
     * Date - Внутренняя дата ККМ.  <p> <p>
     * [id(0x00000056), helpstring("УстановитьДату")] long SetDate();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "date"),
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int SetDate(){
        return drv.invoke("SetDate").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="SetTime() - Устанавливает время во внутренних часах">
    /**
     * Устанавливает время во внутренних часах ККМ. <p> <p>
     * Перед  вызовом  метода  в  свойстве  Password  указать  пароль  системного  администратора  и
     * заполнить свойство Time, в котором указать текущее время.  <p> <p>
     * Работает в режимах 4, 7 и 9 (см. свойство ECRMode).  <p> <p>
     * Не меняет режима ККМ.  <p> <p>
     * Используемые свойства: <p>
     * Password   Пароль для исполнения метода драйвера. <p>
     * Time – Внутреннее время ККМ. <p>
     * TimeStr - Строковое представление свойства Time. <p> <p>
     * [id(0x0000005e), helpstring("УстановитьВремя")] long SetTime(); <p>
     * @return --
     */
    @InputProperties(properties = {
        @DriverProperty(name = "time"),
        @DriverProperty(name = "timeStr"),
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int SetTime(){
        return drv.invoke("SetTime").getInt();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="confirmDate() - подтверждения  программирования  даты ">
    /**
     * Команда  подтверждения  программирования  даты  во  внутренних  часах  ККМ.   <p> <p>
     * Перед  вызовом
     * метода  в  свойстве  Password  указать  пароль  системного  администратора  и  заполнить  свойство
     * Date, в котором указать текущую дату.  <p> <p>
     * Работает только в режиме 6 (см. свойство ECRMode).  <p> <p>
     * При успешном выполнении команды переводит ККМ в режим 4 (см. свойство ECRMode).  <p> <p>
     * Используемые свойства
     * Password   - Пароль для исполнения метода драйвера.
     * Date   - Внутренняя дата ККМ.  <p> <p>
     * [id(0x0000000c), helpstring("ПодтвердитьДату")] long ConfirmDate();
     * @return --
     */
    @InputProperties(properties = {
        @DriverProperty(name = "date"),
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
    })
    @CallState(state = "6")
    @Override
    public synchronized int confirmDate(){
        return drv.invoke("ConfirmDate").getInt();
    }
    //</editor-fold>
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="режим работы">
    //<editor-fold defaultstate="collapsed" desc="ECRMode : int - Режим ККМ">
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
     *  <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus. <p>
     *  <p>
     * [id(0x00000089), propget, helpstring("РежимККМ")] long ECRMode(); <p>
     * @return --
     */
    @Override
    public synchronized int getECRMode(){
        return drv.getPropertyAsInt("ECRMode");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ECRMode8Status : int - Статус режима 8">
    /**
     * Статус режима 8. <p> <p>
     *
     * Находясь в режиме 8, ККМ может быть в одном из состояний: <p>
     * Статус режима 8  Описание статуса режима ККМ  <p>
     * 0  Открыт чек продажи  <p>
     * 1  Открыт чек покупки  <p>
     * 2  Открыт чек возврата продажи  <p>
     * 3  Открыт чек возврата покупки  <p>
     *  <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus.  <p>
     * См. также: свойство ECRMode.  <p>
     *  <p>
     * [id(0x0000008a), propget, helpstring("Статус8Режима")] long ECRMode8Status(); <p>
     * @return --
     */
    @Override
    public synchronized int getECRMode8Status(){
        return drv.getPropertyAsInt("ECRMode8Status");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ECRModeDescription : String - Описание режима ККМ">
    /**
     * ECRModeDescription. <p>
     * <p>
     * Свойство содержит строку с описанием на русском языке режима ККМ (см. столбцы <p>
     * «Описание статуса режима ККМ» в описании свойств ECRMode и ECRModeStatus). <p>
     * Может использоваться вместо свойства ECRModeDescription, так как является его <p>
     * «расширенной» версией для описания статуса не только 8-го режима, но и всех остальных. <p>
     * Модифицируется методами GetECRStatus и GetShortECRStatus. <p>
     * <p>
     * [id(0x0000008b), propget, helpstring("ОписаниеРежимаККМ")] BSTR ECRModeDescription(); <p>
     */
    @Override
    public synchronized String getECRModeDescription(){
        return drv.getPropertyAsString("ECRModeDescription");
    }
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
    @Override
    public synchronized int getECRAdvancedMode(){
        return drv.getPropertyAsInt("ECRAdvancedMode");
    }
    
    /**
     * Свойство содержит строку с описанием на русском языке подрежима ККМ.
     * <p>
     * (см. столбец «Описание подрежима ККМ» в описании свойства ECRAdvancedMode).
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     * <p>
     * [id(0x00000085), propget, helpstring("ОписаниеПодрежимаККМ")] BSTR ECRAdvancedModeDescription();
     * @return
     */
    @Override
    public synchronized String getECRAdvancedModeDescription(){
        return drv.getPropertyAsString("ECRAdvancedModeDescription");
    }
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
    @Override
    public synchronized int getEmergencyStopCode(){
        return drv.getPropertyAsInt("EmergencyStopCode");
    }
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
     * @return
     */
    @Override
    public synchronized String getEmergencyStopCodeDescription(){
        return drv.getPropertyAsString("EmergencyStopCodeDescription");
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getShortECRStatus() - Метод запрашивает состояние ККМ">
    /**
     * GetShortECRStatus - Метод запрашивает состояние ККМ. <p>
     *
     * Перед вызовом метода в свойстве Password указать пароль оператора. <p>
     * После успешного выполнения команды заполняются свойства,  <p>
     * указанные в таблице «Модифицируемые свойства».  <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен. <p>
     * Метод может вызываться в любом режиме, кроме режима 1 (см. свойство ECRMode). <p>
     * Не меняет режима ККМ. <p>
     *  <p>
     * [id(0x00000028), helpstring("ПолучитьКороткийЗапросСостоянияККМ")] long GetShortECRStatus(); <p>
     * <p>
     *
     * Используемые свойства: Password<p>
     *
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber - Порядковый номер оператора, чей пароль был введен.
     * <li>ECRFlags Признаки (флаги) ККМ (раскладывается в следующее битовое поле)
     * <li>ReceiptRibbonIsPresent Признак наличия в ККМ рулона чековой ленты. FALSE – рулона чековой ленты нет, TRUE – рулон чековой ленты есть.
     * <li>JournalRibbonIsPresent Признак наличия в ККМ рулона операционного журнала. FALSE – рулона операционного журнала нет, TRUE – рулон
     * <li>SlipDocumentIsPresent Признак наличия в ККМ подкладного документа. FALSE – подкладного документа нет, TRUE – подкладной документ есть.
     * <li>SlipDocumentIsMoving Признак прохождения подкладного документа под датчиком контроля подкладного документа. FALSE – подкладной документ отсутствует под датчиком контроля подкладного документа, TRUE – подкладной документ проходит под датчиком.
     * <li>PointPosition Признак положения десятичной точки. FALSE – десятичная точка отделяет 0 разрядов, TRUE – десятичная точка отделяет 2 разряда.
     * <li>EKLZIsPresent Признак наличия в ККМ ЭКЛЗ. FALSE – ЭКЛЗ нет, TRUE – ЭКЛЗ есть.
     * <li>JournalRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE – чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li>ReceiptRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE – чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li>JournalRibbonLever Признак положения рычага термоголовки ленты операционного журнала TRUE – рычаг термоголовки ленты операционного журнала поднят; FALSE – рычаг термоголовки ленты опущен.
     * <li>ReceiptRibbonLever Признак положения рычага термоголовки чековой ленты. TRUE – рычаг термоголовки чековой ленты поднят; FALSE – рычаг термоголовки чековой ленты опущен.
     * <li>LidPositionSensor Признак положения крышки корпуса. TRUE – крышка корпуса не установлена; FALSE – крышка корпуса установлена.
     * <li>IsPrinterLeftSensorFailure Признак отказа левого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE – имеет место отказ датчика.
     * <li>IsPrinterRightSensorFailure Признак отказа правого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE – имеет место отказ датчика.
     * <li>IsDrawerOpen Признак состояния денежного ящика. TRUE – денежный ящик открыт; FALSE – денежный ящик закрыт
     * <li>IsEKLZOverflow Признак состояния ЭКЛЗ. TRUE – ЭКЛЗ близка к переполнению, FALSE – ЭКЛЗ ещё не близка к переполнению.
     * <li>QuantityPointPosition Признак положения десятичной точки в количестве товара. TRUE – 3 знака после запятой; FALSE – 6 знаков.
     * <li>ECRMode Режим ККМ, т.е. одно из состояний ККМ, в котором она может находиться (расшифровку режимов смотри в описании свойства)
     * <li>ECRModeDescription Свойство содержит строку с описанием на русском языке режима ККМ (см. столбец «Описание режима ККМ» в свойстве
     * <li>ECRMode8Status Одно из состояний, когда ККМ находится в режиме 8:
     * <li>ECRModeStatus Одно из состояний, когда ККМ находится в режимах 13 и 14.
     * <li>ECRAdvancedMode Подрежим ККМ – одно из подсостояний ККМ, в котором она может находиться. Подрежимы предназначены для корректного завершения операций при печати документов в случае нештатных ситуаций.
     * <li>ECRAdvancedModeDescription Свойство содержит строку с описанием на русском языке подрежима ККМ (см. столбец «Описание подрежима ККМ» в свойстве ECRAdvancedMode).
     * <li>QuantityOfOperations Количество выполненных операций регистрации (продаж, покупок, возвратов продаж или возвратов покупок) в чеке.
     * <li>BatteryVoltage Напряжение резервной батареи.
     * <li>PowerSourceVoltage Напряжение источника питания.
     * <li>FMResultCode Код ошибки ФП.
     * <li>EKLZResultCode Код ошибки ЭКЛЗ.
     * </ul>
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "ECRFlags"),
        @DriverProperty(name = "receiptRibbonIsPresent"),
        @DriverProperty(name = "journalRibbonIsPresent"),
        @DriverProperty(name = "slipDocumentIsPresent"),
        @DriverProperty(name = "slipDocumentIsMoving"),
        @DriverProperty(name = "pointPosition"),
        @DriverProperty(name = "EKLZIsPresent"),
        @DriverProperty(name = "journalRibbonOpticalSensor"),
        @DriverProperty(name = "receiptRibbonOpticalSensor"),
        @DriverProperty(name = "journalRibbonLever"),
        @DriverProperty(name = "receiptRibbonLever"),
        @DriverProperty(name = "lidPositionSensor"),
        @DriverProperty(name = "printerLeftSensorFailure"),
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
        @DriverProperty(name = "EKLZResultCode"),
        @DriverProperty(name = "FMResultCode"),
        @DriverProperty(name = "powerSourceVoltage"),
        @DriverProperty(name = "batteryVoltage"),
        @DriverProperty(name = "quantityOfOperations"),
        @DriverProperty(name = "ECRAdvancedModeDescription"),
        @DriverProperty(name = "ECRModeStatus"),
        @DriverProperty(name = "ECRMode8Status"),
        @DriverProperty(name = "ECRAdvancedMode"),
        @DriverProperty(name = "ECRModeDescription"),
        @DriverProperty(name = "ECRMode"),
        @DriverProperty(name = "quantityPointPosition"),
        @DriverProperty(name = "EKLZOverflow"),
        @DriverProperty(name = "drawerOpen"),
        @DriverProperty(name = "printerRightSensorFailure"),
    })
    @Override
    public synchronized int getShortECRStatus(){
        return drv.invoke("GetShortECRStatus").getInt();
    }
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
     * <li> OpenDocumentNumber  Сквозной номер последнего документа ККМ.
     * <li> ECRFlags Признаки (флаги) ККМ (раскладывается в следующее битовое поле)
     * <li> ReceiptRibbonIsPresent Признак наличия в ККМ рулона чековой ленты. FALSE – рулона чековой ленты нет, TRUE – рулон чековой ленты есть.
     * <li> JournalRibbonIsPresent Признак наличия в ККМ рулона операционного журнала. FALSE – рулона операционного журнала нет, TRUE – рулон есть
     * <li> SKNOStatus Последний статус СКНО (Для белорусских ККТ).
     * <li> SlipDocumentIsPresent Признак наличия в ККМ подкладного документа. FALSE – подкладного документа нет, TRUE – подкладной документ есть.
     * <li> SlipDocumentIsMoving Признак прохождения подкладного документа под датчиком контроля подкладного документа. FALSE – подкладной документ
     * отсутствует под датчиком контроля подкладного документа, TRUE – подкладной документ проходит под датчиком.
     * <li> PointPosition Признак положения десятичной точки. FALSE – десятичная точка отделяет 0 разрядов, TRUE
     * – десятичная точка отделяет 2 разряда.
     * <li> EKLZIsPresent Признак наличия в ККМ ЭКЛЗ. FALSE - ЭКЛЗ нет, TRUE – ЭКЛЗ есть.
     * <li> JournalRibbonOpticalSensor Признак прохождения ленты операционного журнала под оптическим датчиком
     * операционного журнала. FALSE – ленты операционного журнала нет под оптическим датчиком; TRUE – лента операционного
     * журнала проходит под оптическим датчиком.
     * <li> ReceiptRibbonOpticalSensor Признак прохождения чековой ленты под оптическим датчиком чековой ленты. FALSE –
     * чековой ленты нет под оптическим датчиком; TRUE – чековая лента проходит под оптическим датчиком.
     * <li> JournalRibbonLever Признак положения рычага термоголовки ленты операционного журнала TRUE – рычаг
     * термоголовки ленты операционного журнала поднят; FALSE – рычаг термоголовки ленты опущен.
     * <li> ReceiptRibbonLever Признак положения рычага термоголовки чековой ленты. TRUE – рычаг термоголовки
     * чековой ленты поднят; FALSE – рычаг термоголовки чековой ленты опущен.
     * <li> LidPositionSensor Признак положения крышки корпуса. TRUE – крышка корпуса не установлена; FALSE –
     * <li> IsPrinterLeftSensorFailure Признак отказа левого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE
     * – имеет место отказ датчика.
     * <li> IsPrinterRightSensorFailure Признак отказа правого датчика печатающего механизма. FALSE – отказа датчика нет, TRUE
     * – имеет место отказ датчика.
     * <li> IsDrawerOpen Признак состояния денежного ящика. TRUE – денежный ящик открыт; FALSE – денежный ящик закрыт
     * <li> IsEKLZOverflow Признак состояния ЭКЛЗ. TRUE – ЭКЛЗ близка к переполнению, FALSE – ЭКЛЗ ещё не близка к переполнению.
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
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "ECRBuild"),
        @DriverProperty(name = "logicalNumber"),
        @DriverProperty(name = "openDocumentNumber"),
        @DriverProperty(name = "ECRFlags"),
        @DriverProperty(name = "receiptRibbonIsPresent"),
        @DriverProperty(name = "journalRibbonIsPresent"),
        @DriverProperty(name = "SKNOStatus"),
        @DriverProperty(name = "slipDocumentIsPresent"),
        @DriverProperty(name = "slipDocumentIsMoving"),
        @DriverProperty(name = "pointPosition"),
        @DriverProperty(name = "EKLZIsPresent"),
        @DriverProperty(name = "journalRibbonOpticalSensor"),
        @DriverProperty(name = "receiptRibbonOpticalSensor"),
        @DriverProperty(name = "journalRibbonLever"),
        @DriverProperty(name = "receiptRibbonLever"),
        @DriverProperty(name = "lidPositionSensor"),
        @DriverProperty(name = "printerLeftSensorFailure"),
        @DriverProperty(name = "printerRightSensorFailure"),
        @DriverProperty(name = "drawerOpen"),
        @DriverProperty(name = "EKLZOverflow"),
        @DriverProperty(name = "quantityPointPosition"),
        @DriverProperty(name = "ECRMode"),
        @DriverProperty(name = "ECRModeDescription"),
        @DriverProperty(name = "ECRMode8Status"),
        @DriverProperty(name = "ECRModeStatus"),
        @DriverProperty(name = "ECRAdvancedMode"),
        @DriverProperty(name = "ECRAdvancedModeDescription"),
        @DriverProperty(name = "portNumber"),
        @DriverProperty(name = "FMSoftVersion"),
        @DriverProperty(name = "FMBuild"),
        @DriverProperty(name = "FMSoftDate"),
        @DriverProperty(name = "date"),
        @DriverProperty(name = "time"),
        @DriverProperty(name = "timeStr"),
        @DriverProperty(name = "FMFlags"),
        @DriverProperty(name = "FM1IsPresent"),
        @DriverProperty(name = "FM2IsPresent"),
        @DriverProperty(name = "licenseIsPresent"),
        @DriverProperty(name = "FMOverflow"),
        @DriverProperty(name = "batteryLow"),
        @DriverProperty(name = "lastFMRecordCorrupted"),
        @DriverProperty(name = "FMSessionOpen"),
        @DriverProperty(name = "FM24HoursOver"),
        @DriverProperty(name = "serialNumber"),
        @DriverProperty(name = "sessionNumber"),
        @DriverProperty(name = "freeRecordInFM"),
        @DriverProperty(name = "registrationNumber"),
        @DriverProperty(name = "freeRegistration"),
    })
    @Override
    public synchronized int getECRStatus(){
        return drv.invoke("GetECRStatus").getInt();
    }
    //</editor-fold>
    
    
    //<editor-fold defaultstate="collapsed" desc="continuePrint()">
    /**
     * Команда возобновления печати после заправки в ККМ бумаги.
     * <p>
     * После заправки бумаги ККМ
     * находится в подрежиме 3 (см. свойство ECRAdvancedMode) до тех пор, пока не будет вызван
     * данный метод.
     * Перед вызовом метода в свойстве Password указать пароль оператора.
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.
     * Работает в любом режиме, но только в подрежиме 3 (см. свойства ECRMode,
     * ECRAdvancedMode).
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
        @DriverProperty(name = "operatorNumber"),
    })
    @CallState(state = "*.3")
    @Override
    public synchronized int continuePrint() {
        return drv.invoke("ContinuePrint").getInt();
    }
    //</editor-fold>
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
     * @return subj
     */
    @Override
    public synchronized boolean isEKLZOverflow() {
        return drv.getPropertyAsBoolean("IsEKLZOverflow");
    }
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
     * @return subj
     */
    @Override
    public synchronized boolean isFM1IsPresent() {
        return drv.getPropertyAsBoolean("FM1IsPresent");
    }
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
     * [id(0x0000009a), propget, helpstring("ФП2Есть")]  VARIANT_BOOL FM2IsPresent();
     * @return subj
     */
    @Override
    public synchronized boolean isFM2IsPresent() {
        return drv.getPropertyAsBoolean("FM2IsPresent");
    }
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
    @Override
    public synchronized int getPortNumber() {
        return drv.getPropertyAsInt("PortNumber");
    }
    
    @Override
    public synchronized void setPortNumber(int v) {
        drv.setProperty("PortNumber", v);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="openSession() - Открыть Смену">
    /**
     * Метод  передает  команду  «E0h»,  при  этом  в  ФП  открывается  смена,  а  ККТ  переходит  в  режим
     * «Открытой смены».
     * <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора, который открыл текущий
     * чек.
     * <p>
     * Используемые свойства
     * <p>
     * Password   Целое  до 8 разрядов  RW  Пароль для исполнения метода драйвера.
     * <p>
     * [id(0x00000260), helpstring("ОткрытьСмену")]
     * long OpenSession();
     */
    @CallState(state = "")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "resultCode"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int openSession(){
        return drv.invoke("OpenSession").getInt();
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="resetECR() - СбросККМ">
    /**
     * СбросККМ.
     * <p>
     * Метод выполняет следующую последовательность действий
     *
     * <ol>
     * <li> Выполняет команду ожидания печати (WaitForPrinting ).
     *
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
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "resultCode"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @CallState()
    @Override
    public synchronized int resetECR() {
        return drv.invoke("ResetECR").getInt();
    }
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
     */
    @CallState()
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "waitForPrintingDelay"),
        @DriverProperty(name = "connectionTimeout"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "resultCode"),
        @DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int waitForPrinting() {
        return drv.invoke("WaitForPrinting").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="waitForPrintingDelay : int - Задержка в мс, использующаяся в методе WaitForPrinting">
    /**
     * WaitForPrintingDelay.
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
    @Override
    public synchronized int getWaitForPrintingDelay(){
        return drv.getPropertyAsInt("WaitForPrintingDelay");
    }
    @Override
    public synchronized void setWaitForPrintingDelay( int t ){
        drv.setProperty("WaitForPrintingDelay", t);
    }
//</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="сonnectionTimeout : int - Таймаут подключения">
    /**
     * Таймаут подключения.
     * Используется методами WaitConnection, WaitForPrinting
     * @return
     */
    @Override
    public synchronized int getConnectionTimeout(){
        return drv.getPropertyAsInt("ConnectionTimeout");
    }
    
    @Override
    public synchronized void setConnectionTimeout( int v ){
        drv.setProperty("ConnectionTimeout", v);
    }
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
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "resultCode"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int interruptDataStream() {
        return drv.invoke("InterruptDataStream").getInt();
    }
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
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int interruptTest() {
        return drv.invoke("InterruptTest").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="resetSettings() - Технологическое обнуление">
    /**
     * Технологическое обнуление.
     * <p>
     * Метод производит операцию технологического обнуления.
     * Технологическое обнуление доступно только после вскрытия пломбы на кожухе ККМ и
     * выполнения последовательности действий, описанных в ремонтной документации на ККМ.
     * Работает в режиме 9 (см. свойство ECRMode).
     * <p>
     * [id(0x0000004d), helpstring("ТехнологическоеОбнуление")] long ResetSettings();
     */
    @CallState(state = "9")
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int resetSettings() {
        return drv.invoke("ResetSettings").getInt();
    }
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
    @InputProperties(properties = {
        @DriverProperty(name = "password")
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int resetSummary(){
        return drv.invoke("ResetSummary").getInt();
    }
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
     * Работает только в режиме 7 (см. свойство ECRMode).
     * Не меняет режима ККМ.
     * <p>
     * Используемые свойства
     * <p>
     * Password Пароль для исполнения метода драйвера.
     * <p>
     * PointPosition (Логич.) Признак положения десятичной точки.<p>
     * FALSE – десятичная точка отделяет 0 разрядов,<p>
     * TRUE – десятичная точка отделяет 2 разряда.
     * <p>
     * [id(0x0000005b), helpstring("УстановитьПоложенияТочки")] long SetPointPosition();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "pointPosition"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int SetPointPosition() {
        return drv.invoke("SetPointPosition").getInt();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="pointPosition : boolean - Положение десятичной точки">
    /**
     * ПоложениеТочки.
     * <p>
     * Признак положения десятичной точки. <p>
     * FALSE – десятичная точка отделяет 0 разрядов, <p>
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
    @Override
    public synchronized boolean isPointPosition() {
        return drv.getPropertyAsBoolean("PointPosition");
    }
    
    @Override
    public synchronized void setPointPosition(boolean p) {
        drv.setProperty("PointPosition", p);
    }
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
    @InputProperties(properties = {
//        @DriverProperty(name = "password"),
//        @DriverProperty(name = "pointPosition"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @CallState()
    @Override
    public synchronized int saveState() {
        return drv.invoke("SaveState").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="restoreState() - Восстановить состояние">
    /**
     * Восстановить состояние.
     * <p>
     * Восстанавливает сохраненные ранее с помощью метода SaveState значения всех свойств драйвера.
     * <p>
     * [id(0x000002ab), helpstring("ВосстановитьСостояние")] long RestoreState();
     */
    @CallState()
    @InputProperties(properties = {
//        @DriverProperty(name = "password"),
//        @DriverProperty(name = "pointPosition"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int restoreState() {
        return drv.invoke("RestoreState").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="лицензии">
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
        @DriverProperty(name = "password"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "license"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int readLicense() {
        return drv.invoke("ReadLicense").getInt();
    }
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
     */
    @CallState(state = "!1")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "license"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int writeLicense() {
        return drv.invoke("WriteLicense").getInt();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="license : String - Текстовый параметр, содержащий лицензию">
    /**
     * Текстовый параметр, содержащий лицензию (см. «Инструкцию по эксплуатации»/«Руководство
     * оператора»). Допустимая длина строки: до 5 символов. Допустимы только символы «0», «1», «2»,
     * «3», «4», «5», «6», «7», «8» и «9».
     * Используется методом WriteLicense .  Модифицируется методом   ReadLicense.
     *
     * [id(0x000000ae), propget, helpstring("Лицензия")]
     * BSTR License();
     *
     * [id(0x000000ae), propput, helpstring("Лицензия")]
     * void License([in] BSTR rhs);
     * @return
     */
    @Override
    public synchronized String getLicense(){
        return drv.getPropertyAsString("License");
    }
    
    @Override
    public synchronized void setLicense(String str){
        if( str==null )throw new IllegalArgumentException( "str==null" );
        drv.setProperty("License", str);
    }
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
    @Override
    public synchronized boolean isLicenseIsPresent() {
        return drv.getPropertyAsBoolean("LicenseIsPresent");
    }
    //</editor-fold>
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="stringForPrinting : String - Строка для печати">
    /**
     * Строка для печати. <p> <p>
     *
     * Строка символов кодовой таблицы WIN1251 для печати.  <p>
     * В  случае,  когда  свойство  используется  методами  PrintString,  PrintWideString,
     * PrintStringWithFont,  в  свойстве  передается  до  249,  249  и  248  символов  соответственно.
     *  <p> <p>
     *
     * Но количество  символов,  которые  будут  выведены  на  печать,  зависит  от  модели  ККМ,  ширины
     * строки на ленте, параметров настроек ККМ (например, перенос длинных строк), шрифта, которым
     * печатается  строка.   <p> <p>
     *
     * В  методах  Sale,  SaleEx,  Buy,  BuyEx,  ReturnSale,  ReturnSaleEx,  ReturnBuy,
     * ReturnBuyEx,  Storno,  StornoEx,  CloseCheck,  Discount,  Charge,  StornoDiscount,  StornoCharge,
     * FNCloseCheckEx,  FNDiscountOperation,  FNStorno,  длина  строки  не  превышает  40  символов
     * (печатается на чеке в строке, идущей перед строкой, содержащей цену/сумму и/или количество).  <p> <p>
     *
     * При  использовании  методами  RegistrationOnSlipDocument, StandardRegistrationOnSlipDocument,
     * ChargeOnSlipDocument, StandardChargeOnSlipDocument,  DiscountOnSlipDocument,
     * StandardDiscountOnSlipDocument, CloseCheckOnSlipDocument,  StandardCloseCheckOnSlipDocument  и
     * FillSlipDocumentWithUnfiscalInfo данное свойство заполняется символами для вывода на ПД
     * (не более 250 символов). <p><p>
     *
     * [id(0x000000d8), propget, helpstring("СтрокаДляПечати")] BSTR StringForPrinting();<p>
     * [id(0x000000d8), propput, helpstring("СтрокаДляПечати")] void StringForPrinting([in] BSTR rhs);
     * @return --
     */
    @Override
    public synchronized String getStringForPrinting(){
        return drv.getPropertyAsString("StringForPrinting");
    }
    
    @Override
    public synchronized void setStringForPrinting( String str ){
        if( str==null )throw new IllegalArgumentException("str==null");
        drv.setProperty("StringForPrinting", str);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="stringQuantity : int - Количество строк">
    /**
     * Количество строк. <p> <p>
     *
     * Количество строк, на которое необходимо продвинуть документ.  <p> <p>
     *
     * Диапазон значений: 1..255 (максимальное количество строк определяется
     * размером буфера печати, но не превышает 255).  <p> <p>
     *
     * Используется методом FeedDocument. <p><p>
     *
     * [id(0x000000d9), propget, helpstring("КоличествоСтрок")] long StringQuantity();<p>
     * [id(0x000000d9), propput, helpstring("КоличествоСтрок")] void StringQuantity([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getStringQuantity(){
        return drv.getPropertyAsInt("StringQuantity");
    }
    
    @Override
    public synchronized void setStringQuantity( int q ){
        drv.setProperty("StringQuantity", q);
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="summ1 : Currency - Сумма1">
    /**
     * Сумма1 <p> <p>
     *
     * Свойство, используемое для хранения различных значений денежных сумм. <p>
     * Используется методами CashIncome, CashOutcome, CloseCheck, Discount, Charge,
     * StornoDiscount, StornoCharge, ChargeOnSlipDocument, StandardChargeOnSlipDocument,
     * DiscountOnSlipDocument, StandardDiscountOnSlipDocument, CloseCheckOnSlipDocument,
     * CloseCheckEx, FNBuildCorrectionReceipt,.FNCloseCheckEx.
     * Модифицируется  методами   GetFMRecordsSum,  CheckSubTotal,  GetEKLZCode2Report,
     * FNFindDocument, FNStorno. <p> <p>
     * 
     * [id(0x000000da), propget, helpstring("Сумма1")] CURRENCY Summ1(); <p>
     * [id(0x000000da), propput, helpstring("Сумма1")] void Summ1([in] CURRENCY rhs);
     * @return --
     */
    @Override
    public synchronized com.jacob.com.Currency getSumm1(){
        return drv.getProperty("Summ1").getCurrency();
    }
    @Override
    public synchronized void setSumm1( com.jacob.com.Currency cur ){
        if( cur==null )throw new IllegalArgumentException("cur==null");
        drv.setProperty("Summ1", new Variant(cur));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="summ2 : Currency - Сумма2">
    /**
     * Сумма2 <p> <p>
     *
     * Свойство, используемое для хранения различных значений денежных сумм.  <p>
     * Используется методами CloseCheck, CloseCheckOnSlipDocument, 
     * StandardCloseCheckOnSlipDocument, CloseCheckEx, FNCloseCheckEx (в свойство записывается 
     * сумма типа оплаты 2).  <p> <p>
     * 
     * Модифицируется методами   GetFMRecordsSum, GetEKLZCode2Report, FNStorno. <p> <p>
     * 
     * [id(0x000000db), propget, helpstring("Сумма2")] CURRENCY Summ2(); <p>
     * [id(0x000000db), propput, helpstring("Сумма2")] void Summ2([in] CURRENCY rhs); <p>
     * @return --
     */
    @Override
    public synchronized com.jacob.com.Currency getSumm2(){
        return drv.getProperty("Summ2").getCurrency();
    }
    @Override
    public synchronized void setSumm2( com.jacob.com.Currency cur ){
        if( cur==null )throw new IllegalArgumentException("cur==null");
        drv.setProperty("Summ2", new Variant(cur));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="summ3 : Currency - Сумма3">
    /**
     * Сумма3 <p> <p>
     *
     * Свойство, используемое для хранения различных значений денежных сумм. 
     * Используется методами CloseCheck, CloseCheckOnSlipDocument, 
     * StandardCloseCheckOnSlipDocument, CloseCheckEx FNCloseCheckEx (в свойство записывается 
     * сумма типа оплаты 3).  <p> <p>
     * 
     * Модифицируется методами   GetFMRecordsSum, GetEKLZCode2Report.  <p> <p>
     * 
     * [id(0x000000dc), propget, helpstring("Сумма3")] CURRENCY Summ3(); <p>
     * [id(0x000000dc), propput, helpstring("Сумма3")] void Summ3([in] CURRENCY rhs); <p>
     * @return --
     */
    @Override
    public synchronized com.jacob.com.Currency getSumm3(){
        return drv.getProperty("Summ3").getCurrency();
    }
    @Override
    public synchronized void setSumm3( com.jacob.com.Currency cur ){
        if( cur==null )throw new IllegalArgumentException("cur==null");
        drv.setProperty("Summ3", new Variant(cur));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="summ4 : Currency - Сумма4">
    /**
     * Сумма4 <p> <p>
     *
     * Свойство, используемое для хранения различных значений денежных сумм. 
     * Используется методами CloseCheck, CloseCheckOnSlipDocument, 
     * StandardCloseCheckOnSlipDocument, CloseCheckEx, FNCloseCheckEx (в свойство записывается 
     * сумма типа оплаты 4).  <p>
     * Модифицируется методами   GetFMRecordsSum, GetEKLZCode2Report.  <p> <p>
     * 
     * [id(0x000000dd), propget, helpstring("Сумма4")] CURRENCY Summ4(); <p>
     * [id(0x000000dd), propput, helpstring("Сумма4")] void Summ4([in] CURRENCY rhs); <p>
     * @return --
     */
    @Override
    public synchronized com.jacob.com.Currency getSumm4(){
        return drv.getProperty("Summ4").getCurrency();
    }
    @Override
    public synchronized void setSumm4( com.jacob.com.Currency cur ){
        if( cur==null )throw new IllegalArgumentException("cur==null");
        drv.setProperty("Summ4", new Variant(cur));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="discountOnCheck : double - Скидка на чек">
    /**
     * Скидка на чек. <p> <p>
     * Диапазон значений: 0…99,99.  <p>
     * Используется методами CloseCheck, CloseCheckOnSlipDocument,
     * StandardCloseCheckOnSlipDocument FNCloseCheckEx .   <p>  <p>
     *
     * [id(0x0000007e), propget, helpstring("СкидкаНаЧек")] double DiscountOnCheck();  <p>
     * [id(0x0000007e), propput, helpstring("СкидкаНаЧек")] void DiscountOnCheck([in] double rhs);
     * @return --
     */
    @Override
    public synchronized double getDiscountOnCheck(){
        return drv.getProperty("DiscountOnCheck").getDouble();
    }
    @Override
    public synchronized void setDiscountOnCheck(double v){
        drv.setProperty("DiscountOnCheck", new Variant(v));
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="price : Currency - Цена за единицу товара">
    /**
     * Цена за единицу товара. <p> <p>
     * Используется  методами   Sale,  SaleEx,  Buy,  BuyEx,  ReturnSale,  ReturnSaleEx,  ReturnBuy,
     * ReturnBuyEx,  Storno,  StornoEx,  SetRKParameters,  RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, FNDiscountOperation.   <p> <p>
     *
     * Модифицируется методом GetRKStatus, FNStorno.  <p> <p>
     *
     * [id(0x000000c0), propget, helpstring("Цена")] CURRENCY Price(); <p>
     * [id(0x000000c0), propput, helpstring("Цена")] void Price([in] CURRENCY rhs);
     * @return --
     */
    @Override
    public synchronized Currency getPrice(){
        return drv.getProperty("Price").getCurrency();
    }
    @Override
    public synchronized void setPrice(Currency cur){
        if( cur==null )throw new IllegalArgumentException("cur==null");
        drv.setProperty("Price", new Variant(cur));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="quantity : double - Количество  товара">
    /**
     * Количество  товара. <p> <p>
     *
     * Используется  методами  Sale,  Buy,  ReturnSale,  ReturnBuy,  Storno,
     * RegistrationOnSlipDocument,  StandardRegistrationOnSlipDocument,  FNDiscountOperation,
     * FNStorno (диапазон значений от 0,001 до 9999999,999, то есть округляется до трёх знаков после
     * запятой),  а  также  методами  SaleEx,  BuyEx,  ReturnSaleEx,  ReturnBuyEx,  StornoEx
     * (диапазон значений от 0,000001 до 9999999,999999, то есть округляется до шести знаков после запятой).  <p> <p>
     *
     * [id(0x000000c1), propget, helpstring("Количество")] double Quantity(); <p>
     * [id(0x000000c1), propput, helpstring("Количество")] void Quantity([in] double rhs);
     * @return --
     */
    @Override
    public synchronized double getQuantity(){
        return drv.getProperty("Quantity").getDouble();
    }
    
    @Override
    public synchronized void setQuantity(double v){
        drv.setProperty("Quantity", new Variant(v));
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="department : int -  Номер отдела (секции)">
    /**
     * Номер отдела (секции). <p> <p>
     *
     * Диапазон значений: 0…16.  <p> <p>
     * Используется методами
     * Sale, SaleEx, Buy, BuyEx, ReturnSale, ReturnSaleEx, ReturnBuy,
     * ReturnBuyEx, Storno, StornoEx, EKLZDepartmentReportInDatesRange,
     * EKLZDepartmentReportInSessionsRange, GetEKLZDepartmentReportInDatesRange,
     * GetEKLZDepartmentReportInSessionsRange, RegistrationOnSlipDocument,
     * StandardRegistrationOnSlipDocument, FNDiscountOperation, FNStorno.  <p> <p>
     *
     * [id(0x0000007b), propget, helpstring("Отдел")] long Department(); <p>
     * [id(0x0000007b), propput, helpstring("Отдел")] void Department([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getDepartment(){
        return drv.getPropertyAsInt("Department");
    }
    @Override
    public synchronized void setDepartment(int dep){
        drv.setProperty("Department",dep);
    }
//</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="tax1 : int - 1-ый номер налоговой группы">
    /**
     * 1-ый номер налоговой группы.  <p> <p>
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx, 
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge, 
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument, 
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument, 
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument, 
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, 
     * FNDiscountOperation, FNStorno, а так же всеми методами регистрации продаж нефтепродуктов и 
     * методом. 
     *  <p> <p>
     * 
     * [id(0x000000e0), propget, helpstring("Налог1")] long Tax1(); <p>
     * [id(0x000000e0), propput, helpstring("Налог1")] void Tax1([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getTax1(){
        return drv.getPropertyAsInt("Tax1");
    }
    @Override
    public synchronized void setTax1(int dep){
        drv.setProperty("Tax1",dep);
    }
    //</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="tax2 : int - 2-ый номер налоговой группы">
    /**
     * 2-ой номер налоговой группы.  <p> <p>
     * 
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx, 
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge, 
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument, 
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument, 
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument, 
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же 
     * всеми методами регистрации продаж нефтепродуктов и методом. 
     * Диапазон значений: 0..4 (0 – нет налоговой группы). 
     *  <p> <p>
     * 
     * [id(0x000000e1), propget, helpstring("Налог2")] long Tax2();  <p>
     * [id(0x000000e1), propput, helpstring("Налог2")] void Tax2([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getTax2(){
        return drv.getPropertyAsInt("Tax2");
    }
    @Override
    public synchronized void setTax2(int dep){
        drv.setProperty("Tax2",dep);
    }
    //</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="tax3 : int - 3-ый номер налоговой группы">
    /**
     * 3-ой номер налоговой группы.  <p> <p>
     * 
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx, 
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge, 
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument, 
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument, 
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument, 
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же 
     * всеми методами регистрации продаж нефтепродуктов и методом. 
     * Диапазон значений: 0..4 (0 – нет налоговой группы).
     *  <p> <p>
     * 
     * [id(0x000000e2), propget, helpstring("Налог3")] long Tax3(); <p> 
     * [id(0x000000e2), propput, helpstring("Налог3")] void Tax3([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getTax3(){
        return drv.getPropertyAsInt("Tax3");
    }
    @Override
    public synchronized void setTax3(int dep){
        drv.setProperty("Tax3",dep);
    }
    //</editor-fold>
  
    //<editor-fold defaultstate="collapsed" desc="tax4 : int - 4-ый номер налоговой группы">
    /**
     * 4-ой номер налоговой группы.  <p> <p>
     * 
     * Используется методами регистрации Sale, SaleEx, Buy, BuyEx, 
     * ReturnSale, ReturnSaleEx, ReturnBuy, ReturnBuyEx, Storno, StornoEx, Charge, StornoCharge, 
     * Discount, StornoDiscount, CloseCheck, RegistrationOnSlipDocument, 
     * StandardRegistrationOnSlipDocument, ChargeOnSlipDocument, 
     * StandardChargeOnSlipDocument, DiscountOnSlipDocument, StandardDiscountOnSlipDocument, 
     * CloseCheckOnSlipDocument, StandardCloseCheckOnSlipDocument, FNCloseCheckEx, а так же 
     * всеми методами регистрации продаж нефтепродуктов и методом. 
     * Диапазон значений: 0..4 (0 – нет налоговой группы). 
     *  <p> <p>
     * 
     * [id(0x000000e3), propget, helpstring("Налог4")] long Tax4(); <p>
     * [id(0x000000e3), propput, helpstring("Налог4")] void Tax4([in] long rhs);
     * @return --
     */
    @Override
    public synchronized int getTax4(){
        return drv.getPropertyAsInt("Tax4");
    }
    @Override
    public synchronized void setTax4(int dep){
        drv.setProperty("Tax4",dep);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="saleEx() - Продажа Точно">
    /**
     * ПродажаТочно. <p> 
     *
     * Продажа – торговая операция, при которой товар перемещается от оператора к клиенту, а деньги –
     * в обратном направлении: от клиента к оператору.  <p> 
     * Команда  производит  регистрацию  продажи  определенного  количества  товара  в  определенную
     * секцию  с  вычислением  налогов  (см.  «Инструкцию  по  эксплуатации»/«Руководство
     * оператора») без закрытия чека.  <p> 
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в  таблице  используемые  свойства. <p> 
     * В  свойстве  SysAdminPassword  должен  быть  указан  пароль
     * системного администратора.  <p> 
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен. <p> 
     * Работает  в  режимах  2  (проверка  на  окончание  24  часов  производится  запросом  из  ФП  до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=0) и 9 (см. свойства ECRMode и
     * ECRMode8Status).  <p> 
     * Переводит ККМ в режим 8 подрежим 0, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).  <p> 
     *
     * <b>Используемые свойства</b>
     * <ul>
     * <li>Password - Пароль для исполнения метода драйвера
     * <li>SysAdminPassword   - Пароль системного администратора для исполнения метода драйвера.
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
     *
     * Модифицируемые свойства
     * <ul>
     * <li>OperatorNumber   - Порядковый номер оператора, чей пароль был введен.
     * </ul>
     *
     * Внимание!: Данный метод SaleEx отличается от метода Sale лишь тем, что в методе SaleEx
     * округление количества (см. свойство Quantity) идёт не до трёх знаков после запятой, а до
     * шести знаков.  <p> <p>
     *
     * [id(0x00000054), helpstring("ПродажаТочно")] long SaleEx();
     * @return --
     */
    @CallState(state = "2|4|7|8.0|9")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "sysAdminPassword"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int saleEx(){
        return drv.invoke("SaleEx").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="sysAdminPassword : int - Значение пароля системного администратора">
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
    @Override
    public synchronized int getSysAdminPassword(){
        return drv.getPropertyAsInt("SysAdminPassword");
    }
    @Override
    public synchronized void setSysAdminPassword(int v){
        drv.setProperty("SysAdminPassword", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="sale() - Продажа">
    /**
     * Продажа. 
     * <p>
     *
     * Продажа – торговая операция, при которой товар перемещается от оператора к клиенту,
     * а деньги – в обратном направлении: от клиента к оператору.  <p> 
     * Команда  производит  регистрацию  продажи  определенного  количества  товара  в  определенную
     * секцию  с  вычислением  налогов  (см.  «Инструкцию  по  эксплуатации»/«Руководство оператора»)
     * без закрытия чека.  <p> 
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.  <p> 
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p> 
     *
     * Работает  в  режимах  2  (проверка  на  окончание  24  часов  производится  запросом  из  ФП  до
     * выполнения операции), 4, 7, 8 (если статус 8-го режима ККМ=0) и 9 (см. свойства ECRMode и
     * ECRMode8Status).  <p> 
     * Переводит ККМ в режим 8 подрежим 0, или из режима 2 в режим 3 при истечении 24 часов смены
     * (см. свойства ECRMode, ECRMode8Status).  <p> 
     *
     * <b>Используемые свойства</b>
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
     *
     * [id(0x00000053), helpstring("Продажа")] long Sale();
     * @return
     */
    @CallState(state = "2|4|7|8.0|9")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "resultCodeDescription"),
    })
    @Override
    public synchronized int sale(){
        return drv.invoke("Sale").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="closeCheck() - закрытие  чека">
    /**
     * Метод  производит  закрытие  чека  комбинированным  типом  оплаты  с  вычислением  налогов  и
     * суммы сдачи. <p> 
     *
     * Перед вызовом метода в свойстве Password указать пароль оператора и заполнить перечисленные
     * в таблице используемые свойства.  <p> 
     *
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p>
     * В свойстве Change возвращается сумма сдачи.  <p>
     * Работает в режиме 8 (см. свойство ECRMode).  <p>
     * Переводит ККМ в режим 2 или 3 (см. свойство ECRMode).  <p>
     *
     * Используемые свойства
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
     * [id(0x0000000a), helpstring("ЗакрытьЧек")] long CloseCheck();
     * @return Код ошибки или 0
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
        @DriverProperty(name = "summ2"),
        @DriverProperty(name = "summ3"),
        @DriverProperty(name = "summ4"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "discountOnCheck"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "change"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int closeCheck(){
        return drv.invoke("CloseCheck").getInt();
    }
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
     * Используемые свойства  <p>
     * <b>Password</b> Пароль для исполнения метода драйвера.  <p>
     * <b>CheckType</b> Тип открываемого документа/чека
     * («0» - продажа, «1» - покупка, «2» - возврат продажи, «3» - возврат покупки).
     * <p>
     * Модифицируемые свойства - none
     * <p>
     * [id(0x0000003c), helpstring("ОткрытьЧек")] long OpenCheck();
     * @return Код ошибки или 0
     */
    @CallState(state = "2|4|7|9")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "checkType"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @Override
    public synchronized int openCheck() {
        return drv.invoke("OpenCheck").getInt();
    }
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
     */
    @Override
    public synchronized int getCheckType() {
        return drv.getPropertyAsInt("CheckType");
    }
    @Override
    public synchronized void setCheckType( int v ){
        drv.setProperty("CheckType", v);
    }
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
    @Override
    public synchronized int getOperatorNumber() {
        return drv.getPropertyAsInt("OperatorNumber");
    }
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
     */
    @Override
    public synchronized Currency getChange() {
        return drv.getProperty("Change").getCurrency();
    }
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
    @Override
    public synchronized int getECRBuild() {
        return drv.getPropertyAsInt("ECRBuild");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="ECRSoftDate : Date - Дата ПО ККМ.">
    /**
     * Дата ПО ККМ.
     * <p>
     * Тип: Date / Дата (свойство доступно только для чтения)
     * <p>
     * Дата внутреннего программного обеспечения ККМ.      * <p>
     * [id(0x0000008d), propget, helpstring("ДатаПОККМ")]
     * DATE ECRSoftDate();
     * <p>
     */
    @Override
    public synchronized Date getECRSoftDate() {
        return drv.getProperty("ECRSoftDate").getJavaDate();
    }
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
    @Override
    public synchronized int getLogicalNumber() {
        return drv.getPropertyAsInt("LogicalNumber");
    }
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
    @Override
    public synchronized int getOpenDocumentNumber() {
        return drv.getPropertyAsInt("OpenDocumentNumber");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="receiptRibbonIsPresent : boolean - Рулон Чековой Ленты Есть">
    /**
     * РулонЧековойЛентыЕсть.
     *
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     *
     * Признак  наличия  в  ККМ  рулона  чековой  ленты.  FALSE  –  рулона  чековой  ленты  нет,  TRUE  –
     * рулон чековой ленты есть.
     *
     * Модифицируется методами GetECRStatus и GetShortECRStatus.
     */
    @Override
    public synchronized boolean isReceiptRibbonIsPresent() {
        return drv.getPropertyAsBoolean("ReceiptRibbonIsPresent");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      * <p>
     * [id(0x000000a6), propget, helpstring("РулонОперационногоЖурналаЕсть")]
     * VARIANT_BOOL JournalRibbonIsPresent();
     */
    @Override
    public synchronized boolean isJournalRibbonIsPresent() {
        return drv.getPropertyAsBoolean("JournalRibbonIsPresent");
    }
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
    @Override
    public synchronized int getSKNOStatus() {
        return drv.getPropertyAsInt("SKNOStatus");
    }
    
    @Override
    public synchronized void setSKNOStatus(int v) {
        drv.setProperty("SKNOStatus", v);
    }
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
    @Override
    public synchronized boolean isSlipDocumentIsPresent() {
        return drv.getPropertyAsBoolean("SlipDocumentIsPresent");
    }
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
    @Override
    public synchronized boolean isSlipDocumentIsMoving() {
        return drv.getPropertyAsBoolean("SlipDocumentIsMoving");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      * <p>
     * [id(0x000000a8), propget, helpstring("ОптичДатчикОперационогоЖурнала")]
     * VARIANT_BOOL JournalRibbonOpticalSensor();
     */
    @Override
    public synchronized boolean isJournalRibbonOpticalSensor() {
        return drv.getPropertyAsBoolean("JournalRibbonOpticalSensor");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      * <p>
     * [id(0x000000c5), propget, helpstring("ОптичДатчикЧековойЛенты")]
     * VARIANT_BOOL ReceiptRibbonOpticalSensor();
     */
    @Override
    public synchronized boolean isReceiptRibbonOpticalSensor() {
        return drv.getPropertyAsBoolean("ReceiptRibbonOpticalSensor");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      * <p>
     * [id(0x000000a7), propget, helpstring("РычагТермоголовкиОперационногоЖурнала")]
     * VARIANT_BOOL JournalRibbonLever();
     */
    @Override
    public synchronized boolean isJournalRibbonLever() {
        return drv.getPropertyAsBoolean("JournalRibbonLever");
    }
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
    @Override
    public synchronized boolean isReceiptRibbonLever() {
        return drv.getPropertyAsBoolean("ReceiptRibbonLever");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      * <p>
     * [id(0x000000b0), propget, helpstring("ДатчикКрышкиКорпуса")]
     * VARIANT_BOOL LidPositionSensor();
     */
    @Override
    public synchronized boolean isLidPositionSensor() {
        return drv.getPropertyAsBoolean("LidPositionSensor");
    }
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
    @Override
    public synchronized boolean isPrinterLeftSensorFailure() {
        return drv.getPropertyAsBoolean("IsPrinterLeftSensorFailure");
    }
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
    @Override
    public synchronized boolean isPrinterRightSensorFailure() {
        return drv.getPropertyAsBoolean("IsPrinterRightSensorFailure");
    }
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
    @Override
    public synchronized boolean isDrawerOpen() {
        return drv.getPropertyAsBoolean("IsDrawerOpen");
    }
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
    @Override
    public synchronized int getQuantityStringNumber(){
        return drv.getPropertyAsInt("QuantityStringNumber");
    }
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
     * Модифицируется методами GetECRStatus и GetShortECRStatus.      <p>
     * [id(0x000001d6), propget, helpstring("ПоложениеТочкиВКоличестве")]
     * VARIANT_BOOL QuantityPointPosition();
     */
    @Override
    public synchronized boolean isQuantityPointPosition() {
        return drv.getPropertyAsBoolean("QuantityPointPosition");
    }
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
    @Override
    public synchronized boolean isFMOverflow() {
        return drv.getPropertyAsBoolean("FMOverflow");
    }
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
    @Override
    public synchronized int getFMResultCode() {
        return drv.getPropertyAsInt("FMResultCode");
    }
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
    @Override
    public synchronized int getFNCurrentDocument() {
        return drv.getPropertyAsInt("FNCurrentDocument");
    }
    @Override
    public synchronized void setFNCurrentDocument( int v) {
        drv.setProperty("FNCurrentDocument", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="FMSoftDate : Date - Дата ПО ФП.">
    /**
     * Дата ПО ФП.
     * <p>
     * Тип: Date / Дата (свойство доступно только для чтения)
     * <p>
     * Дата внутреннего программного обеспечения ККМ.
     * <p>
     * Модифицируется методом GetECRStatus.      * <p>
     * [id(0x0000009e), propget, helpstring("ДатаПОФП")]
     * DATE FMSoftDate();
     */
    @Override
    public synchronized Date getFMSoftDate() {
        return drv.getProperty("FMSoftDate").getJavaDate();
    }
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
    @Override
    public synchronized String getFMSoftVersion() {
        return drv.getPropertyAsString("FMSoftVersion");
    }
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
    @Override
    public synchronized int getFMStringNumber(){
        return drv.getPropertyAsInt("FMStringNumber");
    }
    @Override
    public synchronized void setFMStringNumber(int v){
        drv.setProperty("FMStringNumber", v);
    }
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
    @Override
    public synchronized int getFMMode() {
        return drv.getPropertyAsInt("FMMode");
    }
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
    @Override
    public synchronized int getFMFlagsEx() {
        return drv.getPropertyAsInt("FMFlagsEx");
    }
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
    @Override
    public synchronized int getFMFlags() {
        return drv.getPropertyAsInt("FMFlags");
    }
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
    @Override
    public synchronized boolean isBatteryLow() {
        return drv.getPropertyAsBoolean("IsBatteryLow");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="lastFMRecordCorrupted : boolean - Последняя Запись в ФП испорчена.">
    /**
     * Последняя Запись в ФП испорчена.
     * <p>
     * Тип: WordBool / Логическое (свойство доступно только для чтения)
     * <p>
     * Признак того, что последняя запись в ФП испорчена. TRUE – последняя запись в ФП испорчена;
     * FALSE – последняя запись в ФП не испорчена. Модифицируется методом GetECRStatus.      * <p>
     * [id(0x000001dd), propget, helpstring("ПоследняяЗаписьВФПИспорчена")]
     * VARIANT_BOOL IsLastFMRecordCorrupted();
     * <p>
     */
    @Override
    public synchronized boolean isLastFMRecordCorrupted() {
        return drv.getPropertyAsBoolean("IsLastFMRecordCorrupted");
    }
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
    @Override
    public synchronized boolean isFM24HoursOver() {
        return drv.getPropertyAsBoolean("IsFM24HoursOver");
    }
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
     * Модифицируется методом GetECRStatus.      * <p>
     * [id(0x000001de), propget, helpstring("СменаВФПОткрыта")]
     * VARIANT_BOOL IsFMSessionOpen();
     */
    @Override
    public synchronized boolean isFMSessionOpen() {
        return drv.getPropertyAsBoolean("IsFMSessionOpen");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="sessionNumber : int - Номер смены">
    /** Номер смены.
     *
     * Тип: Integer / Целое
     *
     * Номер последней закрытой на ККМ смены (в случае, когда свойство модифицируется методами
     * GetECRStatus, GetFiscalizationParameters, FNCloseSession, FNFindDocument,
     * FNGetCurrentSessionParams) и номер некой закрытой смены (в случае, когда используется
     * методами EKLZJournalOnSessionNumber, ReadEKLZSessionTotal).
     *
     * Когда модифицируется
     * методами GetEKLZCode2Report и FNOpenSession – номер текущей смены.
     *
     * Диапазон значений: 0..2100.
     *
     * Примечание: всегда до фискализации ФП и до снятия первого суточного отчета с гашением
     * номер последней закрытой смены равен 0.
     * 
     * [id(0x000000d1), propget, helpstring("НомерСмены")]
     * long SessionNumber();
     */
    @Override
    public synchronized int getSessionNumber(){
        return drv.getPropertyAsInt("SessionNumber");
    }
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
     * См. также: методы: MFPGetPrepareActivizationResult, MFPPrepareActivization.      * <p>
     * [id(0x000000a2), propget, helpstring("ИНН")]
     * BSTR INN();
     * <p>
     * [id(0x000000a2), propput, helpstring("ИНН")]
     * void INN([in] BSTR rhs);
     */
    @Override
    public synchronized String getINN() {
        return drv.getPropertyAsString("INN");
    }
    
    @Override
    public synchronized void setINN(String str) {
        if (str == null) {
            throw new IllegalArgumentException("str==null");
        }
        drv.setProperty("INN", str);
    }
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
    @Override
    public synchronized int getRegistrationNumber() {
        return drv.getPropertyAsInt("RegistrationNumber");
    }
    
    @Override
    public synchronized void setRegistrationNumber(int v) {
        drv.setProperty("RegistrationNumber", v);
    }
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
    @Override
    public synchronized int getFreeRecordInFM() {
        return drv.getPropertyAsInt("FreeRecordInFM");
    }
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
     * Модифицируется методами GetECRStatus, Fiscalization, FNGetExpirationTime.      * <p>
     * [id(0x000000a1), propget, helpstring("ОсталосьПеререгистраций")]
     * long FreeRegistration();
     */
    @Override
    public synchronized int getFreeRegistration() {
        return drv.getPropertyAsInt("FreeRegistration");
    }
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
     */
    @CallState(state = "2|4|7|8.2|9")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @Override
    public synchronized int saleReturn() {
        return drv.invoke("ReturnSale").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @CallState(state = "2|4|7|8.1|9")
    @Override
    public synchronized int buy() {
        return drv.invoke("Buy").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @CallState(state = "2|4|7|8.3|9")
    @Override
    public synchronized int buyReturn() {
        return drv.invoke("ReturnBuy").getInt();
    }
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
     */
    @CallState(state = "8")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "quantity"),
        @DriverProperty(name = "price"),
        @DriverProperty(name = "department"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @Override
    public synchronized int storno() {
        return drv.invoke("Storno").getInt();
    }
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
        @DriverProperty(name = "password"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @CallState(state = "2|3")
    @Override
    public synchronized int repeatDocument() {
        return drv.invoke("RepeatDocument").getInt();
    }
    //</editor-fold>
           
    //<editor-fold defaultstate="collapsed" desc="cancelCheck() - Операция  производит  аннулирование  (отмену)  всего  чека ">
    /**
     * Операция  производит  аннулирование  (отмену)  всего  чека.  <p>
     *
     * При  этом  на  чеке  печатается  «ЧЕК АННУЛИРОВАН».  <p>
     * Перед вызовом метода в свойстве Password указать пароль оператора.  <p>
     * В свойстве OperatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p>
     * Работает в режиме 8 (см. свойство ECRMode).  <p>
     * Переводит ККМ в режим, в котором ККМ была до открытия чека, или в режим 3 (см. свойство
     * ECRMode).  <p>
     * Используемые свойства <p>
     * Password - Пароль для исполнения метода драйвера.  <p> 
     * 
     * Модифицируемые свойства: <p>
     * OperatorNumber   - Порядковый номер оператора, чей пароль был введен.  <p>
     * 
     * [id(0x00000005), helpstring("АннулироватьЧек")]
     * long CancelCheck();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "change"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int cancelCheck(){
        return drv.invoke( "CancelCheck" ).getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "openDocumentNumber"),
    })
    @CallState(state = "2|3|4|7|9")
    @Override
    public synchronized int cashIncome() {
        return drv.invoke("CashIncome").getInt();
    }
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
     * <p>Модифицируемые свойства
     * <ul>
     * <li> OperatorNumber Порядковый номер оператора, чей пароль был
     * введен.
     * <li> OpenDocumentNumber Сквозной номер последнего документа ККМ.
     * </ul>
     * [id(0x00000007), helpstring("Выплата")]
     * long CashOutcome();
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "openDocumentNumber"),
    })
    @CallState(state = "2|3|4|7|9")
    @Override
    public synchronized int cashOutcome() {
        return drv.invoke("CashOutcome").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="beginDocument() - Включает рижим буферизации команд.">
    /**
     * Включает рижим буферизации команд.
     * <p>
     * Все последующие команды будут вноситься в буфер, и выполнены только после команды EndDocument.     
     * <p>
     * [id(0x0000079f), helpstring("НачатьДокумент")]
     * long BeginDocument();
     */
    @InputProperties(properties = {
        //@DriverProperty(name = "password"),
        //@DriverProperty(name = "summ1"),
    })
    @OutputProperties(properties = {
        //@DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "openDocumentNumber"),
    })
    @CallState()
    @Override
    public synchronized int beginDocument() {
        return drv.invoke("BeginDocument").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        //@DriverProperty(name = "openDocumentNumber"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int charge() {
        return drv.invoke("Charge").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "summ1"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int checkSubTotal(){
        return drv.invoke("CheckSubTotal").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
        @DriverProperty(name = "summ2"),
        @DriverProperty(name = "summ3"),
        @DriverProperty(name = "summ4"),
        @DriverProperty(name = "discountOnCheck"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "change"),
        @DriverProperty(name = "KPKStr"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int closeCheckWithKPK(){
        return drv.invoke("CloseCheckWithKPK").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "summ1"),
        @DriverProperty(name = "summ2"),
        @DriverProperty(name = "summ3"),
        @DriverProperty(name = "summ4"),
        @DriverProperty(name = "discountOnCheck"),
        @DriverProperty(name = "tax1"),
        @DriverProperty(name = "tax2"),
        @DriverProperty(name = "tax3"),
        @DriverProperty(name = "tax4"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "change"),
        @DriverProperty(name = "KPKStr"),
    })
    @CallState(state = "8")
    @Override
    public synchronized int discount(){
        return drv.invoke("Discount").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="endDocument() - Выполнение всех команд, внесенных в буфер выход из режима буферизации команд">
    /**
     * Выполнение всех команд, внесенных в буфер и выход из режима буферизации команд.
     * <p>
     * [id(0x000007a0), helpstring("ЗавершитьДокумент")]
     * long EndDocument();
     */
    @InputProperties(properties = {
    })
    @OutputProperties(properties = {
    })
    @CallState()
    @Override
    public synchronized int endDocument() {
        return drv.invoke("EndDocument").getInt();
    }
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
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
    })
    @Override
    public synchronized int printReportWithCleaning() {
        return drv.invoke("PrintReportWithCleaning").getInt();
    }
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
     */
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "stringQuantity"),
        @DriverProperty(name = "useSlipDocument"),
        @DriverProperty(name = "useReceiptRibbon"),
        @DriverProperty(name = "useJournalRibbon"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
    })
    @CallState(state = "!8|!10|!11|!12|!14|!*.*.4|!*.*.5")
    @Override
    public synchronized int feedDocument() {
        return drv.invoke("FeedDocument").getInt();
    }
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
    @Override
    public synchronized boolean isUseSlipDocument() {
        return drv.getPropertyAsBoolean("UseSlipDocument");
    }
    @Override
    public synchronized void setUseSlipDocument( boolean v ){
        drv.setProperty("UseSlipDocument", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="useReceiptRibbon : boolean - Использовать чековую ленту">
    /**
     * Использовать чековую ленту.
     * <p>
     * Тип: WordBool / Логическое
     * <p>
     * Признак операции с чековой лентой. FALSE – не производить операцию над чековой лентой,
     * TRUE – производить операцию над чековой лентой.
     * <p>
     * Используется методами PrintString, PrintWideString, FeedDocument.      * <p>
     * [id(0x000000f3), propget, helpstring("ИспользоватьЧековуюЛенту")]
     * VARIANT_BOOL UseReceiptRibbon();
     * <p>
     * [id(0x000000f3), propput, helpstring("ИспользоватьЧековуюЛенту")]
     * void UseReceiptRibbon([in] VARIANT_BOOL rhs);
     */
    @Override
    public synchronized boolean isUseReceiptRibbon() {
        return drv.getPropertyAsBoolean("UseReceiptRibbon");
    }
    
    @Override
    public synchronized void setUseReceiptRibbon(boolean v) {
        drv.setProperty("UseReceiptRibbon", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="useJournalRibbon : boolean - Использовать операционный журнал">
    /**
     * Использовать операционный журнал.
     * <p>
     * Тип: WordBool / Логическое
     * <p>
     * Признак операции с лентой операционного журнала. FALSE – не производить операцию над
     * лентой операционного журнала, TRUE – производить операцию над лентой операционного
     * журнала.
     * <p>
     * Используется методами PrintString, PrintWideString, FeedDocument.      * <p>
     * [id(0x000000f2), propget, helpstring("ИспользоватьОперационныйЖурнал")]
     * VARIANT_BOOL UseJournalRibbon();
     * <p>
     * [id(0x000000f2), propput, helpstring("ИспользоватьОперационныйЖурнал")]
     * void UseJournalRibbon([in] VARIANT_BOOL rhs);
     */
    @Override
    public synchronized boolean isUseJournalRibbon() {
        return drv.getPropertyAsBoolean("UseJournalRibbon");
    }
    
    @Override
    public synchronized void setUseJournalRibbon(boolean v) {
        drv.setProperty("UseJournalRibbon", v);
    }
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
    @CallState(state = "!11|!12|!14")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "useReceiptRibbon"),
        @DriverProperty(name = "useJournalRibbon"),
        @DriverProperty(name = "stringForPrinting"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
    })
    @Override
    public synchronized int printString(){
        return drv.invoke("PrintString").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GetCashReg() - Получить Денежный Регистр.">
    /**
     * Получить Денежный Регистр.
     * <p>
     * Запрос  содержимого  денежного  регистра  (см.  «Инструкцию  по  эксплуатации»/«Руководство
     * оператора», раздел «О денежных и операционных регистрах»).
     * <p>
     * <b>Денежные  регистры</b>  –  регистры  в  энергонезависимой  памяти  ККМ.
     * <p>
     * Содержимое  их  можно  запросить  командой  протокола, в которой указывается
     * номер регистра.
     * <p>
     * Состав денежных регистров:
     * Накопления в отделы по 4 типам торговых операций (продажа, покупка,
     * возврат продажи, возврат покупки) в чеке:
     * <ul>
     * <li> 0…3 – 1;
     * <li> 4…7 – 2;
     * <li> 8…11 – 3;
     * <li> 12…15 – 4;
     * <li> 16…19 – 5;
     * <li> 20…23 – 6;
     * <li> 24…27 – 7;
     * <li> 28…31 – 8;
     * <li> 32…35 – 9;
     * <li> 36…39 – 10;
     * <li> 40…43 – 11;
     * <li> 44…47 – 12;
     * <li> 48…51 – 13;
     * <li> 52…55 – 14;
     * <li> 56…59 – 15;
     * <li> 60…63 – 16.
     * <li> 64…67  –  скидки  по  4  типам  торговых  операций  (продажа,  покупка,
     * возврат продажи, возврат покупки) в чеке;
     * <li> 68…71  –  надбавки  по  4  типам  торговых  операций  (продажа,  покупка,
     * возврат продажи, возврат покупки) в чеке;
     * Накопления по видам оплаты по 4 типам торговых операций (продажа,
     * покупка, возврат продажи, возврат покупки) в чеке:
     * <li> 72…75 – наличными;
     * <li> 76…79 – видом оплаты 2;
     * <li> 80…83 – видом оплаты 3;
     * <li> 84…87 – видом оплаты 4; <p>
     * Обороты по налогам по 4 типам торговых операций (продажа, покупка,
     * возврат продажи, возврат покупки) в чеке:
     * <li> 88…91 – А;
     * <li> 92…95 – Б;
     * <li> 96…99 – В;
     * <li> 100…103 – Г; <p>
     * Налоги  по  4  типам  торговых  операций  (продажа,  покупка,  возврат
     * продажи, возврат покупки) в чеке:
     * <li> 104…107 – А;
     * <li> 108…111 – Б;
     * <li> 112…115 – В;
     * <li> 116…119 – Г;
     * <li> 120 – наличность в кассе в чеке; <p>
     * Накопления в отделы по 4 типам торговых операций (продажа, покупка,
     * возврат продажи, возврат покупки) за смену:
     * <li> 121…124 – 1;
     * <li> 125…128 – 2;
     * <li> 129…132 – 3;
     * <li> 133…136 – 4;
     * <li> 137…140 – 5;
     * <li> 141…144 – 6;
     * <li> 145…148 – 7;
     * <li> 149…152 – 8;
     * <li> 153…156 – 9;
     * <li> 157…160 – 10;
     * <li> 161…164 – 11;
     * <li> 165…168 – 12;
     * <li> 169…172 – 13;
     * <li> 173…176 – 14;
     * <li> 177…180 – 15;
     * <li> 181…184 – 16.
     * <li> 185…188  –  скидки  по  4  типам  торговых  операций  (продажа,  покупка,
     * возврат продажи, возврат покупки) за смену;
     * <li> 189…192 – надбавки по 4 типам торговых операций (продажа, покупка,
     * возврат продажи, возврат покупки) за смену; <p>
     * Накопления по видам оплаты по 4 типам торговых операций (продажа,
     * покупка, возврат продажи, возврат покупки) за смену:
     * <li> 193…196 – наличными;
     * <li> 197…200 – видом оплаты 2;
     * <li> 201…204 – видом оплаты 3;
     * <li> 205…208 – видом оплаты 4; <p>
     * Обороты по налогам по 4 типам торговых операций (продажа, покупка,
     * возврат продажи, возврат покупки) за смену:
     * <li> 209…212 – А;
     * <li> 213…216 – Б;
     * <li> 217…220 – В;
     * <li> 221…224 – Г; <p>
     * Налоги  по  4  типам  торговых  операций  (продажа,  покупка,  возврат
     * продажи, возврат покупки) в смене:
     * <li> 225…228 – А;
     * <li> 229…232 – Б;
     * <li> 233…236 – В;
     * <li> 237…240 – Г;
     * <li> 241 – наличность в кассе за смену;
     * <li> 242 – внесенные суммы за смену;
     * <li> 243 – выплаченные суммы за смену;
     * <li> 244 – необнуляемая сумма до фискализации;
     * <li> 245 – сумма продаж в смене из ЭКЛЗ;
     * <li> 246 – сумма покупок в смене из ЭКЛЗ;
     * <li> 247 – сумма возвратов продаж в смене из ЭКЛЗ;
     * <li> 248 – сумма возвратов покупок в смене из ЭКЛЗ.
     * </ul>
     * <b>Операционные  регистры </b> –  регистры  в  энергонезависимой  памяти
     * ККМ,  служащие  для  подсчета  количества  различных  операций  в  ККМ. <p>
     * Содержимое  их  можно  запросить  командой  протокола, в которой указывается
     * номер регистра. <p> Состав операционных регистров:
     * Количество торговых операций в отделы по 4 типам торговых операций
     * (продажа, покупка, возврат продажи, возврат покупки) в чеке:
     * <ul>
     * <li> 0…3 – 1;
     * <li> 4…7 – 2;
     * <li> 8…11 – 3;
     * <li> 12…15 – 4;
     * <li> 16…19 – 5;
     * <li> 20…23 – 6;
     * <li> 24…27 – 7;
     * <li> 28…31 – 8;
     * <li> 32…35 – 9;
     * <li> 36…39 – 10;
     * <li> 40…43 – 11;
     * <li> 44…47 – 12;
     * <li> 48…51 – 13;
     * <li> 52…55 – 14;
     * <li> 56…59 – 15;
     * <li> 60…63 – 16.
     * <li> 64…67  –  количество  скидок  по  4  типам  торговых  операций  (продажа,
     * покупка, возврат продажи, возврат покупки) в чеке;
     * <li> 68…71 – количество надбавок по 4 типам торговых операций (продажа,
     * покупка, возврат продажи, возврат покупки) в чеке; <p>
     * Количество торговых операций в отделы по 4 типам торговых операций
     * (продажа, покупка, возврат продажи, возврат покупки) за смену:
     * <li> 72…75 – 1;
     * <li> 76…79 – 2;
     * <li> 80…83 – 3;
     * <li> 84…87 – 4;
     * <li> 88…91 – 5;
     * <li> 92…95 – 6;
     * <li> 96…99 – 7;
     * <li> 100…103 – 8;
     * <li> 104…107 – 9;
     * <li> 108…111 – 10;
     * <li> 112…115 – 11;
     * <li> 116…119 – 12;
     * <li> 120…123 – 13;
     * <li> 124…127 – 14;
     * <li> 128…131 – 15;
     * <li> 132…135 – 16.
     * <li> 136…139 – количество скидок по 4 типам торговых операций (продажа,
     * покупка, возврат продажи, возврат покупки) за смену;
     * <li> 140…143  –  количество  надбавок  по  4  типам  торговых  операций
     * (продажа, покупка, возврат продажи, возврат покупки) за смену;
     * <li> 144…147 – количество чеков по 4 типам торговых операций (продажа,
     * покупка, возврат продажи, возврат покупки) за смену;
     * <li> 148…151  –  номер  чека  по  4  типам  торговых  операций  (продажа,
     * покупка, возврат продажи, возврат покупки);
     * <li> 152 – сквозной номер документа;
     * <li> 153 – количество внесений денежных сумм за смену;
     * <li> 154 – количество выплат денежных сумм за смену;
     * <li> 155 – номер внесения денежных сумм;
     * <li> 156 – номер выплаты денежных сумм;
     * <li> 157 – количество отмененных документов;
     * <li> 158 – номер сменного отчета без гашения;
     * <li> 159 – номер сменного отчета с гашением до фискализации;
     * <li> 160 – номер общего гашения;
     * <li> 161 – номер полного фискального отчета;
     * <li> 162 – номер сокращенного фискального отчета;
     * <li> 163 – номер тестового прогона;
     * <li> 164 – номер снятия показаний операционных регистров;
     * <li> 165 – номер отчетов по секциям;
     * <li> 166 – количество аннулирований;
     * <li> 167 – количество запусков теста самодиагностики;
     * <li> 168 – количество активизаций ЭКЛЗ;
     * <li> 169 – количество отчетов по итогам активизации ЭКЛЗ;
     * <li> 170 – количество отчетов по номеру КПК из ЭКЛЗ;
     * <li> 171 – количество отчетов по контрольной ленте из ЭКЛЗ;
     * <li> 172 – количество отчетов по датам из ЭКЛЗ;
     * <li> 173 – количество отчетов по сменам из ЭКЛЗ;
     * <li> 174 – количество отчетов по итогам смен из ЭКЛЗ;
     * <li> 175 – количество отчетов по датам в отделе из ЭКЛЗ;
     * <li> 176 – количество отчетов по сменам в отделе из ЭКЛЗ;
     * <li> 177 – количество закрытий архива ЭКЛЗ;
     * <li> 178 – номер отчетов по секциям.
     * </ul>
     * 
     * Перед вызовом метода необходимо заполнить свойство  <b>registerNumber</b>, в котором указать номер
     * денежного регистра. <p>
     * 
     * Перед вызовом метода в свойстве <b>password</b> указать пароль оператора. <p>
     * 
     * В свойстве <b>operatorNumber</b> возвращается порядковый номер оператора, чей пароль был введен.
     * После  вызова  метода  в  свойстве  <b>contentsOfCashRegister</b>  возвращается  содержимое  денежного
     * регистра,   в свойстве <b>nameCashReg</b> возвращается имя денежного регистра. <p>
     * 
     * <b>Работает во всех режимах.</b> <p>
     * 
     * <b>Не меняет режима ККМ.</b> <p>
     * 
     * <b>Используемые свойства</b><p>
     * <ul>
     * <li>password Пароль для исполнения метода драйвера.
     * <li>registerNumber Номер регистра в командах работы с денежными или операционными регистрами.
     * </ul>
     * 
     * <b>Модифицируемые свойства</b><p>
     * <ul>
     * <li> operatorNumber Порядковый номер оператора, чей пароль был введен.
     * <li> contentsOfCashRegister  Содержимое денежного регистра Содержимое операционного регистра (см. «Инструкцию по
     * эксплуатации»/«Руководство оператора»).
     * <li> nameCashReg  Наименование денежного регистра – строка символов.
     * </ul>
     * 
     * Пример:<p>
     * 
     * Запрос содержимого денежного регистра (наличность в кассе) <p>
     * 
     * Перед тем, как вызвать метод GetCashReg, необходимо заполнить следующие свойства: присвоим свойству
     * <b>password</b> значение «5» (пароль кассира №5 по умолчанию), а свойству <b>registerNumber</b> – значение <b>«241»</b>
     * (номер  денежного  регистра). <p>
     * 
     * Вызовем  метод.  <p>
     * 
     * В  случае  успешного выполнения метода значение свойства
     * <b>resultCode</b>  будет  равно  «0»  («Ошибок  нет»),  в  противном  случае  см.  описание  кода  ошибки  в  свойстве
     * <b>resultCodeDescription</b>.  <p>
     * 
     * Если  <b>resultCode</b>=0,  метод  возвращает  значения  в  следующие  свойства:
     * <b>operatorNumber</b>=5  (порядковый  номер  оператора,  вызвавшего  метод);  <p>
     * <b>contentsOfCashRegister=354656</b> (содержимое  денежного  регистра  №241  –  3546  руб.  56  коп.); <p>
     * <b>nameCashReg</b>=«Наличность  в  кассе» (название регистра). <p>
     * 
     * Листинг вызова метода приведён ниже: <p>
     * 
     * Создание объекта драйвера
     * 
     * <pre>v:=CreateOleObject(‘AddIn.DrvFR’);</pre><p>
     * 
     * Запрос содержимого денежного регистра
     * 
     * <pre>v.Password:=5;</pre>
     * <pre>v.RegisterNumber:=241;</pre>
     * <pre>v.GetCashReg;</pre>
     * <p>
     * [id(0x00000023), helpstring("ПолучитьДенежныйРегистр")] long GetCashReg();
     */
    @CallState(state = "*")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "registerNumber"),
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "contentsOfCashRegister"),
        @DriverProperty(name = "nameCashReg"),
    })
    @Override
    public synchronized int GetCashReg(){
        return drv.invoke("GetCashReg").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="registerNumber - Номер регистра">
    /**
     * Номер регистра в командах работы с денежными или операционными регистрами. <p>
     * Диапазон значений: 0..255.  <p>
     * Используется методами GetCashReg, GetOperationReg.  <p>
     * [id(0x000000c6), propget, helpstring("НомерРегистра")] long RegisterNumber(); <p>
     * [id(0x000000c6), propput, helpstring("НомерРегистра")] void RegisterNumber([in] long rhs);
     */
    @Override
    public synchronized int getRegisterNumber(){
        return drv.getPropertyAsInt("RegisterNumber");
    }
    
    /**
     * Номер регистра в командах работы с денежными или операционными регистрами
     * @param v
     * @see #getRegisterNumber()
     * @see #GetCashReg()
     */
    @Override
    public synchronized void setRegisterNumber(int v){
        drv.setProperty("RegisterNumber", v);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="contentsOfCashRegister">
    /**
     * Содержимое денежного регистра. <p>
     * Тип: Currency / Денежный (свойство доступно только для чтения) <p>
     * 
     * Содержимое денежного регистра (см. «Инструкцию по эксплуатации»/«Руководство оператора»)  <p>
     * 
     * Модифицируется методом GetCashReg. <p>
     * 
     * [id(0x00000073), propget, helpstring("СодержимоеДенежногоРегистра")] CY ContentsOfCashRegister();
     * @return Содержимое денежного регистра
     * @see #GetCashReg()
     */
    @Override
    public synchronized Currency getContentsOfCashRegister(){
        return drv.getProperty("ContentsOfCashRegister").getCurrency();
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="nameCashReg">
    /*
    Наименование денежного регистра
    Тип: WideString / Строка (свойство доступно только для чтения)
    
    Наименование денежного регистра – строка символов в кодировке WIN1251 (см. «Инструкцию по эксплуатации»/«Руководство оператора») .
    
    Модифицируется методом GetCashReg.
    
    [id(0x000000b7), propget, helpstring("НазваниеДенежногоРегистра")] BSTR NameCashReg();
    */
    @Override
    public synchronized String getNameCashReg(){
        return drv.getPropertyAsString("NameCashReg");
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GetOperationReg() - Запрос  содержимого  операционного  регистра.">
    /**
     * Запрос  содержимого  операционного  регистра. <p>
     * (см.  «Инструкцию  по эксплуатации»/«Руководство оператора», раздел «О денежных и операционных регистрах»).
     * <p>
     * 
     * Перед вызовом метода необходимо заполнить свойство  registerNumber, в котором указать номер
     * операционного регистра.  <p>
     * Перед вызовом метода в свойстве password указать пароль оператора.  <p>
     * В свойстве operatorNumber возвращается порядковый номер оператора, чей пароль был введен.  <p>
     * После  вызова  метода  в  свойстве  contentsOfOperationRegister  возвращается  содержимое
     * операционного  регистра,  в  свойстве  nameOperationReg  возвращается  имя  операционного
     * регистра.  <p>
     * <b>Работает во всех режимах.</b> <p>
     * <b>Не меняет режима ККМ.</b><p>
     * 
     * <b>Используемые свойства</b>
     * <ul>
     * <li> <b>password</b> Пароль для исполнения метода драйвера
     * <li> <b>registerNumber</b> Номер регистра в командах работы с денежными или операционными регистрами.
     * </ul>
     * 
     * <b>Модифицируемые свойства</b>
     * <ul>
     * <li> <b>operatorNumber</b> Порядковый номер оператора, чей пароль был введен.
     * <li> <b>contentsOfOperationRegister</b> Содержимое операционного регистра.
     * <li> <b>nameOperationReg</b> Наименование операционного регистра
     * </ul>
     * 
     * Пример: <p>
     * запрос содержимого операционного регистра 148 (номер чека продажи)  <p>
     * Перед тем, как вызвать метод GetOperationReg, необходимо заполнить следующие
     * свойства: присвоим свойству <b>password</b> значение «1» (пароль кассира №1 по умолчанию),
     * а свойству <b>registerNumber</b> – значение «148» (номер операционного регистра). <p>
     * Вызовем метод. <p>
     * В случае успешного выполнения метода значение свойства ResultCode будет
     * равно «0» («Ошибок нет»), в противном случае см. описание кода ошибки в свойстве
     * resultCodeDescription. <p>
     * Если resultCode=0, метод возвращает значения в следующие
     * свойства: operatorNumber=1 (порядковый номер оператора, вызвавшего метод); <p>
     * contentsOfOperationRegister=13 (содержимое операционного регистра №148 – 13 чеков
     * продаж); NameOperationReg=«Номер чека продажи» (название регистра).  <p>
     * Листинг вызова метода приведён ниже:  <p>
     * Создание объекта драйвера
     * 
     * <pre>v:=CreateOleObject(‘AddIn.DrvFR’);</pre>
     * 
     * Запрос содержимого операционного регистра
     * 
     * <pre>
     * v.Password:=1;
     * v.RegisterNumber:=148;
     * v.GetOperationReg;
     * </pre>
     * 
     * [id(0x0000002f), helpstring("ПолучитьОперационныйРегистр")] long GetOperationReg();
     * 
     * @return код ошибки или 0
     * @see #GetCashReg()
     */
    @CallState(state = "*")
    @InputProperties(properties = {
        @DriverProperty(name = "password"),
        @DriverProperty(name = "registerNumber")
    })
    @OutputProperties(properties = {
        @DriverProperty(name = "operatorNumber"),
        @DriverProperty(name = "contentsOfOperationRegister"),
        @DriverProperty(name = "nameOperationReg")
    })
    @Override
    public synchronized int GetOperationReg(){
        return drv.invoke("GetOperationReg").getInt();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="contentsOfOperationRegister">
    /**
     * Содержимое операционного регистра.
     * <p>
     * Тип: Integer / Целое (свойство доступно только для чтения)
     * <p>
     * Содержимое операционного регистра (см. «Инструкцию по эксплуатации»/«Руководство
     * оператора»).
     * <p>
     * Модифицируется методом GetOperationReg.
     * <p>
     * [id(0x00000074), propget, helpstring("СодержимоеОперационногоРегистра")] long ContentsOfOperationRegister();
     * @return Содержимое операционного регистра.
     * @see #GetCashReg()
     *
     */
    @Override
    public synchronized int getContentsOfOperationRegister() {
        return drv.getPropertyAsInt("ContentsOfOperationRegister");
    }
//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="nameOperationReg">
    /**
     * Название операционного регистра.
     * <p>
     * Тип: WideString / Строка (свойство доступно только для чтения)
     * <p>
     * Наименование операционного регистра – строка символов (см.
     * «Инструкцию по эксплуатации»/«Руководство оператора») .
     * <p>
     * Модифицируется методом GetOperationReg.
     * <p>
     * [id(0x000000b8), propget, helpstring("НазваниеОперационногоРегистра")] BSTR NameOperationReg();
     * @return  Название операционного регистра.
     * @see #GetCashReg()
     */
    @Override
    public synchronized String getNameOperationReg() {
        return drv.getPropertyAsString("NameOperationReg");
    }
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
}
