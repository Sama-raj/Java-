package assignment1;


import java.util.Scanner;


public class FlexiRentSystem {
 private RentalProperty properties[] = new RentalProperty[50];
 private int index = 0;
 Scanner input = new Scanner(System.in);

 private void showMenu() {
	 //Displaying the Menu
  System.out.println("**** FLEXIRENT SYSTEM MENU ****");
  System.out.println("Add Property:			1");
  System.out.println("Rent Property:			2");
  System.out.println("Return Property:                3");
  System.out.println("Property Maintenance:		4");
  System.out.println("Complete Maintenance:		5");
  System.out.println("Display All Properties:       	6");
  System.out.println("Exit Program:			7");
  System.out.print("Enter your choice: ");
 }


 public void runSystem() {
  boolean shouldExitLoop = false;
  while (!shouldExitLoop) {
   showMenu();
   int menu = Integer.parseInt(input.nextLine());
   switch (menu) {
    case 1:
     RentalProperty property = addProperty();
     if (property != null) {
      properties[index++] = property;
     }
     break;
    case 2:
     rentProperty();
     break;
    case 3:
     returnProperty();
     break;
    case 4:
     startMaintenace();
     break;
    case 5:
     completeMaintenance();
     break;
    case 6:
     displayAll();
     break;
    case 7:
     shouldExitLoop = true;
     break;
    default:
     System.out.println("Error: Please enter a valid choice.");
     break;

   }
  }
 }

 private void rentProperty() {
  System.out.print("Enter property ID: ");
  String propertyID = input.nextLine();
  int index = getPropertyIndex(propertyID);
//Checking for duplicate property ID
  if (index != -1) {
   System.out.print("Customer ID:");
   String customerId = input.nextLine();
   System.out.print("Rent date (dd/mm/yyyy):");
   String rentDate = input.nextLine();
   System.out.print("How many days?: ");
   int days = getIntegerInput("Enter Invalid days");
   if (days == -1)
    return;

   boolean result = properties[index].rent(customerId, DateTime.getDateFromString(rentDate), days);
   if (result) {
    System.out.println(properties[index].getPropertyType().name() + " " + propertyID + " is now rented by customer " + customerId);
   } else {
    System.out.println(properties[index].getPropertyType().name() + " " + propertyID + " could not be rented");
   }
  } else {
   System.out.println("Error: No propery exist with id: " + propertyID);
  }

 }

 private void returnProperty() {
  System.out.print("Enter property id: ");
  String propertyID = input.nextLine();
  int index = getPropertyIndex(propertyID);
//Checking for duplicate property ID
  if (index != -1) {
   System.out.print("Return date (dd/mm/yyyy):");
   String returnDate = input.nextLine();
   boolean result = properties[index].returnDate(DateTime.getDateFromString(returnDate));
   if (result) {
    System.out.println(properties[index].getPropertyType().name() + " " + propertyID + " returned by customer ");
    System.out.println(properties[index].getDetails());
   } else {
    System.out.println(properties[index].getPropertyType().name() + " " + propertyID + " could not be returned");
   }
  } else {
   System.out.println("Error: No propery exist with id: " + propertyID);
  }
 }

 private void displayAll() {
  for (int i = 0; i < index; i++) {
   System.out.println(properties[i].getDetails());
  }
 }

 private void completeMaintenance() {
  System.out.println("Enter propery id: ");
  String propertyID = input.nextLine();
  int proprtyIndex = getPropertyIndex(propertyID);
//Checking for duplicate property ID
  if (proprtyIndex != -1) {
   if (properties[proprtyIndex].getPropertyType() == RentalProperty.PropertyType.PremiumSuite) {
    System.out.println("Maintenance completion date (dd/mm/yyyy): ");
    String dateOfMaintenance = input.nextLine();
    ((PremiumSuite) properties[proprtyIndex]).completeMaintenance(DateTime.getDateFromString(dateOfMaintenance));
    System.out.println("Premium Suite " + propertyID + "has all maintenance completed and ready for rent");
   } else {
    System.out.println("Apartment " + propertyID + " has all maintenance completed and ready for rent");
   }
   properties[proprtyIndex].completeMaintenance(new DateTime());
  } else {
   System.out.println("No property exist with id: " + propertyID);
  }
 }

