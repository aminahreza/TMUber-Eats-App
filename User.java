// Class that simulates a user of a simple Uber app

public class User 
{
  private String accountId;  
  private String name;
  private String address;
  private double wallet;
  private int rides;
  private int deliveries;
  
  public User(String id, String name, String address, double wallet)
  {
    this.accountId = id;
    this.name = name;
    this.address = address;
    this.wallet = wallet;
    this.rides = 0;
    this.deliveries = 0;
  } 

  // Getters and Setters
  public String getAccountId()
  {
    return accountId;
  }
  public void setAccountId(String accountId)
  {
    this.accountId = accountId;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(int wallet)
  {
    this.wallet = wallet;
  }
  public int getRides()
  {
    return rides;
  }
  public void addRide()
  {
    this.rides++;
  }
  public void subtractRide() {    // added method to decrement # of rides for a user when a ride is cancelled
    if (rides > 0) {
    this.rides--; }
  }

  public void addDelivery()
  {
    this.deliveries++;
  }
  public void subtractDelivery() {    // added method to decrement # of deliveries for a user when a delivery is cancelled
    if (deliveries > 0) {
    this.deliveries--; }
  }

  public int getDeliveries()
  {
    return deliveries;
  }
  
  // Pay for the cost of the service
  // This method assumes that there are sufficient funds in the wallet
  public void payForService(double cost)
  {
    wallet -= cost;
  }
  // Print Information about a User  
  public void printInfo()
  {
    System.out.printf("Id: %-5s Name: %-15s Address: %-15s Wallet: %2.2f", accountId, name, address, wallet);
  }
  
  /*
   * Two users are equal if they have the same name and address.
   * This method is overriding the inherited method in superclass Object
   */
  public boolean equals(Object other)
  {
    if (!(other instanceof User)) {               // check if other is an instance of User class
      return false;
    }
    User user = (User) other;                     // cast other to User
    return (this.name.equals(user.name) && this.address.equals(user.address));  // check if the names and addresses of this and other are equal

  }
}
