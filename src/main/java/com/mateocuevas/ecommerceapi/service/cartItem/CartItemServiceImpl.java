package com.mateocuevas.ecommerceapi.service.cartItem;

import com.mateocuevas.ecommerceapi.entity.CartItem;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.exception.PorductStockException;
import com.mateocuevas.ecommerceapi.respository.CartItemRepository;
import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    /**
     * This method searches for the product that arrives by parameter in the database, and verifies if it is in stock.
     * If there is the requested quantity, the stock is updated and the cartitem object is returned
     * @param productId The id of the product that wants to be added to the cart.
     * @param quantity The quantity that wants to be added to the cart.
     * @return The CartItem with the data of the product, quantity and the total price.
     * @throws EntityNotFoundException If the product id that was sent is not in the database .
     * @throws PorductStockException If there is not enough stock of the product.
     */
    @Override
    public CartItem productToCartItem(Long productId, Integer quantity) {
        Product product=productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        if(product.getStock()>=quantity){
            throw new PorductStockException("there is not enough stock of the product:", product.getTitle());
        }
        product.setStock(product.getStock()-quantity);
        productRepository.save(product);
        return CartItem.builder()
                .id(null)
                .cart(null)
                .product(product)
                .quantity(quantity)
                .totalPrice(quantity*product.getPrice())
                .build();
    }

    @Override
    public void saveCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

}