 private void startMaintenace() {
  System.out.println("Enter propery id: ");
  String propertyID = input.nextLine();
  int proprtyIndex = getPropertyIndex(propertyID);
//Checking for duplicate property ID
  if (proprtyIndex != -1) {
   boolean result = properties[proprtyIndex].performMaintenance();
   if (result) {
    System.out.println(properties[proprtyIndex].getPropertyType() + " " + propertyID + " is now under maintenance.");
   } else {
    System.out.println(properties[proprtyIndex].getPropertyType() + " " + propertyID + " can't be moved to maintenance.");
   }
  } else {
   System.out.println("No property exist with id: " + propertyID);
  }
 }

 //Method to convert property ID to index
 private int getPropertyIndex(String propertyID) {
  int propertyIndex = -1;
  for (int i = 0; i < index; i++) {
   if (properties[i].getPropertyId().equalsIgnoreCase(propertyID)) {
    propertyIndex = i;
    break;
   }
  }
  return propertyIndex;
 }

 private String getapartmentPropertyId() {
  System.out.print("Enter property id: ");
  String propertyID = input.nextLine();
  //Checking for Condition of Apartment ID
  if (!(propertyID.startsWith("A"))) {
   System.out.println("Error: Invalid propery id");
   return null;
  }

  return propertyID;

 }
 private String getpremiumPropertyId() {
  System.out.print("Enter property id: ");
  String propertyID = input.nextLine();
//Checking for Condition of PremiumSuite ID
  if (!(propertyID.startsWith("S"))) {
   System.out.println("Error: Invalid propery id");
   return null;
  }

  return propertyID;


 }

 private String getStringInput(String message) {
  System.out.print(message);
  String inp = input.nextLine();

  return inp;

 }

 private int getIntInput(String message, String err) {
  System.out.print(message);
  int inp = getIntegerInput(err);

  return inp;

 }

 private RentalProperty addProperty() {
  RentalProperty rentalProperty = null;
  String propertyID = null, streetName, suburb;
  int streetNumber;
  String propertyType = getStringInput("Enter property type: (Apartment/ PremiumSuite): ");
  if (propertyType == null)
   return null;
  if (propertyType.equalsIgnoreCase("Apartment")) {
   propertyID = getapartmentPropertyId();

  } else if (propertyType.equalsIgnoreCase("PremiumSuite"))
   propertyID = getpremiumPropertyId();

  else {
   System.out.println("Error: Invalid property type.");
   return null;
  }
  if (propertyID == null)
   return null;
  int propertyIndex = getPropertyIndex(propertyID);
  if (propertyIndex == -1) {

   streetNumber = getIntInput("Enter street number: ", "Error: Invalid Street number");
   if (streetNumber == -1) {
    return null;
   }
   streetName = getStringInput("Enter street name: ");
   suburb = getStringInput("Enter suburb: ");
   if (propertyType.equalsIgnoreCase("PremiumSuite")) {
    int bedrooms = 3;
    String date = getStringInput("ENter last maintenance date(dd/mm/yyyy): ");
    //Storing all the details into an object of RentalProperty
    rentalProperty = new PremiumSuite(propertyID, streetNumber, streetName, suburb, bedrooms, RentalProperty.PropertyType.PremiumSuite, DateTime.getDateFromString(date));
    System.out.println("Premium Suite with id: " + propertyID + " successfully added.");
   } else if (propertyType.equalsIgnoreCase("Apartment")) {
    int bedrooms = getIntInput("Enter number of bedrooms: ", "Error: Invalid Bedroom number");
    if (bedrooms == -1)
     return null;
    if (bedrooms > 3 || bedrooms <= 0) {
     System.out.println("Invalid Bedroom number");
     return null;
    }
    rentalProperty = new Apartment(propertyID, streetNumber, streetName, suburb, bedrooms, RentalProperty.PropertyType.Apartment);
    System.out.println("Apartement with id: " + propertyID + " successfully added.");
   } else {
    System.out.println("Error: Invalid property type.");
   }
  } else {
   System.out.println("Propery with id: " + propertyID + " already exist.");
  }
  return rentalProperty;
 }


 private int getIntegerInput(String errorMessage) {
  int num = -1;
  try {
   num = Integer.parseInt(input.nextLine());
  } catch (Exception e) {
   System.out.println(errorMessage);
  }
  return num;
 }


}