package assignment1;


import java.text.ParseException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class Apartment extends RentalProperty {
 //Initializing the parameters
 private final int PER_DAY_COST_OF_1_BEDROOM_APARTMENT = 143;
 private final int PER_DAY_COST_OF_2_BEDROOM_APARTMENT = 210;
 private final int PER_DAY_COST_OF_3_BEDROOM_APARTMENT = 319;
 private final int MAXIMUM_DAYS_FOR_RENT = 28;
 private final int MIMIMUM_DAYS_FOR_RENT_BETWEEN_SUNDAY_AND_THURSDAY = 2;
 private final int MIMIMUM_DAYS_FOR_RENT_ON_FRIDAY_OR_SATURDAY = 3;

 public Apartment(String propertyId, int streetNumber, String streetName, String suburb, int numberOfBedroom, PropertyType propertyType) {
  super(propertyId, streetNumber, streetName, suburb, numberOfBedroom, propertyType);
 }
 //Checking for the rent date between sunday and Thursday
 private boolean isRentDateBetweenSundayAndThrusday(DateTime rentDate) {
  String rdate = rentDate.toString();
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
  LocalDate date = LocalDate.parse(rdate, formatter);
  DayOfWeek d = date.getDayOfWeek();
  if (d == DayOfWeek.SATURDAY || d == DayOfWeek.FRIDAY) {
   return false;
  } else
   return true;
 }

 // Checking the condition for renting
 private boolean isRentingForValidNumberOfDays(DateTime rentDate, int numOfRentDay) {
  boolean isValid = false;
  if (numOfRentDay > MAXIMUM_DAYS_FOR_RENT) {
   isValid = false;
  } else {
   if (numOfRentDay <= 1) {
    isValid = false;
   } else {
    if (numOfRentDay >= MIMIMUM_DAYS_FOR_RENT_ON_FRIDAY_OR_SATURDAY) {
     isValid = true;
    } else {
     if (numOfRentDay == MIMIMUM_DAYS_FOR_RENT_BETWEEN_SUNDAY_AND_THURSDAY && isRentDateBetweenSundayAndThrusday(rentDate)) {
      isValid = true;
     }
    }
   }
  }
  return isValid;
 }

 @Override
 public boolean rent(String customerId, DateTime rentDate, int numOfRentDay) {
  boolean isAvailableForRent;
  if (getPropertyStatus() == PropertyStatus.Available) {
   if (numOfRentDay > 1 && isRentingForValidNumberOfDays(rentDate, numOfRentDay)) {
    setPropertyStatus(PropertyStatus.Rented);
    RentalRecord record = new RentalRecord(getPropertyId() + "_" + customerId + "_" + rentDate.getEightDigitDate(), rentDate, new DateTime(rentDate, numOfRentDay));
    addNewRentalRecord(record);
    isAvailableForRent = true;
   } else {
    isAvailableForRent = false;
   }
  } else {
   isAvailableForRent = false;
  }

  return isAvailableForRent;
 }

 @Override
 public boolean returnDate(DateTime returnDate) {
  RentalRecord currentRentalRecord = getRentalRecord()[0];
  DateTime rentDate = currentRentalRecord.getRentDate();
  int diffDate = DateTime.diffDays(returnDate, rentDate);
  if (diffDate <= 0) {
   return false;
  }

  DateTime estimatedReturnDate = currentRentalRecord.getEstimatedReturnDate();
  int lateDays = DateTime.diffDays(returnDate, estimatedReturnDate);
  double rentalFee = 0;
  double lateFee = 0;
  // if estimated date is earlier
  if (lateDays >= 0) {
   int daysWithoutLateFee = DateTime.diffDays(estimatedReturnDate, rentDate);

   switch (getNumberOfBedroom()) {
    case 1:
     rentalFee = daysWithoutLateFee * PER_DAY_COST_OF_1_BEDROOM_APARTMENT;
     lateFee = lateDays * 115 * PER_DAY_COST_OF_1_BEDROOM_APARTMENT / 100;
     break;
    case 2:
     rentalFee = daysWithoutLateFee * PER_DAY_COST_OF_2_BEDROOM_APARTMENT;
     lateFee = lateDays * 115 * PER_DAY_COST_OF_2_BEDROOM_APARTMENT / 100;
     break;
    case 3:
     rentalFee = daysWithoutLateFee * PER_DAY_COST_OF_3_BEDROOM_APARTMENT;
     lateFee = lateDays * 115 * PER_DAY_COST_OF_3_BEDROOM_APARTMENT / 100;
     break;
   }

  } else // if return date is earlier
  {
   switch (getNumberOfBedroom()) {
    case 1:
     rentalFee = PER_DAY_COST_OF_1_BEDROOM_APARTMENT * diffDate;
     break;
    case 2:
     rentalFee = diffDate * PER_DAY_COST_OF_2_BEDROOM_APARTMENT;
     break;
    case 3:
     rentalFee = diffDate * PER_DAY_COST_OF_3_BEDROOM_APARTMENT;
     break;
   }
  }
  currentRentalRecord.setActualReturnDate_LateFee_RentalFee(returnDate, lateFee, rentalFee);
  setPropertyStatus(PropertyStatus.Available);
  return true;
 }

 @Override
 public String toString() {
  return super.toString();
 }

 @Override
 public String getDetails() {
  // String concatenation
  String output = String.format("%-20s%s\n", " Property ID:", getPropertyId());
  output += String.format("%-20s%s\n", "Address:", getStreetNumber() + " " + getStreetName() + " " + getSuburb());
  output += String.format("%-20s%s\n", "Type:", getPropertyType().name());
  output += String.format("%-20s%d\n", "Bedroom: ", getNumberOfBedroom());
  output += String.format("%-20s%s\n", "Status:", getPropertyStatus().name());
  int index = getRentalRecordIndex();
  if (index == 0) {
   output += String.format("%-20s%s\n", "RENTAL RECORD:", "empty");
  } else {
   output += String.format("%-20s\n", "RENTAL RECORD:");
   int i = 0;
   RentalRecord record[] = getRentalRecord();
   while (record[i] != null) {
    output += record[i].getDetails() + "\n";
    output += "--------------------------------------";
    i++;
   }
  }
  return output;
 }

 @Override
 public boolean completeMaintenance(DateTime completionDate) {
  if (getPropertyStatus() == PropertyStatus.UnderMaintenance) {
   setPropertyStatus(PropertyStatus.Available);
   return true;
  } else {
   return false;
  }
 }
}