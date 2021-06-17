/*
 * Copyright 2021 nghiatc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ntc.vntok;

import java.io.IOException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nghiatc
 * @since Jun 17, 2021
 * 
 * cd ntc-vntok
 *
 * # Run all the unit test classes. 
 * mvn test
 *
 * # Run a single test class. 
 * mvn -Dtest=TestApp1 test
 *
 * # Run a single test method from a test class. 
 * mvn -Dtest=TestApp1#methodname test
 */
public class TestDefault {
    private static VnTok vntok;

    @BeforeClass
    public static void initTestDefault() throws IOException {
        vntok = new VnTok();
    }

    @Before
    public void beforeEachTest() {
        //System.out.println("This is executed before each Test");
    }

    @After
    public void afterEachTest() {
        //System.out.println("This is executed after each Test");
    }

    @Test
    public void testDefault() {
        String s = "VNTok là công cụ tách từ Tiếng Việt.";
        String rs = vntok.tokenizeSentence(s);
        String exp = "VNTok là công_cụ tách từ Tiếng_Việt .";
        Assert.assertEquals("TestDefault", exp, rs);
    }
}
