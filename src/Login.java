import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame implements ActionListener {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton; // 회원가입 버튼 추가
    private UsersDao userDao;
    private JButton backButton;


    public Login() {
        getContentPane().setBackground(new Color(255, 248, 220));
        setTitle("로그인");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임이 닫힐 때 프로그램 종료
        setLocationRelativeTo(null);


        // 컴포넌트 초기화
        userIdField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("로그인");
        loginButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        loginButton.setBackground(new Color(222, 184, 135));
        signupButton = new JButton("회원가입"); // 회원가입 버튼 생성
        signupButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        signupButton.setBackground(new Color(222, 184, 135));

        // 사용자 정의 레이아웃 설정
        getContentPane().setLayout(null);

        // 컴포넌트 위치 설정
        JLabel userIdLabel = new JLabel("아이디:");
        userIdLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        userIdLabel.setBounds(98, 80, 51, 30);
        userIdField.setBounds(162, 81, 250, 30);

        JLabel passwordLabel = new JLabel("비밀번호:");
        passwordLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        passwordLabel.setBounds(86, 120, 63, 30);
        passwordField.setBounds(162, 121, 250, 30);

        loginButton.setBounds(162, 179, 100, 30); // 로그인 버튼 위치 수정
        signupButton.setBounds(312, 179, 100, 30); // 회원가입 버튼 위치 설정

        // 컴포넌트 추가
        getContentPane().add(userIdLabel);
        getContentPane().add(userIdField);
        getContentPane().add(passwordLabel);
        getContentPane().add(passwordField);
        getContentPane().add(loginButton);
        getContentPane().add(signupButton); // 회원가입 버튼 추가

        backButton = new JButton("뒤로 가기");
        backButton.setBackground(new Color(222, 184, 135));
        backButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        backButton.setBounds(315, 33, 97, 23);
        getContentPane().add(backButton);

        // 이벤트 리스너 등록
        loginButton.addActionListener(this);
        signupButton.addActionListener(this); // 회원가입 버튼에 대한 이벤트 리스너 등록
        backButton.addActionListener(this); // 뒤로 가기 버튼에 대한 이벤트 리스너 등록

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

            // 로그인 확인
            if (userDao.loginUser(userId, userpassword)) {
                if (userId.equals("wonjun") && userpassword.equals("4092")) {
                    // managerMain 클래스 화면 표시
                    managerMain managerMain = new managerMain();
                    managerMain.setVisible(true);

                    dispose(); // 현재 로그인 창 닫기
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 성공");

                    // 랜덤 메뉴 선택
                    MenuDao menuDao = new MenuDao();
                    MenuBean randomMenu = menuDao.getRandomMenu(5); // categorynum 5번은 디저트 메뉴에서 랜덤으로 하나 증정

                    if (randomMenu != null) {
                        JOptionPane.showMessageDialog(this,randomMenu.getMenuname() + " 당첨되셨습니다. 맛있게 드세요!");
                        // TOrderMain 클래스 화면 표시
                        TOrderMain orderMain = new TOrderMain();
                        orderMain.setVisible(true);

                        dispose(); // 현재 로그인 창 닫기
                    } else {
                        JOptionPane.showMessageDialog(this, "랜덤 메뉴 선택 실패. 다시 시도해주세요.");
                    }
                }
            }
        } else if (e.getSource() == signupButton) { // 회원가입 버튼을 눌렀을 때
            Signup signupForm = new Signup();
            signupForm.setVisible(true);
        } else if (e.getSource() == backButton) {
            // 뒤로 가기 버튼을 눌렀을 때 TOrderMain 클래스 실행
            TOrderMain tOrderMain = new TOrderMain();
            tOrderMain.setVisible(true);
            dispose(); // 현재 로그인 창 닫기
        }
    }


    public static void main(String[] args) {
        Login loginForm = new Login();
        loginForm.setVisible(true);
    }
}
