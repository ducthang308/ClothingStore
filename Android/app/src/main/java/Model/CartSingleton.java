package Model;

import java.util.ArrayList;
import java.util.List;

public class CartSingleton {
    private static CartSingleton instance;
    private final List<CartItems> cartItems;

    private CartSingleton() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartSingleton getInstance() {
        if (instance == null) {
            instance = new CartSingleton();
        }
        return instance;
    }

    public void addToCart(CartItems item) {
        cartItems.add(item);
    }

    public List<CartItems> getCartItems() {
        return cartItems;
    }
}