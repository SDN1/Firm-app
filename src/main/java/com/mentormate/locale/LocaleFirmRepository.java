package com.mentormate.locale;

import com.mentormate.entity.Firm;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LocaleFirmRepository {
    private List<Firm> firmList;

    public LocaleFirmRepository() {
        firmList = new ArrayList<>();
        firmList.add(new Firm(1, "SDN Company", "346456754", true, (float) 10));
        firmList.add(new Firm(1, "SDN2 Company", "346456754", true, (float) 10));
        firmList.add(new Firm(1, "SDN3 Company", "346456754", true, (float) 10));
        firmList.add(new Firm(1, "SDN4 Company", "346456754", true, (float) 10));
    }

    public Firm getFirm(int id) {
        if(id > firmList.size())
            throw new EntityNotFoundException("Operation get failed. Firm with id= " + id + " Not Found!");
        return firmList.get(id - 1);
    }

    public Optional<Firm> getFirm(String firmName) {
        return firmList.stream().filter(f -> f.getName().equals(firmName)).findAny();
    }

    public void saveFirm(Firm firm) {
        firmList.add(firm);
    }
}
