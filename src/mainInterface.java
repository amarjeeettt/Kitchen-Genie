import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class mainInterface extends JPanel {
    JPanel imgPanel;
    JLabel imgLogo, textLabel;

    public mainInterface() {
        setLayout(null);
        setPreferredSize(new Dimension(1000,685));
        ImageIcon imgIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/mainIMGLogo.png")));

        imgLogo = new JLabel(imgIcon);
        imgLogo.setBounds(0,0,1000,685);

        setBounds(280,90,1000,685);
        setBackground(Color.decode("#E9E9E9"));

        add(imgLogo);
    }
}
