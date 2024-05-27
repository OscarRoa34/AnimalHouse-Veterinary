package co.edu.uptc.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pet {
    private int petId;
    private String petName;
    private String specie;
    private String breed;
    private int petAge;
    private Person owner;
    private List<Person> keepers;
}