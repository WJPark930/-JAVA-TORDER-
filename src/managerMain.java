import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class managerMain extends JFrame implements ActionListener {

    public managerMain() {
        initialize();
    }

    private void initialize() {
        setTitle("관리자 모드");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 248, 220));
        getContentPane().add(panel);
        panel.setLayout(null);
        
        JButton addMenu = new JButton("메뉴 추가");
        addMenu.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 20));
        addMenu.setBackground(new Color(222, 184, 135));
        addMenu.setBounds(45, 31, 194, 143);
        panel.add(addMenu);
        
        JButton removeMenu = new JButton("메뉴 삭제");
        removeMenu.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 20));
        removeMenu.setBackground(new Color(222, 184, 135));
        removeMenu.setBounds(285, 31, 208, 143);
        panel.add(removeMenu);
        
        JButton updateMenu = new JButton("메뉴 수정");
        updateMenu.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 20));
        updateMenu.setBackground(new Color(222, 184, 135));
        updateMenu.setBounds(45, 221, 194, 129);
        panel.add(updateMenu);
        
        JButton salesButton = new JButton("매출 확인");
        salesButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 20));
        salesButton.setBackground(new Color(222, 184, 135));
        salesButton.setBounds(285, 220, 208, 130);
        panel.add(salesButton);
        
        JButton gotoInitialScreen = new JButton("처음으로");
        gotoInitialScreen.setBackground(new Color(222, 184, 135));
        gotoInitialScreen.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 12));
        gotoInitialScreen.setBounds(536, 31, 125, 34);
        panel.add(gotoInitialScreen);
        
        gotoInitialScreen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitialScreen initialScreen = new InitialScreen();
                initialScreen.setVisible(true);
                dispose();
            }
        });
        
        
        addMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMenu addMenuDialog = new AddMenu(managerMain.this);
                addMenuDialog.setVisible(true);
            }
        });
        
        
        updateMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateMenu updateMenuDialog = new UpdateMenu(managerMain.this);
                updateMenuDialog.setVisible(true);
            }
        });


        removeMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteMenu deleteMenuDialog = new DeleteMenu(managerMain.this);
                deleteMenuDialog.setVisible(true);
            }
        });

       
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                int totalSales = CardMain.getTotalSales() + PaymentFrame.getTotalSales();
                JOptionPane.showMessageDialog(managerMain.this, "오늘의 매출은 " + totalSales + "원 입니다.");
            }
        });

        
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                int totalSales = CardMain.getTotalSales() + PaymentFrame.getTotalSales();
                int cardSales = CardMain.getTotalSales();
                int cashSales = PaymentFrame.getTotalSales();
                JOptionPane.showMessageDialog(managerMain.this, 
                    "총 매출: " + totalSales + "원\n" +
                    "카드 매출: " + cardSales + "원\n" +
                    "현금 매출: " + cashSales + "원");
            }
        });


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}
