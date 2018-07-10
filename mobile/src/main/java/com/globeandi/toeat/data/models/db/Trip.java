package com.globeandi.toeat.data.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "trips")
public class Trip {

    @Expose
    @ColumnInfo(name = "id")
    @PrimaryKey
    public Long mId;

    @Expose
    @ColumnInfo(name = "title")
    public String mTitle;

    @Expose
    @SerializedName("is_completed")
    @ColumnInfo(name = "is_completed")
    public Boolean mIsCompleted;

    @Expose
    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    public String mCreatedAt;

    @Expose
    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    public String mUpdatedAt;


     /*
    private List<User> TripMembers;
    private String countdown;
    private LocalTime eatingTime;
     */
}
