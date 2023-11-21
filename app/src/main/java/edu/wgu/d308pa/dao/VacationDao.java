package edu.wgu.d308pa.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import edu.wgu.d308pa.entities.Vacation;
import edu.wgu.d308pa.entities.VacationsWithExcursions;

@Dao
public interface VacationDao {

    @Query("SELECT * FROM vacation")
    List<Vacation> getAll();

    @Query("SELECT * FROM vacation WHERE vacationId = :vacationId")
    Vacation findById(long vacationId);

    @Query("SELECT * FROM vacation WHERE title LIKE :title LIMIT 1")
    Vacation findByName(String title);

    @Update
    void update(Vacation vacation);

    @Insert
    void insert(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Delete
    void deleteAll(List<Vacation> vacations);

    @Transaction
    @Query("SELECT * FROM vacation")
    List<VacationsWithExcursions> getVacationsWithExcursions();
}
