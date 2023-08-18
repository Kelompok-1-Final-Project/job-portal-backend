package com.lawencon.jobportal.candidate.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConvert {
	public static LocalDateTime convertDate(String date) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		final LocalDateTime dateConvert = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
		return dateConvert;
	}
	public static LocalDateTime convertDateWithFormatter(String date) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		final LocalDateTime dateConvert = LocalDateTime.parse(date, formatter);
		return dateConvert;
	}
}
