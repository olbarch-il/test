package com.task.dropit.model.delivery;

import com.task.dropit.model.timeslots.TimeSlotEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "deliveries")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id", referencedColumnName = "timeslot_id", insertable = false, updatable = false)
    private TimeSlotEntity timeSlot;

    @Column(name = "timeslot_id")
    private Long timeSlotId;

}

