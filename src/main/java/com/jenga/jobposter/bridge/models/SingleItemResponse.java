/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jenga.jobposter.bridge.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T>
 * @author david
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleItemResponse<T> {
    private Status status;
    private T data;


    public static SingleItemResponse<Object> returnData(Status status, Object data) {
        SingleItemResponse<Object> response = new SingleItemResponse();
        response.setStatus(status);
        response.setData(data);
        return response;
    }
}
