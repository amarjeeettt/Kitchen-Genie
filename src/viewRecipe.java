import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class viewRecipe extends JScrollPane {
    JPanel viewMainPanel, buttonViewPanel;
    JTextField viewDishTitleText;
    JLabel ingredientsLabel, procedureLabel;
    JButton closeButton;
    JTextArea viewIngredientsTextArea, viewProcedureTextArea;
    String folderPath, fullPath, fileName;
    public viewRecipe(String folderPath) {

        ImageIcon closeIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/closeIcon.png")));
        Image close = closeIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        ImageIcon resizedClose = new ImageIcon(close);

        this.folderPath = folderPath;

        viewMainPanel = new JPanel(null);
        viewMainPanel.setPreferredSize(new Dimension(1000,850));

        buttonViewPanel = new JPanel(new GridLayout(0,1,5,0));
        buttonViewPanel.setBounds(860,50,50,50);

        closeButton = new JButton(resizedClose);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.addActionListener( event -> {
            setVisible(false);
        });

        buttonViewPanel.add(closeButton);

        viewDishTitleText = new JTextField();
        viewDishTitleText.setFont(new Font("Century Gothic", Font.BOLD,65));
        viewDishTitleText.setForeground(Color.decode("#00204A"));
        viewDishTitleText.setEditable(false);
        viewDishTitleText.setBorder(null);
        viewDishTitleText.setBounds(80,50,680,100);

        ingredientsLabel = new JLabel("Ingredients");
        ingredientsLabel.setForeground(Color.decode("#363B64"));
        ingredientsLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        ingredientsLabel.setBounds(80, 170, 150,100);

        viewIngredientsTextArea = new JTextArea();
        viewIngredientsTextArea.setForeground(Color.decode("#00204A"));
        viewIngredientsTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        viewIngredientsTextArea.setEditable(false);
        viewIngredientsTextArea.setBounds(80,265,825, 215);

        procedureLabel = new JLabel("Procedures");
        procedureLabel.setForeground(Color.decode("#363B64"));
        procedureLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        procedureLabel.setBounds(80,485,150,100);

        viewProcedureTextArea = new JTextArea();
        viewIngredientsTextArea.setForeground(Color.decode("#00204A"));
        viewIngredientsTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        viewProcedureTextArea.setEditable(false);
        viewProcedureTextArea.setBounds(80, 585,825,215);

        viewMainPanel.add(viewDishTitleText);
        viewMainPanel.add(ingredientsLabel);
        viewMainPanel.add(viewIngredientsTextArea);
        viewMainPanel.add(procedureLabel);
        viewMainPanel.add(viewProcedureTextArea);
        viewMainPanel.add(buttonViewPanel);

        setViewportView(viewMainPanel);
        setBounds(280,90,1000,685);

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getViewport().setBackground(Color.decode("#E9E9E9"));
    }
    public String fileNameLocate(String fullPath, String fileName) {
        this.fullPath = fullPath;
        this.fileName = fileName;

        return fullPath + File.separator + fileName + ".txt";
    }
    public void setDish(String fullPath, String fileName) {
        this.fullPath = fullPath;
        this.fileName = fileName;

        String filePath = fullPath + File.separator + fileName + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            viewDishTitleText.setText(line);
            viewDishTitleText.revalidate();
            viewDishTitleText.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setIngredients(String fullPath, String fileName) {
        this.fullPath = fullPath;
        this.fileName = fileName;

        String filePath = fullPath + File.separator + fileName + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the first two lines
            reader.readLine();
            reader.readLine();

            // Read lines until a blank newline is encountered
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            // Set the extracted contents as the text in the JTextField
            viewIngredientsTextArea.setText(sb.toString().trim());
            viewIngredientsTextArea.revalidate();
            viewIngredientsTextArea.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setProcedure(String fullPath, String fileName) {
        this.fullPath = fullPath;
        this.fileName = fileName;

        String filePath = fullPath + File.separator + fileName + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sb = new StringBuilder();
            boolean foundProcedure = false;
            String line;

            // Read lines until the end of the file, extracting contents after the line containing "procedure"
            while ((line = reader.readLine()) != null) {
                if (foundProcedure) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                } else if (line.contains("**procedure**")) {
                    foundProcedure = true;
                }
            }

            // Set the extracted contents as the text in the JTextField
            viewProcedureTextArea.setText(sb.toString().trim());
            viewProcedureTextArea.revalidate();
            viewProcedureTextArea.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
