package edu.wgu.d308pa.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion")
public class Excursion {

    @PrimaryKey
    public int excursionId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "vacation_id")
    public long fkVacationId;
}
