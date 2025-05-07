package presentation;

import javax.swing.*;
import java.awt.*;
import DAO.GestionEmployeeJDBC;
import DAO.IGestionEmployee;
import entities.Employee;

public class MessageWindow extends JFrame {
    private JTextArea conversationArea = new JTextArea(15, 50);
    private JTextField messageField = new JTextField(40);
    private JButton sendButton = new JButton("Send");

    private int employeeId;
    private IGestionEmployee gestion = new GestionEmployeeJDBC();

    public MessageWindow(int employeeId) {
        super("Conversation with Employee");

        this.employeeId = employeeId;

     
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 400);

        conversationArea.setEditable(false);
        conversationArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(conversationArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new FlowLayout());
        messagePanel.add(messageField);
        messagePanel.add(sendButton);
        add(messagePanel, BorderLayout.SOUTH);

        loadConversation();

        sendButton.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageField.setText("");
            }
        });

        setVisible(true);
    }

    private void loadConversation() {
        conversationArea.setText("Welcome to the conversation with Employee ID: " + employeeId + "\n");
    }

    private void sendMessage(String message) {
        conversationArea.append("You: " + message + "\n");
    }
}
