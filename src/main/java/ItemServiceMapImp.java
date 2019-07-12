import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class ItemServiceMapImp implements ItemService{

    private HashMap<String, Collection<Item>> itemHashMap;

    public ItemServiceMapImp(){
        itemHashMap = new HashMap<>();
    }

    @Override
    public void addItem(String search, Item item) {
        if (itemHashMap.containsKey(search)){
            Collection<Item> col = itemHashMap.get(search);
            col.add(item);
            itemHashMap.put(search, col);
        }
        else
            System.err.println("Invalid query parameter.");
    }

    @Override
    public LinkedList<String> getItemsTitle(String search) {
        LinkedList<String> list = new LinkedList<>();
        if (itemHashMap.containsKey(search)) {
            Collection<Item> col = itemHashMap.get(search);
            for (Item i : col) {
                list.add(i.getTitle());
            }
        } else {
            System.err.println("Invalid query parameter.");
        }
        return list;
    }

    @Override
    public Collection<Item> searchItems(String search) {
        Collection<Item> col = null;
        try {
            if (! itemHashMap.containsKey(search)){
                Search searchI = new Search(search);
                col =  searchI.searchItem(search);
                itemHashMap.put(search, col);
            } else {
                System.err.println("Invalid query parameter.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return col;
    }

    @Override
    public Collection<Item> getItems(String search) {
        Collection<Item> col = null;
        if (!itemHashMap.containsKey(search)){
            System.err.println("Invalid query parameter or search do no exits.");
            throw new NullPointerException("Empty Items collection. First generate a serch");
        }
        else
            col = itemHashMap.get(search);
        return col;
    }

    @Override
    public Item getItem(String search, String id) {
        Item item = null;
        if (itemHashMap.containsKey(search)){
            Collection<Item> col = itemHashMap.get(search);
            for (Item i : col) {
                if(i.getId().equals(id))
                    return item=i;
            }
        } else
            System.err.println("Invalid query parameter.");
        return item;
    }

    @Override
    public Item editItem(String search, String id, Item item) {
        Item value = null;
        if (itemHashMap.containsKey(search)) {
            Collection<Item> col = itemHashMap.get(search);
            for (Item i: col) {
                if (i.getId().equals(id)){
                    i = item;
                    value = i;
                    col.add(i);
                }
            }
            itemHashMap.put(search, col);
        } else
            System.err.println("Invalid query parameter.");
        return value;
    }

    @Override
    public void deleteItem(String search, String id) {
        if (itemHashMap.containsKey(search)) {
            Collection<Item> col = itemHashMap.get(search);
            for (Item i: col) {
                if (i.getId().equals(id)){
                    col.remove(i);
                }
            }
            itemHashMap.put(search, col);
        } else
            System.err.println("Invalid query parameter.");
    }

    @Override
    public Collection<Item> getItemsPriceRange(String search, float maxPrice, float minPrice) {
        return null;
    }

    @Override
    public Collection<Item> getItemsTag(String search, String tag) {
        Collection<Item> colFinal = null;
        if (itemHashMap.containsKey(search)) {
            Collection<Item> col = itemHashMap.get(search);
            for (Item i: col) {
                if (i.getTags().contains(tag))
                    colFinal.add(i);
            }
            itemHashMap.put(search, col);
        } else{
            System.err.println("Invalid query parameter.");
        }
        return colFinal;
    }
}
