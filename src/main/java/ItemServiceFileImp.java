import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.LinkedList;

public class ItemServiceFileImp implements ItemService {
    private FileOutputStream out;

    public ItemServiceFileImp() throws FileNotFoundException {
        out = new FileOutputStream("c://temp//itemFile.txt");
    }

    @Override
    public void addItem(String search, Item body) {

    }

    @Override
    public LinkedList<String> getItemsTitle(String search) {
        return null;
    }

    @Override
    public Collection<Item> searchItems(String search) {
        return null;
    }

    @Override
    public Collection<Item> getItems(String search) {
        return null;
    }

    @Override
    public Item getItem(String search, String id) {
        return null;
    }

    @Override
    public Item editItem(String search, String id, Item item) {
        return null;
    }

    @Override
    public void deleteItem(String search, String id) {

    }

    @Override
    public Collection<Item> getItemsPriceRange(String search, int maxPrice, int minPrice) {
        return null;
    }

    @Override
    public Collection<Item> getItemsTag(String search, String tag) {
        return null;
    }
}
