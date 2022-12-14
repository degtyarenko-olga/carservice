package com.degtyarenko.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Class contains information about <b>car</b> with properties
 * <b>id</b>, <b>carCaseModel</b>, <b>dateOfIssue</b>, <b>vinCode</b>
 * and <b>color</b>
 *
 * @author Degtyarenko Olga
 * @version 1.0
 * @since 2022-12-22
 */
@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "car", schema = "carservice")
@ToString
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_case_model_id")
    @JsonManagedReference
    private CarCaseModel carCaseModel;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "vin_code")
    private String vinCode;

    @Column
    private String color;

}
