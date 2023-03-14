package com.task.dropit.dao;

import com.task.dropit.model.holidays.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidaysRepository extends JpaRepository<HolidayEntity, Long> {

}
