import java.awt.*;
import javax.swing.*;

public class SeparatorListCellRenderer extends DefaultListCellRenderer {

    private static final Color SEPARATOR_COLOR = Color.GRAY;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
                index, isSelected, cellHasFocus);

        // 첫 번째 레코드가 아니고 이전 레코드와 다른 값이면 구분선 추가
        if (index > 0 && !value.equals(list.getModel().getElementAt(index - 1))) {
            label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, SEPARATOR_COLOR));
        } else {
            label.setBorder(null);
        }

        return label;
    }
}
