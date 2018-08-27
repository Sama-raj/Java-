package assignment1;

public abstract class RentalProperty {
 private RentalRecord[] rentalRecord = new RentalRecord[10];
 private int rentalRecordIndex = 0;
 private String propertyId;
 private int streetNumber;
 private String streetName;
 private String suburb;
 private int numberOfBedroom;
 private PropertyType propertyType;
 private PropertyStatus propertyStatus;

 public RentalProperty(String propertyId, int streetNumber, String streetName, String suburb, int numberOfBedroom, PropertyType propertyType) {
  this.propertyId = propertyId;
  this.streetNumber = streetNumber;
  this.streetName = streetName;
  this.suburb = suburb;
  this.numberOfBedroom = numberOfBedroom;
  this.propertyType = propertyType;
  this.propertyStatus = PropertyStatus.Available;
 }



 public abstract boolean rent(String customerId, DateTime rentDate, int numOfRentDay);

 public abstract boolean returnDate(DateTime returnDate);

 public boolean performMaintenance() {
  if (getPropertyStatus() == PropertyStatus.Rented) {
   return false;
  } else {
   setPropertyStatus(PropertyStatus.UnderMaintenance);
   return true;
  }
 }

 public abstract boolean completeMaintenance(DateTime completionDate);

 @Override
 public String toString() {
  String output = propertyId + ":" + streetNumber + ":" + streetName + ":" + suburb + ":" + propertyType.name() + ":" + numberOfBedroom + ":" + propertyStatus.name();
  return output;
 }

 public abstract String getDetails();

 public int getRentalRecordIndex() {
  return rentalRecordIndex;
 }

 public void setRentalRecordIndex(int rentalRecordIndex) {
  this.rentalRecordIndex = rentalRecordIndex;
 }

 public String getPropertyId() {
  return propertyId;
 }

 public void setPropertyId(String propertyId) {
  this.propertyId = propertyId;
 }

 public int getStreetNumber() {
  return streetNumber;
 }

 public void setStreetNumber(int streetNumber) {
  this.streetNumber = streetNumber;
 }

 public String getStreetName() {
  return streetName;
 }

 public void setStreetName(String streetName) {
  this.streetName = streetName;
 }

 public String getSuburb() {
  return suburb;
 }

 public void setSuburb(String suburb) {
  this.suburb = suburb;
 }

 public int getNumberOfBedroom() {
  return numberOfBedroom;
 }

 public void setNumberOfBedroom(int numberOfBedroom) {
  this.numberOfBedroom = numberOfBedroom;
 }

 public PropertyType getPropertyType() {
  return propertyType;
 }

 public void setPropertyType(PropertyType propertyType) {
  this.propertyType = propertyType;
 }

 public PropertyStatus getPropertyStatus() {
  return propertyStatus;
 }

 public void setPropertyStatus(PropertyStatus propertyStatus) {
  this.propertyStatus = propertyStatus;
 }

 public RentalRecord[] getRentalRecord() {
  return rentalRecord;
 }

 public void addNewRentalRecord(RentalRecord record) {
  //Array of 10 rental records
  shiftRecordByOne();

  rentalRecord[0] = record;
  if (rentalRecordIndex != 10) {
   rentalRecordIndex++;
  }
 }

 private void shiftRecordByOne() {
  for (int i = 8; i >= 0; i--) {
   rentalRecord[i + 1] = rentalRecord[i];
  }
 }

 enum PropertyType {
  Apartment,
  PremiumSuite;
 }

 enum PropertyStatus {
  Available,
  Rented,
  UnderMaintenance;
 }
}