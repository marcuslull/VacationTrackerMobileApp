package edu.wgu.d308pa.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.wgu.d308pa.entities.Excursion;

@Dao
public interface ExcursionDao {

    @Query("SELECT * FROM excursion")
    List<Excursion> getAll();

    @Query("SELECT * FROM excursion WHERE excursionId = :excursionId")
    Excursion findById(long excursionId);

    @Query("SELECT * FROM excursion WHERE title LIKE :title LIMIT 1")
    Excursion findByTitle(String title);

    @Query("SELECT * FROM excursion WHERE fkVacationId = :vacationId")
    List<Excursion> getAllWithVacationId(Long vacationId);

    @Update
    void update(Excursion excursion);

    @Insert
    void insert(Excursion excursion);

    @Delete
    void delete(Excursion excursion);
}
