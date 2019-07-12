public enum StorageMode {
    MAP("Map"),
    FILE("File");

    private String storageMode;

    StorageMode(String storageMode){

    }

    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }
}
