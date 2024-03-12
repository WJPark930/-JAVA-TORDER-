import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private UsersDao userDao;
    private JButton backButton;


    public Login() {
        getContentPane().setBackground(new Color(255, 248, 220));
        setTitle("로그인");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        userIdField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("로그인");
        loginButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        loginButton.setBackground(new Color(222, 184, 135));
        signupButton = new JButton("회원가입"); // 회원가입 버튼 생성
        signupButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        signupButton.setBackground(new Color(222, 184, 135));


        getContentPane().setLayout(null);

        JLabel userIdLabel = new JLabel("아이디:");
        userIdLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        userIdLabel.setBounds(98, 80, 51, 30);
        userIdField.setBounds(162, 81, 250, 30);

        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        passwordLabel.setBounds(86, 120, 63, 30);
        passwordField.setBounds(162, 121, 250, 30);

        loginButton.setBounds(162, 179, 100, 30);
        signupButton.setBounds(312, 179, 100, 30);

        // 컴포넌트 추가
        getContentPane().add(userIdLabel);
        getContentPane().add(userIdField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordField);
        getContentPane().add(loginButton);
        getContentPane().add(signupButton);

        backButton = new JButton("뒤로 가기");
        backButton.setBackground(new Color(222, 184, 135));
        backButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        backButton.setBounds(315, 33, 97, 23);
        getContentPane().add(backButton);

        // 이벤트 리스너 등록
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);
        backButton.addActionListener(this);

        // UsersDao 인스턴스 생성
        userDao = new UsersDao();
        userDao.connect();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userId = userIdField.getText();
            String userpassword = new String(passwordField.getPassword());

            if (userId.isEmpty() || userpassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 입력하세요.");
                return;
            }

            //관리자 계정으로 접속
            if (userDao.loginUser(userId, userpassword)) {
                if (userId.equals("wonjun") && userpassword.equals("4092")) {
                    managerMain managerMain = new managerMain();
                    managerMain.setVisible(true);

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 성공");

                    // 랜덤 메뉴 선택
                    MenuDao menuDao = new MenuDao();
                    MenuBean randomMenu = menuDao.getRandomMenu(5); // categorynum 5번은 디저트 메뉴에서 랜덤으로 하나 증정

                    if (randomMenu != null) {
                        JOptionPane.showMessageDialog(this,randomMenu.getMenuname() + " 당첨되셨습니다. 맛있게 드세요!");
                        TOrderMain orderMain = new TOrderMain();
                        orderMain.setVisible(true);

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "랜덤 메뉴 선택 실패. 다시 시도해주세요.");
                    }
                }
            }
        } else if (e.getSource() == signupButton) {
            Signup signupForm = new Signup();
            signupForm.setVisible(true);
        } else if (e.getSource() == backButton) {
            TOrderMain tOrderMain = new TOrderMain();
            tOrderMain.setVisible(true);
            dispose();
        }
    }


    public static void main(String[] args) {
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }
}
