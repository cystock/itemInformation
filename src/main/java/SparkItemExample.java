import static spark.Spark.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Collection;
import java.util.List;

public class SparkItemExample {
    public static void main(String[] args) {
        final ItemService itemService = new ItemServiceMapImp();
        //final ItemService itemService1 = new ItemServiceFileImp();

        post("/items/storagemode", (req, res) -> {
            res.type("application/json");
            String storage = req.queryParams("storagemode");
            if (storage.toUpperCase().equals("MAP"))
                return new Gson().toJson(new StandarResponse(StorageMode.MAP));
            else if (storage.toUpperCase().equals("FILE"))
                return new Gson().toJson(new StandarResponse(StorageMode.FILE));
            else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid value"));

        });

        // Trae todos los items para una busqueda
        get("/items/search", (req, res) -> {
            res.type("application/json");
            Collection<Item> col= itemService.searchItems(req.queryParams("search"));
            if (col != null)
                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
            else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid search value. Empty items collection"));
        });

        get("/items", (req, res) -> {
            res.type("application/json");
            if (req.queryParams("orderCriteria").toUpperCase().equals("LISTING_TYPE"))
                Item.orderCriteria = Item.OrderCriteria.LISTING_TYPE;
            else if (req.queryParams("orderCriteria").toUpperCase().equals("PRICE"))
                Item.orderCriteria = Item.OrderCriteria.PRICE;
            else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid orderCriteria value"));
            Collection<Item> col= itemService.getItems(req.queryParams("search"));
            if (col != null)
                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
            else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Invalid search value. Empty items collection"));
        });

        // Crear un item
        put("/items/create", (req, res) -> {
            res.type("application/json");
            Item item = new Gson().fromJson(req.body(), Item.class);
            itemService.addItem(req.queryParams("search"), item); //TODO: Error
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS));
        });

        // Editar un item
        post("/items/:id", (req, res) -> {
            res.type("application/json");
            Item item = new Gson().fromJson(req.body(), Item.class);
            Item i = itemService.editItem(req.queryParams("search"), req.params(":id"), item);
            if (i != null){
                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, "Item updated"));
            } else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not update item"));

        });

        //Traer un item REVISAR
        get("/items/:id", (req, res) -> {
            String search = req.queryParams("search");
            res.type("application/json");
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(itemService.getItem(search, req.params(":id")))));
        });

        // borrar un elemento
        delete("/items/:id", (req, res) -> {
            String search = req.queryParams("search");
            res.type("application/json");
            itemService.deleteItem(search, req.params(":id"));
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, "Item deleted"));
        });

        get("/items/titles", (req, res) -> {
            res.type("application/json");
            List<String> list = itemService.getItemsTitle(req.queryParams("search"));
            if (list != null){
                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(list)));
            } else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not create items list by title"));
        });

        get("/items", (req, res) -> {
            Collection<Item> col = itemService.getItemsTag(req.queryParams("search"), req.queryParams("tag"));
            if (col != null){
                return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(col)));
            } else
                return new Gson().toJson(new StandarResponse(StatusResponse.ERROR, "Can not create items collection by tags"));
        });

    }
}
