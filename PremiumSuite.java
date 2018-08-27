package assignment1;


public class PremiumSuite extends RentalProperty {
 private DateTime lastMaintenanceData;
 private double PER_DAY_RENT = 554;
 private double PER_DAY_LATE_FEE = 662;

 public PremiumSuite(String propertyId, int streetNumber, String streetName, String suburb, int numberOfBedroom, PropertyType propertyType, DateTime lastMaintenanceData) {
  super(propertyId, streetNumber, streetName, suburb, numberOfBedroom, propertyType);
  this.lastMaintenanceData = lastMaintenanceData;
  setPropertyStatus(PropertyStatus.Available);
 }


 private boolean isValidRentPeriod(DateTime rentDate) {
  DateTime newMaintenacneDate = new DateTime(lastMaintenanceData, 10);
  int diff = DateTime.diffDays(rentDate, newMaintenacneDate);
  return diff > 0;
 }

 @Override
 public boolean rent(String customerId, DateTime rentDate, int numOfRentDay) {
  boolean isAvailableForRent;
  if (getPropertyStatus() == PropertyStatus.Available) {
   if (numOfRentDay >= 1 && isValidRentPeriod(rentDate)) {
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
   rentalFee = daysWithoutLateFee * PER_DAY_RENT;
   lateFee = lateDays * PER_DAY_LATE_FEE;
  } else // if return date is earlier
  {
   rentalFee = diffDate * PER_DAY_RENT;
  }
  currentRentalRecord.setActualReturnDate_LateFee_RentalFee(returnDate, lateFee, rentalFee);

  setPropertyStatus(PropertyStatus.Available);
  return true;
 }



 @Override
 public boolean completeMaintenance(DateTime completionDate) {
  if (getPropertyStatus() == PropertyStatus.UnderMaintenance) {
   setPropertyStatus(PropertyStatus.Available);
   lastMaintenanceData = completionDate;
   return true;
  } else {
   return false;
  }
 }

 @Override
 public String getDetails() {
  String output = String.format("%-20s%s\n", " Property ID:", getPropertyId());
  output += String.format("%-20s%s\n", "Address:", getStreetNumber() + " " + getStreetName() + " " + getSuburb());
  output += String.format("%-20s%s\n", "Type:", getPropertyType().name());
  output += String.format("%-20s%d\n", "Bedroom: ", getNumberOfBedroom());
  output += String.format("%-20s%s\n", "Status:", getPropertyStatus().name());
  output += String.format("%-20s%s\n", "Last maintenance:", lastMaintenanceData.toString());
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

 public String toString() {
  return super.toString() + ":" + lastMaintenanceData;
 }

}