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

import com.ntc.vntok.io.Outputer;
import com.ntc.vntok.segmenter.Segmenter;
import com.ntc.vntok.tokens.LexerRule;
import com.ntc.vntok.tokens.TaggedWord;
import com.ntc.vntok.tokens.WordToken;
import com.ntc.vntok.utils.JsonUtils;
import com.ntc.vntok.utils.ResourceUtil;
import com.ntc.vntok.utils.UTF8FileUtility;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author nghiatc
 * @since Mar 25, 2021
 */
public class Tokenizer {

    /**
     * List of rules for this lexer
     */
    //private List<LexerRule> rules = new ArrayList<>();
    private static List<LexerRule> rules;

    /**
     * The current input stream
     */
    private InputStream inputStream;

    /**
     * Current reader, keep track of our position within the input file
     */
    private LineNumberReader lineReader;

    /**
     * Current line
     */
    private String line;

    /**
     * Current column
     */
    private int column;

    /**
     * A list of tokens containing the result of tokenization
     */
    private List<TaggedWord> result = new ArrayList<>();

    /**
     * A lexical segmenter
     */
    private final Segmenter segmenter;
    
    /**
     * A lexer token outputer
     */
    private Outputer outputer = new Outputer();

    /**
     * Are ambiguities resolved? True by default.
     */
    private boolean isAmbiguitiesResolved = true;

    private final ResultMerger resultMerger = new ResultMerger();

    //private static ResultSplitter resultSplitter = new ResultSplitter();
    private static ResultSplitter resultSplitter;

    /**
     * Creates a tokenizer from a lexers filename and a segmenter.
     *
     * @param segmenter a lexical segmenter<ol></ol>
     */
    public Tokenizer(Segmenter segmenter) {
        this.segmenter = segmenter;
        // load the lexer rules
        if (rules == null) {
            loadLexerRules();
        }
        if (resultSplitter == null) {
            resultSplitter = new ResultSplitter();
        }
        System.out.println("Initializing tokenizer...OK");
    }
    
    public Tokenizer(Segmenter segmenter, VTConfig cfg) throws FileNotFoundException {
        this.segmenter = segmenter;
        // load the lexer rules
        if (rules == null) {
            if (cfg.getLexers() == null || cfg.getLexers().isEmpty()) {
                loadLexerRules();
            } else {
                loadLexerRules(cfg.getLexers());
            }
        }
        if (resultSplitter == null) {
            if (cfg.getNamePrefix() == null || cfg.getNamePrefix().isEmpty()) {
                resultSplitter = new ResultSplitter();
            } else {
                resultSplitter = new ResultSplitter(cfg.getNamePrefix());
            }
        }
        System.out.println("Initializing tokenizer...OK");
    }

    /**
     * Creates a tokenizer from a lexers filename and a segmenter.
     *
     * @param lexersFilename the file that contains lexer rules
     * @param segmenter a lexical segmenter<ol></ol>
     * @throws java.io.FileNotFoundException
     */
    public Tokenizer(String lexersFilename, Segmenter segmenter) throws FileNotFoundException {
        this.segmenter = segmenter;
        // load the lexer rules
        if (rules == null) {
            loadLexerRules(lexersFilename);
        }
        if (resultSplitter == null) {
            resultSplitter = new ResultSplitter();
        }
        System.out.println("Initializing tokenizer...OK");
    }

    /**
     * Creates a tokenizer from a properties object and a segmenter. This is the prefered way to create a tokenizer.
     *
     * @param properties
     * @param segmenter
     * @throws java.io.FileNotFoundException
     */
    public Tokenizer(Properties properties, Segmenter segmenter) throws FileNotFoundException {
        this.segmenter = segmenter;
        // load the lexer rules
        if (rules == null) {
            loadLexerRules(properties.getProperty("lexers"));
        }
        if (resultSplitter == null) {
            resultSplitter = new ResultSplitter();
        }
        System.out.println("Initializing tokenizer...OK");
    }

