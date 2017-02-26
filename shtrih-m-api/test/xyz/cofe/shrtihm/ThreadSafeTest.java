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
package xyz.cofe.shrtihm;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import xyz.cofe.shtrihm.ThreadSafeDriver;

/**
 *
 * @author user
 */
public class ThreadSafeTest {
    
    public ThreadSafeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void test01(){
        System.out.println("test 01");
        System.out.println("=============================");
        
        System.out.println("create driver");
        final ThreadSafeDriver driver = new ThreadSafeDriver();
        
        Thread th1 = new Thread("thread 1"){
            @Override
            public void run() {
                System.out.println("start "+Thread.currentThread().getName());
                System.out.println("password "+driver.getPassword());
            }
        };
        th1.setDaemon(true);

        Thread th2 = new Thread("thread 2"){
            @Override
            public void run() {
                System.out.println("start "+Thread.currentThread().getName());
                System.out.println("result code desc "+driver.getResultCodeDescription());
            }
        };
        th2.setDaemon(true);
        
        th1.start();
        System.out.println("wait "+th1.getName()+" for end");
        while( th1.isAlive() ){
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSafeTest.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }

        th2.start();
        System.out.println("wait "+th2.getName()+" for end");
        while( th2.isAlive() ){
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadSafeTest.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }
}
