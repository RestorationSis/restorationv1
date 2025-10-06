package com.restorationservice.restorationv1.model.claim;

import com.fasterxml.jackson.annotation.JsonView;
import com.restorationservice.restorationv1.component.EntityChangeLogListener;
import com.restorationservice.restorationv1.json.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "representation_types")
public class RepresentativeType {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @JsonView(Views.Summary.class)
    private long id;

    @NotNull(message = "Name must not be null")
    @Column(name = "name", nullable = false)
    @JsonView(Views.Summary.class)
    private String name;
}
