package com.capgemini.hotelbooking.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.capgemini.hotelbooking.bean.BookingBean;
import com.capgemini.hotelbooking.bean.HotelBean;
import com.capgemini.hotelbooking.bean.RoomBean;
import com.capgemini.hotelbooking.bean.UserBean;
import com.capgemini.hotelbooking.exception.BookingException;
import com.capgemini.hotelbooking.service.AdminService;
import com.capgemini.hotelbooking.service.CommonService;
import com.capgemini.hotelbooking.service.CustomerService;
import com.capgemini.hotelbooking.service.IAdminService;
import com.capgemini.hotelbooking.service.ICommonService;
import com.capgemini.hotelbooking.service.ICustomerService;

public class Client {
	public static void main(String[] args) throws BookingException {
		Scanner scanner = new Scanner(System.in); 
		System.out.println("Welcome to ChaloGhume.com\n\n\n");
		System.out.println("-------------------------");
		
		boolean exitFlag = false;
		
		do{
			System.out.println("\n\n1.Login");
			System.out.println("2.Sign Up");
			System.out.println("3.Exit\n");
			System.out.print("Please select one of the options : ");
			int option = scanner.nextInt();
			ICommonService commonService = null;
			switch(option){
				case 1:
					System.out.print("\nUsername : ");
					String username = scanner.next();
					System.out.print("Password : ");
					String password = scanner.next();
					commonService = new CommonService();
					UserBean userBean = commonService.login(username, password);
					if(userBean == null){
						System.out.println("Please enter valid credentials and try again.");
					}
					else{
						System.out.println("\nWelcome " + userBean.getUserName());
						if("admin".equals(userBean.getRole())){
							boolean adminExitFlag = false;
							do{
								IAdminService adminService = new AdminService();
								System.out.println("\n1.Add new hotel");
								System.out.println("2.Update existing hotel");
								System.out.println("3.Delete a hotel");
								System.out.println("4.Add new room");
								System.out.println("5.Update existing room");
								System.out.println("6.Delete a room");
								System.out.println("7.View all hotels");
								System.out.println("8.View bookings of specific hotel");
								System.out.println("9.View bookings of specific date");
								System.out.println("10.View guest list for a specific hotel");
								System.out.println("11.Logout");
								System.out.print("\nPlease select one of the options : ");
								option = scanner.nextInt();
								switch(option){
									case 1:
										boolean cityFlag=false,phoneFlag=false,ratingFlag=false,emailFlag=false,faxFlag=false;
										String city=null,primaryPhone=null,secondaryPhone=null,rating=null,email=null,fax=null;
										float averageRatePerNight=0;
										int hotelId = 0;
										System.out.print("\nPlease enter hotel name : ");
										String hotelName = scanner.next();
										
										do{
											System.out.print("\nPlease enter city (eg. Mumbai) : ");
											city= scanner.next();
											cityFlag = AdminService.validateCity(city);
											if(cityFlag==false){
												System.out.println("\nPlease enter valid city!");
											}
										}while(cityFlag==false);
										
										System.out.print("\nPlease enter address of hotel : ");
										String address = scanner.next();
										
										System.out.print("\nPlease enter description about hotel : ");
										String description = scanner.next();
										
										System.out.print("\nPlease enter the estimate average rate of room per night : ");
										averageRatePerNight = scanner.nextFloat();
										
										do{
											System.out.print("\nPlease enter primary phone number : ");
											primaryPhone = scanner.next();
											phoneFlag = AdminService.validateMobileNumber(primaryPhone);
											if(phoneFlag==false){
												System.out.println("\nPlease enter valid phone number!");
											}
										}while(phoneFlag==false);
										
										do{
											System.out.print("\nPlease enter secondary phone number : ");
											secondaryPhone = scanner.next();
											phoneFlag = AdminService.validateMobileNumber(secondaryPhone);
											if(phoneFlag==false){
												System.out.println("\nPlease enter valid phone number");
											}
										}while(phoneFlag==false);
										
										do{
											System.out.print("\nPlease enter the rating of hotel : ");
											rating = scanner.next();
											ratingFlag = AdminService.validateRating(rating);
											if(ratingFlag==false){
												System.out.println("\nPlease enter valid rating!");
											}
										}while(ratingFlag==false);
										
										do{
											System.out.print("\nPlease enter hotel email : ");
											email = scanner.next();
											emailFlag = AdminService.validateEmail(email);
											if(emailFlag==false){
												System.out.println("\nPlease enter valid email!");
											}
										}while(emailFlag==false);
										
										do{
											System.out.print("\nPlease enter hotel fax : ");
											fax = scanner.next();
											faxFlag = AdminService.validateFax(fax);
											if(faxFlag==false){
												System.out.println("\nPlease enter valid fax!");
											}
										}while(faxFlag==false);
										
										
										HotelBean hotelBean = new HotelBean(hotelId, city, hotelName, address, description, 
												averageRatePerNight, primaryPhone, secondaryPhone, rating, email, fax);
										hotelId = adminService.addHotelDetails(hotelBean);
										
										if(hotelId > 0){
											System.out.println("\nHotel registration successful.");
										}
										else{
											System.out.println("\nHotel registration error.");
										}
										
										break;
									case 2:
										String[] attributeNames = {"","CITY","HOTEL_NAME","ADDRESS","DESCRIPTION","AVG_RATE_PER_NIGHT",
																	"PHONE_NO1","PHONE_NO2","RATING","EMAIL","FAX"};
										List<HotelBean> hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											boolean hotelIdFlag = false;
											int hotelID = 0;
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
											
											do{
												System.out.print("\nPlease enter hotel ID for which you want to update a detail : ");
												hotelID = scanner.nextInt();
												hotelIdFlag = AdminService.validateHotelID(Integer.toString(hotelID));
												if(hotelIdFlag==false){
													System.out.println("\nPlease enter valid hotel ID!");
												}
											}while(hotelIdFlag==false);
											
											boolean optionFlag=false;
											int attributeOption = 0;
											do{
												System.out.println("\n1.City");
												System.out.println("2.Hotel Name");
												System.out.println("3.Address");
												System.out.println("4.Description");
												System.out.println("5.Average Rate Per Night");
												System.out.println("6.Primary Phone Number ");
												System.out.println("7.Secondary Phone Number");
												System.out.println("8.Rating");
												System.out.println("9.Email");
												System.out.println("10.Fax\n");
												System.out.print("Please enter your choice from the list : ");
												attributeOption = scanner.nextInt();
												
												if(attributeOption>=1 && attributeOption<=10){
													optionFlag = true;
												}
												if(optionFlag==false){
													System.out.println("\nPlease enter valid option!");
												}
											}while(optionFlag==false);
											
											System.out.print("\nPlease enter the new value : ");
											String attributeValue = scanner.next();
											
											int recsAffected = adminService.updateHotelDetails(hotelID, attributeNames[attributeOption], attributeValue);
											if(recsAffected == 0){
												throw new BookingException("Update Error.");
											}
											else{
												System.out.println("\nHotel Details successfully updated.");
											}
											
										}
										else{
											System.out.println("\nNo hotels found.");
										}
										break;
									case 3:
										hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											boolean hotelIdFlag = false;
											int hotelID = 0;
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
											
											do{
												System.out.print("\nPlease enter the hotel ID for the hotels you want to delete : ");
												hotelID = scanner.nextInt();
												hotelIdFlag = AdminService.validateHotelID(Integer.toString(hotelID));
												if(hotelIdFlag==false){
													System.out.println("\nPlease enter valid hotel ID!");
												}
											}while(hotelIdFlag==false);
											
											boolean statusCode = adminService.deleteHotelDetails(hotelID);
											if(statusCode){
												System.out.println("\nHotel Details successfully deleted.");
											}
											else
											{
												throw new BookingException("Hotel deletion error. Please try again later.");
											}
										}
										else
										{
											System.out.println("\nNo hotels found.");
										}
										break;
									case 4:
										int roomId = 0;
										hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											boolean hotelIdFlag = false, roomNumberFlag=false, optionFlag=false, rateFlag = false, photoFlag = false;
											int hotelID = 0;
											String roomNumber = null, photo = null;
											float perNightRate = 0;
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
											
											do{
												System.out.print("\nPlease enter the hotel ID in which you want to add a room : ");
												hotelID = scanner.nextInt();
												hotelIdFlag = AdminService.validateHotelID(Integer.toString(hotelID));
												if(hotelIdFlag==false){
													System.out.println("\nPlease enter valid hotel ID!");
												}
											}while(hotelIdFlag==false);
											
											do{
												System.out.print("\nPlease enter room number :");
												roomNumber = scanner.next();
												roomNumberFlag = AdminService.validateRoomNumber(roomNumber);
												if(roomNumberFlag==false){
													System.out.println("\nPlease enter valid room number!");
												}
											}while(roomNumberFlag==false);
											
											String[] roomTypes = {"","Non AC","AC Prime","AC Deluxe","Super AC Deluxe"};
											do{
												System.out.println("1.Non AC");
												System.out.println("2.AC Prime");
												System.out.println("3.AC Deluxe");
												System.out.println("4.Super AC Deluxe");
												System.out.print("\nPlease enter option of room type :");
												option = scanner.nextInt();
												if(option>=1 && option<=4){
													optionFlag = true;
												}
												if(optionFlag==false){
													System.out.println("\nPlease enter valid option!");
												}
											}while(optionFlag==false);
											
											do{
												System.out.print("\nPlease enter cost/night of the room :");
												perNightRate = scanner.nextFloat();
												rateFlag = AdminService.validatePerNightRate(Float.toString(perNightRate));
												if(rateFlag==false){
													System.out.println("\nPlease enter valid amount!");
												}
											}while(rateFlag==false);
											
											do{
												System.out.print("\nPlease enter the filename of the photo :");
												photo = scanner.next();
												photoFlag = AdminService.validatePhoto(photo);
												if(photoFlag==false){
													System.out.println("\nPlease enter valid filename!");
												}
											}while(photoFlag==false);
											
											RoomBean roomBean = new RoomBean(hotelID, roomId, roomNumber, roomTypes[option], perNightRate, true, photo);
											roomId = adminService.addRoomDetails(roomBean);
											if(roomId > 0){
												System.out.println("\nRoom registration successful.");
											}
											else{
												System.out.println("\nRoom registration error.");
											}
										}
										else{
											System.out.println("\nNo hotels found.");
										}
										break;
										
									case 5:
										String[] attributeList = {"","ROOM_NO","ROOM_TYPE","PER_NIGHT_RATE","PHOTO"};
										List<RoomBean> roomList = commonService.retrieveRooms();
										if(roomList.size() > 0){
											boolean roomIdFlag = false, optionFlag = false;
											int roomID = 0, attributeOption = 0;
											System.out.println("\nFollowing are the list of rooms : \n");
											System.out.println(roomList);
											
											do{
												System.out.print("\n\nPlease enter room ID for which you want to update a detail : ");
												roomID = scanner.nextInt();
												roomIdFlag = AdminService.validateRoomID(Integer.toString(roomID));
												if(roomIdFlag==false){
													System.out.println("\nPlease enter valid room ID");
												}
											}while(roomIdFlag==false);
											
											do{
												System.out.println("\n\n1.Room Number");
												System.out.println("2.Room Type");
												System.out.println("3.Cost per night");
												System.out.println("4.Photo Filename\n");
												System.out.print("Please enter your choice from the list : ");
												attributeOption = scanner.nextInt();
												if(attributeOption>=1 && attributeOption<=4){
													optionFlag = true;
												}
												if(optionFlag==false){
													System.out.println("\nPlease enter valid option!");
												}
											}while(optionFlag==false);
											
											System.out.print("\nPlease enter the new value : ");
											String attributeValue = scanner.next();
											
											int recsAffected = adminService.updateRoomDetails(roomID, attributeList[attributeOption], attributeValue);
											if(recsAffected == 0){
												throw new BookingException("\nUpdate Error.");
											}
											else{
												System.out.println("\nRoom Details successfully updated.");
											}
										}
										else{
											System.out.println("\nNo rooms found.");
										}
										break;
									case 6:
										roomList = commonService.retrieveRooms();
										if(roomList.size() > 0){
											int roomID = 0;
											boolean roomIdFlag = false; 
											System.out.println("\nFollowing are the list of rooms : \n");
											System.out.println(roomList);
											
											do{
												System.out.print("\nPlease enter the room ID for the room you want to delete : ");
												roomID = scanner.nextInt();
												roomIdFlag = AdminService.validateRoomID(Integer.toString(roomID));
												if(roomIdFlag==false){
													System.out.println("\nPlease enter valid room ID");
												}
											}while(roomIdFlag==false);
											
											boolean statusCode = adminService.deleteRoomDetails(roomID);
											if(statusCode){
												System.out.println("\nRoom Details successfully deleted.");
											}
											else{
												throw new BookingException("\nRoom deletion error. Please try again later.");
											}
										}
										else{
											System.out.println("\nNo rooms found.");
										}
										break;
									case 7:
										hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
										}
										else{
											System.out.println("\nNo hotels found.");
										}
										break;
									case 8:
										hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											boolean hotelIdFlag = false;
											int hotelID = 0;
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
											
											do{
												System.out.print("\nPlease enter the hotelID from the above list for which you want to view bookings : ");
												hotelID = scanner.nextInt();
												hotelIdFlag = AdminService.validateHotelID(Integer.toString(hotelID));
												if(hotelIdFlag==false){
													System.out.println("\nPlease enter valid hotel ID");
												}
											}while(hotelIdFlag==false);
											
											List<BookingBean> bookingList = adminService.viewBookingsOfHotel(hotelID);
											if(bookingList.size() > 0){
												System.out.println("\nFollowing are the list of bookings for hotel ID " + hotelID + ": \n");
												System.out.println(bookingList);
											}
											else{
												System.out.println("\nNo bookings found.");
											}
										}
										else{
											System.out.println("\nNo hotels found.");
										}
										break;
									case 9:
										boolean dateFlag = false;
										String targetDate = null;
										do{
											System.out.print("\nPlease enter the date in format(dd-mm-yyyy) for which you want to view bookings : ");
											targetDate = scanner.next();
											dateFlag = AdminService.validateDate(targetDate);
											if(dateFlag==false){
												System.out.println("\nPlease enter valid date in the specified!");
											}
										}while(dateFlag==false);
										
										DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
										LocalDate parsedBookedFromDate = LocalDate.parse(targetDate, formatter);
										
										List<BookingBean> bookingList = adminService.viewBookingsOfDate(parsedBookedFromDate);
										if(bookingList.size() > 0){
											System.out.println("\nFollowing are the list of bookings for date " + targetDate + ": \n");
											System.out.println(bookingList);
										}
										else{
											System.out.println("\nNo bookings found.");
										}
										break;
									case 10:
										hotelList = commonService.retrieveHotels();
										if(hotelList.size() > 0){
											boolean hotelIdFlag = false;
											int hotelID = 0;
											System.out.println("\nFollowing are the list of hotels : \n");
											System.out.println(hotelList);
											
											do{
												System.out.print("\nPlease enter the hotelID from the above list for which you want to view guest list : ");
												hotelID = scanner.nextInt();
												hotelIdFlag = AdminService.validateHotelID(Integer.toString(hotelID));
												if(hotelIdFlag==false){
													System.out.println("\nPlease enter valid hotel ID!");
												}
											}while(hotelIdFlag==false);
											
											List<UserBean> guestList = adminService.viewGuestList(hotelID);
											if(guestList.size() > 0){
												System.out.println("\nFollowing are the list of guests for hotel ID " + hotelID + ": \n");
												System.out.println(guestList);
											}
											else{
												System.out.println("\nNo guest list found.");
											}
										}
										else{
											System.out.println("\nNo hotels found.");
										}
										break;
									case 11:
										System.out.println("\nAll changes saved. Logging you out " + userBean.getUserName() +"...\n");
										adminExitFlag = true;
										break;
									default:
										System.out.println("\nPlease select valid option!");
										break;
								}
							}while(adminExitFlag == false);
						}
						else if("customer".equals(userBean.getRole())){
							boolean customerExitFlag = false;
							do{
								ICustomerService customerService = new CustomerService();
								System.out.println("\n\n1.Search for hotel rooms");
								System.out.println("2.Book Hotel Rooms");
								System.out.println("3.View Booking status");
								System.out.println("4.Log out\n");
								System.out.print("Please select one of the options : ");
								option = scanner.nextInt();
								switch(option){
									case 1:
										boolean cityFlag = false;
										String city = null;
										do{
											System.out.print("\nEnter the city in which you want to find a room : ");
											city = scanner.next();
											cityFlag = CustomerService.validateCity(city);
											if(cityFlag==false){
												System.out.println("\nPlease enter valid city!(eg. Pune)");
											}
										}while(cityFlag==false);
										
										List<RoomBean> roomList = customerService.searchAvailableRooms(city.toLowerCase());
										if(roomList.size() > 0){
											System.out.println(roomList);
										}
										else{
											System.out.println("\nNo rooms found.");
										}
										break;
									case 2:
										roomList = commonService.retrieveRooms();
										if(roomList.size() > 0){
											int bookingID = 0,roomID = 0, numAdults = 0, numChildren = 0;
											boolean roomIdFlag = false, numAdultsFlag=false, numChildrenFlag = false, dateFlag = false;
											String bookedFrom = null, bookedTo = null;
											
											List<RoomBean> tempRoomList = new ArrayList<RoomBean>();
											for(RoomBean room : roomList){
												if(room.isAvailable()){
													tempRoomList.add(room);
												}
											}
											
											if(tempRoomList.size() > 0){
												System.out.println("\nFollowing are the list of rooms : \n");
												System.out.println(tempRoomList);
												do{
													System.out.print("\nPlease enter the room ID from the above list : ");
													roomID = scanner.nextInt();
													roomIdFlag = CustomerService.validateRoomID(Integer.toString(roomID));
													if(roomIdFlag){
														roomIdFlag = false;
														for(RoomBean room : tempRoomList){
															if(room.getRoomID() == roomID){
																roomIdFlag = true;
															}
														}
													}
													if(roomIdFlag==false){
														System.out.println("\nPlease enter valid room ID!");
													}
												}while(roomIdFlag==false);
												
												int userID = userBean.getUserID(); 
												DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
												
												long differenceInDays = 0;
												LocalDate parsedBookedToDate = null, parsedBookedFromDate = null;
												do{
													do{
														System.out.print("\nEnter Check-In date (dd-mm-yyyy) : ");
														bookedFrom = scanner.next();
														dateFlag = CustomerService.validateDate(bookedFrom);
														if(dateFlag==false){
															System.out.println("\nPlease enter valid date in the specified format!");
														}
													}while(dateFlag == false);
													
													parsedBookedFromDate = LocalDate.parse(bookedFrom, formatter);
													
													do{
														System.out.print("Enter Check-Out date (dd-mm-yyyy): ");
														bookedTo = scanner.next();
														dateFlag = CustomerService.validateDate(bookedTo);
														if(dateFlag==false){
															System.out.println("\nPlease enter valid date in the specified format!");
														}
													}while(dateFlag == false);
													
													parsedBookedToDate = LocalDate.parse(bookedTo, formatter);
													
													differenceInDays = parsedBookedFromDate.until(parsedBookedToDate, ChronoUnit.DAYS);
													
													if(differenceInDays <= 0){
														System.out.println("\nPlease enter valid check out date!");
													}
													
												}while(differenceInDays<=0);
												
												do{
													System.out.print("\nEnter number of adults : ");
													numAdults = scanner.nextInt();
													numAdultsFlag = CustomerService.validateNumAdults(Integer.toString(numAdults));
													if(numAdultsFlag==false){
														System.out.println("\nPlease enter valid number!");
													}
												}while(numAdultsFlag==false);
												
												do{
													System.out.print("\nEnter number of children : ");
													numChildren = scanner.nextInt();
													numChildrenFlag = CustomerService.validateNumChildren(Integer.toString(numChildren));
													if(numChildrenFlag==false){
														System.out.println("\nPlease enter valid number!");
													}
												}while(numChildrenFlag==false);
												
												float amount = 0;
												
												BookingBean bookingBean = new BookingBean(bookingID, roomID, userID, numAdults, numChildren, amount, parsedBookedFromDate, parsedBookedToDate);
												bookingID = customerService.bookRoom(bookingBean);
												if(bookingID>0){
													System.out.println("\nBooking is Successful! Your booking ID is: "+bookingID);
												}
												else{
													System.out.println("\nBooking failed");
												}
											}
											else{
												System.out.println("\nNo rooms found.");
											}
										}
										break;
									case 3:
										List<Integer> bookingIDs = customerService.getBookingIDs(userBean.getUserID());
										int bookingId =0 ;
										System.out.println("\nList of your Booking Reference Numbers:");
										for(Integer id : bookingIDs){
											System.out.println(id + "\n");
										}
										System.out.print("Enter booking id : ");
										bookingId = scanner.nextInt();
										List<List<Object>> bookingList = customerService.viewBookingStatus(bookingId,userBean.getUserID());
										if(bookingList.size() > 0){
											System.out.println("\nYour booking status is : ");
											for(List<Object> booking : bookingList){
												System.out.println("Room number : " + booking.get(0));
												System.out.println("Booking ID : " + booking.get(1));
												System.out.println("Check in Date : " + booking.get(2));
												System.out.println("Check out Date : " + booking.get(3));
											}
										}
										else{
											System.out.println("No bookings done. Book rooms and get exciting offers.");
										}
										break;
									case 4:
										System.out.println("\nAll changes saved. Logging you out " + userBean.getUserName() + "...\n");
										customerExitFlag = true;
										break;
									default:
										System.out.println("\nPlease select valid option!");
										break;
								}
							}while(customerExitFlag == false);
						}
					}
					break;
				case 2:
					String role = null, mobileNumber = null, phoneNumber = null, email = null;
					int userID = 0;
					boolean userNameFlag = false, mobileNumberFlag = false, emailFlag = false;
					commonService = new CommonService();
					List<String> userNameList = commonService.retrieveUserNames();
					
					do{
						System.out.print("\nPlease enter a unique username : ");
						username = scanner.next();
						if(userNameList.contains(username)){
							userNameFlag = false;
							System.out.println("\nUsername already exists!");
						}
						else{
							userNameFlag = true;
						}
					}while(userNameFlag==false);
					
					System.out.print("\nPlease enter password : ");
					password = scanner.next();
					
					do{
						System.out.print("\nPlease enter your mobile number : ");
						mobileNumber = scanner.next();
						mobileNumberFlag = CommonService.validateMobileNumber(mobileNumber);
						if(mobileNumberFlag==false){
							System.out.println("\nPlease enter valid mobile number!");
						}
					}while(mobileNumberFlag==false);
					
					do{
						System.out.print("\nPlease enter your phone number : ");
						phoneNumber = scanner.next();
						mobileNumberFlag = CommonService.validateMobileNumber(phoneNumber);
						if(mobileNumberFlag==false){
							System.out.println("\nPlease enter valid phone number!");
						}
					}while(mobileNumberFlag==false);
					
					System.out.print("\nPlease enter your address : ");
					String address = scanner.next();
					
					do{
						System.out.print("\nPlease enter your email : ");
						email = scanner.next();
						emailFlag = CommonService.validateEmail(email);
						if(emailFlag==false){
							System.out.println("\nPlease enter valid email!");
						}
					}while(emailFlag==false);
					
					userBean = new UserBean(userID, password, role, username, mobileNumber, address, email, phoneNumber);
					
					userID = commonService.registerUser(userBean);
					if(userID <= 0){
						throw new BookingException("Problem in signing up the user.");
					}
					else{
						System.out.println("Sign up successful. Your user ID is " + userID);
					}
					break;
				case 3:
					System.out.println("\nThank you. Please visit again.");
					exitFlag = true;
					break;
				default:
					System.out.println("\nPlease select valid option!");
					break;
			}
		}while(exitFlag == false);
		scanner.close();
	}
}
