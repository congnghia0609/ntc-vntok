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

import com.ntc.visd.ViSD;
import com.ntc.vntok.nio.XMLCorpusExporter;
import com.ntc.vntok.tokens.TaggedWord;
import com.ntc.vntok.utils.FileIterator;
import com.ntc.vntok.utils.TextFileFilter;
import com.ntc.vntok.utils.UTF8FileUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.bind.JAXBException;
import opennlp.tools.sentdetect.SentenceDetector;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 *
 * @author nghiatc
 * @since Jan 16, 2021
 */
public class VnTok {

    private static Tokenizer tokenizer = null;

    private static ViSD sentenceDetector = null;

    /**
     * Number of tokens procesed
     */
    private static int nTokens = 0;

    /**
     * Default constructor
     *
     * @throws java.io.IOException
     */
    public VnTok() throws IOException {
        tokenizer = TokenizerProvider.getInstance().getTokenizer();
        sentenceDetector = new ViSD();
    }

    /**
     * Creates a tokenizer with parameters given in a properties object.
     *
     * @param properties Properties
     * @throws java.io.IOException
     */
    public VnTok(Properties properties) throws IOException {
        tokenizer = TokenizerProvider.getInstance(properties).getTokenizer();
        sentenceDetector = new ViSD(properties.getProperty("pathModelSD"));
    }

