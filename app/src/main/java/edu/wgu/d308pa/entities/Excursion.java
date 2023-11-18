package edu.wgu.d308pa.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    public long excursionId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "date")
    public long date;

    @ColumnInfo(name = "fkVacationId")
    public long fkVacationId;

    public long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(long excursionId) {
        this.excursionId = excursionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getVacationId() {
        return fkVacationId;
    }

    public void setVacationId(long vacationId) {
        this.fkVacationId = vacationId;
    }
}
