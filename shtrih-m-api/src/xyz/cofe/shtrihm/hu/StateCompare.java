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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Проверяет совпадает ли режим с указанным в CallState/TargetState
 * @author nt.gocha@gmail.com
 */
public class StateCompare {
    //<editor-fold defaultstate="collapsed" desc="log Функции">
    private static final Logger logger = Logger.getLogger(StateCompare.class.getName());
    
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
        logger.entering(StateCompare.class.getName(), method, args);
    }
    private static void logExiting(String method,Object result){
        logger.exiting(StateCompare.class.getName(), method, result);
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
     * Проверяет совпадает ли режим
     * @param mode режим
     * @param stateString возможные состояния
     * @return true - режим совпадает с указанными возможными состояниями
     * @see xyz.cofe.shtrihm.CallState
     * @see xyz.cofe.shtrihm.TargetState
     */
    public static boolean equals( ECRMode mode, String stateString ){
        return equals(mode, stateString, true);
    }

    /**
     * Проверяет совпадает ли режим
     * @param mode режим
     * @param stateString возможные состояния
     * @param allowUnknowStates состояния отмеченные как ? - допустимы
     * @return true - режим совпадает с указанными возможными состояниями
     * @see xyz.cofe.shtrihm.CallState
     * @see xyz.cofe.shtrihm.TargetState
     */
    public static boolean equals( ECRMode mode, String stateString, boolean allowUnknowStates ){
        if( mode==null )throw new IllegalArgumentException( "mode==null" );
        if( stateString==null )throw new IllegalArgumentException( "stateString==null" );

        if( stateString.trim().length()==0 )return true;
        if( stateString.trim().equals("*") )return true;
        if( stateString.trim().equals("?") && allowUnknowStates )return true;
        
        String[] orStatesArray = stateString.split("\\s*\\|\\s*");
        for( String orStates : orStatesArray ){
            String[] andStatesArray = orStates.split("\\s*\\&\\s*");
            
            boolean andState = true;
            
            for( String state : andStatesArray ){
                boolean eq = equalsComponent(mode, state, allowUnknowStates);
                if( !eq ){
                    andState = false;
                    break;
                }
            }
            
            if( andState )return true;
        }
        
        return false;
    }
    
    protected static boolean equalsComponent( ECRMode mode, String state, boolean allowUnknowStates ){
        if( equalsComponent_Mode_Sub_Adv( mode, state, allowUnknowStates ) )return true;
        if( equalsComponent_Mode_Sub( mode, state, allowUnknowStates ) )return true;
        if( equalsComponent_Mode( mode, state, allowUnknowStates ) )return true;
        return false;
    }

    private static Pattern modeOnlyPattern = Pattern.compile("(?is)^(\\!)?(\\d+|\\*)$");
    protected static boolean equalsComponent_Mode( ECRMode mode, String state, boolean allowUnknowStates ){
        Matcher mModeM8 = modeOnlyPattern.matcher(state);
        if( mModeM8.matches() ){
            String notPrefix    = mModeM8.group(1);
            boolean invertRes   = notPrefix!=null && notPrefix.equalsIgnoreCase("!");
            
            String modeStr      = mModeM8.group(2);
            boolean anyMode     = modeStr.equalsIgnoreCase("*");
            int nMode           = anyMode ? -1 : Integer.parseInt(modeStr);
            
            boolean modeMatched    = anyMode    ? true : mode.getMode() == nMode;
            
            boolean allModeMatched = modeMatched;
            
            return invertRes ? !allModeMatched : allModeMatched;
        }
        
        return false;
    }
        
    private static Pattern modeAndM8Pattern = Pattern.compile("(?is)^(\\!)?(\\d+|\\*)\\.(\\d+|\\*)$");
    protected static boolean equalsComponent_Mode_Sub( ECRMode mode, String state, boolean allowUnknowStates ){
        Matcher mModeM8 = modeAndM8Pattern.matcher(state);
        if( mModeM8.matches() ){
            String notPrefix    = mModeM8.group(1);
            boolean invertRes   = notPrefix!=null && notPrefix.equalsIgnoreCase("!");
            
            String modeStr      = mModeM8.group(2);
            boolean anyMode     = modeStr.equalsIgnoreCase("*");
            int nMode           = anyMode ? -1 : Integer.parseInt(modeStr);
            
            String m8Str        = mModeM8.group(3);
            boolean anySubMode  = m8Str.equalsIgnoreCase("*");
            int nSubMode        = anySubMode ? -1 : Integer.parseInt(m8Str);
            
            boolean modeMatched    = anyMode    ? true : mode.getMode() == nMode;
            boolean subModeMatched = anySubMode ? true : mode.getMode8Status()== nSubMode;
            
            boolean allModeMatched = modeMatched && subModeMatched;
            
            return invertRes ? !allModeMatched : allModeMatched;
        }
        
        return false;
    }
    
    private static Pattern modeAndM8aMPattern = Pattern.compile("(?is)^(\\!)?(\\d+|\\*)\\.(\\d+|\\*)\\.(\\d+|\\*)$");
    protected static boolean equalsComponent_Mode_Sub_Adv( ECRMode mode, String state, boolean allowUnknowStates ){
        Matcher mModeM8Adv = modeAndM8aMPattern.matcher(state);
        if( mModeM8Adv.matches() ){
            String notPrefix    = mModeM8Adv.group(1);
            boolean invertRes   = notPrefix!=null && notPrefix.equalsIgnoreCase("!");
            
            String modeStr      = mModeM8Adv.group(2);
            boolean anyMode     = modeStr.equalsIgnoreCase("*");
            int nMode           = anyMode ? -1 : Integer.parseInt(modeStr);
            
            String m8Str        = mModeM8Adv.group(3);
            boolean anySubMode  = m8Str.equalsIgnoreCase("*");
            int nSubMode        = anySubMode ? -1 : Integer.parseInt(m8Str);
            
            String mAdvStr      = mModeM8Adv.group(4);
            boolean anyAdvMode  = mAdvStr.equalsIgnoreCase("*");
            int nAdvMode        = anyAdvMode ? -1 : Integer.parseInt(mAdvStr);
            
            boolean modeMatched    = anyMode    ? true : mode.getMode() == nMode;
            boolean subModeMatched = anySubMode ? true : mode.getMode8Status()== nSubMode;
            boolean advModeMatched = anyAdvMode ? true : mode.getAdvancedMode()== nAdvMode;
            
            boolean allModeMatched = modeMatched && subModeMatched && advModeMatched;
            
            return invertRes ? !allModeMatched : allModeMatched;
        }
        
        return false;
    }
}
