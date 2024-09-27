package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {
    private Map<String, Map<String, String>> countrylanguage = new HashMap<>();
    private List<String> countries = new ArrayList<>();
    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    @SuppressWarnings({"checkstyle:EmptyLineSeparator", "checkstyle:SuppressWarnings"})

    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    @SuppressWarnings("checkstyle:RegexpMultiline")
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);
                String country = countryObject.getString("alpha3");
                this.countries.add(country);

                Map<String, String> translations = new HashMap<>();
                for (String key : countryObject.keySet()) {
                    if (!"alpha2".equals(key) && !"alpha3".equals(key) && !"id".equals(key)) {
                        translations.put(key, countryObject.getString(key));
                    }
                }
                this.countrylanguage.put(country, translations);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    // My Part
    @Override
    public List<String> getCountryLanguages(String country) {
        return new ArrayList<>(this.countrylanguage.get(country).keySet());
    }

    @Override
    public List<String> getCountries() {
        return this.countries;
    }

    @Override
    public String translate(String country, String language) {
        return this.countrylanguage.get(country).get(language);
    }
}
