package hanjo.simukgak;

public class Food {
    private String info_seller;
    private String food_name;
    private int food_price;

    public void set_info_seller(String seller) {
        info_seller = seller;
    }

    public void set_food_name(String name) {
        food_name = name;
    }

    public void set_food_price(int price) {
        food_price = price;
    }

    public String get_info_seller() {
        return info_seller;
    }

    public String get_food_name() {
        return food_name;
    }

    public int get_food_price() {
        return food_price;
    }

    public boolean equals(Food another) {
        return info_seller == another.info_seller &&
                food_name == another.food_name &&
                food_price == another.food_price;
    }
}
