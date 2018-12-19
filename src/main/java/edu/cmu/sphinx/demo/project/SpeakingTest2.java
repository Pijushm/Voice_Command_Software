package edu.cmu.sphinx.demo.project;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.IOException;

/**
 * Created by Pijush on 8/29/2017.
 */
public class SpeakingTest2 {
    private static SpeechResult result;

    public static void main(String[] args) throws Exception {

       /* Configuration configuration = new Configuration();

        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/3453.dic");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/3453.lm");



        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
// Start recognition process pruning previously cached data.
        recognizer.startRecognition(true);

// Pause recognition process. It can be resumed then with startRecognition(false).


        while ((result = recognizer.getResult()) != null) {
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();*/
    }

    public LiveSpeechRecognizer getSpeech() throws IOException {
        Configuration configuration = new Configuration();

        configuration
                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration
                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/dictionary.dic");
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/language.lm");


        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
// Start recognition process pruning previously cached data.
        //recognizer.startRecognition(true);

// Pause recognition process. It can be resumed then with startRecognition(false).


        return recognizer;
    }

}
