package edu.wgu.d308pa.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacation")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    public long vacationId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "hotel")
    public String hotel;

    @ColumnInfo(name = "start")
    public long start;

    @ColumnInfo(name = "end")
    public long end;

    public long getVacationId() {
        return vacationId;
    }

    public void setVacationId(long vacationId) {
        this.vacationId = vacationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
