import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddMenu extends JDialog {
	private JTextField menuIdField;
	private JTextField menuNameField;
	private JTextField priceField;
	private JTextField categoryField;
	private JButton addButton;
	private JButton backButton;
	private JFrame frame;

	public AddMenu(Frame parent) {
		super(parent, "메뉴 추가", true);
		initialize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 300);
		setLocationRelativeTo(null);
	}

	private void initialize() {
		getContentPane().setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		getContentPane().setBackground(new Color(255, 248, 220));
		getContentPane().setLayout(null);

		JLabel infoLabel = new JLabel("메뉴 고유번호, 메뉴명, 가격, 카테고리 분류를 입력하세요");
		infoLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		infoLabel.setBackground(new Color(222, 184, 135));
		infoLabel.setBounds(36, 10, 361, 26);
		getContentPane().add(infoLabel);

		menuIdField = new JTextField();
		menuIdField.setBounds(170, 46, 137, 21);
		getContentPane().add(menuIdField);
		menuIdField.setColumns(10);

		menuNameField = new JTextField();
		menuNameField.setColumns(10);
		menuNameField.setBounds(170, 88, 137, 21);
		getContentPane().add(menuNameField);

		priceField = new JTextField();
		priceField.setColumns(10);
		priceField.setBounds(170, 140, 137, 21);
		getContentPane().add(priceField);

		categoryField = new JTextField();
		categoryField.setColumns(10);
		categoryField.setBounds(170, 171, 137, 21);
		getContentPane().add(categoryField);

		JLabel menuIdLabel = new JLabel("고유번호 :");
		menuIdLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		menuIdLabel.setBackground(new Color(255, 248, 220));
		menuIdLabel.setBounds(91, 46, 79, 21);
		getContentPane().add(menuIdLabel);

		JLabel menuNameLabel = new JLabel("메뉴명 :");
		menuNameLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		menuNameLabel.setBackground(new Color(255, 248, 220));
		menuNameLabel.setBounds(104, 88, 66, 21);
		getContentPane().add(menuNameLabel);

		JLabel priceLabel = new JLabel("가격 :");
		priceLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		priceLabel.setBackground(new Color(255, 248, 220));
		priceLabel.setBounds(117, 133, 53, 21);
		getContentPane().add(priceLabel);

		JLabel categoryLabel = new JLabel("카테고리 분류 : ");
		categoryLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		categoryLabel.setBackground(new Color(255, 248, 220));
		categoryLabel.setBounds(67, 171, 103, 21);
		getContentPane().add(categoryLabel);

		addButton = new JButton("추가하기");
		addButton.setBackground(new Color(222, 184, 135));
		addButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		addButton.setBounds(91, 202, 105, 26);
		getContentPane().add(addButton);

		backButton = new JButton("뒤로 가기");
		backButton.setBackground(new Color(222, 184, 135));
		backButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		backButton.setBounds(233, 202, 105, 26);
		getContentPane().add(backButton);

		// 추가 버튼에 ActionListener 추가
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 입력된 메뉴 정보 가져오기
				int menuId = Integer.parseInt(menuIdField.getText());
				String menuname = menuNameField.getText();
				int price = Integer.parseInt(priceField.getText());
				int categorynum = Integer.parseInt(categoryField.getText());

				// 메뉴 정보를 데이터베이스에 추가하는 메소드 호출
				MenuDao menuDao = new MenuDao();
				MenuBean newMenu = new MenuBean(menuId,menuname,price, categorynum);
				menuDao.addMenu(newMenu);

				// 메뉴 추가 완료 메시지 출력
				JOptionPane.showMessageDialog(AddMenu.this, "메뉴가 추가되었습니다.");

				// 현재 창 닫기
				dispose();
			}
		});

		// 뒤로 가기 버튼에 ActionListener 추가
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 이전 화면인 managerMain을 생성하여 보여줌
				managerMain managerMainScreen = new managerMain();
				managerMainScreen.setVisible(true);

				// 현재 창 닫기
				dispose();
			}
		});


	}
}
