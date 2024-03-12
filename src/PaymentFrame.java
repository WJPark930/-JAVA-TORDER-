import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.UIManager;


public class PaymentFrame extends JFrame implements ActionListener {
    private JButton cashPaymentButton;
    private JButton cardPaymentButton;
    private int totalPrice;
    private CartDao cartDao; 
    
    
    boolean paymentComplete = false;

    private Map<Integer, Integer> cart; // 장바구니를 가리키는 참조
    private DefaultListModel<MenuBean> cartListModel; // 장바구니 목록을 가리키는 참조

    public interface PaymentCompleteListener {
        void onPaymentComplete(boolean cartCleared);
    }

    private PaymentCompleteListener paymentCompleteListener;
   
    public PaymentFrame(int totalPrice, CartDao cartDao, PaymentCompleteListener listener) {
        this.totalPrice = totalPrice;
        this.cartDao = cartDao;
        this.paymentCompleteListener = listener;
        initialize();
    }


    private void initialize() {
        setTitle("결제하기");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(575, 442);
        setLocationRelativeTo(null); // 화면 중앙에 표시

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 248, 220));

        getContentPane().add(panel);
        panel.setLayout(null);

        cashPaymentButton = new JButton("현금 결제");
        cashPaymentButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
        cashPaymentButton.setBackground(new Color(222, 184, 135));
        cashPaymentButton.setBounds(63, 152, 182, 166);
        cashPaymentButton.addActionListener(this); // 현금 결제 버튼에 액션 리스너 추가
        panel.add(cashPaymentButton);

        cardPaymentButton = new JButton("카드 결제");
        cardPaymentButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
        cardPaymentButton.setBackground(new Color(222, 184, 135));
        cardPaymentButton.setBounds(315, 152, 164, 166);
        cardPaymentButton.addActionListener(this); // 카드 결제 버튼에 액션 리스너 추가
        panel.add(cardPaymentButton);
        
        JLabel PayinfoLabel = new JLabel("<html>결제하실 총 금액은 " + totalPrice + "원 입니다.<br>수량과 금액을 확인해 주세요</html>");
        //JLabel에서 줄바꿈을 구현하기 위하여 html태그를 사용하였습니다
        
        PayinfoLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
        PayinfoLabel.setBackground(new Color(222, 184, 135));
        PayinfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        PayinfoLabel.setBounds(33, 43, 481, 79);
        panel.add(PayinfoLabel);

        // calculateTotalPrice 메서드 호출 추가
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cashPaymentButton) { //현금결제 버튼 클릭 시
            handleCashPayment(); //현금결제 메서드 호출
        } else if (e.getSource() == cardPaymentButton) { //카드결제 버튼 클릭 시 
            try {
                CardMain cardMain = new CardMain(totalPrice);
                cardMain.setVisible(true);
                paymentCompleteListener.onPaymentComplete(true); // 장바구니를 비웠음을 알림
                paymentComplete = true;
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleCashPayment() {
        boolean paymentComplete = false;
        
        do {
            String input = JOptionPane.showInputDialog("결제할 총 금액은 "+ totalPrice + "\n 투입금액: ");
            if (input == null) {
                dispose(); // 사용자가 취소를 누르면 결제 창 종료
                return; // 메서드 종료
            }
            try {
                int insertedAmount = Integer.parseInt(input); // 사용자가 입력한 금액을 저장
                while (insertedAmount < totalPrice) { //투입 금액이 totalPrice보다 작을 때 까지 반복
                    int insufficient = totalPrice - insertedAmount;
                    input = JOptionPane.showInputDialog(insufficient + "원을 더 투입해 주세요: ");
                    if (input == null) {
                        dispose();
                        return;
                    }
                    insertedAmount += Integer.parseInt(input);
                }
                int change = insertedAmount - totalPrice;
                if (change > 0) {
                    JOptionPane.showMessageDialog(this, "거스름돈은 " + change + "원 입니다.\n결제가 완료되었습니다. 감사합니다");
                } else {
                    JOptionPane.showMessageDialog(this, "결제가 완료되었습니다. 감사합니다");
                }
                paymentCompleteListener.onPaymentComplete(true); // 장바구니를 비웠음을 알림
                paymentComplete = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "올바른 금액을 입력해 주세요.");
            }
        } while (!paymentComplete);
        dispose(); // 결제 창 종료
    }



    // CartDao를 사용하여 장바구니의 총 가격을 가져오는 부분.
    private int calculateTotalPriceFromCart() {
        int totalPrice = cartDao.getCartTotalPrice(); // CartDao를 사용하여 장바구니의 총 가격을 가져옴
        return totalPrice; // 총 가격 반환
    }
}
