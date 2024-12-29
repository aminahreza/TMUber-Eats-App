/*  This class simulates an ride service for a simple Uber app
 * 
 * A TMUberRide is a TMUberService with some extra functionality
 */
public class TMUberRide extends TMUberService
{
  private int numPassengers;
  private boolean requestedXL;
  
  public static final String TYPENAME = "RIDE";
  
  // Constructor to initialize all inherited and new instance variables 
  public TMUberRide(String from, String to, User user, int distance, double cost)    // remove reference to Driver object
  {
    super(from, to, user, distance, cost, TYPENAME);      // call super to inherit the indicated variables from TMUberService
    numPassengers = 0;                                            // initialize instance variables
    requestedXL = false;
  }
  
  public String getServiceType()
  {
    return TYPENAME;
  }

  public int getNumPassengers()
  {
    return numPassengers;
  }

  public void setNumPassengers(int numPassengers)
  {
    this.numPassengers = numPassengers;
  }

  public boolean isRequestedXL()
  {
    return requestedXL;
  }

  public void setRequestedXL(boolean requestedXL)
  {
    this.requestedXL = requestedXL;
  }
}
