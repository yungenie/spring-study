package hello.itemservice.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.web.validation.form.ItemSaveForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class BeanValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        System.out.println("@BeforeEach");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void beanValidation() {
        //ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        //Validator validator = factory.getValidator();

        //Item item = new Item();
        //item.setItemName("상품");
        //item.setPrice(1111);
        //item.setQuantity(1);
        //item.setItemName(" "); //공백
        //item.setPrice(0);
        //item.setQuantity(10000);

        ItemSaveForm itemSaveForm = new ItemSaveForm();
        itemSaveForm.setItemName(" ");


        Set<ConstraintViolation<ItemSaveForm>> violations = validator.validate(itemSaveForm);
        for (ConstraintViolation<ItemSaveForm> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }

    }
}
