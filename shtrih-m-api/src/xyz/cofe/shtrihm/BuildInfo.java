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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Информация о сборке
 * @author nt.gocha@gmail.com
 */
public class BuildInfo {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(BuildInfo.class.getName());
    
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
        logger.entering(BuildInfo.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(BuildInfo.class.getName(), method, result);
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
    
    private static Properties props = null;
    private synchronized static Properties properties(){
        if( props!=null )return props;
        
        URL url = BuildInfo.class.getResource("build.properties");
        if( url==null ){
            props = new Properties();
            return props;
        }
        
        props = new Properties();
        
        try {
            InputStream strm = url.openStream();
            props.load(strm);
            strm.close();
        } catch (IOException ex) {
            Logger.getLogger(BuildInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return props;
    }
    
    /**
     * Возвращает свойства сборки
     * @return свойства
     */
    public synchronized static Properties getProperties(){
        return properties();
    }
    
    /**
     * Версия продукта (мажор.минор.номер_сборки)
     * @return Версия продукта
     */
    public synchronized static String getVersion(){
        return properties().getProperty("version", "unknow-buildinfo");
    }
    
    /**
     * Мажорная версия
     * @return Мажорная версия
     */
    public synchronized static int getMajorVersion(){
        String mver = properties().getProperty("version.major", "-1");
        return Integer.parseInt(mver);
    }
     
    /**
     * Минорная версия
     * @return минорная версия
     */
    public synchronized static int getMinorVersion(){
        String mver = properties().getProperty("version.minor", "-1");
        return Integer.parseInt(mver);
    }
        
    /**
     * Номер сборки
     * @return номер сборки
     */
    public synchronized static int getBuildNUmber(){
        String mver = properties().getProperty("build.number", "-1");
        return Integer.parseInt(mver);
    }
        
    /**
     * Дата сборки (yyyy-MM-dd HH:mm:ss)
     * @return дата сборки
     */
    public synchronized static String getBuildTimeString(){
        return properties().getProperty("version.buildtime", "unknow-buildinfo");
    }

    /**
     * Домашняя страница проекта
     * @return домашнаяя страница проекта
     */
    public synchronized static String getHomepage(){
        String val = properties().getProperty("src.homepage", "http://?");
        return val;
    }

    /**
     * Имя лицензии по которой распостраняется исходники
     * @return Имя лицензии по которой распостраняется исходники
     */
    public synchronized static String getLicenseName(){
        String val = properties().getProperty("src.license.name", "?");
        return val;
    }
}
