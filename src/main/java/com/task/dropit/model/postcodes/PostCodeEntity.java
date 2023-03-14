package com.task.dropit.model.postcodes;

import com.task.dropit.model.timeslots.TimeSlotEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "postcodes")
@Setter
@Getter
public class PostCodeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postcode_id")
    private Long id;
    @Column(name = "code")
    private String postalCode;
    @ManyToMany(mappedBy = "postcodes")
    private Set<TimeSlotEntity> timeslots = new HashSet<>();

}
