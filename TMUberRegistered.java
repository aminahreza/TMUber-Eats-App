import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // uses database of Preregistered users, loaded from the 'users' file

    // modified method takes in a String filename and returns an Array List of User objects, throws a FileNotFound exception
    public static ArrayList<User> loadPreregisteredUsers(String fileName) throws IOException
    {
        ArrayList<User> users = new ArrayList<>();      // create a new Array List of User objects
        File file = new File(fileName);
        Scanner userScanner = new Scanner(file);        // create a scanner that takes in a File 

        while (userScanner.hasNextLine()) {             // get the information in the file and assign to variables
            String name = userScanner.nextLine();
            String address = userScanner.nextLine();
            double wallet = Double.parseDouble(userScanner.nextLine());

            users.add(new User(generateUserAccountId(users), name, address, wallet));   // add a new User object to the Array List with the collected info
        }
        userScanner.close();
        return users;           // return the Array List
    }

        // modified method takes in a String filename and returns an Array List of Driver objects, throws a FileNotFound exception
    public static ArrayList<Driver> loadPreregisteredDrivers(String fileName) throws IOException
    {
        ArrayList<Driver> drivers = new ArrayList<>();      // create a new Array List of Driver objects
        File file = new File(fileName);
        Scanner driverScanner = new Scanner(file);          // create a scanner that takes in a File 

        while (driverScanner.hasNextLine()) {               // get the information in the file and assign to variables
            String name = driverScanner.nextLine();
            String carModel = driverScanner.nextLine();
            String carLicensePlate = driverScanner.nextLine();
            String address = driverScanner.nextLine();
            int zone = CityMap.getCityZone(address);        // get the zone the address is in

            // add a new Driver object to the Array List with the collected info
            drivers.add(new Driver(generateDriverId(drivers), name, carModel, carLicensePlate, address, zone));
        }
        driverScanner.close();
        return drivers;           // return the Array List
    }
}

