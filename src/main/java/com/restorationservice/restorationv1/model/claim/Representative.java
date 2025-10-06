package com.restorationservice.restorationv1.model.claim;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.json.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(EntityChangeLogListener.class)
@Table(
    name = "representative",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"representation_type", "email"})
    }
)
public class Representative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "representation_type", referencedColumnName = "id")
    private RepresentativeType representationType;

    @Column(name = "law_firm_name")
    private String lawFirmName;

    @Column(name = "name")
    private String name;

    @Column(name = "cellphone")
    private String cellphone;

    @NotNull
    @Column(name = "email")
    private String email;

    @Column(name = "representative_status")
    @Enumerated(EnumType.STRING)
    private RepresentativeStatus representativeStatus;
}