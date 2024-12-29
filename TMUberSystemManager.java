import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

/* 
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users;    // users variable that references a Map with String key and User value
  private ArrayList<User> userList;   // userList variable that references an Array List of User objects
  private ArrayList<Driver> drivers;

  private Queue<TMUberService>[] serviceRequestsQueue;    // serviceRequestsQueue variable that references a list of queues of TMUberService objects

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users = new TreeMap<>();        // initialize users variable as a Tree Map
    drivers = new ArrayList<Driver>();
    serviceRequestsQueue = new LinkedList[4];        // initialize serviceRequestsQueue variable as a Linked List of length 4
    serviceRequestsQueue[0] = new LinkedList<TMUberService>();    // Queue for city zone 1
    serviceRequestsQueue[1] = new LinkedList<TMUberService>();    // Queue for city zone 2
    serviceRequestsQueue[2] = new LinkedList<TMUberService>();    // Queue for city zone 3
    serviceRequestsQueue[3] = new LinkedList<TMUberService>();    // Queue for city zone 4
    
    totalRevenue = 0;
  }
  // added setUsers method
  public void setUsers(ArrayList<User> userList) {
    users.clear();                  // clear any existing values from the Map 
    for (User user : userList) {      
        users.put(user.getAccountId(), user);     // put the users from the given userList with the account ID into the users Map
    }
    this.userList = new ArrayList<>(users.values());
  }

  // added setDrivers method
  public void setDrivers(ArrayList<Driver> drivers) {
    this.drivers = drivers;         // sets the drivers instance variable with the given drivers Array List
}
  // helper method to find drivers given a driverId
public Driver findDriver(String driverId) {
        for (Driver driver : drivers) {        // iterate through the drivers Array List & search for the driver with the given driver ID
            if (driver.getId().equals(driverId)) {
                return driver;
            }
        }
        return null; // Driver not found
    }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  String errMsg = null;

  public String getErrorMessage()
  {
    return errMsg;
  }
  
  // Given user account id, find user in list of users
  public User getUser(String accountId)
  {
    for (int i = 0; i < userList.size(); i++) {                  // iterate through the list of users
      if (userList.get(i).getAccountId().equals(accountId)) {    // check if the account ID of the current user matches the specified account ID
        return userList.get(i);                                  // return the user if a match is found
      }
    }
    return null;                                              // else return null
  }
  
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    for (int i = 0; i < userList.size(); i++) {                  // iterate through the list of users
      if (userList.get(i).getAccountId().equals(user.getAccountId())) {      // compare the account ID of each user with the account ID of the specified user
        return true;                                          // return true if a user with the same account ID is found (duplicate)
      }
    }
    return false;  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   for (int i = 0; i < drivers.size(); i++) {                  // iterate through the list of drivers
    if (drivers.get(i).getId().equals(driver.getId())) {       // compare the driver ID of each driver with the driver ID of the specified driver
      return true;                                             // return true if a driver with the same driver ID is found (duplicate)
    }
   }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {

    for (Queue<TMUberService> queue : serviceRequestsQueue) {   // iterate through each queue of service requests
      for (TMUberService service : queue) {                     // iterate through the service requests in the current queue
        if (service.equals(req)) {                          // check if the current service request is equal to the requested service
          return true;
        }
      }
    }
    return false;
  }
  
  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Go through all drivers and see if one is available
  // Choose the first available driver
  // Return null if no available driver
  private Driver getAvailableDriver()
  {
    for (int i = 0; i < drivers.size(); i++) {                 // iterate through the list of drivers
      if (drivers.get(i).getStatus() == Driver.Status.AVAILABLE) {    // check if the status of the current driver in the list is AVAILABLE
        return drivers.get(i);                                 // if it is, return the available driver
      }
    }
    return null;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    int index = 1;

    for (User user : users.values())      // iterate over each user in the users map
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();            // print the index and the user's information using the printInfo() method
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();                                      // this method will print all the registered drivers
    
    for (int i = 0; i < drivers.size(); i++) {                 // iterate through the list of drivers
      int index = i + 1;                                       // the index for the output display, it is 1 more than the actual index of the list
      System.out.printf("%-2s. ", index);               // print the index and the driver information using printInfo() method
      drivers.get(i).printInfo();

      // if the driver is currently driving, print information about the current service
      if (drivers.get(i).getStatus().equals(Driver.Status.DRIVING)) {
        TMUberService currentService = drivers.get(i).getService();
        if (currentService != null) {
            System.out.println();       // print from and to address of the service
            System.out.println("From: " + currentService.getFrom() + "    To: " + currentService.getTo());
        }
      }
      System.out.println("\n"); 
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println();                                      // this method will print all the active service requests
    
    for (int i = 0; i < serviceRequestsQueue.length; i++) {
      System.out.println("ZONE " + i);
      System.out.println("======");
      Queue<TMUberService> currentQueue = serviceRequestsQueue[i];      // Get the current queue of service requests
      int index = 1;                                  // the index for the output display
      
      for (TMUberService service : currentQueue) {    // iterate over each service request in the current queue
        System.out.printf("%-2s. ------------------------------------------------------------", index++);
        service.printInfo();                     // print the index and the service requests information using printInfo() method
        System.out.println(); 
        System.out.println();
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    if (name == null || name.isEmpty()) {                      // check if the name is valid (not null or empty)
      throw new InvalidUserException("Invalid User Name");
    }
    if (CityMap.validAddress(address) == false) {              // check if the address is valid using the CityMap validAddress method
      throw new InvalidAddressException("Invalid User Address");
    }
    
    if (wallet < 0) {                                          // check if the wallet balance is valid (non negative)
      throw new InsufficientFundsException("Invalid Money in Wallet");
    }
    ArrayList<User> userList = new ArrayList<>(users.values());
    for (User user : userList) {                   // iterate through the list of users
      if (user.getAccountId().equals(TMUberRegistered.generateUserAccountId(userList))) {  // check if the user is already in the system
          throw new UserDuplicateFoundException("User Already Exists in System");
      }
    }
    String accountId = TMUberRegistered.generateUserAccountId(userList);

    users.put(accountId, new User(accountId, name, address, wallet));    // create a new user and add it to the map of users

  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    if (name == null || name.isEmpty()) {                      // check if the name is valid (not null or empty)
      throw new InvalidDriverException("Invalid Driver Name");
    }

    if (carModel == null || carModel.isEmpty()) {              // check if the car model is valid (not null or empty)
      throw new InvalidCarException("Invalid Car Model");
    }

    if (carLicencePlate == null || carLicencePlate.isEmpty()) {      // check if the licence plate is valid (not null or empty)
      throw new InvalidCarException("Invalid Car Licence Plate");
    }
    if (CityMap.validAddress(address) == false) {              // check if the address is valid using the CityMap validAddress method
      throw new InvalidAddressException("Invalid Driver Address");
    }

    int zone = CityMap.getCityZone(address);

    // check if the driver is already in the system
    for (int i = 0; i < drivers.size(); i++) {
      if (drivers.get(i).equals(new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address, zone))) {
        throw new DriverDuplicateFoundException("Driver Already Exists in System");
      }
    }
    // create a new driver and add it to the list of drivers
    drivers.add(new Driver(TMUberRegistered.generateDriverId(drivers), name, carModel, carLicencePlate, address, zone));
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // set all the variables needed
    User user = getUser(accountId);
    int distance = CityMap.getDistance(from, to);
    Driver driver = getAvailableDriver();
    double cost = getRideCost(distance);
    
    if (accountId == null || accountId.isEmpty()) {
      throw new InvalidUserException("Invalid User Account ID");
    }
    
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to) || from == null || from.isEmpty() || to == null || to.isEmpty()) {   // check if the address is valid using the CityMap validAddress method
      throw new InvalidAddressException("Invalid Address");
    }

    if (user == null) {                                        // check if user is in the list
      throw new InvalidUserException("User Account Not Found");
    }

    if (cost > user.getWallet()) {                             // check if user has enough money
      throw new InsufficientFundsException("Insufficient Funds");
    }

    if (distance <= 1) {                                       // check if distance is valid (at least one city block)
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }

    if (driver == null) {                                      // check if a driver is available
      throw new InvalidDriverException("No Drivers Available");
    }

    TMUberRide ride = new TMUberRide(from, to, user, distance, cost);    // create new ride object

    if (existingRequest(ride)) {                               // check if user has existing ride request
      throw new InvalidException("User Already Has Ride Request");
    }

    serviceRequestsQueue[CityMap.getCityZone(from)].add(ride);          // add the ride request to the appropriate zone queue
    user.addRide();                                            // increment the number of rides for the user
    
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    User user = getUser(accountId);
    int distance = CityMap.getDistance(from, to);
    Driver driver = getAvailableDriver();
    double cost = getDeliveryCost(distance);
    
    if (accountId == null || accountId.isEmpty()) {
      throw new InvalidUserException("Invalid User Account ID");
    }
    if (!CityMap.validAddress(from) || !CityMap.validAddress(to) || from == null || from.isEmpty() || to == null || to.isEmpty()) {  // check if the address is valid using the CityMap validAddress method
      throw new InvalidAddressException("Invalid Address");
    }
    if (restaurant == null || restaurant.isEmpty()) {           // check if restaurant is valid
      throw new InvalidException("Invalid Restaurant Name");
    }
    if (foodOrderId == null || foodOrderId.isEmpty()) {         // check if order ID is valid
      throw new InvalidException("Invalid Food Order ID");
    }
    if (user == null) {                                        // check if user is in the list
      throw new InvalidUserException("User Account Not Found");
    }
    if (cost > user.getWallet()) {                             // check if user has enough money
      throw new InsufficientFundsException("Insufficient Funds");
    }
    if (distance <= 1) {                                       // check if distance is valid (at least one city block)
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }
    if (driver == null) {                                      // check if a driver is available
      throw new InvalidDriverException("No Drivers Available");
    }

    TMUberDelivery delivery = new TMUberDelivery(from, to, user, distance, cost, restaurant, foodOrderId);

    if (existingRequest(delivery)) {                           // check if user has existing delivery request with the same restaurant and ID number
      throw new InvalidException("User Already Has Delivery Request at Restaurant with this Food Order");
    }

    int cityZone = CityMap.getCityZone(from);                  // get the city zone
    serviceRequestsQueue[cityZone].add(delivery);              // add the delivery request to the appropriate zone queue
    user.addDelivery();                                        // increment the number of deliveries for the user
    
  }


  // Cancel an existing service request. 
  public void cancelServiceRequest(int request, int zone)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    
    // check if the zone number is valid (between 0 and 3)
    if (zone < 0 || zone > 3) {
      throw new InvalidException("Invalid Zone #");
    }

    // check if the request number is < 0 or greater than the size of the queue
    if (request < 0 || request > serviceRequestsQueue[zone].size()) {
      throw new InvalidException("Invalid Request #");
    }

    // get the service request from the appropriate queue
    Queue<TMUberService> queue = serviceRequestsQueue[zone];
    TMUberService[] serviceArray = queue.toArray(new TMUberService[0]);
    TMUberService service = serviceArray[request-1];
    
    User user = service.getUser();         // get the user associated with the request

    if (service instanceof TMUberRide) {   // check if the request to be cancelled is an instance of the TMUberRide class
      user.subtractRide();                 // if it is, then call the subtractRide method to decrement the user's number of ride requests
    } 
    else if (service instanceof TMUberDelivery) {    // otherwise, check if it is an instance of the TMUberDelivery class
      user.subtractDelivery();                       // if it is, then call the subtractDelivery method
    }
    queue.remove(service);                            // removes the request from the appropriate queue

  }
  
  // Drop off a ride or a delivery. This completes a service.
  public void dropOff(String driverId)
  {
    Driver driver = null;
    for (Driver driverInList : drivers) {
        if (driverInList.getId().equals(driverId)) {
            driver = driverInList;
            break;
        }
    }

    if (driver == null) {
        throw new DriverNotFoundException("Driver Not Found");
    }

    if (driver.getStatus() != Driver.Status.DRIVING) {
        throw new InvalidDriverException("Driver is Not Driving");
    }

    TMUberService service = driver.getService();
    if (service == null) {
        throw new InvalidDriverException("No Service Assigned to Driver");
    }

    totalRevenue += service.getCost();                // add service cost to revenues
    double payment = service.getCost() * PAYRATE;
    driver.pay(payment);                              // pay the driver
    totalRevenue -= payment;                          // deduct driver fee from total revenues

    User user = service.getUser();
    user.payForService(service.getCost());            // user pays for ride or delivery

    driver.setStatus(Driver.Status.AVAILABLE);        // set the driver back to AVAILABLE
    driver.setService(null);                  // remove the service associated with the driver

    String dropOffAddress = service.getTo();          // get the dropoff address and the zone
    int cityZone = CityMap.getCityZone(dropOffAddress);
    driver.setAddress(dropOffAddress);                // set the driver's new address and zone
    driver.setZone(cityZone);

    int zoneFrom = CityMap.getCityZone(service.getFrom());    // remove the service from the queue it was in
    serviceRequestsQueue[zoneFrom].remove(service);

  }

  public void driveTo(String driverId, String address) {
    
    Driver driver = null;
    for (Driver d : drivers) {              // find the driver with the given ID
        if (d.getId().equals(driverId)) {
            driver = d;
            break;
        }
    }
    if (driver == null) {
      throw new InvalidDriverException("Invalid Driver Id");
    }

    if (driver.getStatus() != Driver.Status.AVAILABLE) {
      throw new DriverStatusException("Driver not available");
    }
    if (!CityMap.validAddress(address)) {
      throw new InvalidAddressException("Invalid Address");
    }

    driver.setAddress(address);                   // set the driver's new address to the given address

    if (CityMap.getCityZone(address) != -1) {
      driver.setZone(CityMap.getCityZone(address));     // if the zone has changed, get and set the new zone
    }

  }

  public void pickup(String driverId) {
    
    Driver driver = null;
    for (Driver d : drivers) {              // find the driver with the given ID
        if (d.getId().equals(driverId)) {
            driver = d;
            break;
        }
    }
    if (driver == null || driverId.isEmpty()) {
      throw new InvalidDriverException("Invalid Driver Id");
    }
    int zone = driver.getZone();                     // get the driver's zone 
    Queue<TMUberService> queue = serviceRequestsQueue[zone];         // get the appropriate zone queue
    
    if (queue.isEmpty()) {
      System.out.println();
      throw new EmptyQueueException("No Service Request in Zone " + zone);
    }

    TMUberService service = queue.poll();           // get the next service request from the queue
    driver.setService(service);                     // assign the service request to the driver
    driver.setStatus(Driver.Status.DRIVING);        // set the driver's status to DRIVING 
    driver.setAddress(service.getFrom());           // set the driver's address to the service request's from address
  }

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    userList = new ArrayList<>(users.values());
    Collections.sort(userList, new nameComparator());       // use the nameComparator to sort the users by name
    System.out.println();
    int index = 1;

    for (User user : userList)            // iterate through each user in the sorted userList
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }                                               
  }

  // Helper class for method sortByUserName
  private class nameComparator implements Comparator<User>
  {
    public int compare(User u1, User u2) {
      return u1.getName().compareTo(u2.getName());           // compare the two users' names
    }
  }

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    userList = new ArrayList<>(users.values());
    Collections.sort(userList, new userWalletComparator());     // use the userWalletComparator to sort the users by wallet
    System.out.println();
    int index = 1;

    for (User user : userList)            // iterate through each user in the sorted userList
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }                                               
  }
  // Helper class for use by sortByWallet
  private class userWalletComparator implements Comparator<User> 
  {
    public int compare(User u1, User u2) {
      return Double.compare(u1.getWallet(), u2.getWallet());      // compare the two users by their wallets
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance()
  {
    System.out.println();

    for (Queue<TMUberService> queue : serviceRequestsQueue) {       // iterate through each queue of service requests
      ArrayList<TMUberService> services = new ArrayList<>(queue);
      Collections.sort(services, new distanceComparator());         // use the distanceComparator to sort the services by distance
      queue.clear();                                                // clear the current queue
      queue.addAll(services);                                       // add all the sorted services back to the queue
    }
    listAllServiceRequests();      // calls the listAllServiceRequests method to list current service requests after sorting them
  }

    // added a Helper class for use by sortByDistance
  private class distanceComparator implements Comparator<TMUberService> 
  {
    public int compare(TMUberService service1, TMUberService service2) {
      return  Integer.compare(service1.getDistance(), service2.getDistance());    // compare the two services by their distances
    }
  }

}

// custom exception classes created by extending RunTimeException
class DriverNotFoundException extends RuntimeException {
  public DriverNotFoundException() {}
  public DriverNotFoundException(String msg) {
      super(msg);
  }
}

class InvalidUserException extends RuntimeException {
  public InvalidUserException() {}
  public InvalidUserException(String msg) {
      super(msg);
  }
}

class InvalidAddressException extends RuntimeException {
  public InvalidAddressException() {}
  public InvalidAddressException(String msg) {
      super(msg);
  }
}

class InvalidException extends RuntimeException {
  public InvalidException() {}
  public InvalidException(String msg) {
      super(msg);
  }
}

class InvalidDriverException extends RuntimeException {
  public InvalidDriverException() {}
  public InvalidDriverException(String msg) {
      super(msg);
  }
}

class InvalidCarException extends RuntimeException {
  public InvalidCarException() {}
  public InvalidCarException(String msg) {
      super(msg);
  }
}

class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException() {}
  public InsufficientFundsException(String msg) {
      super(msg);
  }
}

class InsufficientDistanceException extends RuntimeException {
  public InsufficientDistanceException() {}
  public InsufficientDistanceException(String msg) {
      super(msg);
  }
}

class UserDuplicateFoundException extends RuntimeException {
  public UserDuplicateFoundException() {}
  public UserDuplicateFoundException(String msg) {
      super(msg);
  }
}

class DriverDuplicateFoundException extends RuntimeException {
  public DriverDuplicateFoundException() {}
  public DriverDuplicateFoundException(String msg) {
      super(msg);
  }
}

class DriverStatusException extends RuntimeException {
  public DriverStatusException() {}
  public DriverStatusException(String msg) {
      super(msg);
  }
}

class EmptyQueueException extends RuntimeException {
  public EmptyQueueException() {}
  public EmptyQueueException(String msg) {
      super(msg);
  }
}