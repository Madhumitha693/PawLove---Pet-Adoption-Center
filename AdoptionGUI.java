import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AdoptionGUI extends JFrame {
    private AdoptionCenter center;
    private JPanel adoptedPetsPanel = new JPanel();
    private JPanel usersListPanel = new JPanel();

    private User currentUser = null;
    private Pet selectedPet = null;

    private DefaultListModel<Pet> petListModel = new DefaultListModel<>();
    private JList<Pet> petJList = new JList<>(petListModel);
    private JTextArea historyArea = new JTextArea();

    private JComboBox<String> petTypeFilter;

    public AdoptionGUI(AdoptionCenter center) {
        this.center = center;

        setTitle("PAWLOVE - Pet Adoption Center");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Register", buildRegisterTab());
        tabs.add("View Pets", buildViewPetsTab());
        tabs.add("Apply", buildApplyTab());
        tabs.add("Adopted Pets", buildAdoptedPetsTab());
        tabs.add("Registered Users", buildRegisteredUsersTab());

        add(tabs);
        setVisible(true);
    }

    // ------------------ TAB: Registered Users --------------------
    private JComponent buildRegisteredUsersTab() {
        usersListPanel.setLayout(new BoxLayout(usersListPanel, BoxLayout.Y_AXIS));
        usersListPanel.setBorder(BorderFactory.createTitledBorder("List of Registered Users"));

        refreshUserList();
        return new JScrollPane(usersListPanel);
    }

    private void refreshUserList() {
        usersListPanel.removeAll();
        for (User user : center.getUsers()) {
            JTextArea info = new JTextArea(
                "Name: " + user.name + "\n" +
                "Contact: " + user.contact + "\n" +
                "Preferences: " + user.preferences + "\n" +
                "Living: " + user.livingArrangements
            );
            info.setEditable(false);
            info.setFont(new Font("Arial", Font.PLAIN, 14));
            info.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            info.setBackground(new Color(245, 245, 245));
            info.setPreferredSize(new Dimension(600, 80));

            usersListPanel.add(info);
            usersListPanel.add(Box.createVerticalStrut(10));
        }
        usersListPanel.revalidate();
        usersListPanel.repaint();
    }

    // ------------------ TAB: Register --------------------
    private JPanel buildRegisterTab() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("User Registration"));

        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField preferencesField = new JTextField();
        JTextField livingField = new JTextField();
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Pet Preferences:"));
        panel.add(preferencesField);
        panel.add(new JLabel("Living Arrangements:"));
        panel.add(livingField);
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String contact = contactField.getText().trim();
            String pref = preferencesField.getText().trim();
            String live = livingField.getText().trim();

            if (name.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.");
                return;
            }

            currentUser = new User(name, contact, pref, live);
            center.registerUser(currentUser);
            JOptionPane.showMessageDialog(this, "Registration successful!\nWelcome, " + name);
            refreshUserList();
        });

        return panel;
    }

    // ------------------ TAB: View Pets --------------------
    private JPanel buildViewPetsTab() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Available Pets"));

        petTypeFilter = new JComboBox<>(new String[]{"All", "Dog", "Cat", "Bird"});
        mainPanel.add(petTypeFilter, BorderLayout.NORTH);

        JPanel petCardPanel = new JPanel();
        petCardPanel.setLayout(new BoxLayout(petCardPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(petCardPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        petTypeFilter.addActionListener(e -> {
            showFilteredPets(petCardPanel);
        });

        showFilteredPets(petCardPanel);
        return mainPanel;
    }

    private void showFilteredPets(JPanel petCardPanel) {
        petCardPanel.removeAll();
        String filter = (String) petTypeFilter.getSelectedItem();

        for (Pet pet : center.getAvailablePets()) {
            String type = pet.getClass().getSimpleName();
            if ("All".equals(filter) || filter.equalsIgnoreCase(type)) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                card.setPreferredSize(new Dimension(600, 150));

                JLabel imageLabel;
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource(pet.getImagePath()));
                    Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(scaled));
                } catch (Exception ex) {
                    imageLabel = new JLabel("No Image");
                }

                JTextArea info = new JTextArea(
                    "Name: " + pet.name + "\n" +
                    "Breed: " + pet.breed + "\n" +
                    "Age: " + pet.age + " years"
                );
                info.setEditable(false);
                info.setFont(new Font("Arial", Font.PLAIN, 14));

                JButton selectButton = new JButton("Select");
                selectButton.addActionListener(e -> {
                    selectedPet = pet;
                    JOptionPane.showMessageDialog(this, pet.name + " selected for adoption!");
                });

                JPanel rightPanel = new JPanel(new BorderLayout());
                rightPanel.add(info, BorderLayout.CENTER);
                rightPanel.add(selectButton, BorderLayout.SOUTH);

                card.add(imageLabel, BorderLayout.WEST);
                card.add(rightPanel, BorderLayout.CENTER);

                petCardPanel.add(card);
                petCardPanel.add(Box.createVerticalStrut(10));
            }
        }

        petCardPanel.revalidate();
        petCardPanel.repaint();
    }

    // ------------------ TAB: Apply --------------------
    private JPanel buildApplyTab() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createTitledBorder("Adoption Application"));

    JButton applyButton = new JButton("Apply to Adopt Selected Pet");
    JButton cancelButton = new JButton("Cancel Adoption");
    JTextArea adoptionDetails = new JTextArea();
    adoptionDetails.setEditable(false);
    adoptionDetails.setFont(new Font("Arial", Font.PLAIN, 14));
    JScrollPane scroll = new JScrollPane(adoptionDetails);

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(applyButton);
    buttonPanel.add(cancelButton);

    panel.add(buttonPanel, BorderLayout.NORTH);
    panel.add(scroll, BorderLayout.CENTER);

    applyButton.addActionListener(e -> {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please register first.");
            return;
        }

        if (selectedPet == null) {
            JOptionPane.showMessageDialog(this, "Please select a pet from 'View Pets' tab.");
            return;
        }

        center.applyForAdoption(currentUser, selectedPet);
        JOptionPane.showMessageDialog(this, "Application submitted!\n" + selectedPet.adoptionMessage());

        adoptionDetails.setText(
            "Adopted Pet: " + selectedPet.name + " (" + selectedPet.breed + ")\n" +
            "Age: " + selectedPet.age + "\n" +
            "Message: " + selectedPet.adoptionMessage()
        );

        selectedPet = null;
        updatePetList();
        updateHistory();
        refreshAdoptedPets();
    });

    cancelButton.addActionListener(e -> {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "No user registered.");
            return;
        }

        // Cancel latest adoption by this user
        Application toRemove = null;
        for (Application app : center.getApplications()) {
            if (app.getUser() == currentUser && app.getPet().isAdopted) {
                toRemove = app;
            }
        }

        if (toRemove != null) {
            toRemove.getPet().cancelAdoption();
            center.getApplications().remove(toRemove);

            adoptionDetails.setText("");
            JOptionPane.showMessageDialog(this, "Adoption cancelled.");

            updatePetList();
            updateHistory();
            refreshAdoptedPets();
        } else {
            JOptionPane.showMessageDialog(this, "You have no current adoptions to cancel.");
        }
    });

    return panel;
}


    private void updateHistory() {
        historyArea.setText("");
        for (Application app : center.getApplications()) {
            historyArea.append(app.getSummary() + "\n");
        }
    }

    private void updatePetList() {
        petListModel.clear();
        String filter = (String) petTypeFilter.getSelectedItem();
        for (Pet pet : center.getAvailablePets()) {
            if ("All".equals(filter) || pet.getClass().getSimpleName().equalsIgnoreCase(filter)) {
                petListModel.addElement(pet);
            }
        }
    }

    // ------------------ TAB: Adopted Pets --------------------
    private JComponent buildAdoptedPetsTab() {
    adoptedPetsPanel.setBorder(BorderFactory.createTitledBorder("Adopted Pets Info"));
    refreshAdoptedPets();
    JScrollPane scrollPane = new JScrollPane(adoptedPetsPanel);
    return scrollPane;
}

