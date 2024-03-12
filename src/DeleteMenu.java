import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DeleteMenu extends JDialog {
    private JComboBox<String> menuList;
    private JButton deleteButton;
    private JButton gotoManagerMain;
    private MenuDao mdao;
    private JFrame frame;
    private JLabel titleLabel;

    public DeleteMenu(Frame parent) {
        super(parent, "메뉴 삭제", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400,300);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(new Color(255, 248, 220));
        getContentPane().setLayout(null);
        
        menuList = new JComboBox<>();
        menuList.setBounds(96, 102, 191, 23);
        getContentPane().add(menuList);
        
        deleteButton = new JButton("삭제 하기");
        deleteButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        deleteButton.setBackground(new Color(222, 184, 135));
        deleteButton.setBounds(74, 164, 97, 23);
        getContentPane().add(deleteButton);
        
        
        gotoManagerMain = new JButton("뒤로 가기");
        gotoManagerMain.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        gotoManagerMain.setBackground(new Color(222, 184, 135));
        gotoManagerMain.setBounds(222, 164, 97, 23);
        getContentPane().add(gotoManagerMain);
        
        titleLabel = new JLabel("삭제할 메뉴를 선택 후, 삭제하기 버튼을 클릭해 주세요.");
        titleLabel.setBackground(new Color(222, 184, 135));
        titleLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(40, 31, 320, 33);
        getContentPane().add(titleLabel);
       
        
        initialize();
    }

    private void initialize() {
        mdao = new MenuDao();

        // 메뉴 목록을 콤보 상자에 표시
        ArrayList<MenuBean> menuData = mdao.getAllMenu();
        for (MenuBean menu : menuData) {
            menuList.addItem(menu.getMenuname());
        }

        // 삭제 버튼에 addActionListener 추가하여 선택한 메뉴를 데이터베이스에서 삭제하는 작업
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = menuList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedMenuName = (String) menuList.getSelectedItem();
                    int option = JOptionPane.showConfirmDialog(null, "정말로 " + selectedMenuName + "을(를) 삭제하시겠습니까?", "메뉴 삭제", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        // 선택한 메뉴를 데이터베이스에서 삭제
                        boolean result = mdao.deleteMenu(selectedMenuName);
                        if (result) {
                            JOptionPane.showMessageDialog(null, selectedMenuName + "이(가) 삭제되었습니다.");
                            menuList.removeItemAt(selectedIndex); // 콤보 상자에서 삭제된 메뉴 제거
                        } else {
                            JOptionPane.showMessageDialog(null, "메뉴 삭제에 실패했습니다.");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "삭제할 메뉴를 선택해주세요.");
                }
            }
        });

        gotoManagerMain.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
    }
}
