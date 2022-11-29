package com.farukkaradeniz.springbatchpagingreader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "MY_ENTITY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyEntity {
    @Id
    private String id;
    private String name;
    private LocalDateTime date;
}
