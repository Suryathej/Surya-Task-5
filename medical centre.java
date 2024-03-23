package medicalcenter;

import java.util.Date;
import java.text.SimpleDateFormat;

public class ClientDate {
    // Method to get the current date formatted as dd/mm/yy
    public static String getDate() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        return dateFormat.format(currentDate);
    }

    // Example usage
    public static void main(String[] args) {
        System.out.println("Current Date: " + getDate());
    }
}
