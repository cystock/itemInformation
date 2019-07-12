import org.eclipse.jetty.server.RequestLog;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
        try {
            Collection<Item> col = getCollection(search);
            for (Item i : col) {
                list.add(i.getTitle());
            }
        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
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
        Collection<Item> colFinal = null;
        if (!itemHashMap.containsKey(search)){
            System.err.println("Invalid query parameter or search do no exits.");
            throw new NullPointerException("Empty Items collection. First generate a serch");
        }
        else
            col = itemHashMap.get(search);
            colFinal = col.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        return colFinal;
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
        try {
            Collection<Item> col = getCollection(search);
            for (Item i: col) {
                if (i.getId().equals(id)){
                    col.remove(i);
                }
            }
            itemHashMap.put(search, col);
        }  catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
        }
    }

    @Override
    public Collection<Item> getItemsPriceRange(String search, int maxPrice, int minPrice) {
        Collection<Item> colFinal = new ArrayList<>();
        try{
            Collection<Item> col = getCollection(search);
            for ( Item i: col){
                if ( i.getPrice() <= maxPrice && i.getPrice() >= minPrice)
                    colFinal.add(i);
            }

        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
        }
        return colFinal;
    }

    @Override
    public Collection<Item> getItemsTag(String search, String tag) {
        Collection<Item> colFinal = new ArrayList<>();

        try {
            Collection<Item> col = getCollection(search);
            for (Item i: col) {
                if (i.getTags().contains(tag))
                    colFinal.add(i);
            }
        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
        }
        return colFinal;
    }

    private Collection<Item> getCollection(String search) throws ItemException {
        Collection<Item> col = null;
        if (itemHashMap.containsKey(search)) {
            col = itemHashMap.get(search);
            if (col == null)
                throw new ItemException("Empty collection");
        } else
            throw new ItemException("Empty collection");

        return col;
    }


}
