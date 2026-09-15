package com.aydsii.demo.dto;

public class ApiResponse<T> {
    private int status;
    private String messege;
    private T data;

    public ApiResponse() {
    }
    
    //Constructor
    public ApiResponse(int status, String messege, T data){
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    //Setters
    public void setStatus(int status){this.status = status;}
    public void setMessege(String messege){this.messege = messege;}
    public void setData(T data){this.data = data;}

    //Getters
    public int getStatus(){return status;}
    public String getMessege(){return messege;}
    public T getData(){return data;}
}
