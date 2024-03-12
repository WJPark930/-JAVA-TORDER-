import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InitialScreen {

	private JFrame frame;

	public static void main(String[] args) {	
		try {
			InitialScreen window = new InitialScreen();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InitialScreen() {
		initialize();
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 248, 220));
		frame.setBounds(100, 100, 788, 529);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		JButton restaurantButton = new JButton("매장 식사");
		restaurantButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 25));
		restaurantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		restaurantButton.setBackground(new Color(222, 184, 135));
		restaurantButton.setBounds(142, 135, 165, 227);
		frame.getContentPane().add(restaurantButton);

		JButton takeoutButton = new JButton("포장 주문");
		takeoutButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 25));
		takeoutButton.setBackground(new Color(222, 184, 135));
		takeoutButton.setBounds(455, 135, 177, 227);
		frame.getContentPane().add(takeoutButton);

		JButton managerButton = new JButton("관리자 모드 접속");
		managerButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
		managerButton.setBackground(new Color(222, 184, 135));
		managerButton.setBounds(594, 405, 153, 38);
		frame.getContentPane().add(managerButton);

		JLabel lblNewLabel = new JLabel("매장 식사인지, 포장 주문인지 선택 하세요.");
		lblNewLabel.setBackground(new Color(222, 184, 135));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 15));
		lblNewLabel.setBounds(142, 53, 490, 44);
		frame.getContentPane().add(lblNewLabel);

		restaurantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 매장 식사 버튼 클릭 시 TOrderMain 클래스 실행
				TOrderMain tOrderMain = new TOrderMain();
				tOrderMain.setVisible(true);
				frame.dispose();
			}
		});

		takeoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 포장 주문 버튼 클릭 시 TOrderMain 클래스 실행
				TOrderMain tOrderMain = new TOrderMain();
				tOrderMain.setVisible(true);
				frame.dispose();
			}
		});

		managerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 관리자 접속 버튼 클릭 시 Login 클래스 실행
				Login login = new Login();
				login.setVisible(true);
				frame.dispose();
			}

		});

		
	}
}
