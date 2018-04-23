package online.hotelmanagement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import online.hotelmanagement.entity.Booking;
import online.hotelmanagement.entity.RoomAmenity;
import online.hotelmanagement.entity.RoomImages;
import online.hotelmanagement.entity.RoomType;
import online.hotelmanagement.repository.BookingRepository;
import online.hotelmanagement.repository.RoomAmenityRepository;
import online.hotelmanagement.repository.RoomImagesRepository;
import online.hotelmanagement.repository.RoomTypeRepository;

@Service
public class RoomTypeService {
	@Autowired
	private RoomTypeRepository roomTypeRepo;
	@Autowired
	private BookingRepository bookingRepo;
	@Autowired
	private RoomImagesRepository roomImagesRepo;
	@Autowired
	private RoomAmenityRepository roomAmenityRepo;

	public void saveRoomType(RoomType roomType) {
		if (roomType.getRoomTypeId() != 0 && roomType.getRoomTypeId() != null) {
			RoomType rt = roomTypeRepo.findByRoomTypeId(roomType.getRoomTypeId());
			if (rt != null) {
				roomType.setImagePath(rt.getImagePath());
			}
		}
		roomTypeRepo.save(roomType);
	}

	public void deleteRoomTypeImage(Long imageId) {
		roomImagesRepo.delete(imageId);
	}

	public RoomType getRoomType(Long roomTypeId) {
		return roomTypeRepo.findByRoomTypeId(roomTypeId);
	}

	public List<RoomType> getAllRoomTypes() {
		return roomTypeRepo.findAll();
	}

