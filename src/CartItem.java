public class CartItem {
    private MenuBean menu;
    private int quantity;
    private int totalPrice;

    public CartItem(MenuBean menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
        this.totalPrice = menu.getPrice() * quantity;
    }
    
    public CartItem(int totalPrice) {  
    	this.totalPrice = totalPrice; 
    }

    public MenuBean getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}
