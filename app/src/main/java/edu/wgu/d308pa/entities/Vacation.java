package edu.wgu.d308pa.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacation")
public class Vacation {

    @PrimaryKey
    public int vacationId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "hotel")
    public String hotel;

    @ColumnInfo(name = "start")
    public long start;

    @ColumnInfo(name = "end")
    public long end;
}
