package com.task.dropit.model.timeslots;

import com.task.dropit.model.delivery.DeliveryEntity;
import com.task.dropit.model.postcodes.PostCodeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "timeslots")
@Builder
public class TimeSlotEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeslot_id")
    long id;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "timeslots_postcodes",
            joinColumns = @JoinColumn(name = "timeslot"),
            inverseJoinColumns = @JoinColumn(name = "postcode"))
    private Set<PostCodeEntity> postcodes;
    @OneToMany(mappedBy = "timeSlot")
    private List<DeliveryEntity> deliveries;
}
