import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        List<ZoneId> zoneIdList = getZoneIds();

        for(ZoneId zoneId : zoneIdList){
            LocalTime currentTime = LocalTime.now(zoneId);
            LocalTime openTime = currentTime.minus(Duration.ofSeconds(2L));
            LocalTime closeTime = currentTime.plus(Duration.ofSeconds(2L));
            Restaurant  testRestaurant =initCustomRestaurant(openTime,closeTime);
            testRestaurant.setZoneIdForQuery(zoneId);
            assertTrue(testRestaurant.isRestaurantOpen());
        }
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        List<ZoneId> zoneIdList = getZoneIds();

        for(ZoneId zoneId : zoneIdList){
            LocalTime currentTime = LocalTime.now(zoneId);
            LocalTime openTime = currentTime.minus(Duration.ofSeconds(2L));
            LocalTime closeTime = currentTime.minus(Duration.ofSeconds(2L));
            Restaurant  testRestaurant =initCustomRestaurant(openTime,closeTime);
            testRestaurant.setZoneIdForQuery(zoneId);
            assertFalse(testRestaurant.isRestaurantOpen());
        }
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant =initDefaultRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant =initDefaultRestaurant();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant =initDefaultRestaurant();
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private Restaurant initRestaurant(LocalTime openingTime, LocalTime closingTime){
        Restaurant restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        return restaurant;
    }

    private Restaurant initDefaultRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        return initRestaurant(openingTime,closingTime);
    }

    private Restaurant initCustomRestaurant( LocalTime openingTime, LocalTime closingTime){
        return initRestaurant(openingTime,closingTime);
    }

    private List<ZoneId> getZoneIds(){
        return ZoneId.getAvailableZoneIds()
                .stream()
                .map( zoneIdString -> ZoneId.of(zoneIdString))
                .collect(Collectors.toList()) ;
    }


}