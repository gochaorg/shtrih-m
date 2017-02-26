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
import xyz.cofe.shtrihm.Driver;
import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.shtrihm.jacob.ShtrihMJacobDriver;

/**
 * Потоко-безопасная работа с драйвером
 * @author nt.gocha@gmail.com
 */
public class ThreadSafeDriver extends ThreadSafe<Driver>
    implements Driver
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(ThreadSafeDriver.class.getName());
    
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
        logger.entering(ThreadSafeDriver.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(ThreadSafeDriver.class.getName(), method, result);
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
    
    protected static Driver driver;
    
    public ThreadSafeDriver(){        
    }
    
    @Override
    protected Driver createSafeObject() {
        synchronized( ThreadSafeDriver.class ){
            if( driver!=null ){
                return driver;
            }
            logInfo("create thread unsafe driver {0}", ShtrihMJacobDriver.class.getName());
            driver = new ShtrihMJacobDriver();
            return driver;
        }
    }

    @Override
    protected void releaseSafeObject(Driver safeObj) {
    }

    @Override
    public int SetDate() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.SetDate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int SetPointPosition() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.SetPointPosition();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int SetTime() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.SetTime();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int beginDocument() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.beginDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int buy() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.buy();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int buyReturn() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.buyReturn();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int cancelCheck() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.cancelCheck();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int cashIncome() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.cashIncome();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int cashOutcome() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.cashOutcome();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int charge() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.charge();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int checkConnection() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.checkConnection();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int checkSubTotal() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.checkSubTotal();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int closeCheck() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.closeCheck();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int closeCheckWithKPK() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.closeCheckWithKPK();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int confirmDate() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.confirmDate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int connect() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.connect();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int continuePrint() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.continuePrint();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int disconnect() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.disconnect();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int discount() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.discount();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int endDocument() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.endDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int feedDocument() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.feedDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getBaudRate() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getBaudRate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getChange() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getChange();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getCheckType() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getCheckType();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getComNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getComNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getComputerName() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getComputerName();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getConnectionTimeout() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getConnectionTimeout();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getConnectionType() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getConnectionType();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Date getDate() {
        Task<Date,Driver> task = runTask( new Consumer<Date,Driver>() {
            @Override
            public Date accept(Driver unsafeDriver) {
                return unsafeDriver.getDate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getDepartment() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getDepartment();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public double getDiscountOnCheck() {
        Task<Double,Driver> task = runTask( new Consumer<Double,Driver>() {
            @Override
            public Double accept(Driver unsafeDriver) {
                return unsafeDriver.getDiscountOnCheck();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getECRAdvancedMode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getECRAdvancedMode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getECRAdvancedModeDescription() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getECRAdvancedModeDescription();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getECRBuild() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getECRBuild();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getECRMode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getECRMode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getECRMode8Status() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getECRMode8Status();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getECRModeDescription() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getECRModeDescription();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Date getECRSoftDate() {
        Task<Date,Driver> task = runTask( new Consumer<Date,Driver>() {
            @Override
            public Date accept(Driver unsafeDriver) {
                return unsafeDriver.getECRSoftDate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getECRStatus() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getECRStatus();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getEmergencyStopCode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getEmergencyStopCode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getEmergencyStopCodeDescription() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getEmergencyStopCodeDescription();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFMFlags() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFMFlags();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFMFlagsEx() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFMFlagsEx();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFMMode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFMMode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFMResultCode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFMResultCode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Date getFMSoftDate() {
        Task<Date,Driver> task = runTask( new Consumer<Date,Driver>() {
            @Override
            public Date accept(Driver unsafeDriver) {
                return unsafeDriver.getFMSoftDate();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getFMSoftVersion() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getFMSoftVersion();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFMStringNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFMStringNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFNCurrentDocument() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFNCurrentDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFreeRecordInFM() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFreeRecordInFM();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getFreeRegistration() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getFreeRegistration();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getINN() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getINN();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getLicense() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getLicense();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getLogicalNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getLogicalNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getOpenDocumentNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getOpenDocumentNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getOperatorNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getOperatorNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getPassword() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getPassword();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isPointPosition() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isPointPosition();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getPortNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getPortNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getPrice() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getPrice();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getProtocolType() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getProtocolType();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public double getQuantity() {
        Task<Double,Driver> task = runTask( new Consumer<Double,Driver>() {
            @Override
            public Double accept(Driver unsafeDriver) {
                return unsafeDriver.getQuantity();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getQuantityStringNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getQuantityStringNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isReceiptRibbonIsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isReceiptRibbonIsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getRegistrationNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getRegistrationNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getResultCode() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getResultCode();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getResultCodeDescription() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getResultCodeDescription();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getSKNOStatus() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getSKNOStatus();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getSessionNumber() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getSessionNumber();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getShortECRStatus() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getShortECRStatus();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getStringForPrinting() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getStringForPrinting();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getStringQuantity() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getStringQuantity();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getSumm1() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getSumm1();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getSumm2() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getSumm2();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getSumm3() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getSumm3();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Currency getSumm4() {
        Task<Currency,Driver> task = runTask( new Consumer<Currency,Driver>() {
            @Override
            public Currency accept(Driver unsafeDriver) {
                return unsafeDriver.getSumm4();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getSysAdminPassword() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getSysAdminPassword();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getTax1() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getTax1();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getTax2() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getTax2();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getTax3() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getTax3();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getTax4() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getTax4();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public Date getTime() {
        Task<Date,Driver> task = runTask( new Consumer<Date,Driver>() {
            @Override
            public Date accept(Driver unsafeDriver) {
                return unsafeDriver.getTime();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public String getTimeStr() {
        Task<String,Driver> task = runTask( new Consumer<String,Driver>() {
            @Override
            public String accept(Driver unsafeDriver) {
                return unsafeDriver.getTimeStr();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getTimeout() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getTimeout();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int getWaitForPrintingDelay() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.getWaitForPrintingDelay();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int interruptDataStream() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.interruptDataStream();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int interruptTest() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.interruptTest();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isBatteryLow() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isBatteryLow();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isCheckEJConnection() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isCheckEJConnection();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isCheckFMConnection() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isCheckFMConnection();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isDrawerOpen() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isDrawerOpen();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isEKLZOverflow() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isEKLZOverflow();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isFM1IsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isFM1IsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isFM24HoursOver() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isFM24HoursOver();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isFM2IsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isFM2IsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isFMOverflow() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isFMOverflow();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isFMSessionOpen() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isFMSessionOpen();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isJournalRibbonIsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isJournalRibbonIsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isJournalRibbonLever() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isJournalRibbonLever();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isJournalRibbonOpticalSensor() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isJournalRibbonOpticalSensor();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isLastFMRecordCorrupted() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isLastFMRecordCorrupted();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isLicenseIsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isLicenseIsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isLidPositionSensor() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isLidPositionSensor();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isPrinterLeftSensorFailure() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isPrinterLeftSensorFailure();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isPrinterRightSensorFailure() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isPrinterRightSensorFailure();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isQuantityPointPosition() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isQuantityPointPosition();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isReceiptRibbonLever() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isReceiptRibbonLever();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isReceiptRibbonOpticalSensor() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isReceiptRibbonOpticalSensor();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isSlipDocumentIsMoving() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isSlipDocumentIsMoving();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isSlipDocumentIsPresent() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isSlipDocumentIsPresent();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isUseJournalRibbon() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isUseJournalRibbon();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isUseReceiptRibbon() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isUseReceiptRibbon();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public boolean isUseSlipDocument() {
        Task<Boolean,Driver> task = runTask( new Consumer<Boolean,Driver>() {
            @Override
            public Boolean accept(Driver unsafeDriver) {
                return unsafeDriver.isUseSlipDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int openCheck() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.openCheck();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int openSession() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.openSession();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int printReportWithCleaning() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.printReportWithCleaning();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int printString() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.printString();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int readLicense() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.readLicense();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int repeatDocument() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.repeatDocument();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int resetECR() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.resetECR();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int resetSettings() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.resetSettings();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int resetSummary() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.resetSummary();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int restoreState() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.restoreState();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int sale() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.sale();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int saleEx() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.saleEx();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int saleReturn() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.saleReturn();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int saveState() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.saveState();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public void setBaudRate(final int brate) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setBaudRate(brate);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setCheckEJConnection(final boolean v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setCheckEJConnection(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setCheckFMConnection(final boolean v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setCheckFMConnection(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setCheckType(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setCheckType(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setComNumber(final int cport) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setComNumber(cport);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setComputerName(final String cname) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setComputerName(cname);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setConnectionTimeout(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setConnectionTimeout(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setConnectionType(final int type) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setConnectionType(type);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setDate(final Date d) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setDate(d);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setDepartment(final int dep) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setDepartment(dep);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setDiscountOnCheck(final double v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setDiscountOnCheck(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setFMStringNumber(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setFMStringNumber(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setFNCurrentDocument(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setFNCurrentDocument(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setINN(final String str) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setINN(str);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setLicense(final String str) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setLicense(str);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setPassword(final int pswd) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                unsafeDriver.setPassword(pswd);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setPointPosition(final boolean p) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setPointPosition(p);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setPortNumber(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setPortNumber(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setPrice(final Currency cur) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setPrice(cur);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setProtocolType(final int ptype) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setProtocolType(ptype);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setQuantity(final double v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setQuantity(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setRegistrationNumber(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setRegistrationNumber(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSKNOStatus(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSKNOStatus(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setStringForPrinting(final String str) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setStringForPrinting(str);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setStringQuantity(final int q) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setStringQuantity(q);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSumm1(final Currency cur) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSumm1(cur);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSumm2(final Currency cur) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSumm2(cur);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSumm3(final Currency cur) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSumm3(cur);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSumm4(final Currency cur) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSumm4(cur);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setSysAdminPassword(final int v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setSysAdminPassword(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTax1(final int tax) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTax1(tax);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTax2(final int tax) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTax2(tax);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTax3(final int tax) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTax3(tax);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTax4(final int tax) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTax4(tax);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTime(final Date date) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTime(date);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTimeStr(final String time) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTimeStr(time);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setTimeout(final int timeout) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setTimeout(timeout);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setUseJournalRibbon(final boolean v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setUseJournalRibbon(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setUseReceiptRibbon(final boolean v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                unsafeDriver.setUseReceiptRibbon(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setUseSlipDocument(final boolean v) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setUseSlipDocument(v);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public void setWaitForPrintingDelay(final int t) {
        Task<Object,Driver> task = runTask( new Consumer<Object,Driver>() {
            @Override
            public Object accept(Driver unsafeDriver) {
                unsafeDriver.setWaitForPrintingDelay(t);
                return null;
            }
        });
        task.waitForFinished();
    }

    @Override
    public int storno() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.storno();
            }
        });
        return task.waitForFinished();
    }

    @Override
    public int waitForPrinting() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.waitForPrinting();
            }
        });
        return task.waitForFinished();        
    }

    @Override
    public int writeLicense() {
        Task<Integer,Driver> task = runTask( new Consumer<Integer,Driver>() {
            @Override
            public Integer accept(Driver unsafeDriver) {
                return unsafeDriver.writeLicense();
            }
        });
        return task.waitForFinished();
    }
}