    /**
     * Creates a tokenizer with parameters given in a properties file
     *
     * @param propertiesFilename
     * @throws java.io.IOException
     */
    public VnTok(String propertiesFilename) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(propertiesFilename));
        tokenizer = TokenizerProvider.getInstance(properties).getTokenizer();
        sentenceDetector = new ViSD(properties.getProperty("pathModelSD"));
    }

    /**
     * A segment method, written for integration with other tools.
     *
     * @param sentence a sentence to be segmented
     * @return a segmented sentence
     */
    public String segment(String sentence) {
        StringBuilder result = new StringBuilder();
        StringReader reader = new StringReader(sentence);
        // tokenize the sentence
        try {
            tokenizer.tokenize(reader);
            List<TaggedWord> list = tokenizer.getResult();
            for (TaggedWord taggedWord : list) {
                String word = taggedWord.toString();
                if (TokenizerOptions.USE_UNDERSCORE) {
                    word = word.replaceAll("\\s+", "_");
                } else {
                    word = "[" + word + "]";
                }
                result.append(word);
                result.append(' ');
            }
            // update nTokens
            nTokens += list.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString().trim();
    }

    /**
     * Tokenizes a text. If the option "use sentence detector" is on, the sentence detector is called to segment the
     * text into sentences; then the tokenizer is used to tokenize detected sentences. If the option is off, the text is
     * directly tokenized by the tokenizer.
     *
     * @param text a text to tokenize.
     * @return an array of tokenized sentences.
     * @see TokenizerOptions
     * @see Tokenizer
     * @see SentenceDetector
     */
    public String[] tokenize2(String text) {
        List<String> result = new ArrayList<>();
        if (TokenizerOptions.USE_SENTENCE_DETECTOR) {
            try {
                String[] sentences = sentenceDetector.visd(text);
                for (String sentence : sentences) {
                    // segment the sentence
                    result.add(segment(sentence));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // process all the text without detecting sentences
            result.add(segment(text));
        }
        // return the result
        return result.toArray(new String[result.size()]);
    }

    public List<String> tokenize(String text) {
        List<String> result = new ArrayList<>();
        if (TokenizerOptions.USE_SENTENCE_DETECTOR) {
            List<String> sentences = sentenceDetector.visd2List(text);
            for (String sentence : sentences) {
                // segment the sentence
                result.add(segment(sentence));
            }
        } else {
            // process all the text without detecting sentences
            result.add(segment(text));
        }
        // return the result
        return result;
    }

    public String tokenizeSentence(String input) {
        //List<String> sentences = tokenize(input);
        return String.join(" ", tokenize(input));
    }

    /**
     * Turns the sentence detector on.
     */
    public void turnOnSentenceDetection() {
        TokenizerOptions.USE_SENTENCE_DETECTOR = true;
    }

    /**
     * Turns the sentence detector off.
     */
    public void turnOffSentenceDetection() {
        TokenizerOptions.USE_SENTENCE_DETECTOR = false;
    }

    /**
     * Tokenizes an input file and write result to an output file. These files are text files with UTF-8 encoding.
     *
     * @param inputFile input file
     * @param outputFile output file
     */
    public void tokenize2(String inputFile, String outputFile) {
        tokenizer.tokenize(inputFile);
        tokenizer.exportResult(outputFile);
    }

    /**
     * Tokenizes an input file and write result to an output file. These files are text files with UTF-8 encoding.
     *
     * @param inputFile input file
     * @param outputFile output file
     * @throws javax.xml.bind.JAXBException
     */
    public void tokenize(String inputFile, String outputFile) throws JAXBException {
        UTF8FileUtility.createWriter(outputFile);
        String[] paragraphs = UTF8FileUtility.getLines(inputFile);

        if (!TokenizerOptions.XML_OUTPUT) {
            for (String p : paragraphs) {
                List<String> sentences = tokenize(p);
                for (String s : sentences) {
                    UTF8FileUtility.write(s.trim());
                    UTF8FileUtility.write("\n");
                }
            }
        } else { // XML outputer
            List<List<TaggedWord>> list = new ArrayList<>();
            for (String p : paragraphs) {
                try {
                    tokenizer.tokenize(new StringReader(p));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // make a copy of the result of tokenization
                List<TaggedWord> result = new ArrayList<>(tokenizer.getResult());
                list.add(result);
                nTokens += result.size();
            }
            String output = new XMLCorpusExporter().export(list);
            UTF8FileUtility.write(output);
        }
        UTF8FileUtility.closeWriter();
    }

    /**
     * Tokenizes all files in a directory.
     *
     * @param inputDir an input dir
     * @param outputDir an output dir
     * @throws javax.xml.bind.JAXBException
     */
    public void tokenizeDirectory(String inputDir, String outputDir) throws JAXBException {
        TextFileFilter fileFilter = new TextFileFilter(TokenizerOptions.TEXT_FILE_EXTENSION);
        File inputDirFile = new File(inputDir);
        // get the current dir 
        String currentDir = new File(".").getAbsolutePath();
        //String inputDirPath = currentDir + File.separator + inputDir;
        String outputDirPath = currentDir + File.separator + outputDir;

        // get all input files
        File[] inputFiles = FileIterator.listFiles(inputDirFile, fileFilter);
        System.out.println("Tokenizing all files in the directory, please wait...");
        long startTime = System.currentTimeMillis();
        for (File aFile : inputFiles) {
            // get the simple name of the file
            String input = aFile.getName();
            // the output file have the same name with the automatic file 
            String output = outputDirPath + File.separator + input;
            // tokenize the file
            tokenize(aFile.getAbsolutePath(), output);
        }
        long endTime = System.currentTimeMillis();
        float duration = (float) (endTime - startTime) / 1000;
        System.out.println("Tokenized " + nTokens + " words of " + inputFiles.length + " files in " + duration + " (s).\n");
    }

    /**
     * Gets the tokenizer.
     *
     * @return the tokenizer
     */
    public static Tokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     * Gets the sentence detector.
     *
     * @return the sentence detector.
     */
    public static ViSD getSentenceDetector() {
        return sentenceDetector;
    }

    /**
     * The starting point of the programme.
     *
     * @param args
     * @throws javax.xml.bind.JAXBException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws JAXBException, IOException {
        // create Options object
        Options options = new Options();

        // add options
        options.addOption("nu", false, "Use spaces for separating syllables of words (no underscores)");
        options.addOption("sd", false, "Use sentence detection before tokenizing");
        options.addOption("xo", false, "Use XML output");
        options.addOption("i", true, "Input file or directory");
        options.addOption("o", true, "Output file or directory");
        options.addOption("e", true, "File extension");

        // create a parser for parsing command line
        CommandLineParser parser = new PosixParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            String inputFile = null;
            String outputFile = null;
            // get the input file
            if (line.hasOption("i")) {
                inputFile = line.getOptionValue("i");
            } else {
                System.err.println("You must provide an input Vietnamese text file (UTF-8 encoding) or an input directory.");
                formatter.printHelp("vnTokenizer", options);
                System.exit(1);
            }
            // get the output file
            if (line.hasOption("o")) {
                outputFile = line.getOptionValue("o");
            } else {
                System.err.println("You must provide an output file name or directory.");
                formatter.printHelp("vnTokenizer", options);
                System.exit(1);
            }

            if (line.hasOption("nu")) {
                TokenizerOptions.USE_UNDERSCORE = false;
            }

            if (line.hasOption("sd")) {
                TokenizerOptions.USE_SENTENCE_DETECTOR = true;
            }

            if (line.hasOption("xo")) {
                TokenizerOptions.XML_OUTPUT = true;
            }

            // tokenize
            VietTokenizer vietTokenizer = new VietTokenizer();
//            VietTokenizer vietTokenizer = new VietTokenizer("tokenizer.properties");
//            File wdir = new File("");
//            String conf = wdir.getAbsolutePath() + File.separator + "tokenizer.properties";
//            System.out.println("conf: " + conf);
//            VietTokenizer vietTokenizer = new VietTokenizer(conf);
            if (new File(inputFile).isDirectory()) {
                if (line.hasOption("e")) {
                    String ext = line.getOptionValue("e");
                    TokenizerOptions.TEXT_FILE_EXTENSION = ext;
                }
                // tokenize the whole directory
                vietTokenizer.tokenizeDirectory(inputFile, outputFile);
            } else {
                // clear the old result
                nTokens = 0;
                // tokenize the file
                System.out.println("Tokenizing, please wait...");
                long startTime = System.currentTimeMillis();
                vietTokenizer.tokenize(inputFile, outputFile);
                long endTime = System.currentTimeMillis();
                float duration = (float) (endTime - startTime) / 1000;
                System.out.println("Tokenized " + nTokens + " words" + " in " + duration + " (s).\n");
            }
            System.out.println("Done.");
        } catch (ParseException e) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            formatter.printHelp("vnTokenizer", options);
            System.exit(1);
        }
    }
}
