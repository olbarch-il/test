package com.task.dropit.service.delivery;

import com.task.dropit.dao.DeliveryRepository;
import com.task.dropit.dao.TimeSlotRepository;
import com.task.dropit.exception.DeliveryMissingException;
import com.task.dropit.exception.TimeslotMissingException;
import com.task.dropit.model.delivery.DeliveryEntity;
import com.task.dropit.model.timeslots.TimeSlotEntity;
import com.task.dropit.tools.CapacityHandler;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService implements Delivery{
    private final DeliveryRepository deliveryRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final CapacityHandler bookingCount;

    private static final String BOOKED_STATUS = "booked";
    private static final String CANCELED_STATUS = "canceled";

    public DeliveryService(DeliveryRepository deliveryRepository, TimeSlotRepository timeSlotRepository, CapacityHandler bookingCount) {
        this.deliveryRepository = deliveryRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.bookingCount = bookingCount;
    }

    @Override
    public Long bookDelivery(Long userId, Long timeslotId) {
        TimeSlotEntity timeslot = timeSlotRepository.findById(timeslotId)
                .orElseThrow(() -> new TimeslotMissingException("no timeslots for this id: " + timeslotId));
        DeliveryEntity delivery = bookDeliveryIfCapacityAllows(timeslotId, timeslot);
        return delivery.getId();
    }
    @Transactional
    @Override
    public void cancelDelivery(long id) {
        DeliveryEntity delivery = deliveryRepository.findById(id)
                .orElseThrow(() ->  new DeliveryMissingException("delivery with id " + id + "is not present in the system"));
        delivery.setStatus(CANCELED_STATUS);
        TimeSlotEntity timeSlot = delivery.getTimeSlot();
        bookingCount.decrement(timeSlot);
    }

    public List<DeliveryEntity> getDeliveriesByTimeSlot(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return deliveryRepository.findByTimeSlotStartTimeBetweenAndStatus(startOfDay, endOfDay, BOOKED_STATUS);
    }

    @Transactional
    private DeliveryEntity bookDeliveryIfCapacityAllows(Long timeslotId, TimeSlotEntity timeslot) {
        checkCapacity(timeslotId, timeslot);
        return saveDelivery(timeslotId, timeslot);
    }

    private void checkCapacity(Long timeslotId, TimeSlotEntity timeslot) {
        long count = deliveryRepository.countByTimeSlotId(timeslotId);
        LocalDate day = timeslot.getStartTime().toLocalDate();
        bookingCount.checkSlotCapacity(count);
        bookingCount.checkDayCapacity(day);
    }

    private DeliveryEntity saveDelivery(Long timeslotId, TimeSlotEntity timeslot) {
        DeliveryEntity deliveryEntity = new DeliveryEntity();
        deliveryEntity.setStatus(BOOKED_STATUS);
        deliveryEntity.setTimeSlotId(timeslotId);
        deliveryEntity.setTimeSlot(timeslot);
        return deliveryRepository.save(deliveryEntity);
    }



}
