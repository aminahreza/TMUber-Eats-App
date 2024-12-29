/* General class that simulates a ride or a delivery in a simple Uber app
 * 
 * Implement the Comparable interface and compare two service requests based on the distance
 */
abstract public class TMUberService 
{
  private String from;
  private String to;
  private User user;
  private String type;  // Currently Ride or Delivery but other services could be added      
  private int distance; // Units are City Blocks
  private double cost;  // Cost of the service
  
  public TMUberService(String from, String to, User user, int distance, double cost, String type)
  {
    this.from = from;
    this.to = to;
    this.user = user;
    this.distance = distance;
    this.cost = cost;
    this.type = type;
    // this.distance = 0;
  }


  // Subclasses define their type (e.g. "RIDE" OR "DELIVERY") 
  abstract public String getServiceType();

  // Getters and Setters
  public String getFrom()
  {
    return from;
  }
  public void setFrom(String from)
  {
    this.from = from;
  }
  public String getTo()
  {
    return to;
  }
  public void setTo(String to)
  {
    this.to = to;
  }
  public User getUser()
  {
    return user;
  }
  public void setUser(User user)
  {
    this.user = user;
  }
  public int getDistance()
  {
    return distance;
  }
  public void setDistance(int distance)
  {
    this.distance = distance;
  }
  public double getCost()
  {
    return cost;
  }
  public void setCost(double cost)
  {
    this.cost = cost;
  }

  // Compare 2 service requests based on distance
  
  public int compareTo(TMUberService other)
  {
      return Integer.compare(this.distance, other.distance);    // compare the distances of this service request and other
  }

  // Check if 2 service requests are equal (this and other)
  // They are equal if its the same type and the same user
  public boolean equals(Object other)
  {
    if (!(other instanceof TMUberService)) {            // check if other is an instance of TMUberService class
      return false; 
  }
    TMUberService service = (TMUberService) other;      // cast other to TMUberService
        return this.type.equals(service.type) && this.user.equals(service.user);      // check if the types and users of this and other are equal
  }
  
  // Print Information 
  public void printInfo()
  {
    System.out.printf("\nType: %-9s From: %-15s To: %-15s", type, from, to);
    System.out.print("\nUser: ");
    user.printInfo();
  }
}
