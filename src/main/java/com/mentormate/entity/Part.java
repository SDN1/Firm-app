package com.mentormate.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "part")
public class Part {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "part_id")
    private Integer part_id;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;


    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "firm_id")
    private Firm firm;

    @DecimalMax("100.00")
    @NotNull(message = "percents field can not be null.")
    private Float percents;

    public Part() {

    }

    public Part(Integer part_id, Person person, Firm firm, Float percents) {
        this.part_id = part_id;
        this.person = person;
        this.firm = firm;
        this.percents = percents;
    }

    public Integer getPart_id() {
        return part_id;
    }

    public void setPart_id(Integer part_id) {
        this.part_id = part_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Float getPercents() {
        return percents;
    }

    public void setPercents(Float percents) {
        this.percents = percents;
    }

    public void addToPercents(Float count) {
        this.percents += count;
    }

    public void subtractionToPercents(Float count) {
        this.percents -= count;
    }
}
