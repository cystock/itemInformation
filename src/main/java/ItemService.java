import com.google.gson.JsonElement;

import java.util.Collection;
import java.util.LinkedList;

public interface ItemService {
    void addItem(String search, Item body);
    LinkedList<String> getItemsTitle(String search); //trae una lista de titulos de items
    Collection<Item> searchItems(String search);
    Collection<Item> getItems(String search); // trae una collection de objetos bajo una busqueda y los agrega al storage
    Item getItem(String search, String id);
    Item editItem(String search, String id, Item item);
    void deleteItem(String search, String id);
    Collection<Item> getItemsPriceRange(String search, float maxPrice, float minPrice);
    Collection<Item> getItemsTag(String search, String tag);
}
