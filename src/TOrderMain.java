import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class TOrderMain extends JFrame implements ActionListener, PaymentFrame.PaymentCompleteListener  {
	//implements의 기능인 다중 상속 지원!
	private JButton[] btnCategory = new JButton[5];
	private DefaultListModel<String> menuListModel;
	private JList<String> menuList;
	private DefaultListModel<MenuBean> cartListModel; // 장바구니 목록을 위한 모델
	private JList<MenuBean> cartlist; // 장바구니 목록을 표시할 JList
	private JButton orderButton;
	private JButton eventButton;
	private JButton orderViewButton;
	private JButton payButton; 
	private JButton returnButton;
	private PaymentFrame paymentFrame;
	private JFrame frame;


	private UsersDao udao;
	private CategoryDao cdao;
	private MenuDao mdao;
	private CartDao cartDao;
	

	private HashMap<Integer, Integer> cart; // 장바구니 아이템을 담을 HashMap

	public TOrderMain() {
		getContentPane().setBackground(new Color(255, 248, 220));
		setTitle("주문 키오스크");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(788, 529);
		setResizable(false);
		setLocationRelativeTo(null);

		// DAO 초기화
		udao = new UsersDao();
		cdao = new CategoryDao();
		mdao = new MenuDao();
		cartDao = new CartDao(); // CartDao 초기화
		

		// 장바구니 초기화
		cart = new HashMap<>();

		// 전체 레이아웃을 null로 설정
		getContentPane().setLayout(null);

		// 제목 패널 생성 및 프레임에 추가
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(222, 184, 135));
		JLabel titleLabel = new JLabel("테이블 오더 프로그램");
		titleLabel.setFont(new Font("휴먼모음T", Font.BOLD, 20));
		titleLabel.setBounds(20, 20, 300, 30);
		titlePanel.add(titleLabel);
		titlePanel.setBounds(10, 10, 747, 40);
		getContentPane().add(titlePanel);

		// 메뉴 목록과 주문 버튼, 이벤트 참여 메뉴를 담는 패널 생성
		JPanel menuPanel = new JPanel();
		menuPanel.setBackground(new Color(255, 248, 220));
		menuPanel.setLayout(null); // null 레이아웃으로 변경
		menuPanel.setBounds(10, 60, 747, 342);

		// 메뉴 목록 초기화
		menuListModel = new DefaultListModel<>();
		JScrollPane menuScrollPane = new JScrollPane(); // JScrollPane에 menuList 추가
		menuScrollPane.setBounds(12, 70, 250, 250); // JScrollPane의 크기 조절
		menuPanel.add(menuScrollPane); // JScrollPane을 menuPanel에 추가
		menuList = new JList<>(menuListModel);
		menuScrollPane.setViewportView(menuList);
		menuList.setBackground(new Color(255, 250, 240));
		menuList.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15)); // 폰트 설정

		// 장바구니에 담기 버튼
		orderButton = new JButton("장바구니에 담기");
		orderButton.setBackground(new Color(222, 184, 135));
		orderButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		orderButton.addActionListener(this);
		orderButton.setBounds(75, 20, 129, 40);
		menuPanel.add(orderButton);

		// 이벤트 참여 버튼
		eventButton = new JButton("디저트 증정 이벤트!");
		eventButton.setBackground(new Color(222, 184, 135));
		eventButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		eventButton.addActionListener(this);
		eventButton.setBounds(481, 291, 244, 29);
		menuPanel.add(eventButton);

		// 장바구니 보기 버튼
		orderViewButton = new JButton("장바구니 보기");
		orderViewButton.setBackground(new Color(222, 184, 135));
		orderViewButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		orderViewButton.addActionListener(this);
		orderViewButton.setBounds(307, 20, 129, 40);
		menuPanel.add(orderViewButton);

		getContentPane().add(menuPanel);

		// 장바구니 리스트 초기화
		cartListModel = new DefaultListModel<>();
		JScrollPane cartScrollPane = new JScrollPane();
		cartScrollPane.setBounds(276, 70, 193, 250);
		menuPanel.add(cartScrollPane);
		cartlist = new JList<>(cartListModel);
		cartScrollPane.setViewportView(cartlist);
		cartlist.setForeground(new Color(0, 0, 0));
		cartlist.setBackground(new Color(255, 250, 240));

		// 결제하기 버튼 초기화
		payButton = new JButton("결제하기");
		payButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		payButton.setBackground(new Color(222, 184, 135));
		payButton.setBounds(481, 252, 246, 29);
		payButton.addActionListener(this); // ActionListener 추가
		menuPanel.add(payButton);

		// 키오스크 주문 방법 초기화
		JTextPane textPane = new JTextPane();
		textPane.setBackground(new Color(255, 248, 220));
		textPane.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		textPane.setText(" <주문 방법> \n \n 1. 화면 하단 카테고리를 클릭해 주세요\n \n 2. 왼쪽 목록에 나오는 메뉴를 클릭 후 장바구니에 담아주세요.\n \n 3.'장바구니 보기' 버튼을 클릭하여, 총 수량과 금액을 확인해 주세요\n \n 4. 문제가 없으시다면, 결제하기 버튼을 클릭 후, 결제를 진행해 주세요");
		textPane.setBounds(481, 44, 244, 198);
		menuPanel.add(textPane);

		//처음으로 돌아가기 버튼
		returnButton = new JButton("처음으로");
		returnButton.setBackground(new Color(222, 184, 135));
		returnButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		returnButton.setBounds(628, 11, 97, 23);
		returnButton.addActionListener(this);
		menuPanel.add(returnButton);


		// 카테고리 버튼 초기화
		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(new GridLayout(1, 5)); // 1행 5열의 그리드 레이아웃
		categoryPanel.setBounds(20, 424, 737, 56);
		categoryPanel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		String[] btnCategoryLabels = {"한식", "중식", "일식", "주류", "디저트"};
		for (int i = 0; i < btnCategoryLabels.length; i++) {
			btnCategory[i] = new JButton(btnCategoryLabels[i]);
			btnCategory[i].addActionListener(this);
			btnCategory[i].setBackground(new Color(222, 184, 135)); // 색상 지정
			categoryPanel.add(btnCategory[i]);
		}
		getContentPane().add(categoryPanel); // 카테고리 패널을 프레임에 추가
	}

	public void onPaymentComplete(boolean cartCleared) { 
		if (cartCleared) {
			// 장바구니를 비웠으면 목록을 초기화
			cart.clear();
			cartListModel.clear();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int totalQuantity = 0; // 총 수량을 나타내는 변수
		int totalPrice = 0; // 총 가격을 나타내는 변수


		if (e.getSource() == orderButton) {
			// 장바구니에 메뉴를 담기
			int selectedIndex = menuList.getSelectedIndex();
			if (selectedIndex != -1) {
				String selectedMenu = menuListModel.getElementAt(selectedIndex);
				int menuId = Integer.parseInt(selectedMenu.substring(1, selectedMenu.indexOf("]"))); // 메뉴 ID 추출
				// 장바구니에 메뉴 추가
				cart.put(menuId, cart.getOrDefault(menuId, 0) + 1);
				JOptionPane.showMessageDialog(this, "장바구니에 메뉴가 추가되었습니다.");
			} else {
				JOptionPane.showMessageDialog(this, "메뉴를 선택해주세요.");
			}
		} else if (e.getSource() == orderViewButton) {
			// 장바구니 보기 버튼 클릭 시 장바구니 리스트 출력
			cartListModel.clear(); // 기존 장바구니 목록 초기화
			totalQuantity = 0; // 총 수량을 저장할 변수 초기화
			totalPrice = 0; // 총 가격을 저장할 변수 초기화
			for (int menuId : cart.keySet()) {
				int quantity = cart.get(menuId);
				MenuBean menu = mdao.getMenuById(menuId);
				int price = menu.getPrice();
				totalPrice += price * quantity; // 총 가격 계산
				totalQuantity += quantity; // 총 수량 누적
				// 장바구니 목록에 메뉴와 수량 추가
				cartListModel.addElement(new MenuBean(menu.getMenuId(), menu.getMenuname(), price, quantity));
			}
			// 총 수량과 총 가격을 문자열로 형식화하여 장바구니 목록의 마지막에 추가
			cartListModel.addElement(new MenuBean(-1, String.format("총 수량: %d, 총 가격: %d", totalQuantity, totalPrice), totalPrice, totalQuantity));
		}

		else if (e.getSource() == eventButton) {
			// 이벤트 버튼 클릭 시 로그인 프레임 표시
			Login loginForm = new Login();
			loginForm.setVisible(true);

		} else if (e.getSource() == payButton) {
			// 결제 버튼 클릭 시 PaymentFrame 실행
			int totalPriceInCart = calculateTotalPrice(); // 장바구니의 총 가격 계산
			PaymentFrame paymentFrame = new PaymentFrame(totalPriceInCart, cartDao, this); // PaymentFrame에 PaymentCompleteListener 전달하여 생성
			paymentFrame.setVisible(true); // PaymentFrame 실행



		} else if (e.getSource() == returnButton) {
			// 처음으로 돌아가기 버튼 클릭 시 InitialScreen으로 이동
			InitialScreen initialScreen = new InitialScreen();
			initialScreen.setVisible(true);
			dispose(); // 현재 창 닫기
		} 

		else {
			for (int i = 0; i < btnCategory.length; i++) {
				if (e.getSource() == btnCategory[i]) {
					// 카테고리 버튼 클릭 시 해당 카테고리의 메뉴 목록을 가져와서 표시
					ArrayList<MenuBean> menuData = mdao.getMenuByCategory(i + 1);
					menuListModel.clear(); // 기존 목록 초기화
					for (MenuBean menu : menuData) {
						menuListModel.addElement(String.format("[%d] %s - 가격: %d", menu.getMenuId(), menu.getMenuname(), menu.getPrice()));
						btnCategory[i].setBackground(new Color(222, 184, 135));
					}
					break;
				}
			}
		}
	}

	// 장바구니의 총 가격을 계산하는 메서드
	private int calculateTotalPrice() {
		int totalPrice = 0;
		for (int menuId : cart.keySet()) {
			int quantity = cart.get(menuId);
			MenuBean menu = mdao.getMenuById(menuId);
			int price = menu.getPrice();
			totalPrice += price * quantity; // 총 가격 계산
		}
		return totalPrice;
	}



	public static void main(String[] args) {
		TOrderMain torderMain = new TOrderMain();
		torderMain.setVisible(true);
	}
}
