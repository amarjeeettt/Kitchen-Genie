import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class addRecipe extends JScrollPane {
    JPanel addMainPanel, buttonAddPanel;
    JTextField addDishTitleText;
    JLabel ingredientsLabel, procedureLabel;
    JButton saveButton, clearButton, closeButton;
    JTextArea addIngredientsTextArea, addProcedureTextArea;
    String folderPath;
    public addRecipe(String folderPath) {

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

        addMainPanel = new JPanel(null);
        addMainPanel.setPreferredSize(new Dimension(1000,850));

        buttonAddPanel = new JPanel(new GridLayout(0,3,5,0));
        buttonAddPanel.setBounds(785,25,150,50);

        saveButton = new JButton(resizedSave);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setOpaque(false);
        saveButton.addActionListener( event -> {
            if (isNotEmpty(addDishTitleText,addIngredientsTextArea,addProcedureTextArea)) {
                String filename = addDishTitleText.getText();
                String dishTitle = addDishTitleText.getText();
                String ingredientsText = addIngredientsTextArea.getText();
                String procedureText = addProcedureTextArea.getText();

                SaveFileTask task = new SaveFileTask(filename, dishTitle, ingredientsText, procedureText, folderPath);
                task.execute();

                JOptionPane.showMessageDialog(null, "Recipe Added Successfully");
                addDishTitleText.setText("");
                addIngredientsTextArea.setText("");
                addProcedureTextArea.setText("");
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
            addDishTitleText.setText("");
            addIngredientsTextArea.setText("");
            addProcedureTextArea.setText("");
        });
        closeButton = new JButton(resizedClose);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.addActionListener( event -> {
            addDishTitleText.setText("");
            addIngredientsTextArea.setText("");
            addProcedureTextArea.setText("");
            setVisible(false);
        });

        buttonAddPanel.add(closeButton);
        buttonAddPanel.add(saveButton);
        buttonAddPanel.add(clearButton);


        addDishTitleText = new JTextField();
        addDishTitleText.setFont(new Font("Century Gothic", Font.BOLD,65));
        addDishTitleText.setForeground(Color.decode("#00204A"));
        addDishTitleText.setBounds(80,50,680,100);

        ingredientsLabel = new JLabel("Ingredients");
        ingredientsLabel.setForeground(Color.decode("#8A5FC8"));
        ingredientsLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        ingredientsLabel.setBounds(80, 170, 150,100);

        addIngredientsTextArea = new JTextArea();
        addIngredientsTextArea.setForeground(Color.decode("#00204A"));
        addIngredientsTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        addIngredientsTextArea.setBounds(80,265,825, 215);

        procedureLabel = new JLabel("Procedures");
        procedureLabel.setForeground(Color.decode("#8A5FC8"));
        procedureLabel.setFont(new Font("Century Gothic",Font.BOLD,25));
        procedureLabel.setBounds(80,485,150,100);

        addProcedureTextArea = new JTextArea();
        addProcedureTextArea.setForeground(Color.decode("#00204A"));
        addProcedureTextArea.setFont(new Font("Century Gothic", Font.PLAIN,12));
        addProcedureTextArea.setBounds(80, 585,825,215);

        addMainPanel.add(addDishTitleText);
        addMainPanel.add(ingredientsLabel);
        addMainPanel.add(addIngredientsTextArea);
        addMainPanel.add(procedureLabel);
        addMainPanel.add(addProcedureTextArea);
        addMainPanel.add(buttonAddPanel);

        setViewportView(addMainPanel);
        setBounds(280,90,1000,685);

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        getViewport().setBackground(Color.decode("#E9E9E9"));
    }

    private boolean isNotEmpty(JTextField dishTitleText, JTextArea ingredientsTextArea, JTextArea procedureTextArea) {
        return !dishTitleText.getText().isEmpty() && !ingredientsTextArea.getText().isEmpty() && !procedureTextArea.getText().isEmpty();
    }

    private static class SaveFileTask extends SwingWorker<Void, Void> {
        private final String filename;
        private final String folderPath;
        private final String dishTitle;
        private final String ingredientsText;
        private final String procedureText;
        public SaveFileTask(String filename, String dishTitle, String ingredientsText, String procedureText, String folderPath) {
            this.filename = filename;
            this.dishTitle = dishTitle;
            this.ingredientsText = ingredientsText;
            this.procedureText = procedureText;
            this.folderPath = folderPath;
        }

        @Override
        protected Void doInBackground() throws Exception {
            createNewFile(filename, dishTitle, ingredientsText, procedureText, folderPath);
            return null;
        }

        @Override
        protected void done() {
            // File creation is complete
            System.out.println("File creation complete");
        }

        private void createNewFile(String filename, String dishTitle, String ingredientsText, String procedureText, String folderPath) {
            try {
                String projectPath = System.getProperty("user.dir");
                String fullPath = projectPath + File.separator + folderPath;
                String filePath = fullPath + File.separator + filename + ".txt";

                File file = new File(filePath);
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                bufferedWriter.write(dishTitle);
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.write(ingredientsText);
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.write("**procedure**");
                bufferedWriter.newLine();
                bufferedWriter.write(procedureText);
                bufferedWriter.newLine();
                bufferedWriter.newLine();

                bufferedWriter.close();
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}
