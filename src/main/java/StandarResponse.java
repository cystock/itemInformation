import com.google.gson.JsonElement;

public class StandarResponse {
    private StatusResponse status;
    private StorageMode storage;
    private String message;
    private JsonElement data;

    public StandarResponse(StatusResponse status, StorageMode storage, String message, JsonElement data) {
        this.status = status;
        this.storage = storage;
        this.message = message;
        this.data = data;
    }

    public StandarResponse(StatusResponse status){
        this.status = status;
    }

    public StandarResponse(StatusResponse status, String message)
    {
        this.status = status;
        this.message = message;
    }

    public StandarResponse(StatusResponse status, JsonElement data){
        this.status = status;
        this.data = data;
    }

    public StandarResponse(StorageMode storage){
        this.storage = storage;
    }

    public StatusResponse getStatus() {
        return status;
    }

    public void setStatus(StatusResponse status) {
        this.status = status;
    }

    public StorageMode getStorage() {
        return storage;
    }

    public void setStorage(StorageMode storage) {
        this.storage = storage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
