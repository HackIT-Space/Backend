package org.hackit.space.managerapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hackathon {

    private Integer id;

    private String title;

    private String description;

}
