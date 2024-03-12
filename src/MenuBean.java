public class MenuBean {
    private int menuId;
    private String menuname;
    private int price;
    private int categorynum;

    public MenuBean() {
    }

    public MenuBean(int menuId, String menuname, int price, int categorynum) {
        this.menuId = menuId;
        this.menuname = menuname;
        this.price = price;
        this.categorynum = categorynum;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategorynum() {
        return categorynum;
    }

    public void setCategorynum(int categorynum) {
        this.categorynum = categorynum;
    }

    @Override
    public String toString() {
        return menuname + " - 가격: " + price + ", 카테고리 번호: " + categorynum;
    }
}
