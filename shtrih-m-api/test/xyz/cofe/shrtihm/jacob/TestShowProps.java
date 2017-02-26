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
package xyz.cofe.shrtihm.jacob;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class TestShowProps {
    
    public TestShowProps() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        // System.out.println(System.getProperties());
        TreeMap<String,String> m = new TreeMap<String, String>();
        for( Object k : System.getProperties().keySet() )m.put(
            k.toString(), 
            System.getProperty(k.toString())
        );
        
        /*for( String k : m.keySet() ){
            System.out.println(""+k+" = "+m.get(k));
        }*/
        
        System.out.println("os.arch="+m.get("os.arch"));
        System.out.println("user.dir="+m.get("user.dir"));
        
//        File flib = new File("lib\\jacob-1.18-x86.dll");
//        if( !flib.exists() ){
//            throw new Error(flib.getPath()+" not found");
//        }
        
//        try {
//            System.getProperties().put("java.library.path", flib.getParentFile().getCanonicalPath());
//        } catch (IOException ex) {
//            Logger.getLogger(TestShowProps.class.getName()).log(Level.SEVERE, null, ex);
//            throw new Error( ex );
//        }
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
    
//    @Test
    public void test01(){
        System.out.println("test01");
        System.out.println("========");
        
        ActiveXComponent drv = new ActiveXComponent("Addin.DRvFR");
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
        
        System.out.println("show properties");
        drv.invoke("ShowProperties");

        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
    }

//    @Test
    public void test02(){
        System.out.println("test02");
        System.out.println("===========");
        
        ActiveXComponent drv = new ActiveXComponent("Addin.DRvFR");
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
        
//        System.out.println("show properties");
//        drv.invoke("ShowProperties");
//
//        System.out.println("res code = "+drv.getProperty("ResultCode"));
//        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));

        System.out.println("connect");
        Variant connectRes = drv.invoke("Connect");
        
        System.out.println("res = "+connectRes.getInt());
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
        
        /*
        Driver.StringForPrinting = "Тестовая строка";
        Driver.Price = 100;
        Driver.Quantity = 200;
        Driver.Department = 1;
        Driver.Tax1 = 0;
        Driver.Tax2 = 0;
        Driver.Tax3 = 0;
        Driver.Tax4 = 0;
        Driver.Sale();
        */
        
        System.out.println("sale");
        drv.setProperty("StringForPrinting", "Тестовая JavaString");
        drv.setProperty("Price", 50);
        drv.setProperty("Quantity", 25);
        drv.setProperty("Department", 2);
        drv.setProperty("Tax1", 0);
        drv.setProperty("Tax2", 0);
        drv.setProperty("Tax3", 0);
        drv.setProperty("Tax4", 0);
        
        Variant saleRes = drv.invoke("Sale");        
        System.out.println("res = "+saleRes.getInt());
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
        
        /*
        Driver.Password = 30;
        Driver.Summ1 = 1500;
        Driver.Summ2 = 100;
        Driver.Summ3 = 300;
        Driver.Summ4 =300;
        Driver.DiscountOnCheck = 5;
        */
        
        drv.setProperty("Password", 30);
        drv.setProperty("Summ1", 50*25);
        drv.setProperty("Summ2", 0);
        drv.setProperty("Summ3", 0);
        drv.setProperty("Summ4", 0);
        drv.setProperty("DiscountOnCheck", 5);

        drv.setProperty("Tax1", 0);
        drv.setProperty("Tax2", 0);
        drv.setProperty("Tax3", 0);
        drv.setProperty("Tax4", 0);
        
        drv.setProperty("StringForPrinting", "======");
        
        System.out.println("CloseCheck");
        Variant closeCheckRes = drv.invoke("CloseCheck");

        System.out.println("res = "+closeCheckRes.getInt());
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
        
        System.out.println("disconnect");
        Variant discnntRes = drv.invoke("Disconnect");
        
        System.out.println("res = "+discnntRes.getInt());
        System.out.println("res code = "+drv.getProperty("ResultCode"));
        System.out.println("res code desc = "+drv.getProperty("ResultCodeDescription"));
    }
}
