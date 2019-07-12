import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Collection;
import java.util.List;

public class SparkItemExample {
    public  static ItemService itemService;


    public static void main(String[] args) {

        path("/items", () -> {
            post("/storagemode", (req, res) -> {
                res.type("application/json");

                String storagemode = req.queryParams("storagemode");
                if ( storagemode == null )
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Storage mode value is required"));

                if (storagemode.toUpperCase().equals("MAP")) {
                    itemService = new ItemServiceMapImp();
                    return new Gson().toJson(new StandarResponse(StorageMode.MAP));
                }
                else if (storagemode.toUpperCase().equals("FILE")){
                    itemService = new ItemServiceFileImp();
                    return new Gson().toJson(new StandarResponse(StorageMode.FILE));
                }
                else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid storage mode value"));

            });

            get("/search", (req, res) -> {
                res.type("application/json");

                String search = req.queryParams("search");
                if (search == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search value is required"));

                Collection<Item> col= itemService.searchItems(search);
                if (col != null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
                else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid search value. Empty items collection"));
            });

            get("/", (req, res) -> {
                res.type("application/json");

                String search = req.queryParams("search");
                String orderCriteria = req.queryParams("orderCriteria");
                if (search == null || orderCriteria == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search & Order Criteria values are required"));

                if (orderCriteria.toUpperCase().equals("LISTING_TYPE"))
                    Item.orderCriteria = Item.OrderCriteria.LISTING_TYPE;
                else if (orderCriteria.toUpperCase().equals("PRICE"))
                    Item.orderCriteria = Item.OrderCriteria.PRICE;
                else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid orderCriteria value"));

                Collection<Item> col= itemService.getItems(search);
                if (col != null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
                else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid search value. Empty items collection"));
            });

            put("/create", (req, res) -> {
                res.type("application/json");

                String body = req.body();
                String search = req.queryParams("search");
                if (search == null || body == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search & Body values are required"));

                Item item = new Gson().fromJson(req.body(), Item.class);
                itemService.addItem(search, item);

                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, "Item already created"));
            });

            get("/title", (req, res) -> {
                res.type("application/json");

                String search = req.queryParams("search");
                if (search == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search value is required"));

                List<String> list = itemService.getItemsTitle(search);
                if (list.size() != 0){
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(list)));
                } else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not create items list by title"));
            });

            get("/tags", (req, res) -> {
                res.type("application/json");

                String search = req.queryParams("search");
                String tag = req.queryParams("tag");
                if (search == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search & Tag values are required"));
                Collection<Item> col = itemService.getItemsTag(search, tag);
                if (col.size() != 0){
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
                } else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not create items collection by tags"));
            });

            get("/pricerange", (req, res) -> {
                res.type("application/json");

                String search = req.queryParams("search");
                int maxprice = (Integer.parseInt(req.queryParams("maxprice")));
                int minprice = (Integer.parseInt(req.queryParams("minprice")));
                if (search == null)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search value is required"));
                if (minprice < 0 || maxprice < 0)
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Min & Max price values are required"));

                Collection<Item> col = itemService.getItemsPriceRange(search, maxprice, minprice);
                if (col.size() != 0){
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
                } else
                    return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not create items collection by tags"));
            });

            path("/:id", () -> {
                get("", (req, res) -> {
                    res.type("application/json");

                    String search = req.queryParams("search");
                    String id = req.params(":id");
                    if (search == null || id == null)
                        return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search & id values are required"));
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getItem(search, id))));
                });

                delete("", (req, res) -> {
                    String search = req.queryParams("search");
                    res.type("application/json");
                    itemService.deleteItem(search, req.params(":id"));
                    return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, "Item deleted"));
                });

                post("", (req, res) -> {
                    res.type("application/json");

                    String body = req.body();
                    String search = req.queryParams("search");
                    String id = req.params(":id");
                    if (search == null || body == null)
                        return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Search , Body & id values are required"));

                    Item item = new Gson().fromJson(req.body(), Item.class);
                    Item i = itemService.editItem(search, id, item);
                    if (i != null){
                        return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, "Item updated"));
                    } else
                        return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not update item"));

                });
            });

        });


    }
}
