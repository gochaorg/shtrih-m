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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Потоко безопасный, использование одного JAVA потока для работы с DLL
 * @author nt.gocha@gmail.com
 */
public abstract class ThreadSafe<T>
{
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(ThreadSafe.class.getName());
    
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
     * Поток ОС
     */
    protected volatile Thread thread;
    
    /**
     * Возвращает созданый (или создает) исполняющийся поток ОС
     * @return поток ОС
     */
    public synchronized Thread getThread(){
        if( thread!=null ){
            return thread;
        }
        
        thread = new Thread(){
            @Override
            public void run() {
                runThread();
            }
        };

        configure(thread);
        
        thread.start();        
        return thread;
    }
    
    /**
     * Указывает имя потока и поток исполняется как "демон"
     * @param thread поток
     */
    protected void configure( Thread thread ){
        if( thread==null )throw new IllegalArgumentException( "thread==null" );

        thread.setDaemon(true);
        thread.setName("ThreadSafe Shtrish-m driver");
    }

    /**
     * Синхронизированная очередь заданий
     */
    protected Queue<Task> tasks;

    /**
     * 
     * @return 
     */
    public synchronized Queue<Task> getTasks(){
        Thread th = getThread();
        if( !th.isAlive() )th.start();
        
        if( tasks!=null ){
            return tasks;
        }
        
        tasks = new ConcurrentLinkedQueue<Task>();
        return tasks;
    }
    
    protected void runThread(){
        onThreadStarted();
        
        String threadName = Thread.currentThread().getName();
        
        while( true ){
            if( Thread.interrupted() )break;
            
            Queue<Task> queue = getTasks();
            
            Task task = queue.poll();
            
            if( task==null ){
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    break;
                }
                continue;
            }
            
            String taskName = task.getName();
            
            if( taskName!=null && threadName!=null ){
                Thread.currentThread().setName(taskName);
            }
            
            task.runInQueue();

            if( taskName!=null && threadName!=null ){
                Thread.currentThread().setName(threadName);
            }
        }
        
        onThreadFinished();
    }
    
    /**
     * Потоко опасный объект
     */
    protected volatile T safeObject;
    
    /**
     * Создает объект с которым будет работа в потоке.
     * <p>
     * Вызывается в onThreadStarted
     * @return объект
     */
    protected abstract T createSafeObject();
    
    /**
     * Освобождает объект
     * @param safeObj Потоко опасный объект
     */
    protected abstract void releaseSafeObject(T safeObj);
    
    /**
     * Вызывается в начеле исполнения потока
     */
    protected synchronized void onThreadStarted(){
        logInfo("started thread {0} id {1}", Thread.currentThread().getName(), Thread.currentThread().getId());
        safeObject = createSafeObject();
    }

    /**
     * Вызывается в конце исполнения потока
     */
    protected synchronized void onThreadFinished(){
        releaseSafeObject(safeObject);
        safeObject = null;
        logInfo("finished thread {0} id {1}", Thread.currentThread().getName(), Thread.currentThread().getId());
    }

    /**
     * Создает задачу для исполнения и добавляет ее в очередь на исполнение
     * @param code Код принимающий потоко опасный объект
     * @return Задача
     */
    public <R> Task runTask( final Consumer<R,T> code ){
        if( code==null )throw new IllegalArgumentException( "code==null" );
        
        Task<R,T> task = new Task<R,T>() {
            @Override
            protected T getSafeObject() {
                return safeObject;
            }
            
            @Override
            public R run( T safeObject ) {
                return code.accept( safeObject );
            }
        };
        
        getTasks().add(task);
        
        return task;
    }
}
