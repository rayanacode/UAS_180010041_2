package com.bc181.dwimantara.services;

import com.bc181.dwimantara.model.ResponseData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiContent {

    @FormUrlEncoded
    @POST("insert.php")
    Call<ResponseData> addData(
            @Field("nama") String nama,
            @Field("tahun") String tahun,
            @Field("stock") String stock,
            @Field("brand") String brand,
            @Field("photo") String photo,
            @Field("jenis") String jenis
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseData> editData(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("tahun") String tahun,
            @Field("stock") String stock,
            @Field("brand") String brand,
            @Field("photo") String photo,
            @Field("jenis") String jenis
    );

    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseData> deleteData(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("search.php")
    Call<ResponseData> searchData(
            @Field("search") String keyword
    );

    @GET("getdata.php")
    Call<ResponseData> getData();
}
