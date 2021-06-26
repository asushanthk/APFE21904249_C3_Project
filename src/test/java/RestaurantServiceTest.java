import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        String RESTAURANT_NAME ="My Cafe1";
        Restaurant addedRestaurant = initAndAddRestaurant("10:30:00","22:00:00",RESTAURANT_NAME,"Chennai");
        Restaurant returnedRestaurant = service.findRestaurantByName(RESTAURANT_NAME);
        assertEquals(addedRestaurant,returnedRestaurant);
        assertEquals(addedRestaurant.getName(),returnedRestaurant.getName());
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        String RESTAURANT_NAME ="My Cafe2";
        initAndAddRestaurant("10:30:00","22:00:00",RESTAURANT_NAME,"Chennai");
        assertThrows(restaurantNotFoundException.class,()->service.findRestaurantByName("dummy restautant"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        restaurant = initAndAddRestaurant("10:30:00","22:00:00","Amelie's cafe","Chennai");
        addDefaultFoodItemsToRestaurant(restaurant);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        restaurant = initAndAddRestaurant("10:30:00","22:00:00","Amelie's cafe","Chennai");
        addDefaultFoodItemsToRestaurant(restaurant);

        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        restaurant = initAndAddRestaurant("10:30:00","22:00:00","Amelie's cafe","Chennai");
        addDefaultFoodItemsToRestaurant(restaurant);

        int initialNumberOfRestaurants = service.getRestaurants().size();
        initAndAddRestaurant("12:00:00","23:00:00","Pumpkin Tales","Chennai");
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>


    private Restaurant initAndAddRestaurant(String openTime, String closeTime,String restaurantName, String location){
        LocalTime openingTime = LocalTime.parse(openTime);
        LocalTime closingTime = LocalTime.parse(closeTime);
        restaurant = service.addRestaurant(restaurantName,location,openingTime,closingTime);
        return restaurant;
    }

    private void addDefaultFoodItemsToRestaurant(Restaurant restaurant){
        List<Item> itemList = getDefaultItems();
        itemList.forEach(item -> restaurant.addToMenu(item.getName(),item.getPrice()));
    }

    private List<Item> getDefaultItems(){
        List<Item> itemList = new ArrayList<>();
        Item item1 = new Item("Sweet corn soup",119);
        Item item2 = new Item("Vegetable lasagne",269);
        itemList.add(item1);
        itemList.add(item2);
        return itemList;
    }

}