package com.kspt.pms.project;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by kivi on 03.12.17.
 */
@Entity
@Table(name = "MILESTONE")
public class Milestone {

    public enum Status {
        OPENED,
        ACTIVE,
        CLOSED
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private Project project;

    @Column(name = "STATUS")
    private Status status;

    @Column(name = "STARTING_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startingDate;

    @Column(name = "ACTIVATED_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activatedDate;

    @Column(name = "ENDING_TIME", columnDefinition = "DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "CLOSED_TIME", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date closingDate;
}
