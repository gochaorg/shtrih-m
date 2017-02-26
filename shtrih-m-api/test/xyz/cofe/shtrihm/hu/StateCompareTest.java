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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author user
 */
public class StateCompareTest {
    
    public StateCompareTest() {
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
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, ""));
    }

    @Test
    public void test02(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "*"));
    }

    @Test
    public void test03(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "?"));
    }

    @Test
    public void test04(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "1"));
    }

    @Test
    public void test05(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "!2"));
    }

    @Test
    public void test06(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "1.2"));
    }

    @Test
    public void test07(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "1|2"));
    }

    @Test
    public void test08(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "3|*.2"));
    }

    @Test
    public void test09(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "*.*.3"));
    }

    @Test
    public void test10(){
        ECRMode mode = new ECRMode(1, 2, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode, "!*.*.4"));
    }

    @Test
    public void test11(){
        ECRMode mode1 = new ECRMode(1, 2, "aa", 3, "bb");
        ECRMode mode2 = new ECRMode(8, 0, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode1, "1|8.0"));
        assertTrue(StateCompare.equals(mode2, "1|8.0"));
    }

    @Test
    public void test12(){
        ECRMode mode1 = new ECRMode(1, 2, "aa", 3, "bb");
        ECRMode mode2 = new ECRMode(8, 0, "aa", 3, "bb");
        assertTrue(StateCompare.equals(mode1, "1|8.0|!4"));
        assertTrue(StateCompare.equals(mode2, "1|8.0|!4"));
    }

    @Test
    public void test13(){
        ECRMode mode1 = new ECRMode(1, 2, "aa", 3, "bb");
        ECRMode mode2 = new ECRMode(8, 0, "aa", 3, "bb");
        assertTrue( StateCompare.equals(mode1, "!1|!8.0|!4") );
        assertTrue( StateCompare.equals(mode2, "!1|!8.0|!4") );
    }
}
