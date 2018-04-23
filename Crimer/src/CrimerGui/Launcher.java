package CrimerGui;

import javax.swing.*;

public class Launcher {
    private static void createAndShowGUI()
    {
        //Use the Java look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        JTextField location = new JTextField();
        JTextField fromDate = new JTextField();

        final JComponent[] inputs = new JComponent[] {
                new JLabel("Location:"),
                location,
                new JLabel("Date to start from:"),
                fromDate,
        };

        int result = JOptionPane.showConfirmDialog(null, inputs,
                "Crimer", JOptionPane.DEFAULT_OPTION);

        if (result == JOptionPane.OK_OPTION)
        {
            System.out.println("You entered " +
                    location.getText() + ", " +
                    fromDate.getText());
        } else
        {
            System.out.println("User cancelled / closed the dialog, result = " + result);
        }
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(Launcher::createAndShowGUI);
    }
}