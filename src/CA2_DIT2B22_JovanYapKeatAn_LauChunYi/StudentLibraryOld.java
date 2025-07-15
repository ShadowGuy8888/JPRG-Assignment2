/*
    Name: Lau Chun Yi
    Adm No: p2400149
    Class: DIT/FT/2B/22
*/
package CA2_DIT2B22_JovanYapKeatAn_LauChunYi;

import javax.swing.JOptionPane;

 /**
  * @author Lau Chun Yi
  * Main class for the Student Library System
  * Acts as the entry point and main menu controller
  */
public class StudentLibraryOld {
    // Static instances of management classes
    private static final StudentManagement studentManagement = new StudentManagement();
    private static final BookManagement bookManagement = new BookManagement();

    /**
     * Main application loop that displays the primary menu
     */
    public static void run() {

        while (true) {
            // Display main menu options
            String inputNo = JOptionPane.showInputDialog(
                null,
                "Enter your option:\n\n1) Student management\n2) Books in library\n3) Exit",
                "Mini Library System",
                JOptionPane.QUESTION_MESSAGE
            );

            // Handle window close/cancel
            if (inputNo == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Program exited.", 
                    "Message", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                break;
            }

            inputNo = inputNo.trim(); // Clean input

            // Route to appropriate subsystem
            switch (inputNo) {
                case "1":
                    studentManagement.run(); // Launch student management
                    break;

                case "2":
                    bookManagement.run(); // Launch book management
                    break;

                case "3":
                    JOptionPane.showMessageDialog(
                        null, 
                        "Exiting program.", 
                        "Message", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                    break;

                default:
                    AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\huh.wav");
                    JOptionPane.showMessageDialog(
                        null, 
                        "Invalid input. Please enter 1, 2, or 3.", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE
                    );
                    break;
            }
        }
    }

    /**
     * @param args
     * Application entry point
     */
    public static void main(String ...args) {
        System.out.println(System.getProperty("user.dir"));
        AudioPlayer.playSound(System.getProperty("user.dir")+"\\sounds\\greeting.wav");
        run();
    }
}