    /**
     * @return an outputer
     */
    public Outputer getOutputer() {
        return outputer;
    }

    /**
     * @param outputer The outputer to set.
     */
    public void setOutputer(Outputer outputer) {
        this.outputer = outputer;
    }
    
    /**
     * Load lexer specification file. This text file contains lexical rules to tokenize a text
     *
     * @param lexersFilename specification file
     */
    private void loadLexerRules() {
        rules = new ArrayList<>();
        InputStream lexersStream = ResourceUtil.getResourceAsStream(TCommon.LEXERS);
        Map<String, String> mapRules = JsonUtils.Instance.getMap(lexersStream);
        for (String k : mapRules.keySet()) {
            LexerRule lr = new LexerRule(k, mapRules.get(k));
            rules.add(lr);
            //System.out.println(k + ": " + mapRules.get(k));
        }
        System.out.println("Rule lexers loaded.");
    }

    /**
     * Load lexer specification file. This text file contains lexical rules to tokenize a text
     *
     * @param lexersFilename specification file
     */
    private void loadLexerRules(String lexersFilename) throws FileNotFoundException {
        rules = new ArrayList<>();
        InputStream lexersStream = new FileInputStream(lexersFilename);
        Map<String, String> mapRules = JsonUtils.Instance.getMap(lexersStream);
        for (String k : mapRules.keySet()) {
            LexerRule lr = new LexerRule(k, mapRules.get(k));
            rules.add(lr);
        }
        System.out.println("Rule lexers loaded.");
    }

    /**
     * Tokenize a reader. If ambiguities are not resolved, this method selects the first segmentation for a phrase if
     * there are more than one segmentations. Otherwise, it selects automatically the most probable segmentation
     * returned by the ambiguity resolver.
     * @param reader
     * @throws java.io.IOException
     */
    public void tokenize(Reader reader) throws IOException {
        // Firstly, the result list is emptied
        result.clear();
        lineReader = new LineNumberReader(reader);
        // position within the file
        line = null;
        column = 1;
        // do tokenization
        while (true) {
            // get the next token
            TaggedWord taggedWord = getNextToken();
            // stop if there is no more token
            if (taggedWord == null) {
                break;
            }
			// DEBUG 
			//System.out.println("taggedWord = " + taggedWord);
            // if this token is a phrase, we need to use a segmenter
            // object to segment it.
            if (taggedWord.isPhrase()) {
				//System.out.println("taggedWord phrase = " + taggedWord);
                String phrase = taggedWord.getText().trim();
                if (!isSimplePhrase(phrase)) {
                    String[] tokens = null;
                    // segment the phrase
                    List<String[]> segmentations = segmenter.segment(phrase);
                    if (segmentations.isEmpty()) {
                        System.err.println("The segmenter cannot segment the phrase \"" + phrase + "\"");
                    }
                    // resolved the result if there is such option
                    // and the there are many segmentations.
                    if (isAmbiguitiesResolved() && segmentations.size() > 1) {
                        tokens = segmenter.resolveAmbiguity(segmentations);
                    } else {
                        // get the first segmentation
                        Iterator<String[]> it = segmentations.iterator();
                        if (it.hasNext()) {
                            tokens = it.next();
                        }
                    }
                    if (tokens == null) {
                        System.err.println("Problem: " + phrase);
                    }

                    // build tokens of the segmentation
                    if (tokens != null) {
                        for (String tok : tokens) {
                            WordToken token = new WordToken(new LexerRule("word"), tok, lineReader.getLineNumber(), column);
                            result.add(token);
                            column += tok.length();
                        }
                    }
                } else { // phrase is simple
                    if (phrase.length() > 0) {
                        result.add(taggedWord);
                    }
                }
            } else { // lexerToken is not a phrase
                // check to see if it is a named entity
                if (taggedWord.isNamedEntity()) {
                    // try to split the lexer into two lexers
                    TaggedWord[] tokens = resultSplitter.split(taggedWord);
                    if (tokens != null) {
                        result.addAll(Arrays.asList(tokens));
                    } else {
                        result.add(taggedWord);
                    }
                } else {
                    // we simply add it into the list
                    if (taggedWord.getText().trim().length() > 0) {
                        result.add(taggedWord);
                    }
                }
            }
        }
        // close the line reader
        if (lineReader != null) {
            lineReader.close();
        }
        // merge the result
        result = resultMerger.mergeList(result);
    }

