import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";
	Connection conn = null ;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<CategoryBean> lists = new ArrayList<CategoryBean>(); 
	CategoryBean pb = null;
	int cnt = -1;

	public CategoryDao(){
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
}
