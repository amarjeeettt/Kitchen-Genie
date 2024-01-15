import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import javax.swing.*;

public class editRecipe extends JScrollPane {
    JPanel editMainPanel, buttonEditPanel;
    JTextField editDishTitleText;
    JLabel ingredientsLabel, procedureLabel;
    JButton saveButton, clearButton, closeButton;
    JTextArea editIngredientsTextArea, editProcedureTextArea;
    String folderPath, fullPath, fileName;

    public editRecipe(String folderPath) {

        ImageIcon saveIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/saveIcon.png")));
        Image save = saveIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        ImageIcon resizedSave = new ImageIcon(save);

        ImageIcon clearIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/clearIcon.png")));
        Image clear = clearIcon.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
        ImageIcon resizedClear = new ImageIcon(clear);

        ImageIcon closeIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/img/closeIcon.png")));
        Image close = closeIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
        ImageIcon resizedClose = new ImageIcon(close);

        this.folderPath = folderPath;

        editMainPanel = new JPanel(null);
        editMainPanel.setPreferredSize(new Dimension(1000,850));

        buttonEditPanel = new JPanel(new GridLayout(0,3,5,0));
        buttonEditPanel.setBounds(785,25,150,50);

        saveButton = new JButton(resizedSave);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(false);
        saveButton.addActionListener( event -> {
            if (isNotEmpty(editDishTitleText,editIngredientsTextArea,editProcedureTextArea)) {
                String filePath = fileNameLocation(fullPath,fileName);
                String dishTitle = editDishTitleText.getText();
                String ingredientsText = editIngredientsTextArea.getText();
                String procedureText = editProcedureTextArea.getText();

                editFileTask task = new editFileTask(filePath, dishTitle, ingredientsText, procedureText);
                task.execute();

                JOptionPane.showMessageDialog(null, "Recipe Edited Successfully");
                editDishTitleText.setText("");
                editDishTitleText.setText("");
                editDishTitleText.setText("");
                setVisible(false);
            }
            else {
                JOptionPane.showMessageDialog(null, "All fields are required!");
            }

        });
        clearButton = new JButton(resizedClear);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);
        clearButton.setContentAreaFilled(false);
        clearButton.setOpaque(false);
        clearButton.addActionListener( event -> {
            editDishTitleText.setText("");
            editIngredientsTextArea.setText("");
            editProcedureTextArea.setText("");
        });
        closeButton = new JButton(resizedClose);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.addActionListener( event -> {
            editDishTitleText.setText("");
            editIngredientsTextArea.setText("");
            editProcedureTextArea.setText("");
            setVisible(false);
        });

        buttonEditPanel.add(closeButton);
        buttonEditPanel.add(saveButton);
        buttonEditPanel.add(clearButton);


        editDishTitleText = new JTextField();
        editDishTitleText.setFont(new Font("Century Gothic", Font.BOLD,65));
        editDishTitleText.setForeground(Color.decode("#00204A"));
        editDishTitleText.setBounds(80,50,680,100);

        ingredientsLabel = new JLabel("Ingredients");
        ingredientsLabel.setForeground(Color.decode("#363B64"));
        ingredientsLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        ingredientsLabel.setBounds(80, 170, 150,100);

        editIngredientsTextArea = new JTextArea();
        editIngredientsTextArea.setForeground(Color.decode("#00204A"));
        editIngredientsTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        editIngredientsTextArea.setBounds(80,265,825, 215);

        procedureLabel = new JLabel("Procedures");
        procedureLabel.setForeground(Color.decode("#363B64"));
        procedureLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        procedureLabel.setBounds(80,485,150,100);

        editProcedureTextArea = new JTextArea();
        editIngredientsTextArea.setForeground(Color.decode("#00204A"));
        editIngredientsTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        editProcedureTextArea.setBounds(80, 585,825,215);

        editMainPanel.add(editDishTitleText);
        editMainPanel.add(ingredientsLabel);
        editMainPanel.add(editIngredientsTextArea);
        editMainPanel.add(procedureLabel);
        editMainPanel.add(editProcedureTextArea);
        editMainPanel.add(buttonEditPanel);

        setViewportView(editMainPanel);
        setBounds(280,90,1000,685);

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getViewport().setBackground(Color.decode("#E9E9E9"));
    }
    private boolean isNotEmpty(JTextField dishTitleText, JTextArea ingredientsTextArea, JTextArea procedureTextArea) {
        return !dishTitleText.getText().isEmpty() && !ingredientsTextArea.getText().isEmpty() && !procedureTextArea.getText().isEmpty();
    }
    public String fileNameLocation(String fullPath, String filename) {
        this.fullPath = fullPath;
        this.fileName = filename;

        return fullPath + File.separator + filename + ".txt";
    }
    private static class editFileTask extends SwingWorker<Void, Void> {
        private final String filePath;
        private final String dishTitle;
        private final String ingredientsText;
        private final String procedureText;
        public editFileTask(String filePath, String dishTitle, String ingredientsText, String procedureText) {
            this.filePath = filePath;
            this.dishTitle = dishTitle;
            this.ingredientsText = ingredientsText;
            this.procedureText = procedureText;
        }

        @Override
        protected Void doInBackground() throws Exception {
            createNewFile(filePath, dishTitle, ingredientsText, procedureText);
            return null;
        }

        @Override
        protected void done() {
            // File creation is complete
            System.out.println("File creation complete");
        }

        private void createNewFile(String filePath, String dishTitle, String ingredientsText, String procedureText) {
            String newFileName = dishTitle + ".txt";
            Path source = Paths.get(filePath);
            String newContent = dishTitle + "\n\n" + ingredientsText + "\n\n" + "**procedure**" + "\n" + procedureText;
            try {
                Files.writeString(source, newContent);
                Files.move(source, source.resolveSibling(newFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    public void setDish(String fullPath, String fileName) {
        this.fullPath = fullPath;
        this.fileName = fileName;

        String filePath = fullPath + File.separator + fileName + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();

            editDishTitleText.setText(line);
            editDishTitleText.revalidate();
            editDishTitleText.repaint();
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
            editIngredientsTextArea.setText(sb.toString().trim());
            editIngredientsTextArea.revalidate();
            editIngredientsTextArea.repaint();
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
            editProcedureTextArea.setText(sb.toString().trim());
            editProcedureTextArea.revalidate();
            editProcedureTextArea.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
