import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CartDao {
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String id = "sqlid";
    private String pw = "sqlpw";
    private Connection conn = null;

    public CartDao() {
        try {
            Class.forName(driver);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("jar 파일 누락");
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(url, id, pw);
            System.out.println("접속 성공");
        } catch (SQLException e) {
            System.out.println("접속 실패");
            e.printStackTrace();
        }
    }


    
    public void clearCart() {
    	connect();
        String query = "DELETE FROM cart";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
            System.out.println("장바구니가 비워졌습니다.");
        } catch (SQLException e) {
            System.out.println("장바구니 비우기 실패");
            e.printStackTrace();
        }
    }



     public int getCartTotalPrice() {
    	 connect();
         int totalPrice = 0; // 총 가격을 저장할 변수 초기화
         String sql = "select sum(quantity * price) AS total_price FROM cart c JOIN menu m ON c.menuid = m.menuid";
         try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
             ResultSet rs = pstmt.executeQuery();
             if (rs.next()) {
                 totalPrice = rs.getInt("total_price"); // 결과에서 총 가격을 가져옴
             }
         } catch (SQLException e) {
             System.out.println("장바구니 총 가격 조회 실패");
             e.printStackTrace();
         }
         return totalPrice; // 총 가격 반환
     }
     
    private MenuBean getMenuById(int menuId) {
    	connect();
        String sql = "select * from menu where menuid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, menuId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String menuName = rs.getString("menuname");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                return new MenuBean(menuId, menuName, price,quantity);
            }
        } catch (SQLException e) {
            System.out.println("메뉴 조회 실패");
            e.printStackTrace();
        }
        return null;
    }
    
   //여기에 있는 totalPrice를 가져가서 PaymentFrame에서 현금결제, 카드결제를 할 때 사용하고 싶어.
    
    public void updateCartItem(int menuId, int quantity) {
    	connect();
        // 장바구니에 해당 메뉴가 이미 있는지 확인
        boolean itemExists = false;
        String query = "select * from cart where menu_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, menuId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 해당 메뉴가 이미 장바구니에 있는 경우
                itemExists = true;
            }
        } catch (SQLException e) {
            System.out.println("장바구니 조회 실패");
            e.printStackTrace();
        }

        if (itemExists) {
            // 장바구니에 해당 메뉴가 이미 있는 경우, 수량을 증가시킴
            String updateQuery = "update cart set quantity = quantity + ? where menu_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
                pstmt.setInt(1, quantity);
                pstmt.setInt(2, menuId);
                pstmt.executeUpdate();
                System.out.println("장바구니 아이템 업데이트 완료");
            } catch (SQLException e) {
                System.out.println("장바구니 아이템 업데이트 실패");
                e.printStackTrace();
            }
        }
    }

}
