package com.task.dropit.dao;

import com.task.dropit.model.timeslots.TimeSlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
    Set<TimeSlotEntity> findByPostcodes_postalCode(String code);

}
