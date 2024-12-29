import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      try {               // try block for exceptions in TMUberSystemManager
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");      // prompt user for an address
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }

        tmuber.registerNewDriver(name, carModel, license, address);     // call the registerNewDriver method in TMUberSystemManager
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address: %-15s", name, carModel, license, address);
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        tmuber.registerNewUser(name, address, wallet);
        System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)

        String userAccountId = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())            // get the next string in the scanner
        {
          userAccountId = scanner.nextLine(); // set the user account ID to the user input
        }
        String fromAddress = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddress = scanner.nextLine();   // set the initial address to the user input
        }
        String toAddress = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddress = scanner.nextLine();    // set the destination address to the user input
        }
        tmuber.requestRide(userAccountId, fromAddress, toAddress);
        System.out.println();
        System.out.printf("RIDE for: %-15s From: %-15s To: %-15s", tmuber.getUser(userAccountId).getName(), fromAddress, toAddress);  // print the ride details
      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        
        String userAccountIdFood = "";
        System.out.print("User Account Id: ");
        if (scanner.hasNextLine())            // get the next string in the scanner
        {
          userAccountIdFood = scanner.nextLine();     // set the user account ID to the user input
        }
        String fromAddressFood = "";
        System.out.print("From Address: ");
        if (scanner.hasNextLine())
        {
          fromAddressFood = scanner.nextLine();       // set the initial address to the user input
        }
        String toAddressFood = "";
        System.out.print("To Address: ");
        if (scanner.hasNextLine())
        {
          toAddressFood = scanner.nextLine();         // set the destination address to the user input
        }
        String restaurant = "";
        System.out.print("Restaurant: ");
        if (scanner.hasNextLine())
        {
          restaurant = scanner.nextLine();            // set the restaurant to the user input
        }
        String foodOrderNumber = "";
        System.out.print("Food Order #: ");
        if (scanner.hasNextLine())
        {
          foodOrderNumber = scanner.nextLine();       // set the order number to the user input
        }
        tmuber.requestDelivery(userAccountIdFood, fromAddressFood, toAddressFood, restaurant, foodOrderNumber); 
        System.out.println();
        System.out.printf("DELIVERY for: %-15s From: %-15s To: %-15s", tmuber.getUser(userAccountIdFood).getName(), fromAddressFood, toAddressFood);   // print the delivery details
        
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        tmuber.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        int zone = -1;
        System.out.print("Zone #: ");       // prompt user for a zone number
        if (scanner.hasNextInt()) {
          zone = scanner.nextInt();
        }
        int request = -1;
        System.out.print("Request #: ");    // prompt user for a request number
        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        tmuber.cancelServiceRequest(request, zone);       // call the modified cancelServiceRequest method in TMUberSystemManager
        System.out.println("Service request #" + request + " in zone #" + zone + " cancelled");
      }

      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        String driverId = "";
        System.out.print("Driver ID: ");      // prompt user for a driver ID
        if (scanner.hasNextLine())
        {
          driverId = scanner.nextLine();
        }
    
        tmuber.dropOff(driverId);               // call the dropOff method in TMUberSystemManager
        System.out.println("Driver " + driverId + " Dropping Off");
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.println("Total Revenue: " + tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        System.out.print(address);
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      // new PICKUP action
      else if (action.equalsIgnoreCase("PICKUP")) {
        String driverId = "";
        System.out.print("Driver Id: ");    // prompt user to input a driver ID
        if (scanner.hasNextLine()) {
          driverId = scanner.nextLine();
        }
        Driver driver = tmuber.findDriver(driverId);    // get the driver with the associated ID
        String driverAddress = driver.getAddress();
        int zone = CityMap.getCityZone(driverAddress);    // get the zone the driver is picking up in
        tmuber.pickup(driverId);                          // call the pickup method in TMUberSystemManager
        System.out.println("Driver " + driverId + " Picking Up In Zone " + zone);
      }
      // new DRIVETO action
      else if (action.equalsIgnoreCase("DRIVETO")) {
        String driverId = "";
        System.out.print("Driver Id: ");    // prompt user to input a driver ID
        if (scanner.hasNextLine()) {
          driverId = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");      // prompt user to input an address
        if (scanner.hasNextLine()) {
          address = scanner.nextLine();
        }
        int zone = CityMap.getCityZone(address);    // get the zone of the inputted address 
        tmuber.driveTo(driverId, address);          // call the driveTo method of TMUberSystemManager
        System.out.println("Driver " + driverId + " Now in Zone " + zone);
      }

      // new LOADUSERS action
      else if (action.equalsIgnoreCase("LOADUSERS")) {
        String fileName = "";
        System.out.print("Users File: ");    // prompt the user for the user file name
        if (scanner.hasNextLine()) {
          fileName = scanner.nextLine();
        }

        try {
          ArrayList<User> users = TMUberRegistered.loadPreregisteredUsers(fileName);  // call the modified loadPreregisteredUsers method
          tmuber.setUsers(users);                 // call the new setUsers method of TMUberSystemManager
          System.out.println("Users Loaded");
        }

        catch (FileNotFoundException err) {       // catch if there is a FileNotFoundException exception, print that the file is not found
        System.out.println("Users File: " + fileName + " Not Found");
        }
        catch (IOException err) {                 // catch if there is an IOException exception, print that an IO error occurred and exit
        System.out.println("Unexpected IO error occurred");
        System.exit(1);
        }
      }

      // new LOADDRIVERS action
      else if (action.equalsIgnoreCase("LOADDRIVERS")) {
        String fileName = "";
        System.out.print("Drivers File: ");       // prompt the user for a drivers file name
        if (scanner.hasNextLine()) {
          fileName = scanner.nextLine();
        }

        try {
          ArrayList<Driver> drivers = TMUberRegistered.loadPreregisteredDrivers(fileName);  // call the modified loadPreregisteredDrivers method
          tmuber.setDrivers(drivers);               // call the new setDrivers method of TMUberSystemManager
          System.out.println("Drivers Loaded");
        }
        
        catch (FileNotFoundException err) {         // catch if there is a FileNotFoundException exception, print that the file is not found
        System.out.println("Drivers File: " + fileName + " Not Found");
        }
        catch (IOException err) {                 // catch if there is an IOException exception, print that an IO error occurred and exit
          System.out.println("Unexpected IO error occurred");
          System.exit(1);
          }
      }
    }

    // catch all the custom exceptions from the TMUberSystemManager file
    
    catch (DriverNotFoundException err) {
      System.out.println(err.getMessage());
    }
    catch (InvalidUserException err) {
      System.out.println(err.getMessage());
    }
    catch (InvalidAddressException err) {
      System.out.println(err.getMessage());
    }
    catch (InvalidException err) {
      System.out.println(err.getMessage());
    }
    catch (InvalidDriverException err) {
      System.out.println(err.getMessage());
    }
    catch (InvalidCarException err) {
      System.out.println(err.getMessage());
    }
    catch (InsufficientFundsException err) {
      System.out.println(err.getMessage());
    }
    catch (InsufficientDistanceException err) {
      System.out.println(err.getMessage());
    }
    catch (UserDuplicateFoundException err) {
      System.out.println(err.getMessage());
    }
    catch (DriverDuplicateFoundException err) {
      System.out.println(err.getMessage());
    }
    catch (DriverStatusException err) {
      System.out.println(err.getMessage());
    }
    catch (EmptyQueueException err) {
      System.out.println(err.getMessage());
    }

      System.out.print("\n>");
    }
  }
}

