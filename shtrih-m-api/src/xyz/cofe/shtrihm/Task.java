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

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Задача которая выполняет в потоке
 * @author nt.gocha@gmail.com
 */
public abstract class Task<R,T> {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(Task.class.getName());
    
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
        logger.entering(ThreadSafe.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(ThreadSafe.class.getName(), method, result);
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
     * Здесть реализовать код с работой потоко опасного объекта
     * @param safeObject объект с которым работаем
     */
    public abstract R run(T safeObject);
    
    /**
     * Ошибка в выполнении кода
     */
    protected volatile Throwable error = null;
    
    /**
     * Время начала выполнения
     */
    protected volatile Date started = null;
    
    /**
     * Время завершения выполнения
     */
    protected volatile Date finished = null;
    
    /**
     * Возвращает потоко опасный объект
     * @return Потоко опасный объект
     */
    protected abstract T getSafeObject();
    
    /**
     * Имя выполняемого кода
     */
    protected volatile String name = null;
    
    protected volatile R result = null;
    
    /**
     * Вызывается в потоке ThreadSafe
     */
    public synchronized void runInQueue(){ 
        try {
            started = new Date();
            finished = null;
            error = null;
            result = null;
            notifyAll();
            result = run( getSafeObject() );
        } catch (Throwable err) {
            error = err;
            logSevere("task {0} error {1}", this, err.getMessage());
            logException(err);
        } finally {
            finished = new Date();
            notifyAll();
        }
    }
    
    /**
     * Возвращает имя выполняемого кода
     * @return имя или null
     */
    public String getName(){ return name; }
    
    /**
     * Возвращает ошибку выполнения
     * @return ошибка выполнения или null
     */
    public Throwable getRunError(){ return error; }
    
    public Date getStarted(){ return started; }
    
    public Date getFinished(){ return finished; }
    
    /**
     * Флаг что код еще не выполнялся
     * @return true - код еще не выполнялся
     */
    public boolean isPrepared(){
        return started==null && finished==null;
    }
    
    /**
     * Флаг что код еще выполняется
     * @return true - код еще выполняется
     */
    public boolean isRunned(){
        return started!=null && finished==null;
    }
    
    /**
     * Флаг что код уже выполнен
     * @return true - код уже выполнен
     */
    public boolean isFinished(){
        return finished!=null;
    }
    
    /**
     * Ожидание завершения
     */
    public R waitForFinished(){
        while (true) {
            if( isFinished() ){
                break;
            }else{
                synchronized(this){
                    try {
                        wait(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
                        break;
                    }
                }
            }
        }
        
        Throwable err = getRunError();
        if( err!=null )throw new Error(err.getMessage(), err);
        
        return result;
    }
}
