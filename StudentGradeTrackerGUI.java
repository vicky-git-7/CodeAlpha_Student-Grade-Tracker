import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    private String name;
    private int grade;

    public Student(String name, int grade) {
        this.name = name;
        this.grade = grade;
    }

    public String getName() { return name; }
    public int getGrade() { return grade; }
}

public class StudentGradeTrackerGUI extends JFrame {

    private ArrayList<Student> students = new ArrayList<>();
    private DefaultTableModel tableModel;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table Setup
        tableModel = new DefaultTableModel(new String[] {"Student Name", "Grade"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Inputs
        JTextField nameField = new JTextField(15);
        JTextField gradeField = new JTextField(5);
        JButton addButton = new JButton("Add Student");
        JButton summaryButton = new JButton("Show Summary");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);
        inputPanel.add(summaryButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Add Student Action
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String gradeText = gradeField.getText();

            if (name.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int grade = Integer.parseInt(gradeText);
                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(this, "Grade must be between 0-100", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Student student = new Student(name, grade);
                students.add(student);
                tableModel.addRow(new Object[] {name, grade});
                nameField.setText("");
                gradeField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade entered!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Summary Report Action
        summaryButton.addActionListener(e -> showSummaryReport());

        setVisible(true);
    }

    private void showSummaryReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No student records available!");
            return;
        }

        int sum = 0;
        int highest = Integer.MIN_VALUE;
        int lowest = Integer.MAX_VALUE;
        String topStudent = "";
        String lowStudent = "";

        for (Student s : students) {
            int g = s.getGrade();
            sum += g;

            if (g > highest) {
                highest = g;
                topStudent = s.getName();
            }

            if (g < lowest) {
                lowest = g;
                lowStudent = s.getName();
            }
        }

        double avg = (double) sum / students.size();

        String message = "Total Students: " + students.size() +
                "\nAverage Score : " + avg +
                "\nHighest Score : " + highest + " (" + topStudent + ")" +
                "\nLowest Score  : " + lowest + " (" + lowStudent + ")";

        JOptionPane.showMessageDialog(this, message, "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTrackerGUI::new);
    }
}
