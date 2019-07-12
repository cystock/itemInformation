import java.util.LinkedList;

public class Item implements Comparable<Item> {
    private String id;
    private String site_id;
    private String title;
    private int price;
    private Currency currency_id;
    private String listing_type_id;
    private String stop_time; //TODO: date value
    private String thumbnail; //TODO: link value
    private LinkedList tags;


    public enum OrderCriteria{
        PRICE,
        LISTING_TYPE
    }

    public static OrderCriteria orderCriteria;

    public Item(String id, String site_id, String title, int price, Currency currency_id, String listing_type_id, String stop_time, String thumbnail, LinkedList tags) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.currency_id = currency_id;
        this.listing_type_id = listing_type_id;
        this.stop_time = stop_time;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(Currency currency_id) {
        this.currency_id = currency_id;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listing_type_id) {
        this.listing_type_id = listing_type_id;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LinkedList getTags() {
        return tags;
    }

    public void setTags(LinkedList tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", site_id='" + site_id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency_id=" + currency_id +
                ", listing_type_id='" + listing_type_id + '\'' +
                ", stop_time='" + stop_time + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", tags=" + tags +
                '}';
    }

    public int compareTo(Item o) {
        int value = -2;
        switch (orderCriteria){
            case PRICE:
                if (this.price == o.price) value = 0;
                else if (this.price > o.price) value = 1;
                else value = -1;
                break;
            case LISTING_TYPE:
                value = this.listing_type_id.compareTo(o.listing_type_id);
                break;
        }
        return value;
    }
}
