package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {

	private Integer roomNumber;
	private Date checkIn;
	private Date checkOut;

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Reservation(Integer roomNumber, Date checkIn, Date checkOut) throws DomainException {
		if (!checkOut.after(checkIn)) { // Testando se a data de checkout nao e anterior data checkin
			throw new DomainException("Error in reservation: Check-out date must be after check-in date");
		}

		this.roomNumber = roomNumber;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getChekIn() {
		return checkIn;
	}

	public Date getChekOut() {
		return checkOut;
	}

	public long duration() {
		long diff = checkOut.getTime() - checkIn.getTime(); // Pega a diferença das datas em milissegundos
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS); // Converte milisegundos para dias
	}

	public void updateDates(Date checkin, Date checkOut) throws DomainException {

		Date now = new Date(); // Data de agora
		if (checkIn.before(now) || checkOut.before(now)) { // se a data checkin ou checkout forem anteriores a agora nao
															// posso liberar atualizacao
			throw new DomainException("Error in reservation: Reservation dates for updates must be future"); 
		}

		if (!checkOut.after(checkIn)) { // Testando se a data de checkout nao e anterior data checkin
			throw new DomainException("Error in reservation: Check-out date must be after check-in date");

		}

		this.checkIn = checkin;
		this.checkOut = checkOut;
		
		
	}

	@Override
	public String toString() {
		return "Room " + roomNumber + ", check-in: " + sdf.format(checkIn) + ", check-out: " + sdf.format(checkOut)
				+ ", " + duration() + " nights";
	}

}
