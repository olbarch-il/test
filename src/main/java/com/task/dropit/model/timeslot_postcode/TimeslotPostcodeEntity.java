package com.task.dropit.model.timeslot_postcode;

import com.task.dropit.model.postcodes.PostCodeEntity;
import com.task.dropit.model.timeslots.TimeSlotEntity;
import jakarta.persistence.*;


@Entity
@Table(name = "timeslots_postcodes")
public class TimeslotPostcodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslots_postcodes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id")
    private TimeSlotEntity timeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postcode_id")
    private PostCodeEntity postcode;


}
