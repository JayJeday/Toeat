package com.globeandi.toeat.data.models.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by jay on 3/15/2018.
 */
    @Entity(tableName = "groups")
    public class Group {

        @Expose
        @ColumnInfo(name = "id")
        @PrimaryKey
        public Long mId;

        @Expose
        @SerializedName("description")
        @ColumnInfo(name = "description")
        public String mDescription;

        @Expose
        @SerializedName("created_at")
        @ColumnInfo(name = "created_at")
        public String mCreateAt;

        @Expose
        @SerializedName("updated_at")
        @ColumnInfo(name = "updated_at")
        public String mUpdatedAt;
}