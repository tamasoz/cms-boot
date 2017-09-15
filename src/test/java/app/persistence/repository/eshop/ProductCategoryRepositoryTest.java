package app.persistence.repository.eshop;

import app.persistence.entity.eshop.Product;
import app.persistence.entity.eshop.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * @author Samuel Butta
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductCategoryRepositoryTest {


    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    @Autowired
    private ProductRepository productRepository;


    @Test
    public void testSaveCategoryWithParentCategory() {
        ProductCategory pcParent = new ProductCategory();
        pcParent.setName("parent 1");

        productCategoryRepository.save(pcParent);


        ProductCategory pcChild1 = new ProductCategory();
        pcChild1.setName("child 1");
        pcChild1.setParentCategory(pcParent);

        ProductCategory pcChild2 = new ProductCategory();
        pcChild2.setName("child 1");
        pcChild2.setParentCategory(pcParent);

        productCategoryRepository.save(pcChild1);
        productCategoryRepository.save(pcChild2);

        Assert.assertEquals(3, productCategoryRepository.findAll().size());
        Assert.assertEquals(2, productCategoryRepository.findByParentCategory(pcParent).size());
        Assert.assertEquals(1, productCategoryRepository.findByParentCategory(null).size());
    }


    @Test
    public void testSaveProductsAndProductCategories() {
        Product productPhone = new Product();
        productPhone.setTitle("phone");

        Product productTv = new Product();
        productTv.setTitle("tv");

        ProductCategory category = new ProductCategory();

        ArrayList<Product> products = new ArrayList<>();
        products.add(productPhone);
        products.add(productTv);

        category.setProducts(products);

        productPhone.getProductCategories().add(category);
        productTv.getProductCategories().add(category);

        productRepository.save(productPhone);
        productRepository.save(productTv);
        productCategoryRepository.save(category);

        ProductCategory resultCategory = productCategoryRepository.findOne(1L);
    }
}
