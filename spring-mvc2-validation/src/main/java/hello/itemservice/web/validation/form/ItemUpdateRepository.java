package hello.itemservice.web.validation.form;

import org.springframework.stereotype.Repository;

import static hello.itemservice.web.validation.form.ItemSaveRepository.saveStore;


@Repository
public class ItemUpdateRepository {

    public ItemSaveForm findById(Long id) {
        return saveStore.get(id);
    }
    public void update(Long itemId, ItemUpdateForm updateParam) {

        ItemSaveForm saveForm = findById(itemId);

        if (saveForm.getId() != null) {
            saveForm.setItemName(updateParam.getItemName());
            saveForm.setPrice(updateParam.getPrice());
            saveForm.setQuantity(updateParam.getQuantity());

        }
    }

}
