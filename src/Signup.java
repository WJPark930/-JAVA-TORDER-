import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private UsersDao userDao;
    private JButton backButton;
    private JLabel titleLabel;

    public Signup() {
        getContentPane().setBackground(new Color(255, 248, 220));
        setTitle("회원가입");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        // 컴포넌트 초기화
        userIdField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("가입하기");
        registerButton.setBackground(new Color(222, 184, 135));

        // 사용자 정의 레이아웃 설정
        getContentPane().setLayout(null);

        // 컴포넌트 위치 설정
        JLabel userIdLabel = new JLabel("아이디 입력:");
        userIdLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        userIdLabel.setBounds(69, 96, 79, 30);
        userIdField.setBounds(160, 97, 250, 30);

        JLabel passwordLabel = new JLabel("비밀번호 입력:");
        passwordLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        passwordLabel.setBounds(58, 136, 91, 30);
        passwordField.setBounds(160, 137, 250, 30);

        registerButton.setBounds(160, 200, 250, 30);

        // 컴포넌트 추가
        getContentPane().add(userIdLabel);
        getContentPane().add(userIdField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordField);
        getContentPane().add(registerButton);

        backButton = new JButton("뒤로 가기");
        backButton.setBackground(new Color(222, 184, 135));
        backButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        backButton.setBounds(313, 23, 97, 23);
        getContentPane().add(backButton);

        titleLabel = new JLabel("아이디와 비밀번호는 각 4글자 이상 입력해주세요.");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBackground(new Color(222, 184, 135));
        titleLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        titleLabel.setBounds(69, 54, 321, 32);
        getContentPane().add(titleLabel);

        // 이벤트 리스너 등록
        registerButton.addActionListener(this);
        backButton.addActionListener(this); // 뒤로 가기 버튼에 대한 이벤트 리스너 등록

        // UsersDao 인스턴스 생성
        userDao = new UsersDao();
        userDao.connect();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String userId = userIdField.getText();
            String password = new String(passwordField.getPassword());

            if (userId.length() < 4 || password.length() < 4) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호는 각각 4글자 이상이어야 합니다.");
                clearTextField();
                return;
            }

            // 회원 가입
            if (userDao.registerUser(userId, password)) {
                JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
                clearTextField();
            } else {
                JOptionPane.showMessageDialog(this, "회원가입 실패. 이미 등록된 아이디입니다.");
                clearTextField();
            }
        } else if (e.getSource() == backButton) {
            // 뒤로 가기 버튼(backButton)을 눌렀을 때 Login 클래스 실행
            Login login = new Login();
            login.setVisible(true);
            dispose(); // 현재 회원가입 창 닫기
        }
    }

    private void clearTextField() {
        userIdField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        Signup signupForm = new Signup();
        signupForm.setVisible(true);
    }
}