	public List<RoomType> searchRoomTypes(LocalDate startDate, LocalDate endDate, int noOfAdults, int noOfChildren,
			int noOfRooms) {
		List<RoomType> allRoomTypes = roomTypeRepo.findAll();
		Iterator<RoomType> it = allRoomTypes.iterator();
		while (it.hasNext()) {
			RoomType roomType = it.next();
			int noOfRoomsInRoomType = roomType.getNoOfRooms();
			int noOfBookedRoomsInRoomType = 0;
			List<Booking> allBookingsInRoomType = bookingRepo.findByBookingStatusAndRoomTypeId(1,
					roomType.getRoomTypeId());
			for (Booking booking : allBookingsInRoomType) {
				if (startDate.equals(booking.getStartDate()) || startDate.equals(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (startDate.isAfter(booking.getStartDate()) && startDate.isBefore(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (endDate.equals(booking.getStartDate()) || endDate.equals(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (endDate.isAfter(booking.getStartDate()) && endDate.isBefore(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (startDate.isBefore(booking.getStartDate()) && endDate.isAfter(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				}
			}
			if (noOfRoomsInRoomType - noOfBookedRoomsInRoomType < noOfRooms) {
				it.remove();
			}
		}
		return allRoomTypes;
	}

	public List<RoomImages> getRoomImages(Long roomTypeId) {
		return roomImagesRepo.findRoomImagesByRoomTypeId(roomTypeId);
	}

	public List<RoomAmenity> getRoomTypeAmenities(Long roomTypeId) {
		return roomAmenityRepo.findAmenityByRoomTypeId(roomTypeId);
	}

	public List<RoomImages> getRoomImagesForPopup(Long roomTypeId) {
		return roomImagesRepo.findRoomImagesByRoomTypeId(roomTypeId);
	}
	
	public List<RoomType> searchEventHalls(LocalDate startDate, LocalDate endDate, int noOfGuests) {
		List<RoomType> allHalls = roomTypeRepo.findByEventRoomTypeId(noOfGuests);
		Iterator<RoomType> it = allHalls.iterator();
		while (it.hasNext()) {
			RoomType roomType = it.next();
			int noOfRoomsInRoomType = roomType.getNoOfRooms();
			int noOfBookedRoomsInRoomType = 0;
			List<Booking> allBookingsInRoomType = bookingRepo.findByBookingStatusAndRoomTypeId(1,
					roomType.getRoomTypeId());
			for (Booking booking : allBookingsInRoomType) {
				if (startDate.equals(booking.getStartDate()) || startDate.equals(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (startDate.isAfter(booking.getStartDate()) && startDate.isBefore(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (endDate.equals(booking.getStartDate()) || endDate.equals(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (endDate.isAfter(booking.getStartDate()) && endDate.isBefore(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				} else if (startDate.isBefore(booking.getStartDate()) && endDate.isAfter(booking.getEndDate())) {
					noOfBookedRoomsInRoomType += booking.getNoOfRooms();
				}
			}
			if (noOfRoomsInRoomType - noOfBookedRoomsInRoomType < 1) {
				it.remove();
			}
		}
		return allHalls;
	}
	
	private void addRoomDefaultRoomTypes(){
		List<RoomType> allRoomTypes = new ArrayList<RoomType>();
		
		RoomType rm1 = new RoomType();
		rm1.setRoomTypeId((long) 1);
		rm1.setRoomTypeName("Opal Executive Suite");
		rm1.setDescription("Indulge in a regal 630 sq. ft suite with king bed and living area. Replete with fruit platter, modern décor, well-appointed amenities reinforces a sense of luxury and comfort. This is a standard suite. See World of Opal program terms for upgrade eligibility.");
		rm1.setNoOfMaxAdults(2);
		rm1.setNoOfMaxChildren(4);
		rm1.setNoOfRooms(10);
		rm1.setRoomCharge(4000);
		rm1.setImagePath("3.jpg");
		allRoomTypes.add(rm1);
		
		RoomType rm2 = new RoomType();
		rm2.setRoomTypeId((long) 2);
		rm2.setRoomTypeName("Opal Garden Suite");
		rm2.setDescription("This chic 530-600 sq ft suite situated on the 10th floor, offers private garden giving the privacy you desire. Pampering amenities include complimentary fruit, mineral water and soft bathrobes and slippers. This is a premium suite. See World of Opal program terms for upgrade eligibility.");
		rm2.setNoOfMaxAdults(2);
		rm2.setNoOfMaxChildren(4);
		rm2.setNoOfRooms(20);
		rm2.setRoomCharge(6000);
		rm1.setImagePath("4.jpg");
		allRoomTypes.add(rm2);
		
		RoomType rm3 = new RoomType();
		rm3.setRoomTypeId((long) 3);
		rm3.setRoomTypeName("Opal King");
		rm3.setDescription("Unwind in the cozy 270 sq.ft room with king bed and contemporary design. Experience a sense of luxury with a walk-wardrobe, while wired and wireless internet keeps visitors connected.");
		rm3.setNoOfMaxAdults(2);
		rm3.setNoOfMaxChildren(4);
		rm3.setNoOfRooms(10);
		rm3.setRoomCharge(8000);
		rm1.setImagePath("5.jpg");
		allRoomTypes.add(rm3);
		
		RoomType rm4 = new RoomType();
		rm4.setRoomTypeId((long) 4);
		rm4.setRoomTypeName("Opal Twin");
		rm4.setDescription("Unwind in the cozy 270 sq. ft room with two queen beds and contemporary design. Experience a sense of luxury with a walk-in shower, while wired and wireless Internet keep visitors connected.");
		rm4.setNoOfMaxAdults(2);
		rm4.setNoOfMaxChildren(4);
		rm4.setNoOfRooms(10);
		rm4.setRoomCharge(10000);
		rm1.setImagePath("6.jpg");
		allRoomTypes.add(rm4);
		
		RoomType rm5 = new RoomType();
		rm5.setRoomTypeId((long) 5);
		rm5.setRoomTypeName("Pool View King");
		rm5.setDescription("Contemporary design, modern art highlights this 270-sq.ft. room with a king bed & large bay windows, granting pristine views of the pool. The room is well equipped with a work space & hi-tech amenities.");
		rm5.setNoOfMaxAdults(2);
		rm5.setNoOfMaxChildren(4);
		rm5.setNoOfRooms(10);
		rm5.setRoomCharge(12000);
		rm1.setImagePath("7.jpg");
		allRoomTypes.add(rm5);
		
		RoomType rm6 = new RoomType();
		rm6.setRoomTypeId((long) 6);
		rm6.setRoomTypeName("Pool View Twin");
		rm6.setDescription("Contemporary design, modern art highlights this 270-sq.ft. room with a twin bed & large bay windows, granting pristine views of the pool. The room is well equipped with a work space & hi-tech amenities.");
		rm6.setNoOfMaxAdults(2);
		rm6.setNoOfMaxChildren(4);
		rm6.setNoOfRooms(10);
		rm6.setRoomCharge(14000);
		rm1.setImagePath("8.jpg");
		allRoomTypes.add(rm6);
		
		RoomType rm7 = new RoomType();
		rm7.setRoomTypeId((long) 7);
		rm7.setRoomTypeName("");
		rm7.setDescription("");
		rm7.setNoOfMaxAdults(2);
		rm7.setNoOfMaxChildren(4);
		rm7.setNoOfRooms(10);
		rm7.setRoomCharge(20000);
		allRoomTypes.add(rm7);
		
		RoomType rm8 = new RoomType();
		rm8.setRoomTypeId((long) 8);
		rm8.setRoomTypeName("");
		rm8.setDescription("");
		rm8.setNoOfMaxAdults(2);
		rm8.setNoOfMaxChildren(0);
		rm8.setNoOfRooms(10);
		rm8.setRoomCharge(25000);
		allRoomTypes.add(rm8);
		
		RoomType rm9 = new RoomType();
		rm9.setRoomTypeId((long) 9);
		rm9.setRoomTypeName("");
		rm9.setDescription("");
		rm9.setNoOfMaxAdults(50);
		rm9.setNoOfMaxChildren(0);
		rm9.setNoOfRooms(10);
		rm9.setRoomCharge(30000);
		allRoomTypes.add(rm9);
	}
}