    /**
     * Tokenize a file.
     *
     * @param filename a filename
     */
    public void tokenize(String filename) {
        // create a UTF8 reader
        UTF8FileUtility.createReader(filename);
        try {
            tokenize(UTF8FileUtility.reader);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            UTF8FileUtility.closeReader();
        }
    }

    /**
     * A phrase is simple if it contains only one syllable.
     *
     * @param phrase
     * @return true/false
     */
    private boolean isSimplePhrase(String phrase) {
        phrase = phrase.trim();
        return phrase.indexOf(' ') < 0;
    }

    /**
     * Return the next token from the input. Version 2, less greedy method than version 1.
     *
     * @return next token from the input
     * @throws IOException
     */
    private TaggedWord getNextToken() throws IOException {
        // scan the file line by line and quit when no more lines are left
        if (line == null || line.length() == 0) {
            line = lineReader.readLine();
            if (line == null) {
                if (inputStream != null) {
                    inputStream.close();
                }
                lineReader = null;
                return null;
            }
            // an empty line corresponds to an empty tagged word
            if (line.trim().length() == 0) {
                System.err.println("Create an empty line tagged word...");
                //return new TaggedWord(new LexerRule("return", "(\\^\\$)"), "\n");
                return new TaggedWord(new LexerRule("return"), "\n");
            }
            column = 1;
        }
        // match the next token
        TaggedWord token = null;
        // the end of the next token, within the line
        int tokenEnd = -1;
        // the length of the rule that matches the most characters from the input
        int longestMatchLen = -1;
        int lineNumber = lineReader.getLineNumber();
        LexerRule selectedRule = null;
        // find the rule that matches the longest substring of the input
        for (int i = 0; i < rules.size(); i++) {
            LexerRule rule = rules.get(i);
            // get the precompiled pattern for this rule
            Pattern pattern = rule.getPattern();
            // create a matcher to perform match operations on the string 
            // by interpreting the pattern
            Matcher matcher = pattern.matcher(line);
            // find the longest match from the start 
            if (matcher.lookingAt()) {
                int matchLen = matcher.end();
                if (matchLen > longestMatchLen) {
                    longestMatchLen = matchLen;
                    tokenEnd = matchLen;
                    selectedRule = rule;
                }
            }
        }
        // 
        // check if this relates to an email address (to fix an error with email) 
        // yes, I know that this "manual" method must be improved by a more general way.
        // But at least, it can fix an error with email addresses at the moment. :-)
        int endIndex = tokenEnd;
        if (tokenEnd < line.length()) {
            if (line.charAt(tokenEnd) == '@') {
                while (endIndex > 0 && line.charAt(endIndex) != ' ') {
                    endIndex--;
                }
            }
        }
        // the following statement fixes the error reported by hiepnm, for the case like "(School@net)"
        if (endIndex == 0) {
            endIndex = tokenEnd;
        }

        if (selectedRule == null) {
            selectedRule = new LexerRule("word");
        }
        String text = line.substring(0, endIndex);
        token = new TaggedWord(selectedRule, text, lineNumber, column);
        // we match something, skip past the token, get ready
        // for the next match, and return the token
        column += endIndex;
        line = line.substring(endIndex).trim();
		//System.out.println(line);
        return token;
    }
    
