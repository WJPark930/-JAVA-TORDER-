import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDao {
    
    private String driver = "oracle.jdbc.driver.OracleDriver";
    private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String id = "sqlid";
    private String pw = "sqlpw";
    Connection conn = null ;
    PreparedStatement ps = null;
    ResultSet rs = null;
    CardBean card = null;

    public CardDao() {
        try {
            Class.forName(driver);
            System.out.println("드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("jar 파일 누락");
            e.printStackTrace();
        }
    }
    
    public void connect(){
        try {
            conn = DriverManager.getConnection(url,id,pw); // 접속 객체 리턴
        } catch (SQLException e) {
            System.out.println("접속 실패");
            e.printStackTrace();
        } 
    } 

    // 카드 정보를 저장하는 메서드
    public void saveCard(CardBean card) {
        connect();
        String sql = "insert into card (cardid, userid, cardtype, expirydate, cvc) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, card.getCardid());
            ps.setString(2, card.getUserid());
            ps.setString(3, card.getCardtype());
            ps.setString(4, card.getExpirydate());
            ps.setString(5, card.getCvc());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 // 카드 정보를 검색하는 메서드
    public CardBean getCard(String cardId) {
        connect();
        String sql = "select * from card where cardid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                CardBean card = new CardBean();
                card.setCardid(rs.getString("cardid"));
                card.setUserid(rs.getString("userid"));
                card.setCardtype(rs.getString("cardtype"));
                card.setExpirydate(rs.getString("expirydate"));
                card.setCvc(rs.getString("cvc"));
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    // 모든 카드 타입을 가져오는 메서드
    public List<String> getAllCardTypes() {
        connect();
        List<String> cardTypes = new ArrayList<>();
        String sql = "select distinct cardtype from card";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cardTypes.add(rs.getString("cardtype"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return cardTypes;
    }
}
