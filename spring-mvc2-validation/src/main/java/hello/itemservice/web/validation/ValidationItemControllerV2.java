package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    /**
     * 검증v2 (BindingResult)
     * - 객체에 타입 오류 등으로 바인딩이 실패하는 경우에도 해당 에러 결과를 보여줍니다.
     * - 문제점 : 바인딩 실패된 데이터에 대해서는 결과 및 입력 데이터가 유지되지만, 로직 검증 실패된 데이터는 사라진다.
     */
/*    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // BindingResult는 도메인에 바인딩된 결과가 담깁니다. @ModelAttribute 다음 순서에 위치해야 한다.

        //검증 로직 (필드 에러 처리 - FieldError(), FieldError는 ObjectError의 자손)
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수 입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증 (글로벌 에러 처리 - ObjectError())
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            //model.addAttribute("errors", errors); //bindingResult는 자동으로 view에 같이 넘어간다.
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    /**
     * 검증v2 (FieldError, rejectedValue)
     * - 오류 발생시 사용자 입력 값이 유지되도록 처리 합니다. (데이터 보존)
     * - 문제점 : 바인딩 실패시 스프링에서 제공하는 기본 오류 메시지를 사용자에게 보여주게 된다. (사용자가 인식할 수 있는 메시지를 보여줘야 함)
     */
/*    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        *//* FieldError 객체는 오류가 발생한 경우 사용자 입력 값을 보관해줍니다.
        FieldError(objectName, field, rejectedValue, bindingFailure, codes, arguments, defaultMessage)
        objectName : 오류가 발생한 객체 이름
        field : 오류 필드
        rejectedValue : 사용자가 입력한 값(거절된 값) ★
        bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
        codes : 메시지 코드
        arguments : 메시지에서 사용하는 인자
        defaultMessage : 기본 오류 메시지
        *//*

        //검증 로직 - rejectedValue 파라미터에 사용자가 입력한 값(거절된 값)을 추가해줍니다.
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 이름은 필수 입니다."));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null ,null, "수량은 최대 9,999 까지 허용합니다."));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",null ,null, "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    /**
     * 검증v2 (오류 코드와 메시지 처리1)
     * - 오류 코드를 일관성 있게 관리할 수 있음.
     * - 문제점 : 다루기 너무 번거롭다. 조금 더 자동화 해주는 처리가 필요하다.
     */
/*    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        *//* FieldError 객체는 오류가 발생한 경우 사용자 입력 값을 보관해줍니다.
        FieldError(objectName, field, rejectedValue, bindingFailure, codes, arguments, defaultMessage)
        objectName : 오류가 발생한 객체 이름
        field : 오류 필드
        rejectedValue : 사용자가 입력한 값(거절된 값)
        bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
        codes : 메시지 코드 (errors.properties) ★
        arguments : 메시지에서 사용하는 인자
        defaultMessage : 기본 오류 메시지
        *//*

        //검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName", "required.default"}, null, null));
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"} ,new Object[]{9999}, null));
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"} ,new Object[]{10000, resultPrice}, null));
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    /**
     * 검증v2 (오류 코드와 메시지 처리2)
     * - BindingResult의 rejectValue(), reject()를 사용해서 축약된 오류 코드를 사용할 수 있다.
     * - rejectValue(), reject() 메소드 내부적으로 FieldError, ObjectError를 자동으로 생성해 오류 코드를 조합해서 만들어줍니다.
     */
/*    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증에 실패하면 다시 입력 폼으로. 오류 메시지 1개만 보여주고 싶을 때는 앞에서 거르가
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        // BindingResult는 검증해야할 객체를 알고 있음.
        log.info("objectName={}", bindingResult.getObjectName()); // 출력결과 : objectName=item
        log.info("target={}", bindingResult.getTarget()); // 출력결과 : target=Item(id=null, itemName=, price=null, quantity=null)

        *//*
         * bindingResult.rejectValue(String field, String errorCode, String[] errorArgs, String defaultMessage)
         * 도메인 객체의 필드가 존재할 경우
         * field : 도메인 객체 필드
         * errorCode : 메시지 코드의 첫글자 (errors.properties)
         * errorArg : 메시지 코드의 파라미터
         * defaultMessage : 기본 오류 메시지
         *
         * bindingResult.reject(String errorCode, String[] errorArgs, String defaultMessage)
         * 글로벌 오류 처리 (여러개의 필드를 조합하여 검증 해야할 경우)
         * errorCode : 메시지 코드의 첫글자 (errors.properties)
         * errorArg : 메시지 코드의 파라미터
         * defaultMessage : 기본 오류 메시지
         *
         *//*

        // empty, 공백 같은 단순환 기능만 제공
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
        *//*if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }*//*

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 10000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    /**
     * Validator 분리1 - (검증 로직 분리)
     */
/*    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        itemValidator.validate(item, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }*/

    /**
     * Validator2 - (@Validate 애노테이션)
     */
    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //itemValidator.validate(item, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("errors={} ", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