private void refreshAdoptedPets() {
    adoptedPetsPanel.removeAll();
    adoptedPetsPanel.setLayout(new BoxLayout(adoptedPetsPanel, BoxLayout.Y_AXIS));
     if (center.getApplications().isEmpty()) {
        JLabel label = new JLabel("No pets have been adopted yet.");
        label.setFont(new Font("Arial", Font.ITALIC, 16));
        label.setForeground(Color.GRAY);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        adoptedPetsPanel.add(Box.createVerticalStrut(50));
        adoptedPetsPanel.add(label);
        adoptedPetsPanel.revalidate();
        adoptedPetsPanel.repaint();
        return;
    }
    for (Application app : center.getApplications()) {
        Pet pet = app.getPet();
        User user = app.getUser();

        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setPreferredSize(new Dimension(600, 130));

        // Pet Image
        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(pet.getImagePath()));
            Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaled));
        } catch (Exception ex) {
            imageLabel = new JLabel("No Image");
        }

        // Info Area
        JTextArea info = new JTextArea(
            "Pet: " + pet.name + " (" + pet.breed + ")\n" +
            "Age: " + pet.age + "\n" +
            "Adopted By: " + user.name + "\n" +
            "Message: " + pet.adoptionMessage()
        );
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 14));

        // Cancel Button
        JButton cancelButton = new JButton("Cancel Adoption");
        cancelButton.addActionListener(e -> {
            pet.cancelAdoption();
            center.getApplications().remove(app);
            JOptionPane.showMessageDialog(this, "Adoption canceled for " + pet.name);
            updatePetList();
            updateHistory();
            refreshAdoptedPets(); // refresh UI
        });

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(info, BorderLayout.CENTER);
        centerPanel.add(cancelButton, BorderLayout.SOUTH);

        card.add(imageLabel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);

        adoptedPetsPanel.add(card);
        adoptedPetsPanel.add(Box.createVerticalStrut(10));
    }

    adoptedPetsPanel.revalidate();
    adoptedPetsPanel.repaint();
}
}
