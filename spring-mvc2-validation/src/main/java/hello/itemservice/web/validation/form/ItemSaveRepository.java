package hello.itemservice.web.validation.form;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemSaveRepository {
    public static final Map<Long, ItemSaveForm> saveStore = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public ItemSaveForm save(ItemSaveForm form) {
        form.setId(++sequence);
        saveStore.put(form.getId(), form);
        return form;
    }
    public ItemSaveForm findById(Long id) {
        return saveStore.get(id);
    }

    public List<ItemSaveForm> findAll() {
        return new ArrayList<>(saveStore.values());
    }

    public void clearStore() {
        saveStore.clear();
    }

}
