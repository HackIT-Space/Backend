package org.hackit.space.hackathons.entity;

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
