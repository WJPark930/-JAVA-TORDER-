import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UpdateMenu extends JDialog {
    private JComboBox<String> updatecomboBox; 
    private JTextField updatedMenuNameField;
    private JTextField updatedPriceField;
    private JButton updateButton;
    private JButton gotoManagerMain;
    private JTextField updatemenuField;
    private JTextField updatepriceField;
    private JTextField updatecategoryField;
    private JLabel updateMenuLabel;
    private JLabel updatePriceLabel;
    private JLabel updateCategoryLabel;
    private JLabel changeMenuLabel;
    private JTextField changemenutField;
    private JLabel lblNewLabel;

    private MenuDao mdao;
    private JLabel updateMenuLabel_1;

    public UpdateMenu(Frame parent) {
        super(parent, "메뉴 수정", true);
        getContentPane().setBackground(new Color(255, 248, 220));
        getContentPane().setLayout(null);
        setSize(445,334);
        setLocationRelativeTo(null);

        updatemenuField = new JTextField();
        updatemenuField.setColumns(10);
        updatemenuField.setBounds(217, 110, 116, 21);
        getContentPane().add(updatemenuField);

        updatepriceField = new JTextField();
        updatepriceField.setColumns(10);
        updatepriceField.setBounds(217, 144, 116, 21);
        getContentPane().add(updatepriceField);

        updatecategoryField = new JTextField();
        updatecategoryField.setColumns(10);
        updatecategoryField.setBounds(217, 175, 116, 21);
        getContentPane().add(updatecategoryField);

        // 버튼 초기화
        updateButton = new JButton("수정 하기");
        updateButton.setBackground(new Color(222, 184, 135));
        updateButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        updateButton.setBounds(89, 236, 97, 23);
        getContentPane().add(updateButton);

        gotoManagerMain = new JButton("뒤로 가기");
        gotoManagerMain.setBackground(new Color(222, 184, 135));
        gotoManagerMain.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        gotoManagerMain.setBounds(246, 236, 97, 23);
        getContentPane().add(gotoManagerMain);

        updateMenuLabel = new JLabel("변경될 메뉴 : ");
        updateMenuLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 13));
        updateMenuLabel.setBackground(new Color(255, 248, 220));
        updateMenuLabel.setBounds(113, 76, 96, 24);
        getContentPane().add(updateMenuLabel);

        updatecomboBox = new JComboBox();
        updatecomboBox.setBounds(217, 77, 116, 23);
        getContentPane().add(updatecomboBox);
        
        updatePriceLabel = new JLabel("변경될 가격 : ");
        updatePriceLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 13));
        updatePriceLabel.setBackground(new Color(255, 248, 220));
        updatePriceLabel.setBounds(113, 141, 131, 24);
        getContentPane().add(updatePriceLabel);

        updateCategoryLabel = new JLabel("변경될 카테고리 : ");
        updateCategoryLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 13));
        updateCategoryLabel.setBackground(new Color(255, 248, 220));
        updateCategoryLabel.setBounds(89, 172, 131, 24);
        getContentPane().add(updateCategoryLabel);

        changeMenuLabel = new JLabel("변경될 메뉴의 고유번호 : ");
        changeMenuLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 13));
        changeMenuLabel.setBackground(new Color(255, 248, 220));
        changeMenuLabel.setBounds(44, 206, 165, 24);
        getContentPane().add(changeMenuLabel);
        
        changemenutField = new JTextField();
        changemenutField.setColumns(10);
        changemenutField.setBounds(217, 205, 116, 21);
        getContentPane().add(changemenutField);
        
        lblNewLabel = new JLabel("수정할 메뉴를 선택 후, 아래 양식에 맞게 작성해 주세요.");
        lblNewLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(59, 20, 339, 45);
        getContentPane().add(lblNewLabel);
        
        updateMenuLabel_1 = new JLabel("변경될 메뉴명 : ");
        updateMenuLabel_1.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 13));
        updateMenuLabel_1.setBackground(new Color(255, 248, 220));
        updateMenuLabel_1.setBounds(104, 110, 116, 24);
        getContentPane().add(updateMenuLabel_1);

        initialize();
    }

    private void initialize() {

        mdao = new MenuDao();


        ArrayList<MenuBean> menuData = mdao.getAllMenu();
        for (MenuBean menu : menuData) {
            updatecomboBox.addItem(menu.getMenuname());
        }


        updatecomboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedMenu = (String) updatecomboBox.getSelectedItem();

                MenuBean selectedMenuBean = mdao.getMenuByName(selectedMenu); 
                if (selectedMenuBean != null) {
                    String menuName = selectedMenuBean.getMenuname();
                    int price = selectedMenuBean.getPrice();

                    // 가져온 정보를 텍스트 필드에 설정
                    updatemenuField.setText(menuName);
                    updatepriceField.setText(String.valueOf(price));
                } else {
                	JOptionPane.showMessageDialog(UpdateMenu.this, "텍스트 필드를 초기화 할 수 없습니다.");
                }
            }
        });



        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 선택한 메뉴의 정보 가져오기
                String selectedMenuName = (String) updatecomboBox.getSelectedItem();
                String updatedMenuName = updatemenuField.getText();
                int updatedPrice = Integer.parseInt(updatepriceField.getText());
                
                // 선택된 메뉴의 현재 정보를 가져와서 MenuBean 객체로 생성
                MenuBean selectedMenu = mdao.getMenuByName(selectedMenuName);
                if (selectedMenu != null) {
                    // 가져온 메뉴 정보를 업데이트
                    selectedMenu.setMenuname(updatedMenuName);
                    selectedMenu.setPrice(updatedPrice);
                    
                    // 업데이트 메서드 호출하여 데이터베이스에서 수정 작업 수행
                    boolean updateSuccess = mdao.updateMenu(selectedMenu);
                    if (updateSuccess) {
                        // 수정 성공 시 텍스트 필드 클리어
                        updatemenuField.setText("");
                        updatepriceField.setText("");
                       
                        JOptionPane.showMessageDialog(UpdateMenu.this, "메뉴 정보가 수정되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(UpdateMenu.this, "메뉴 정보 수정에 실패했습니다.");
                    }
                } else {
                    JOptionPane.showMessageDialog(UpdateMenu.this, "선택한 메뉴를 찾을 수 없습니다.");
                }
            }
        });


        
        
        // 뒤로 가기 버튼에 addActionListener 추가
        gotoManagerMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 이전 화면인 managerMain을 생성하여 보여줌
                managerMain managerMainScreen = new managerMain();
                managerMainScreen.setVisible(true);

                dispose();
            }
        });
    }
}
