import java.awt.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class helpPanel extends JPanel {
    JLabel helpLabel;
    JTextPane helpText;
    JButton closeButton;
    public helpPanel(JFrame frame) {

        ImageIcon closeIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/closeIcon.png")));
        Image close = closeIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        ImageIcon resizedClose = new ImageIcon(close);

        mainInterface mainInterface = new mainInterface();
        frame.getContentPane().add(mainInterface);
        mainInterface.setVisible(false);

        setLayout(null);
        setBounds(280,90,1000,685);
        setBackground(Color.decode("#E9E9E9"));

        closeButton = new JButton(resizedClose);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.setBounds(870,30,50,50);
        closeButton.addActionListener( event -> {
            setVisible(false);
            mainInterface.setVisible(true);
        });

        helpLabel = new JLabel("Help");
        helpLabel.setBounds(60,30,150,50);
        helpLabel.setForeground(Color.decode("#8A5FC8"));
        helpLabel.setFont(new Font("Century Gothic",Font.BOLD,35));
        helpText = new JTextPane();
        helpText.setOpaque(false);
        helpText.setBounds(60,120,865,180);

        helpText.setFont(new Font("Century Gothic", Font.BOLD,15));

        StyledDocument style = helpText.getStyledDocument();
        SimpleAttributeSet align= new SimpleAttributeSet();
        StyleConstants.setAlignment(align, StyleConstants.ALIGN_JUSTIFIED);
        style.setParagraphAttributes(0, style.getLength(), align, false);

        try {
            style.insertString(style.getLength(), "Add Recipe: To add a new recipe, click on the 'Add Recipe' button and fill in the required details.\n\n", null);
            style.insertString(style.getLength(), "Edit Recipe: To edit a recipe, click on the 'Edit Recipe' button and modify the required details.\n\n", null);
            style.insertString(style.getLength(), "View Recipe: To view a recipe, click on the 'View Recipe' button and simply click the 'Recipe Title' button.\n\n", null);
            style.insertString(style.getLength(), "Delete Recipe: To delete a recipe, click on the 'Delete Recipe' and simply click the 'Recipe Title' button. To\n\ndelete all recipe simply click the 'Delete All' button.", null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        add(helpLabel);
        add(helpText);
        add(closeButton);

    }
}
