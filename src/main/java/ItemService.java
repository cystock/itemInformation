import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public interface ItemService {
    void addItem(String search, Item body) throws ItemException;
    LinkedList<String> getItemsTitle(String search) throws ItemException; //trae una lista de titulos de items
    Collection<Item> searchItems(String search) throws IOException, ItemException;
    Collection<Item> getItems(String search) throws ItemException; // trae una collection de objetos bajo una busqueda y los agrega al storage
    Item getItem(String search, String id) throws ItemException;
    Item editItem(String search, String id, Item item) throws ItemException;
    void deleteItem(String search, String id) throws ItemException;
    Collection<Item> getItemsPriceRange(String search, int maxPrice, int minPrice) throws ItemException;
    Collection<Item> getItemsTag(String search, String tag) throws ItemException;
}
