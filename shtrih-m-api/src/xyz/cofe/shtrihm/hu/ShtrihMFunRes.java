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

import java.util.logging.Level;
import java.util.logging.Logger;
import xyz.cofe.shtrihm.Driver;
import xyz.cofe.shtrihm.jacob.ShtrihMJacobDriver;

/**
 * Результат выполнения функции
 * @author nt.gocha@gmail.com
 */
public class ShtrihMFunRes<T> {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(ShtrihMFunRes.class.getName());
    
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
        logger.entering(ShtrihMFunRes.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(ShtrihMFunRes.class.getName(), method, result);
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
     * Конструктор
     * @param code код резутата (0 - ок)
     * @param description описание
     */
    public ShtrihMFunRes( int code, String description ){
        this.result = null;
        this.code = code;
        this.description = description==null ? ( code==0 ? "Успешно" : "("+code+") Нет описания" ) : description;
    }
    
    /**
     * Конструктор
     * @param result Результат вызова функции
     * @param code код резутата (0 - ок)
     * @param description описание
     */
    public ShtrihMFunRes( T result, int code, String description ){
        this.result = result;
        this.code = code;
        this.description = description==null ? ( code==0 ? "Успешно" : "("+code+") Нет описания" ) : description;
    }
    
    /**
     * Конструктор
     * @param result Результат вызова функции
     * @param code код резутата (0 - ок)
     * @param description описание
     */
    public ShtrihMFunRes( T result, int code, Driver description ){
        this( result, code, description!=null ? description.getResultCodeDescription() : null );
    }
    
    /**
     * Конструктор
     * @param code код резутата (0 - ок)
     * @param description описание
     */
    public ShtrihMFunRes( int code, Driver description ){
        this( null, code, description!=null ? description.getResultCodeDescription() : null );
    }
    
    /**
     * Конструктор
     * @param drv Доайвер
     */
    public ShtrihMFunRes( ShtrihMJacobDriver drv ){
        if( drv==null )throw new IllegalArgumentException( "drv==null" );
        
        this.result = null;
        
        this.code = drv.getResultCode();
        
        String m = drv.getResultCodeDescription();
        this.description = m==null ? ( code==0 ? "Успешно" : "("+code+") Нет описания" ) : m;
    }
    
    /**
     * Результат вызова функции
     */
    public T result;
    
    /**
     * Результат вызова функции
     * @return 
     */
    public T getResult(){ return result; }
    
    /**
     * код резутата (0 - ок)
     */
    public final int code;
    
    /**
     * код резутата (0 - ок)
     * @return 
     */
    public int getCode(){ return code; }
    
    public boolean isCodeSuccess(){ return code==0; }
    
    /**
     * Расшифровка кода
     */
    public final String description;
    
    /**
     * Расшифровка кода
     * @return 
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * Заглушка
     */
    public static final ShtrihMFunRes develDummy = 
        new ShtrihMFunRes(Integer.MIN_VALUE/2+8512, "Нет описания - сие заглушка");
}
