package co.edu.uptc.Pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int personId;
    private String personName;
    private String personLastName;
    private int personAge;
    private String documentType;
    private String documentNumber;
}
