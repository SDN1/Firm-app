package com.mentormate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class FirmProfitDTO {
    @Min(1)
    @NotNull(message = "firmId field can not be null or empty.")
    private Integer firmId;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @NotNull(message = "startDate field can not be null or empty.")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @NotNull(message = "endDate field can not be null or empty.")
    private Date endDate;

    FirmProfitDTO() {
    }

    FirmProfitDTO(Integer firmId, Date startDate, Date endDate) {
        this.firmId =  firmId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getFirmId() {
        return firmId;
    }

    public void setFirmId(Integer firmId) {
        this.firmId = firmId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
