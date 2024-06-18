package com.mateocuevas.ecommerceapi.service.product;

import com.mateocuevas.ecommerceapi.dto.ProductDTO;
import com.mateocuevas.ecommerceapi.entity.Category;
import com.mateocuevas.ecommerceapi.entity.Product;
import com.mateocuevas.ecommerceapi.entity.User;
import com.mateocuevas.ecommerceapi.enums.UserRole;
import com.mateocuevas.ecommerceapi.exception.ProductStockException;
import com.mateocuevas.ecommerceapi.respository.ProductRepository;
import com.mateocuevas.ecommerceapi.service.user.UserService;
import com.mateocuevas.ecommerceapi.service.category.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private static final String API_URL = "https://fakestoreapi.com/products";
    private final RestTemplate restTemplate;

    public Set<ProductDTO> getAllProducts(){
        List<Product> products= productRepository.findAll();
        return products.stream()
                .map(this::productToProductDto)
                .collect(Collectors.toSet());
    }


    public ProductDTO findByTitle(String title){
        Product product=productRepository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
        return productToProductDto(product);
    }

    public Set<ProductDTO> findByPriceBetween(double minPrice, double maxPrice){
        Set<Product> products=productRepository.findByPriceBetween(minPrice,maxPrice);
        return products.stream()
                .map(this::productToProductDto)
                .collect(Collectors.toSet());
    }

    public Set<ProductDTO> findProductByCategory(String categoryRequest){
        Category category=categoryService.findByName(categoryRequest).orElseThrow(EntityNotFoundException::new);
        Set<Product> products=productRepository.findByCategory(category);
        return products.stream()
                .map(this::productToProductDto)
                .collect(Collectors.toSet());
    }

    public Product checkStock(Long productId, Integer quantity){
        Product product=productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);
        if(product.getStock()<quantity){
            throw new ProductStockException("there is not enough stock of the product:", product.getTitle());
        }
        product.setStock(product.getStock()-quantity);
        productRepository.save(product);
        return product;
    }

    public ProductDTO productToProductDto(Product product){
     return ProductDTO.builder()
             .title(product.getTitle())
             .price(product.getPrice())
             .description(product.getDescription())
             .rating(product.getRating())
             .image(product.getImage())
             .category(product.getCategory().getName())
             .Stock(product.getStock())
             .build();
    }

    public Product productDtoToProduct(ProductDTO productDTO){
        return Product.builder()
                .title(productDTO.getTitle())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .rating(productDTO.getRating())
                .image(productDTO.getImage())
                .category(categoryService.findByName(productDTO.getCategory())
                        .orElse(categoryService.createCategory(productDTO.getCategory())))
                .Stock(productDTO.getStock())
                .build();
    }

    /**
     * This method fetches a list of products from an external API and saves them into the local database.
     *
     * It uses `RestTemplate` to make an HTTP GET request to the URL specified by `API_URL`
     * and retrieves an array of `Product` objects.
     *
     * It then iterates over the array of products and calls the `saveProductInUserAdmin` method
     * to save each product into the local database.
     */
    public void fetchAndSaveProducts() {
        Product[] products = restTemplate.getForObject(API_URL, Product[].class);
        if (products != null) {
            for (Product product : products) {
                saveProductInUserAdmin(product);
            }
        }
    }
    /**
     * This method assigns an admin user to a product and saves it into the local database.
     * It also fetches or creates the appropriate category for the product.
     *
     * It first fetches the admin user by their ID, then ensures the product's category exists in the database,
     * and finally, it sets the admin and category to the product before saving it to the repository.
     *
     * @param product The product to be saved.
     */
    @Override
    public void saveProductInUserAdmin(Product product) {
        User userAdmin = userService.findByRole(UserRole.ADMIN).orElseThrow();
        System.out.println(userAdmin.getUsername());
        // Fetch or create the category
        String categoryName = product.getCategory().getName();
        Category category = categoryService.findByName(categoryName).orElseThrow(EntityNotFoundException::new);
        if (category == null) {
            category = new Category();
            category.setName(categoryName);
            category = categoryService.saveCategory(category);
        }
        product.setCategory(category);
        product.setAdmin(userAdmin);
        product.setStock(20);
        // Save the product
        productRepository.save(product);
    }

}


