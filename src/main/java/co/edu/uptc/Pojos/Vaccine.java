package co.edu.uptc.Pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {
    private int vaccineId;
    private String vaccineName;
    private int lifeSpan;
}
