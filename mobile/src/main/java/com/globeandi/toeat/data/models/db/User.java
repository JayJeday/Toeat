package com.globeandi.toeat.data.models.db;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.UUID;

/**
 * Created by jay on 3/15/2018.
 * User class create d for user id,email and app configuration
 */
@Entity(tableName = "users")
public class User {

    @NonNull
    @Expose
    @ColumnInfo(name = "id")
    @PrimaryKey
    public String mId;

    @Expose
    @ColumnInfo(name = "email")
    public String mEmail;

    @Expose
    @SerializedName("username")
    @ColumnInfo(name = "username")
    public String mUsername;

    @Expose
    @SerializedName("picture_url")
    @ColumnInfo(name = "picture_url")
    public String mPicture;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String mCreatedAt;

    @Expose
    @SerializedName("update_at")
    @ColumnInfo(name = "update_at")
    public String mUpdatedAt;

    public User(){
        this.mId = UUID.randomUUID().toString();
    }
}
