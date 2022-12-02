package com.example.restaurant_management_system.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SellerReq {

	@JsonProperty("start_date_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDateTime;
	
	@JsonProperty("end_date_time")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDateTime;

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	
}
