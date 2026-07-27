package com.gencura.common.utils;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	
	private ApiResponse(boolean status,String message, T data) {
		this.success = status;
		this.message = message;
		this.data = data;
		this.timestamp = LocalDateTime.now();
	}
	
	public static <T> ApiResponse<T> success(String message, T data){
		return new ApiResponse<>(true, message, data);
	}
	
	public static <T> ApiResponse<T> create(String message, T data){
		return new ApiResponse<>(true, message, data);
	}
	
	public static <T> ApiResponse<T> update(String message, T data){
		return new ApiResponse<>(true, message, data);
	}
	
	public static <T> ApiResponse<T> delete(String message, T data){
		return new ApiResponse<>(true, message, data);
	}
	public static <T> ApiResponse<T> error(String message, T data){
		return new ApiResponse<>(false, message, data);
	}
}
