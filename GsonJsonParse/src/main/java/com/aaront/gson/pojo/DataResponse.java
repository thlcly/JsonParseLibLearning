package com.aaront.gson.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author tonyhui
 * @since 16/4/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
