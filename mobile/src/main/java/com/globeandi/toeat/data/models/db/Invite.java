package com.globeandi.toeat.data.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "invites")
public class Invite {
    @Expose
    @ColumnInfo(name = "id")
    @PrimaryKey
    public Long mId;

    @Expose
    @SerializedName("group")
    @ColumnInfo(name = "group")
    public String mGroup;

    @Expose
    @SerializedName("response")
    @ColumnInfo(name = "response")
    public Boolean mResponse;

    @Expose
    @SerializedName("created_at")
    public String mCreatedAt;
}
