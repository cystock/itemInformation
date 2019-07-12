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
    public void addItem(String search, Item item) throws ItemException {
        try {
            Collection<Item> col = getCollection(search);
            col.add(item);
            itemHashMap.put(search, col);
        } catch (ItemException e){
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
    }

    @Override
    public LinkedList<String> getItemsTitle(String search) throws ItemException {
        LinkedList<String> list = new LinkedList<>();
        try {
            Collection<Item> col = getCollection(search);
            for (Item i : col) {
                list.add(i.getTitle());
            }
        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
        return list;
    }

    @Override
    public Collection<Item> searchItems(String search) throws ItemException, IOException {
        Collection<Item> col = null;
        try {
            if (!itemHashMap.containsKey(search)) {
                Search searchI = new Search();
                col = searchI.searchItem(search);
                itemHashMap.put(search, col);
            } else
                col = itemHashMap.get(search);
        } catch (IOException e){
            System.out.println("Invalid input. Exception: " + e);
            throw e;
        }
        return col;
    }

    @Override
    public Collection<Item> getItems(String search) throws ItemException{
        Collection<Item> colFinal;
        try {
            Collection<Item> col = getCollection(search);
            colFinal = col.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        } catch (ItemException e){
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
        return colFinal;
    }

    @Override
    public Item getItem(String search, String id) throws ItemException{
        Item item = null;
        try {
            Collection<Item> col = getCollection(search);
            for (Item i : col) {
                if(i.getId().equals(id))
                    item=i;
            }
        } catch (ItemException e){
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
        return item;
    }

    @Override
    public Item editItem(String search, String id, Item item) throws ItemException{
        Item value = null;

        try {
            Collection<Item> col = getCollection(search);
            for (Item i: col) {
                if (i.getId().equals(id)){
                    i = item;
                    value = i;
                    col.add(i);
                }
            }
            itemHashMap.put(search, col);
        } catch (ItemException e){
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
        return value;
    }

    @Override
    public void deleteItem(String search, String id) throws ItemException{
        try {
            Collection<Item> col = getCollection(search);
            for (Item i: col) {
                if (i.getId().equals(id)){
                    col.remove(i);
                }
            }
            itemHashMap.put(search, col);
        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
    }

    @Override
    public Collection<Item> getItemsPriceRange(String search, int maxPrice, int minPrice) throws ItemException {
        Collection<Item> colFinal = new ArrayList<>();
        try{
            Collection<Item> col = getCollection(search);
            for ( Item i: col){
                if ( i.getPrice() <= maxPrice && i.getPrice() >= minPrice)
                    colFinal.add(i);
            }

        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
        }
        return colFinal;
    }

    @Override
    public Collection<Item> getItemsTag(String search, String tag) throws ItemException {
        Collection<Item> colFinal = new ArrayList<>();

        try {
            Collection<Item> col = getCollection(search);
            for (Item i: col) {
                if (i.getTags().contains(tag))
                    colFinal.add(i);
            }
        } catch (ItemException e) {
            System.out.println("Invalid collection. Exception:  " + e);
            throw e;
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
