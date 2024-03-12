public class CartItem {
    private MenuBean menu;
    private int quantity;
    private int totalPrice;

    public CartItem(MenuBean menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
        this.totalPrice = menu.getPrice() * quantity;
    }
    
    public CartItem(int totalPrice) { //이 생성자는 장바구니에 담긴 총 금액을 
    	this.totalPrice = totalPrice; //결제하는 부분으로 가져가기 위한 생성자
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
