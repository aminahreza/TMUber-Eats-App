/* This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is a TMUberService with some extra functionality
 */
public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
  public TMUberDelivery(String from, String to, User user, int distance, double cost,
                        String restaurant, String order)        
  {
    super(from, to, user, distance, cost, TYPENAME);      // call super to inherit the indicated variables from TMUberService
    this.restaurant = restaurant;                                 // initialize instance variables 
    this.foodOrderId = order;
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    TMUberService req = (TMUberService)other;
    if (!req.getServiceType().equals(TMUberDelivery.TYPENAME))
      return false;
    
    // Now check if this delivery and other delivery are equal
    TMUberDelivery delivery = (TMUberDelivery)other;
    return super.equals(other) && delivery.getRestaurant().equals(restaurant) && 
                                  delivery.getFoodOrderId().equals(foodOrderId);

    // If this and other are deliveries, check to see if they are equal
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    // Use inheritance to first print info about a basic service request
    super.printInfo();
    // Then print specific subclass info
    System.out.printf("\nRestaurant: %-9s Food Order #: %-3s", restaurant, foodOrderId); 
  }
}
