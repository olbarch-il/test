package com.task.dropit.tools;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.task.dropit.exception.CapacityExceededException;
import com.task.dropit.model.timeslots.TimeSlotEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CapacityHandler {
    private final LoadingCache<LocalDate, AtomicInteger> cache;
    private final static int CAPACITY_PER_DAY = 10;
    private final static int CAPACITY_PER_SLOT = 10;

    public CapacityHandler() {
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<>() {
                    @Override
                    public AtomicInteger load(LocalDate key) {
                        return new AtomicInteger(0);
                    }
                });
    }

    public void decrement(TimeSlotEntity timeSlot) {
        cache.getIfPresent(timeSlot).decrementAndGet();
    }


    public void checkDayCapacity(LocalDate time) {
        AtomicInteger count = cache.getIfPresent(time);
        if(count == null) {
            cache.put(time, new AtomicInteger(1));
        } else {
            if (count.incrementAndGet() > CAPACITY_PER_DAY) {
                count.decrementAndGet();
                throw new CapacityExceededException("Booking limit exceeded for timeslot with id " + time);
            } else {
                cache.getIfPresent(time).set(count.get());
            }
        }
    }

    public void checkSlotCapacity(long count) {
        if(count >= CAPACITY_PER_SLOT) {
            throw new CapacityExceededException("you can't reserve this timeslot because there are already 2 reservations");
        }
    }

}
