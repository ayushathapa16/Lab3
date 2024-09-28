package org.translation;

import java.util.*;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    static String x = "quit";
    public static void main(String[] args) {
        Translator translator = new JSONTranslator(null);
        // Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String country = promptForCountry(translator);
            if (x.equals(country)) {
                break;
            }

            // convert the country name back into its associated code
            CountryCodeConverter countryConverter = new CountryCodeConverter(country);
            country = countryConverter.fromCountry(country);

            String language = promptForLanguage(translator, country);
            if (x.equals(language)) {
                break;
            }

            // convert language back into its associated code
            LanguageCodeConverter languageConverter = new LanguageCodeConverter(language);
            language = languageConverter.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(country, language));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (x.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        CountryCodeConverter converter = new CountryCodeConverter();

        // convert country codes to actual country names
        for (int i = 0; i < countries.size(); i++) {
            countries.set(i, converter.fromCountryCode(countries.get(i)));
        }

        // sort countries alphabetically
        Collections.sort(countries);

        // print out each country in the sorted list, one per line
        for (String country : countries) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        // note that country here refers to a country code
        List<String> codes = translator.getCountryLanguages(country);
        LanguageCodeConverter converter = new LanguageCodeConverter();
        List<String> languages = new ArrayList<>();

        // convert language codes to the actual language names
        for (String code : codes) {
            String converted = converter.fromLanguageCode(code);
            languages.add(converted);
        }

        // sort the languages alphabetically
        Collections.sort(languages);

        // print out each language in the sorted list, one per line
        for (String language : languages) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
//