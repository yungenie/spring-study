package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    private boolean hasError(Map<String, String> errors) {
        return !errors.isEmpty(); // 부정의 부정을 해석하기 수월하지 않기 때문에 가독성을 위해 함수로 빼기!
    }

    /**
     * 검증V1 (검증 직접 처리)
     * - 검증시 오류가 발생하면 어떤 검증에서 오류가 발생했는 지 Map자료구조에 정보(필드,에러문구)를 담아 타임리프를 이용해 처리를 함.
     * - 문제점 : 타입오류 처리가 안되기 때문에 타입오류된 고객의 입력 데이터는 관리되지 않는다.
     */
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        //model.addAttribute("item", item); // 자동으로 추가해줌.
        //검증에 실패했을 때 재사용 가능하게끔 - 입력했던 값 그대로 다시 화면에 노출 (데이터형에 해당할 떄)

        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        //검증 로직
        if (StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (hasError(errors)) {
            log.info("errors = {} ", errors);
            model.addAttribute("errors", errors); // 착각함. model.addAttribute(key, value) map 형태로 요청한 data이미 model에 담겨져 있음.
            return "validation/v1/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }



    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}

