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

import com.ntc.vntok.tokens.TaggedWord;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class ResultMerger {

    private static String DAY_STRING_1 = "ngày";
    private static String DAY_STRING_2 = "Ngày";

    private static String MONTH_STRING_1 = "tháng";
    private static String MONTH_STRING_2 = "Tháng";

    private static String YEAR_STRING_1 = "năm";
    private static String YEAR_STRING_2 = "Năm";

    public ResultMerger() {

    }

    private TaggedWord mergeDateDay(TaggedWord day, TaggedWord nextToken) {
        TaggedWord taggedWord = null;
        if (nextToken.isDateDay()) {
            String text = day.getText() + " " + nextToken.getText();
            taggedWord = new TaggedWord(nextToken.getRule(), text, nextToken.getLine(), day.getColumn());
        }
        return taggedWord;
    }

    private TaggedWord mergeDateMonth(TaggedWord month, TaggedWord nextToken) {
        TaggedWord taggedWord = null;
        if (nextToken.isDateMonth()) {
            String text = month.getText() + " " + nextToken.getText();
            taggedWord = new TaggedWord(nextToken.getRule(), text, nextToken.getLine(), month.getColumn());
        }
        return taggedWord;
    }

    private TaggedWord mergeDateYear(TaggedWord year, TaggedWord nextToken) {
        TaggedWord taggedWord = null;
        // merge the date year or a number
        if (nextToken.isDateYear() || nextToken.isNumber()) {
            String text = year.getText() + " " + nextToken.getText();
            taggedWord = new TaggedWord(nextToken.getRule(), text, nextToken.getLine(), year.getColumn());
        }
        return taggedWord;
    }

    /**
     * @param token
     * @param nextToken
     * @return a lexer token merging from two tokens or <tt>null</tt>.
     */
    private TaggedWord mergeDate(TaggedWord token, TaggedWord nextToken) {
        if (token.getText().equals(DAY_STRING_1) || token.getText().equals(DAY_STRING_2)) {

            return mergeDateDay(token, nextToken);
        }
        if (token.getText().equals(MONTH_STRING_1) || token.getText().equals(MONTH_STRING_2)) {
            return mergeDateMonth(token, nextToken);
        }
        if (token.getText().equals(YEAR_STRING_1) || token.getText().equals(YEAR_STRING_2)) {
            return mergeDateYear(token, nextToken);
        }
        return null;
    }

    /**
     * Merge the result of the tokenization.
     *
     * @param tokens
     * @return a list of lexer tokens
     */
//    public List<TaggedWord> mergeList(List<TaggedWord> tokens) {
//        List<TaggedWord> result = new ArrayList<>();
//        TaggedWord token = new TaggedWord(""); // a fake start token
//        Iterator<TaggedWord> it = tokens.iterator();
//        while (it.hasNext()) {
//            // get a token
//            TaggedWord nextToken = it.next();
//            // try to merge the two tokens
//            TaggedWord mergedToken = mergeDate(token, nextToken);
//            // if they are merged
//            if (mergedToken != null) {
////				System.out.println(mergedToken.getText()); // DEBUG
//                result.remove(result.size() - 1);
//                result.add(mergedToken);
//            } else { // if they aren't merge
//                result.add(nextToken);
//            }
//            token = nextToken;
//        }
//        return result;
//    }
    public List<TaggedWord> mergeList(List<TaggedWord> tokens) {
        List<TaggedWord> result = new ArrayList<>();
        TaggedWord token = new TaggedWord(""); // a fake start token
        for (TaggedWord nextToken : tokens) {
            // try to merge the two tokens
            TaggedWord mergedToken = mergeDate(token, nextToken);
            // if they are merged
            if (mergedToken != null) {
				//System.out.println(mergedToken.getText()); // DEBUG
                result.remove(result.size() - 1);
                result.add(mergedToken);
            } else { // if they aren't merge
                result.add(nextToken);
            }
            token = nextToken;
        }
        return result;
    }
}
