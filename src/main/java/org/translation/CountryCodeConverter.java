package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import java.util.Map;
import java.util.HashMap;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private List<String> countries = new ArrayList<>();
    private List<String> alpha3 = new ArrayList<>();



    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (int i = 1; i < lines.size(); i++) {
                String[] sep = lines.get(i).split("\t");
                this.countries.add(sep[0]);
                this.alpha3.add(sep[2]);

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        code = code.toUpperCase();

        int i = this.alpha3.indexOf(code);
        return this.countries.get(i);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        int i = this.countries.indexOf(country);
        return this.alpha3.get(i);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return this.countries.size();
    }
}

