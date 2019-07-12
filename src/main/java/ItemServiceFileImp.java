import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class ItemServiceFileImp implements ItemService {
    private FileOutputStream out;

    public ItemServiceFileImp() throws FileNotFoundException {
        out = new FileOutputStream("c://temp//itemFile.txt");
    }


    @Override
    public void addItem(String search, Item body) throws ItemException {

    }

    @Override
    public LinkedList<String> getItemsTitle(String search) throws ItemException {
        return null;
    }

    @Override
    public Collection<Item> searchItems(String search) throws IOException, ItemException {
        return null;
    }

    @Override
    public Collection<Item> getItems(String search) throws ItemException {
        return null;
    }

    @Override
    public Item getItem(String search, String id) throws ItemException {
        return null;
    }

    @Override
    public Item editItem(String search, String id, Item item) throws ItemException {
        return null;
    }

    @Override
    public void deleteItem(String search, String id) throws ItemException {

    }

    @Override
    public Collection<Item> getItemsPriceRange(String search, int maxPrice, int minPrice) throws ItemException {
        return null;
    }

    @Override
    public Collection<Item> getItemsTag(String search, String tag) throws ItemException {
        return null;
    }
}
