package com.mateocuevas.ecommerceapi.service.order;

import com.mateocuevas.ecommerceapi.entity.Cart;
import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Order;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.respository.OrderRepository;
import com.mateocuevas.ecommerceapi.service.cart.CartService;
import com.mateocuevas.ecommerceapi.service.cartItem.CartItemService;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{
    private OrderRepository orderRepository;
    private UserService userService;
    private CartItemService cartItemService;
    private CartService cartService;
    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order makeOrder() {
        User user = userService.getUserAuthenticated().orElseThrow();
        Cart cart = user.getCart();
        Set<CartItem>cartItems = cart.getCartItems();
        for (CartItem cartItem:cartItems) {
            cartItemService.deleteCartItem(cartItem);
        }
        cart.setCartItems(new HashSet<>());
        cartService.saveCart(cart);
        return null;
    }
}
