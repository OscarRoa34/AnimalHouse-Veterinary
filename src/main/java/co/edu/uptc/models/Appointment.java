package co.edu.uptc.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    private int appointmentId;
    private LocalDate appointmentDate;
    private Pet pet;
    private Person responsible;
    private List<Vaccine> vaccines;

    public List<LocalDate> calculateVaccineExpireDates() {
        List<LocalDate> expireDates = new ArrayList<>();
        for (Vaccine vaccine : vaccines) {
            LocalDate expireDate = appointmentDate.plusDays(vaccine.getLifeSpan());
            expireDates.add(expireDate);
        }
        return expireDates;
    }

    public String getVaccineNames() {
        StringBuilder vaccineNames = new StringBuilder();
        for (Vaccine vaccine : vaccines) {
            if (vaccineNames.length() > 0) {
                vaccineNames.append(", ");
            }
            vaccineNames.append(vaccine.getVaccineName());
        }
        return vaccineNames.toString();
    }

}