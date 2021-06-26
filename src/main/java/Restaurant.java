import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    public ZoneId zoneIdForQuery ;
    private List<Item> menu = new ArrayList<Item>();

    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        LocalTime currentTime =  LocalTime.now(getZoneIdForQuery());

        // If isAfterOpenTime is 0 or +1, the restaurant is marked open
        int isAfterOpenTime = currentTime.compareTo(openingTime);
        // If isBeforeCloseTime is not -1, the retaurant is marked closed
        int isBeforeCloseTime = currentTime.compareTo(closingTime);
        // Assumption is that the restaurant is marked open from the timestamp it is open
        //  and closed at the timestamp it is marked closed
        if( isAfterOpenTime >=0 && isBeforeCloseTime < 0 ){
            return true;
        }
        return false;
    }

    public LocalTime getCurrentTime(){ return  LocalTime.now(); }

    public List<Item> getMenu() {
        //Return list of items in the menu
        return menu;
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }

    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    public String getName() {
        return name;
    }

    public ZoneId getZoneIdForQuery() {
        if(null == zoneIdForQuery){
            return ZoneId.of("Asia/Kolkata");
        }
        return zoneIdForQuery;
    }

    public void setZoneIdForQuery(ZoneId zoneIdForQuery) {
        this.zoneIdForQuery = zoneIdForQuery;
    }

    private Map<String,Integer> getItemNameAndPriceMap(){
        Map<String,Integer>  itemNameAndPriceMap = new  HashMap<>();
        for(Item item : menu){
            itemNameAndPriceMap.put(item.getName(),item.getPrice());
        }
        return itemNameAndPriceMap;
    }
}
