package hanjo.simukgak;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class FoodList {
    private String store_name;
    private List<Food> food_list;
    private int amount_food;

    public FoodList() {
        food_list = new ArrayList<>();
        amount_food = 0;
    }

    public void insert_food(Food new_food) {
        food_list.add(new_food);
    }

    public void remove_food(Food new_food) {
        food_list.remove(new_food);
    }

    public List<Food> search_food(String name) {
        List<Food> search_result = new ArrayList<>();

        for(Iterator<Food> it = food_list.iterator();
            it.hasNext();) {
            Food current_food = it.next();
            if(current_food.get_food_name() == name) {
                search_result.add(current_food);
            }
        }
        return search_result;
    }
}
