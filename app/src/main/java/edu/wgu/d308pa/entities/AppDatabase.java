package edu.wgu.d308pa.entities;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import edu.wgu.d308pa.dao.ExcursionDao;
import edu.wgu.d308pa.dao.VacationDao;

@Database(entities = {Excursion.class, Vacation.class, VacationsWithExcursions.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ExcursionDao excursionDao();
    public abstract VacationDao vacationDao();

    //usage for elsewhere: https://developer.android.com/training/data-storage/room#usage
}
