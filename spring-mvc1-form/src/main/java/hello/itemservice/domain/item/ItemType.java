package hello.itemservice.domain.item;

public enum ItemType {

    BOOK("도서"), FOOD("음식"), ETC("기타");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static void main(String[] args) {
        ItemType[] itemTypes = ItemType.values();
        for (ItemType itemType : itemTypes) {
            // BOOK("도서") 도서-Description, BOOK-itemType
            System.out.println(itemType.getDescription() + " - " + itemType);
        }

    }
}
