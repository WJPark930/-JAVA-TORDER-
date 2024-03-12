import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CardMain extends JFrame implements ActionListener {
	private JTextField cardNumberField;
	private JPasswordField passwordField;
	private JButton payButton;
	private int totalAmount;
	JComboBox<String> CardTypecomboBox;
	private static int totalSales;
	private int cardSales;


	public static int getTotalSales() {
		return totalSales;
	}

	public CardMain(int totalAmount) {
		this.totalAmount = totalAmount;
		initialize();
	}

	private void initialize() {
		setTitle("카드 결제");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(437, 254);
		setLocationRelativeTo(null); // 화면 중앙에 표시

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 248, 220));
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel cardIdLabel = new JLabel("카드 번호:");
		cardIdLabel.setBounds(50, 81, 80, 20);
		panel.add(cardIdLabel);

		cardNumberField = new JTextField();
		cardNumberField.setBounds(140, 81, 200, 20);
		panel.add(cardNumberField);

		JLabel passwordLabel = new JLabel("비밀번호:");
		passwordLabel.setBounds(50, 122, 80, 20);
		panel.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(140, 122, 200, 20);
		panel.add(passwordField);

		payButton = new JButton("결제");
		payButton.setBackground(new Color(222, 184, 135));
		payButton.setBounds(156, 162, 100, 30);
		payButton.addActionListener(this); // 결제 버튼에 ActionListener 추가
		panel.add(payButton);

		JLabel cardNumberLabel = new JLabel("카드사 선택:");
		cardNumberLabel.setBounds(50, 38, 80, 20);
		panel.add(cardNumberLabel);

		CardTypecomboBox = new JComboBox<>();
		CardTypecomboBox.setBounds(140, 37, 200, 23);
		panel.add(CardTypecomboBox);

		CardDao cardDao = new CardDao();
		List<String> cardTypes = cardDao.getAllCardTypes();
		for (String cardType : cardTypes) {
			CardTypecomboBox.addItem(cardType);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == payButton) {
			handlePayment();
		}
	}

	private void handlePayment() {
		String cardNumber = cardNumberField.getText();
		String password = new String(passwordField.getPassword());
		String selectedCardType = (String) CardTypecomboBox.getSelectedItem(); // 선택된 카드 타입 가져오기

		CardDao cardDao = new CardDao();
		CardBean card = cardDao.getCard(cardNumber); 
		if (card != null && card.getCvc().equals(password) && card.getCardtype().equals(selectedCardType)) {
			JOptionPane.showMessageDialog(this, "결제가 완료되었습니다. 감사합니다");

			// 매출 누적
			cardSales += totalAmount; // 카드 매출 누적
			totalSales += totalAmount; // 전체 매출 누적

			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "카드 정보가 올바르지 않습니다. 결제 실패");
		}
	}




	public static void main(String[] args) {

		try {
			CardMain window = new CardMain(0); // totalAmount는 여기서 0으로 설정
			window.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