    /**
     * Return the next token from the input. This old version is deprecated.
     *
     * @return next token from the input
     * @throws IOException
     * @see {@link #getNextToken()}
     */
    @SuppressWarnings("unused")
    @Deprecated
    private TaggedWord getNextTokenOld() throws IOException {
        // scan the file line by line and quit when no more lines are left
        if (line == null || line.length() == 0) {
            line = lineReader.readLine();
            if (line == null) {
                if (inputStream != null) {
                    inputStream.close();
                }
                lineReader = null;
                return null;
            }
            // an empty line:
            if (line.length() == 0) {
                return new TaggedWord("\n");
            }
            column = 1;
        }
        // match the next token
        TaggedWord token = null;
        // the end of the next token, within the line
        int tokenEnd = -1;
        // the length of the rule that matches the most characters from the
        // input
        int longestMatchLen = -1;
        String text = "";
        // find the rule that matches the longest substring of the input
        for (int i = 0; i < rules.size(); i++) {
            LexerRule rule = rules.get(i);
            // get the precompiled pattern for this rule
            Pattern pattern = rule.getPattern();
            // create a matcher to perform match operations on the string 
            // by interpreting the pattern
            Matcher matcher = pattern.matcher(line);
            // if there is a match, calculate its length
            // and compare it with the longest match len
            // Here, we attempts to match the input string, starting at the beginning, against the pattern.
            // The method lookingAt() always starts at the beginning of the region; 
            // unlike that method, it does not require that the entire region be matched. 
            // This method returns true if, and only if, a prefix of the input sequence matches 
            // this matcher's pattern
            // Problem: if the string is "abc xyz@gmail.com", the next word will be "abc xyz", 
            // which is a wrong segmentation! Need a less greedy method to overcome this shortcomming.
            if (matcher.lookingAt()) {
                int matchLen = matcher.end();
                if (matchLen > longestMatchLen) {
                    longestMatchLen = matchLen;
                    text = matcher.group(0);
                    System.err.println(rule.getName() + ": " + text);
                    int lineNumber = lineReader.getLineNumber();
                    token = new TaggedWord(rule, text, lineNumber, column);
                    tokenEnd = matchLen;
                }
            }
        }
        // if we didn't match anything, we exit...
        if (token == null) {
            System.err.println("Error! line = " + lineReader.getLineNumber() + ", col = " + column);
            System.out.println(line);
            System.exit(1);
            return null;
        } else {
            // we match something, skip past the token, get ready
            // for the next match, and return the token
            column += tokenEnd;
            line = line.substring(tokenEnd);
            return token;
        }
    }

    /**
     * Export the result of tokenization to a text file, the output format is determined by an outputer
     *
     * @param filename a file to export the result to
     * @param outputer an outputer
     * @see IOutputFormatter
     */
    public void exportResult(String filename, Outputer outputer) {
        System.out.print("Exporting result of tokenization...");
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(outputer.output(result));
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("OK.");
    }

    /**
     * Export the result of tokenization to a text file using the plain output format.
     *
     * @param filename
     */
    public void exportResult(String filename) {
        System.out.print("Exporting result of tokenization...");
        UTF8FileUtility.createWriter(filename);
        for (TaggedWord token : result) {
            UTF8FileUtility.write(token.toString() + "\n");
        }
        UTF8FileUtility.closeWriter();
        System.out.println("OK");
    }

    /**
     * @return Returns the a result of tokenization.
     */
    public List<TaggedWord> getResult() {
        return result;
    }

    /**
     * @param result The result to set.
     */
    public void setResult(List<TaggedWord> result) {
        this.result = result;
    }

    /**
     * Dispose the tokenizer
     *
     */
    public void dispose() {
        // dispose the segmenter
        segmenter.dispose();
        // clear all lexer tokens
        result.clear();
    }

    /**
     * Return <code>true</code> if the ambiguities are resolved by a resolver. The default value is <code>false</code>.
     *
     * @return
     */
    public boolean isAmbiguitiesResolved() {
        return isAmbiguitiesResolved;
    }

    /**
     * Is the ambiguity resolved?
     *
     * @param b <code>true/false</code>
     */
    public void setAmbiguitiesResolved(boolean b) {
        isAmbiguitiesResolved = b;
    }

    /**
     * Return the lexical segmenter
     *
     * @return
     */
    public Segmenter getSegmenter() {
        return segmenter;
    }
}
