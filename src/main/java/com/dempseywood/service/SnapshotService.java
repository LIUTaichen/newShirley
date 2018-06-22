package com.dempseywood.service;

import com.dempseywood.domain.Niggle;
import com.dempseywood.domain.NiggleSnapshot;
import com.dempseywood.domain.enumeration.Priority;
import com.dempseywood.domain.enumeration.Status;
import com.dempseywood.repository.NiggleRepository;
import com.dempseywood.repository.NiggleSnapshotRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SnapshotService {

    private final NiggleSnapshotRepository NiggleSnapshotRepository;
    private final NiggleRepository niggleRepository;


    public SnapshotService(NiggleSnapshotRepository NiggleSnapshotRepository, NiggleRepository niggleRepository) {
        this.NiggleSnapshotRepository = NiggleSnapshotRepository;
        this.niggleRepository = niggleRepository;
    }

    public LocalDate getWeekEndingOn(){
        LocalDate localDate = LocalDate.now(ZoneId.of("NZ"));
        return localDate;
    }

    public List<NiggleSnapshot> generateSnapshots(){
        List<NiggleSnapshot> snapshots = new ArrayList<NiggleSnapshot>();
        LocalDate weekEndingOn = this.getWeekEndingOn();

        for(Status status: Status.values()){
            for(Priority priority : Priority.values()){
                List<Niggle> niggles = niggleRepository.findByStatusAndPriority(status,priority);
                NiggleSnapshot snapshot = new NiggleSnapshot();
                snapshot.setDate(weekEndingOn);
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
        List<NiggleSnapshot> snapshots  = this.generateSnapshots();
        NiggleSnapshotRepository.save(snapshots);
    }

}
