import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";
	Connection conn = null ;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<UsersBean> lists = new ArrayList<UsersBean>(); 
	UsersBean pb = null;
	int cnt = -1;

	public UsersDao(){
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
			System.out.println("접속 성공");
		} catch (SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		} 
	}
	
    // 회원 가입
    public boolean registerUser(String userId, String userPasswd) {
    	connect();
    	String sql = "insert into users (userId, userPasswd) values (?, ?)";
        try {
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, userId);
        	ps.setString(2, userPasswd);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
        	try {
        		if(ps!=null)
				ps.close();
        		if(conn!=null)
        		conn.close();
        		if(rs!=null)
        		rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
    }

    // 로그인
    public boolean loginUser(String userId, String userPasswd) {
    	connect();
    	String sql = "select * from users where userId=? and userPasswd=?";
        try {
        	ps = conn.prepareStatement(sql);
        	ps.setString(1, userId);
        	ps.setString(2, userPasswd);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
        	try {
        		if(ps!=null)
				ps.close();
        		if(conn!=null)
        		conn.close();
        		if(rs!=null)
        		rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
   
        }
    }
}
