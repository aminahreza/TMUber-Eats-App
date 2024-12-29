import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    String[] parts = getParts(address);
    if (parts.length != 3) {
      return false;                 // check and return false if the number of parts is not 3
    }
    if (parts[0].length() != 2 || !allDigits(parts[0])) {        // check if the first part has exactly 2 digits and contains only digits
      return false;
    }
    if ((!parts[2].equalsIgnoreCase("Street") && !parts[2].equalsIgnoreCase("Avenue"))  // check if the second part is a valid street number
        || !(parts[1].toLowerCase().equals("1st") || parts[1].toLowerCase().equals("2nd") || parts[1].toLowerCase().equals("3rd")
        || (parts[1].toLowerCase().charAt(0) >= '4' && parts[1].toLowerCase().charAt(0) <= '9'))) {      // check if second part is "1st", "2nd", "3rd", or a number greater than or equal to 4

      return false;
    }
    return true;    // if all conditions are satisfied, return true (it is a valid address)
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    String parts[] = getParts(address);

    for (int i = 0; i < parts.length - 1; i++) {
      String word = parts[i];
      
      if (i == 0) {
        if (parts[2].equalsIgnoreCase("street")) {
          block[0] = Integer.parseInt(word.substring(0,1));
        }
        else if (parts[2].equalsIgnoreCase("avenue")) {
          block[1] = Integer.parseInt(word.substring(0, 1));
        }
      }

      if (i == 1) {
        if (parts[2].equalsIgnoreCase("street")) {
          block[1] = Integer.parseInt(word.substring(0, 1));
        }
        else if (parts[2].equalsIgnoreCase("avenue")) {
          block[0] = Integer.parseInt(word.substring(0, 1));
        }
      }
    }
    return block;
    
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  
  public static int getDistance(String from, String to)
  {
    int[] blockFrom = getCityBlock(from);               // get the city block coordinates for the from and to addresses
    int[] blockTo = getCityBlock(to);

    int distance = Math.abs(blockTo[0]-blockFrom[0]) + Math.abs(blockTo[1]-blockFrom[1]);  // calculate the distance (sum of the absolute differences of the avenue and street coordinates)

    return distance;

  }

  // get the city zone given an address input
  public static int getCityZone(String address) {
    if (!validAddress(address)) {         // check if the address inputted is valid
      return -1;
    }
    else {
      int[] block = getCityBlock(address);
        
      // specify the boundaries of each zone
      if (block[0] >= 1 && block[0] <= 5 && block[1] >= 6 && block[1] <= 9) {   // block at index 0 is the avenue # and index 1 is the street #
        return 0;               // city zone 0
      }
      else if (block[0] >= 6 && block[0] <= 9 && block[1] >= 6 && block[1] <= 9) {
        return 1;               // city zone 1
      }
      else if (block[0] >= 6 && block[0] <= 9 && block[1] >= 1 && block[1] <= 5) {
        return 2;               // city zone 2
      }
      else if (block[0] >= 1 && block[0] <= 5 && block[1] >= 1 && block[1] <= 5) {
        return 3;               // city zone 3
      }
      else {
        return -1;              // return -1 if not in any of the zones
      }
    }
  }
}
