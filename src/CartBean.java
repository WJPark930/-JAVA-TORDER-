
public class CartBean {
    private int menuId;
    private String menuName;
    private int price;
    private int quantity;

    public CartBean(int menuId, String menuName, int price, int quantity) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartBean [menuId=" + menuId + ", menuName=" + menuName + ", price=" + price + ", quantity=" + quantity
                + "]";
    }
}
