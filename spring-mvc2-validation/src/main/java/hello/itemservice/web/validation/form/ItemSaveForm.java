package hello.itemservice.web.validation.form;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ItemSaveForm {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    // TestDataInit 클래스에 데이터 초기화 하기 위해 생성
    public ItemSaveForm(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
