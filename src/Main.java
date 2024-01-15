import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Objects;


public class Main {
    JPanel topPanel, leftPanel, leftButtonPanel;
    JButton addButton, editButton, deleteButton, viewButton, helpButton;
    JLabel labelText;
    String folderPath = "/src/recipe";
    static int fileCount;
    public Main() {

        ImageIcon Icon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/appLogo.png")));
        Image appLogo = Icon.getImage().getScaledInstance(40,20,Image.SCALE_SMOOTH);

        JFrame frame = new JFrame("Kitchen Genie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(appLogo);
        frame.setSize(1280,800);
        frame.getContentPane().setBackground(Color.decode("#E9E9E9"));
        frame.setLayout(null);

        mainInterface mainInterface = new mainInterface();
        frame.getContentPane().add(mainInterface);

        addRecipe addRecipe = new addRecipe(folderPath);
        frame.getContentPane().add(addRecipe);
        addRecipe.setVisible(false);

        editButtonPanel editButtonPanel = new editButtonPanel(folderPath, frame);
        frame.getContentPane().add(editButtonPanel);
        editButtonPanel.setVisible(false);

        viewButtonPanel viewButtonPanel = new viewButtonPanel(folderPath, frame);
        frame.getContentPane().add(viewButtonPanel);
        viewButtonPanel.setVisible(false);

        deleteButtonPanel deleteButtonPanel = new deleteButtonPanel(folderPath, frame);
        frame.getContentPane().add(deleteButtonPanel);
        deleteButtonPanel.setVisible(false);

        helpPanel helpPanel = new helpPanel(frame);
        frame.getContentPane().add(helpPanel);
        helpPanel.setVisible(false);

        topPanel = new JPanel(null);
        topPanel.setBackground(Color.decode("#18072B"));
        topPanel.setBounds(0,0,1280,90);

        leftPanel = new JPanel(null);
        leftPanel.setBackground(Color.decode("#FCFCFC"));
        leftPanel.setBounds(0,0,280,800);

        labelText = new JLabel("Kitchen Genie.");
        labelText.setHorizontalAlignment(JLabel.CENTER);
        labelText.setForeground(Color.decode("#18072B"));
        labelText.setFont(new Font("Century Gothic",Font.BOLD,25));
        labelText.setBounds(35,25,200,40);
        leftPanel.add(labelText);

        leftButtonPanel = new JPanel(new GridLayout(4,0,0,25));
        leftButtonPanel.setOpaque(false);
        leftButtonPanel.setBounds(35,115,200,625);

        addButton = new JButton("Add Recipe");
        addButton.setFont(new Font("Century Gothic", Font.BOLD,15));
        addButton.setBackground(Color.decode("#E8DFF4"));
        addButton.setForeground(Color.decode("#AD8FD9"));
        addButton.setBorderPainted(false);
        addButton.addActionListener( event -> {
            addRecipe.setVisible(true);
            helpPanel.setVisible(false);
            mainInterface.setVisible(false);
            editButtonPanel.setVisible(false);
            viewButtonPanel.setVisible(false);
            deleteButtonPanel.setVisible(false);
        });

        editButton = new JButton("Edit Recipe");
        editButton.setFont(new Font("Century Gothic", Font.BOLD,15));
        editButton.setBackground(Color.decode("#E8DFF4"));
        editButton.setForeground(Color.decode("#AD8FD9"));
        editButton.setBorderPainted(false);
        editButton.addActionListener( event -> {
            if (countFiles(folderPath) <= 0) { JOptionPane.showMessageDialog(frame,"An Error Occurred! Please Add a Recipe First"); }
            else {
                editButtonPanel.setVisible(true);
                addRecipe.setVisible(false);
                mainInterface.setVisible(false);
                viewButtonPanel.setVisible(false);
                helpPanel.setVisible(false);
                deleteButtonPanel.setVisible(false);
            }
        });

        viewButton = new JButton("View Recipe");
        viewButton.setFont(new Font("Century Gothic", Font.BOLD,15));
        viewButton.setBackground(Color.decode("#E8DFF4"));
        viewButton.setForeground(Color.decode("#AD8FD9"));
        viewButton.setBorderPainted(false);
        viewButton.addActionListener( event -> {
            if (countFiles(folderPath) <= 0) { JOptionPane.showMessageDialog(frame,"An Error Occurred! Please Add a Recipe First"); }
            else {
                viewButtonPanel.setVisible(true);
                addRecipe.setVisible(false);
                mainInterface.setVisible(false);
                editButtonPanel.setVisible(false);
                helpPanel.setVisible(false);
                deleteButtonPanel.setVisible(false);
            }
        });

        deleteButton = new JButton("Delete Recipe");
        deleteButton.setFont(new Font("Century Gothic", Font.BOLD,15));
        deleteButton.setBackground(Color.decode("#E8DFF4"));
        deleteButton.setForeground(Color.decode("#AD8FD9"));
        deleteButton.setBorderPainted(false);
        deleteButton.addActionListener( event -> {
            if (countFiles(folderPath) <= 0) { JOptionPane.showMessageDialog(frame,"An Error Occurred! Please Add a Recipe First"); }
            else {
                deleteButtonPanel.setVisible(true);
                viewButtonPanel.setVisible(false);
                addRecipe.setVisible(false);
                helpPanel.setVisible(false);
                mainInterface.setVisible(false);
                editButtonPanel.setVisible(false);
            }
        });

        helpButton = new JButton("?");
        helpButton.setBounds(1150,10,65,65);
        helpButton.setFont(new Font("Century Gothic", Font.BOLD,40));
        helpButton.setForeground(Color.decode("#E9E9E9"));
        helpButton.setBorderPainted(false);
        helpButton.setFocusPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setOpaque(false);
        helpButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                helpPanel.setVisible(true);
                addRecipe.setVisible(false);
                mainInterface.setVisible(false);
                editButtonPanel.setVisible(false);
                viewButtonPanel.setVisible(false);
                deleteButtonPanel.setVisible(false);
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) { helpButton.setForeground(Color.decode("#C471ED")); }
            @Override
            public void mouseExited(MouseEvent e) { helpButton.setForeground(Color.decode("#E9E9E9")); }
        });
        topPanel.add(helpButton);

        leftButtonPanel.add(addButton);
        leftButtonPanel.add(editButton);
        leftButtonPanel.add(viewButton);
        leftButtonPanel.add(deleteButton);

        leftPanel.add(leftButtonPanel);

        frame.getContentPane().add(leftPanel);
        frame.getContentPane().add(topPanel);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static int countFiles(String folderPath) {
        String projectPath = System.getProperty("user.dir");
        String fullPath = projectPath + File.separator + folderPath;

        File folder = new File(fullPath);
        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile()) {
                fileCount++;
            }
        }
        return fileCount;
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Main();
    }
}