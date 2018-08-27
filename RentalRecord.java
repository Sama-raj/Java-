package assignment1;

public class RentalRecord {
 private String recordId;
 private DateTime rentDate;
 private DateTime estimatedReturnDate;
 private DateTime actualReturnDate;
 private double rentalFee;
 private double lateFee;

 public RentalRecord(String recordId, DateTime rentDate, DateTime estimatedReturnDate) {
  this.recordId = recordId;
  this.rentDate = rentDate;
  this.estimatedReturnDate = estimatedReturnDate;
 }



 @Override
 public String toString() {
  String output = recordId + ":" + rentDate + ":" + estimatedReturnDate + ":";
  if (actualReturnDate != null) {
   output += actualReturnDate + ":" + String.format("%.2f", getRentalFee()) + ":" + String.format("%.2f", getLateFee());
  } else {
   output += "none:none:none";
  }
  return output;
 }

 public String getDetails() {
	 //Storing the Rental Record Details
  String output = String.format("%-30s%s\n", "Record ID:", getRecordId());
  output += String.format("%-30s%s\n", "Rent Date:", getRentDate());
  output += String.format("%-30s%s\n", "Estimated Return Date:", getEstimatedReturnDate());
  if (actualReturnDate != null) {
   output += String.format("%-30s%s\n", "Actual Return Date:", getActualReturnDate());
   output += String.format("%-30s%.2f\n", "Rental Fee:", getRentalFee());
   output += String.format("%-30s%.2f", "Late Fee: ", getLateFee());
  }
  return output;
 }

 public String getRecordId() {
  return recordId;
 }

 public void setRecordId(String recordId) {
  this.recordId = recordId;
 }

 public DateTime getRentDate() {
  return rentDate;
 }

 public void setRentDate(DateTime rentDate) {
  this.rentDate = rentDate;
 }

 public DateTime getEstimatedReturnDate() {
  return estimatedReturnDate;
 }

 public void setEstimatedReturnDate(DateTime estimatedReturnDate) {
  this.estimatedReturnDate = estimatedReturnDate;
 }

 public DateTime getActualReturnDate() {
  return actualReturnDate;
 }

 public void setActualReturnDate(DateTime actualReturnDate) {
  this.actualReturnDate = actualReturnDate;
 }

 public double getRentalFee() {
  return rentalFee;
 }

 public void setRentalFee(double rentalFee) {
  this.rentalFee = rentalFee;
 }

 public double getLateFee() {
  return lateFee;
 }

 public void setLateFee(double lateFee) {
  this.lateFee = lateFee;
 }
 public void setActualReturnDate_LateFee_RentalFee(DateTime actualReturnDate, double lateFee, double rentalFee) {

  this.lateFee = lateFee;
  this.rentalFee = rentalFee;
  this.actualReturnDate = actualReturnDate;

 }
}