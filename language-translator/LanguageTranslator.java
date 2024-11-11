import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LanguageTranslator {
    private Map<String, Map<String, String>> dictionaries;

    public LanguageTranslator() {
        dictionaries = new HashMap<>();
        initializeInbuiltTranslations();
    }

    private void initializeInbuiltTranslations() {
        // Added some inbuilt languages and translations
        addWord("hello", "hello", "english"); // English to English
        addWord("good", "good", "english"); // English to English
        addWord("morning", "morning", "english"); // English to English

        addWord("adios", "bye", "spanish"); // Spanish to English
        addWord("noche", "night", "spanish"); // Spanish to English
        addWord("domingo", "sunday", "spanish"); // Spanish to English

        addWord("bonjour", "hello", "french"); // French to English
        addWord("bien", "good", "french"); // French to English
        addWord("matin", "morning", "french"); // French to English

        // Added some inbuilt sentences for translation
        addWord("this is a cat", "this is a cat", "english"); // English to English
        addWord("how are you", "how are you", "english"); // English to English
        addWord("thank you", "thank you", "english"); // English to English

        addWord("esto es un gato", "this is a cat", "spanish"); // Spanish to English
        addWord("¿cómo estás?", "how are you", "spanish"); // Spanish to English
        addWord("gracias", "thank you", "spanish"); // Spanish to English

        addWord("c'est un chat", "this is a cat", "french"); // French to English
        addWord("comment ça va", "how are you", "french"); // French to English
        addWord("merci", "thank you", "french"); // French to English
    }

    public void addWord(String word, String translation, String language) {
        // Retrieve the language dictionary for the specified language or create a new one if it doesn't exist
        Map<String, String> languageDictionary = dictionaries.getOrDefault(language.toLowerCase(), new HashMap<>());
        // Add the word and its translation to the language dictionary
        languageDictionary.put(word.toLowerCase(), translation.toLowerCase());
        // Update the dictionaries map with the modified language dictionary
        dictionaries.put(language.toLowerCase(), languageDictionary);
    }

    public String translate(String text, String sourceLanguage, String targetLanguage) {
        // Convert the source and target languages to lowercase
        sourceLanguage = sourceLanguage.toLowerCase();
        targetLanguage = targetLanguage.toLowerCase();
    
        // Retrieve the source and target language dictionaries
        Map<String, String> sourceDictionary = dictionaries.get(sourceLanguage);
        Map<String, String> targetDictionary = dictionaries.get(targetLanguage);
    
        // Check if the dictionaries for the specified languages exist
        if (sourceDictionary == null || targetDictionary == null) {
            System.out.println("Translation dictionaries not found for the specified languages.");
            return text;
        }
    
        // Use regular expression to split the text into words while preserving punctuation and remove leading/trailing spaces
        String[] words = text.split("\\s*(?:(?<=\\W)|(?=\\W))\\s*");
    
        StringBuilder translatedText = new StringBuilder();
    
        // Translate each word in the text
        for (String word : words) {
            // Remove leading/trailing spaces and convert to lowercase
            word = word.trim().toLowerCase();
            
            // Remove any leading/trailing punctuation
            while (word.length() > 0 && !Character.isLetterOrDigit(word.charAt(0))) {
                word = word.substring(1);
            }
            while (word.length() > 0 && !Character.isLetterOrDigit(word.charAt(word.length() - 1))) {
                word = word.substring(0, word.length() - 1);
            }
    
            String translatedWord = sourceDictionary.get(word);
    
            if (translatedWord != null) {
                // Append the translated word to the translated text if it exists in the target dictionary,
                // otherwise append the original word
                translatedText.append(targetDictionary.getOrDefault(translatedWord, word)).append(" ");
            } else {
                System.out.println("Translation not found for word: " + word);
                // Append the original word to the translated text
                translatedText.append(word).append(" ");
            }
        }
    
        // Convert the translated text to a string and remove trailing whitespace
        return translatedText.toString().trim();
    }

    public void showAllTranslations(String language) {
        // Retrieve the language dictionary for the specified language
        Map<String, String> languageDictionary = dictionaries.get(language.toLowerCase());

        // Check if the dictionary for the specified language exists
        if (languageDictionary == null) {
            System.out.println("Translation dictionary not found for the specified language.");
            return;
        }

        System.out.println("Translations for " + language + ":");
        // Print all word translations in the language dictionary
        for (Map.Entry<String, String> entry : languageDictionary.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    public void showSupportedLanguages() {
        System.out.println("Supported Languages:");
        for (String language : dictionaries.keySet()) {
            System.out.println("- " + language);
        }
    }

    public static void main(String[] args) {
        LanguageTranslator translator = new LanguageTranslator();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Language Translator by ProjectGurukul!");
        translator.showSupportedLanguages();

        while (true) {
            System.out.println("1. Add Word or Sentence");
            System.out.println("2. Translate Text");
            System.out.println("3. Show All Translations");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter the word or sentence to add: ");
                String word = scanner.nextLine();
                System.out.println("Enter the translation: ");
                String translation = scanner.nextLine();
                System.out.println("Enter the language: ");
                String language = scanner.nextLine();
                // Add the word and its translation to the translator
                translator.addWord(word, translation, language);
            } else if (choice == 2) {
                System.out.println("Enter the text to translate: ");
                String text = scanner.nextLine();
                System.out.println("Enter the source language: ");
                String sourceLanguage = scanner.nextLine();
                System.out.println("Enter the target language: ");
                String targetLanguage = scanner.nextLine();
                // Translate the text using the specified source and target languages
                String translatedText = translator.translate(text, sourceLanguage, targetLanguage);
                System.out.println("Translated text: " + translatedText);
            } else if (choice == 3) {
                System.out.println("Enter the language to show translations: ");
                String language = scanner.nextLine();
                // Show all translations for the specified language
                translator.showAllTranslations(language);
            } else if (choice == 4) {
                System.out.println("Thank you for using the Language Translator by ProjectGurukul!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }
}