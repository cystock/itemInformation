import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Search {
    private String search;

    private final String currencyUrl =  "https://api.mercadolibre.com/currencies/";
    private final String searchUrl = "https://api.mercadolibre.com/sites/MLA/search?q=";



    public Search(String search) {
        this.search = search;
    }

    public Collection<Item> searchItem (String search) throws IOException {
        Collection<Item> itemCollection;
        HashMap<String, JsonElement> currencyMap;
        try {
            HttpURLConnection connectionSearch = createConnection(generateSearchUrl(search));
            String urlStringSearch = bufferedReader(connectionSearch);

            JsonElement jsonElement = new JsonParser().parse(urlStringSearch);
            JsonArray jsonArrayResult = jsonElement.getAsJsonObject().getAsJsonArray("results");

            for (JsonElement je: jsonArrayResult) {
                String currency_id = je.getAsJsonObject().get("currency_id").getAsString();
                String urlStringCurrency = searchCurrencyId(currency_id);
                JsonElement jsonElem = new JsonParser().parse(urlStringCurrency);
                je.getAsJsonObject().add("currency_id", jsonElem.getAsJsonObject());
            }

            String results = jsonArrayResult.toString();

            Gson gson = new Gson();
            Item[] items = gson.fromJson(results, Item[].class);

            itemCollection = createCollection(items);

            return itemCollection;
        } catch (IOException e){
            System.out.println("Invalid collection. Exception: " + e);
        }
        return null;

    }

    private String generateSearchUrl(String search){
        return this.searchUrl.concat(search);
    }

    private String generateCurrencyUrl(String currency){
        return this.currencyUrl.concat(currency);
    }

    private String searchCurrencyId(String currency_id) throws IOException {
        HashMap<String, String> currencyMap = new HashMap<>();
        if (!currencyMap.containsKey(currency_id)){
            HttpURLConnection connectionCurrency = createConnection(generateCurrencyUrl(currency_id));
            String urlStringCurrency = bufferedReader(connectionCurrency);
            currencyMap.put(currency_id, urlStringCurrency);
        }
        return currencyMap.get(currency_id);

    }

    private String bufferedReader(HttpURLConnection connection) throws IOException {
        String urlString = "";
        String current = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((current = in.readLine()) != null){
                urlString += current;
            }
        } catch (IOException e){
            System.out.println("Can not read buffer. Exception: " + e);
        }
        return urlString;
    }

    private HttpURLConnection createConnection(String url){
        HttpURLConnection connection = null;
        try {
            URL connectionUrl = new URL(url);
            URLConnection urlConnection = connectionUrl.openConnection();
            connection = (HttpURLConnection) urlConnection;
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json ; utf-8");
            connection.setRequestProperty("Accept", "application/json" );
        } catch (MalformedURLException e){
            System.out.println("Invalid URL. Exception: " + e.getMessage());
        } catch (IOException e){
            System.out.println("Cannot connect to the defined URL. Exception: " + e.getMessage() );
        }
        return connection;
    }

    private Collection<Item> createCollection(Item[] item){
        Collection<Item> collection = new ArrayList<Item>();
        Arrays.stream(item).forEach(collection::add);
        return collection;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "Search{" +
                "search='" + search + '\'' +
                '}';
    }
}
