package com.geekbrains.backend.test.db;

import com.geekbrains.db.dao.CategoriesMapper;
import com.geekbrains.db.dao.ProductsMapper;
import com.geekbrains.db.model.Categories;
import com.geekbrains.db.model.Products;
import com.geekbrains.db.model.ProductsExample;

import java.util.List;

public class TestDbHW6 {

    public static void main(String[] args) {

        DbService dbService = new DbService();
        ProductsMapper productsMapper = dbService.getProductsMapper();
        CategoriesMapper categoriesMapper = dbService.getCategoriesMapper();

        //  Создание 4 категорий
        String[] createNameCategory = {"Books", "Toys", "Flowers", "Furniture"};
        Categories createCategory = new Categories();
        for (int i=0; i<=3; i++) {
            createCategory.setTitle(createNameCategory[i]);
            categoriesMapper.insert(createCategory);
            System.out.println("Создана категория" + ": " + createCategory);
        }

        //  Добавление 3 продуктов в только что созданные категории
        Products forCreate = new Products();

        String[] productsBooks = {"Book1", "Book2", "Book3"};
        int [] priceProductsBooks = {100, 200, 30000};
        for (int i=0; i<3; i++) {
            forCreate.setTitle(productsBooks[i]);
            forCreate.setPrice(priceProductsBooks[i]);
            forCreate.setCategoryId(3L);
            productsMapper.insert(forCreate);
        }

        String[] productsToys = {"Toys1", "Toys2", "Toys3"};
        int [] priceProductsToys = {400, 500, 600};
        for (int i=0; i<3; i++) {
            forCreate.setTitle(productsToys[i]);
            forCreate.setPrice(priceProductsToys[i]);
            forCreate.setCategoryId(4L);
            productsMapper.insert(forCreate);
        }

        String[] productsFlowers = {"Flowers1", "Flowers2", "Flowers3"};
        int [] priceProductsFlowers = {700, 800, 900};
        for (int i=0; i<3; i++) {
            forCreate.setTitle(productsFlowers[i]);
            forCreate.setPrice(priceProductsFlowers[i]);
            forCreate.setCategoryId(5L);
            productsMapper.insert(forCreate);
        }

        String[] productsFurniture = {"Furniture1", "Furniture2", "Furniture3"};
        int [] priceProductsFurniture = {1000, 2000, 40000};
        for (int i=0; i<3; i++) {
            forCreate.setTitle(productsFurniture[i]);
            forCreate.setPrice(priceProductsFurniture[i]);
            forCreate.setCategoryId(6L);
            productsMapper.insert(forCreate);
        }

        //  Вывести продуктов в каждой из категорий
        ProductsExample filter = new ProductsExample();
        for (long i =1L; i <=6L; i++) {
            filter.createCriteria()
                    .andCategoryIdEqualTo(i);
            System.out.println("  Продукты категории " + "Id" + i + ":\n" + productsMapper.selectByExample(filter) + "\n");
            filter.clear();
        }

        //  Вывести топ 3 самых дорогих продукта
        System.out.println("    Самые дорогие продукты: \n");
        filter.setOrderByClause("PRICE DESC");
        List<Products> products = productsMapper.selectByExample(filter);
        for(int i=0; i < 3; i++)
            System.out.println(products.get(i) + "\n");

        //filter.setOrderByClause("PRICE DESC limit 3");
        //System.out.println("    Самые дорогие продукты: \n" + productsMapper.selectByExample(filter));
        filter.clear();

        dbService.commitSession();
        dbService.closeSession();

    }
}
