package edu.wgu.d308pa.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class VacationsWithExcursions {

    @Embedded
    public Vacation vacation;

    @Relation(parentColumn = "vacationId", entityColumn = "fkVacationId")
    public List<Excursion> excursions;
}
