package crm06.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateConvertor {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	public static String convertToString(LocalDateTime dateTime) {
		return dateTime.format(formatter);
	}

	public static LocalDateTime convertToLocalDateTime(String str) {
		try {
			return LocalDate.parse(str, formatter).atStartOfDay();
		} catch (Exception e) {
			System.out.println("Error convert from String to LocalDateTime: " + e.getMessage());
			return null;
		}
	}
	
	public static LocalDateTime convertToLDTimeFromInput(String str) {
		try {
			return LocalDate.parse(str, formatter2).atStartOfDay();
		} catch (Exception e) {
			System.out.println("Error convert from String to LocalDateTime: " + e.getMessage());
			return null;
		}
	}

	public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
		return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
