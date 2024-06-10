package com.example.phonebook;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Methods {
    @GET("phone-book/")
    Call<Model> getAllData();

    @DELETE("phone-book/{id}/")
    Call<Void> deleteContact(@Path("id") int id);

    @POST("phone-book/")
    Call<Void> addContact(@Body Model.Contact contact);
}
