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

package com.ntc.app;

import com.ntc.vntok.VTConfig;
import com.ntc.vntok.VnTok;

/**
 *
 * @author nghiatc
 * @since Jan 16, 2021
 */
public class MainApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            VTConfig vtc = new VTConfig();
            VnTok vntok = new VnTok(vtc);
            VnTok vntok2 = new VnTok();
            
            //String s = "   ";
            //String s = "Học sinh học sinh học.";
            // Học_sinh học_sinh học .
            //String s = "VNTok là công cụ tách từ Tiếng Việt.";
            // VNTok là công_cụ tách từ Tiếng_Việt .
            String s = "VNTok là công cụ tách từ Tiếng Việt. Học sinh học sinh học.";
            // VNTok là công_cụ tách từ Tiếng_Việt . Học_sinh học_sinh học .
            
            System.out.println(s);
            String ls = vntok.tokenizeSentence(s);
            System.out.println(ls);
            
            System.out.println(s);
            String ls2 = vntok2.tokenizeSentence(s);
            System.out.println(ls2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
