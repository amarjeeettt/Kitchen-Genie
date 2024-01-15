import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;
import javax.swing.*;

public class viewButtonPanel extends JPanel {
    JPanel recipeButtonPanel;
    JButton refreshButton, recipeButton;
    String folderPath;
    public String filename;

    public viewButtonPanel(String folderPath, JFrame frame) {

        ImageIcon refreshIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/refreshIcon.png")));
        Image refresh = refreshIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon resizedRefresh = new ImageIcon(refresh);

        this.folderPath = folderPath;
        String projectPath = System.getProperty("user.dir");
        String fullPath = projectPath + File.separator + folderPath;

        viewRecipe viewRecipe = new viewRecipe(fullPath);
        frame.getContentPane().add(viewRecipe);
        viewRecipe.setVisible(false);

        setLayout(null);
        setPreferredSize(new Dimension(1000,685));

        recipeButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        recipeButtonPanel.setBackground(Color.decode("#FCFCFC"));
        recipeButtonPanel.setBounds(70,30,560,600);

        refreshButton = new JButton(resizedRefresh);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setContentAreaFilled(false);
        refreshButton.setOpaque(false);
        refreshButton.setBounds(870,30,50,50);
        refreshButton.addActionListener( event -> {
            recipeButtonPanel.removeAll();

            File folder = new File(fullPath);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String filename = file.getName();
                        int dotIndex = filename.lastIndexOf(".");
                        if (dotIndex > 0) {
                            filename = filename.substring(0, dotIndex);
                        }
                        JButton recipeButton = new JButton(filename);
                        recipeButton.setFont(new Font("Century Gothic", Font.BOLD,12));
                        recipeButton.setBackground(Color.decode("#E8DFF4"));
                        recipeButton.setForeground(Color.decode("#AD8FD9"));
                        recipeButton.setBorderPainted(false);
                        recipeButton.setPreferredSize(new Dimension(180,45));
                        String finalFilename = filename;
                        recipeButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                viewRecipe.setVisible(true);
                                viewRecipe.setDish(fullPath,finalFilename);
                                viewRecipe.setIngredients(fullPath,finalFilename);
                                viewRecipe.setProcedure(fullPath,finalFilename);
                            }

                        });
                        recipeButtonPanel.add(recipeButton);
                    }
                }
            }
            recipeButtonPanel.revalidate();
            recipeButtonPanel.repaint();
        });

        File folder = new File(fullPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filename = file.getName();
                    int dotIndex = filename.lastIndexOf(".");
                    if (dotIndex > 0) {
                        filename = filename.substring(0, dotIndex);
                    }
                    recipeButton = new JButton(filename);
                    recipeButton.setFont(new Font("Century Gothic", Font.BOLD,12));
                    recipeButton.setBackground(Color.decode("#E8DFF4"));
                    recipeButton.setForeground(Color.decode("#AD8FD9"));
                    recipeButton.setBorderPainted(false);
                    recipeButton.setPreferredSize(new Dimension(180,45));
                    String finalFilename = filename;
                    recipeButton.addActionListener( event -> {
                        setVisible(false);
                        viewRecipe.setVisible(true);
                        viewRecipe.setDish(fullPath,finalFilename);
                        viewRecipe.setIngredients(fullPath,finalFilename);
                        viewRecipe.setProcedure(fullPath,finalFilename);
                    });
                    recipeButtonPanel.add(recipeButton);
                }
            }
        }

        setBounds(280,90,1000,685);
        setBackground(Color.decode("#E9E9E9"));
        add(recipeButtonPanel);
        add(refreshButton);
    }
}
