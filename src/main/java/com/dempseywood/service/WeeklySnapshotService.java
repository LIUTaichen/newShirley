package com.dempseywood.service;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.WeeklyNiggleSnapshot;
import com.dempseywood.domain.enumeration.Priority;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.repository.WeeklyNiggleSnapshotRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeeklySnapshotService {

    private final WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository;
    private final NiggleRepository niggleRepository;


    public WeeklySnapshotService(WeeklyNiggleSnapshotRepository weeklyNiggleSnapshotRepository, NiggleRepository niggleRepository) {
        this.weeklyNiggleSnapshotRepository = weeklyNiggleSnapshotRepository;
        this.niggleRepository = niggleRepository;
    }

    public LocalDate getWeekEndingOn(){
        LocalDate localDate = LocalDate.now(ZoneId.of("NZ"));
        return localDate;
    }

    public List<WeeklyNiggleSnapshot> generateSnapshots(){
        List<WeeklyNiggleSnapshot> snapshots = new ArrayList<WeeklyNiggleSnapshot>();
        LocalDate weekEndingOn = this.getWeekEndingOn();

        for(Status status: Status.values()){
            for(Priority priority : Priority.values()){
                List<Niggle> niggles = niggleRepository.findByStatusAndPriority(status,priority);
                WeeklyNiggleSnapshot snapshot = new WeeklyNiggleSnapshot();
                snapshot.setWeekEndingOn(weekEndingOn);
                snapshot.setStatus(status);
                snapshot.setPriority(priority);
                snapshot.setCount(niggles.size());
                Integer maxAge = 0;
                for(Niggle niggle : niggles){
                    Integer ageOfNiggle = this.getAgeOfNiggle(niggle);
                    if( ageOfNiggle > maxAge ){
                        maxAge = ageOfNiggle;
                    }
                }
                snapshot.setAgeOfOldest(maxAge);
                snapshots.add(snapshot);
            }
        }
        return snapshots;
    }

    public Integer getAgeOfNiggle(Niggle niggle){
        if(niggle.getDateOpened() == null){
            return 0;
        }
        if(niggle.getDateCompleted() != null){
            return 0;
        }
        if(niggle.getDateClosed() != null){
            return 0;
        }
        if(niggle.getStatus() == Status.OPEN || niggle.getStatus() == Status.ON_HOLD || niggle.getStatus() == Status.IN_PROGRESS){
            Instant now = Instant.now();
            Duration between = Duration.between(niggle.getDateOpened(), now);
            return (int) between.toDays();
        }
        return 0;
    }
    public void takeSnapshot(){
        List<WeeklyNiggleSnapshot> snapshots  = this.generateSnapshots();
        weeklyNiggleSnapshotRepository.save(snapshots);
    }

}
