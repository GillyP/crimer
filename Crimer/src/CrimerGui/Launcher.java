/**
 * Launches crimer
 *
 * @author Justin Varley, Alexander "Lex" Adams
 */

package CrimerGui;

import javax.swing.*;

public class Launcher {

    private static String location;
    private static String fromDate;

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(Launcher::createAndShowGUI);
    }

    private static void createAndShowGUI() {
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

        JTextField inputLocation = new JTextField();
        JTextField inputFromDate = new JTextField();

        final JComponent[] inputs = new JComponent[]{
                new JLabel("Location:"),
                inputLocation,
                new JLabel("Date to start from:"),
                inputFromDate,
        };

        int result = JOptionPane.showConfirmDialog(null, inputs, "Crimer", JOptionPane.DEFAULT_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            location = inputLocation.getText();
            fromDate = inputFromDate.getText();
            generateJS(location, fromDate);
        }
        else {
            System.out.println("User cancelled / closed the dialog, result = " + result);
        }
    }

    private static void generateJS(String location, String fromDate) {
        // Logic for JS generation goes here.
    }
}