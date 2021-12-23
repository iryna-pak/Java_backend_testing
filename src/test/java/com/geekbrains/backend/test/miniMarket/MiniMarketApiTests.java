package com.geekbrains.backend.test.miniMarket;

import com.geekbrains.backend.test.miniMarket.model.Product;
import com.geekbrains.backend.test.miniMarket.model.Category;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MiniMarketApiTests {

    private static MiniMarketService apiService;
    private static Long Current_Product_ID = 0L;

    @BeforeAll
    static void beforeAll() {
        apiService = new MiniMarketService();
    }

    /* -----------  category-controller   -----------*/

    @DisplayName("Тест на получение существующей категории по id")
    @Test
    void getCategoryById() throws IOException {
        Category category = apiService.getCategory(1);
        System.out.println(category);
        /* Ассерты на первые два продукта в категории */
        Assertions.assertEquals(1L, category.getId());
        Assertions.assertEquals("Food", category.getTitle());
        //Assertions.assertEquals(99L, category.getProducts().get(0).getId());
        //Assertions.assertEquals("Bread", category.getProducts().get(0).getTitle());
        //Assertions.assertEquals(25, category.getProducts().get(0).getPrice());
        //Assertions.assertEquals("Food", category.getProducts().get(0).getCategoryTitle());
        //Assertions.assertEquals(3L, category.getProducts().get(1).getId());
        //Assertions.assertEquals("Cheese", category.getProducts().get(1).getTitle());
        //Assertions.assertEquals(360, category.getProducts().get(1).getPrice());
        //Assertions.assertEquals("Food", category.getProducts().get(1).getCategoryTitle());
    }

    @DisplayName("Тест на получение несуществующей категории по несуществующему id")
    @Test
    void getCategoryNotExistId() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Category category = apiService.getCategory(100);
        });
    }

    /* -----------  product-controller   -----------*/
    @DisplayName("Получение списка продуктов")
    @Test
    void getProducts() throws IOException {
        List<Product> products = apiService.getProducts();
        System.out.println(products);
    }

    @DisplayName("Создание нового продукта")
    @Test
    @Order(1)
    void postCreateNewProduct() throws IOException {
        Product product = Product.builder()
                .title("NEW-TV")
                .price(13000)
                .categoryTitle("Electronic")
                .build();

        Current_Product_ID = apiService.createProduct(product);
        Product expectedProduct = apiService.getProduct(Current_Product_ID); // получаю продукт по только что созданному id
        System.out.println(expectedProduct);
    }

    @DisplayName("Обновление информации о продукте")
    @Test
    @Order(2)
    void putUpdateProductInfo() throws IOException {
        Product updateProduct = Product.builder()
                .id(Current_Product_ID)
                .price(650)
                .title("Cactus")
                .categoryTitle("Food")
                .build();

        apiService.updateProduct(updateProduct);
        Product newUpdateProduct = apiService.getProduct(Current_Product_ID);
        Assertions.assertEquals("Cactus", newUpdateProduct.getTitle());
        Assertions.assertEquals(650, newUpdateProduct.getPrice());
        Assertions.assertEquals("Food", newUpdateProduct.getCategoryTitle());
        System.out.println(newUpdateProduct);
    }

    @DisplayName("Получение продукта по id")
    @Test
    void getProductIdExist() throws IOException {
        Product product = apiService.getProduct(34);
        Assertions.assertEquals(34L, product.getId());
        Assertions.assertEquals("Cactus", product.getTitle());
        Assertions.assertEquals(600, product.getPrice() );
        Assertions.assertEquals("Food", product.getCategoryTitle());
        System.out.println(product);
    }

    @DisplayName("Тест на правильную обработку по запросу с несуществующим id")
    @Test
    void getProductIdNotExist() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Product product = apiService.getProduct(0);
        });
    }

    @DisplayName("Тест на удаление продукта по id")
    @Test
    @Order(3)
    void deleteProduct() throws IOException {
        apiService.deleteProduct(Current_Product_ID);
    }
}
