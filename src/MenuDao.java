import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class MenuDao {
	private ArrayList<MenuBean> menuList;
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String id = "sqlid";
	private String pw = "sqlpw";

	public MenuDao() {
		menuList = new ArrayList<>();
		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("jar 파일 누락");
			e.printStackTrace();
		}
	}

	
	//----------------------------------------------------------------------------------------------
	
	public ArrayList<MenuBean> getMenuByCategory(int categorynum) {
		ArrayList<MenuBean> categoryMenuList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * from menu where categorynum = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, categorynum);
			rs = ps.executeQuery();

			while (rs.next()) {
				// 결과를 MenuBean 객체로 변환하여 리스트에 추가
				MenuBean menu = new MenuBean();
				menu.setMenuId(rs.getInt("menuId"));
				menu.setMenuname(rs.getString("menuname"));
				menu.setPrice(rs.getInt("price"));

				categoryMenuList.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return categoryMenuList;
	}

	//----------------------------------------------------------------------------------------------
	
	public MenuBean getMenuById(int menuId) {
		MenuBean menu = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, id, pw); // Connection 객체 생성
			String sql = "select * from menu where menuId = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, menuId);
			rs = ps.executeQuery();

			if (rs.next()) {
				menu = new MenuBean();
				menu.setMenuId(rs.getInt("menuId"));
				menu.setMenuname(rs.getString("menuname"));
				menu.setPrice(rs.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return menu;
	}

	public void setMenuList(ArrayList<MenuBean> menuList) {
		this.menuList = menuList;
	}

	//----------------------------------------------------------------------------------------------
	
	public MenuBean getRandomMenu(int categorynum) {
		MenuBean randomMenu = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DriverManager.getConnection(url, id, pw);
			String sql = "select * from (select * from menu where categorynum = ? order by DBMS_RANDOM.VALUE) where rownum <= 1";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, categorynum);
			rs = ps.executeQuery();

			if (rs.next()) {
				randomMenu = new MenuBean();
				randomMenu.setMenuId(rs.getInt("menuId"));
				randomMenu.setMenuname(rs.getString("menuname"));
				randomMenu.setPrice(rs.getInt("price"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return randomMenu;
	}

	//----------------------------------------------------------------------------------------------

	public void addMenu(MenuBean menu) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DriverManager.getConnection(url, id, pw);
            String sql = "insert into menu (menuid, menuname, price, categorynum) values (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, menu.getMenuId());
            ps.setString(2, menu.getMenuname());
            ps.setInt(3, menu.getPrice());
            ps.setInt(4, menu.getCategorynum());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	//----------------------------------------------------------------------------------------------	

	public ArrayList<MenuBean> getAllMenu() {
		 ArrayList<MenuBean> allMenuList = new ArrayList<>();
		 Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            conn = DriverManager.getConnection(url, id, pw);
	            String sql = "select * from menu";
	            ps = conn.prepareStatement(sql);
	            rs = ps.executeQuery();

	            while (rs.next()) {
	                MenuBean menu = new MenuBean();
	                menu.setMenuId(rs.getInt("menuId"));
	                menu.setMenuname(rs.getString("menuname"));
	                menu.setPrice(rs.getInt("price"));
	                menu.setCategorynum(rs.getInt("categorynum"));
	                allMenuList.add(menu);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (ps != null) ps.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return allMenuList;
	}

	
	
	//----------------------------------------------------------------------------------------------
	
	public boolean deleteMenu(String selectedMenuName) {
	    boolean success = false;
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DriverManager.getConnection(url, id, pw);
	        String sql = "delete from menu where menuname = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, selectedMenuName); // 수정: selectedMenuName을 사용하여 설정
	        int rowsAffected = ps.executeUpdate();
	        success = (rowsAffected > 0);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return success;
	}
	
//----------------------------------------------------------------------------------------------
	public boolean updateMenu(MenuBean menu) {
	    boolean success = false;
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = DriverManager.getConnection(url, id, pw);
	        String sql = "update menu set menuname = ?, price = ? where menuid = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, menu.getMenuname());
	        ps.setInt(2, menu.getPrice());
	        ps.setInt(3, menu.getMenuId());
	        int rowsAffected = ps.executeUpdate();
	        success = (rowsAffected > 0);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return success;
	}

	
//----------------------------------------------------------------
	
	public MenuBean getMenuByName(String menuName) {
	    MenuBean menu = null;
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        conn = DriverManager.getConnection(url, id, pw); // Connection 객체 생성
	        String sql = "select * from menu where menuname = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, menuName);
	        rs = ps.executeQuery();

	        if (rs.next()) {
	            menu = new MenuBean();
	            menu.setMenuId(rs.getInt("menuId"));
	            menu.setMenuname(rs.getString("menuname"));
	            menu.setPrice(rs.getInt("price"));

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return menu;
	}


}


