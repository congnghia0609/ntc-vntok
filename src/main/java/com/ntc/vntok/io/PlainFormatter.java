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
package com.ntc.vntok.io;

import com.ntc.vntok.tokens.TaggedWord;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class PlainFormatter implements IOutputFormatter {

    /* (non-Javadoc)
	 * @see vn.hus.tokenizer.io.IOutputFormatter#outputLexeme(vn.hus.tokenizer.tokens.LexerToken)
     */
    @Override
    public String outputLexeme(TaggedWord token) {
        StringBuffer stBuf = new StringBuffer();
        // output only the text of the token
        stBuf.append(token.getText());
        // end of line
        stBuf.append("\n");
        return stBuf.toString();
    }
}
