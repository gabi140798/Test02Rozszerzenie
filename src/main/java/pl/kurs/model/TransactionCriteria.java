package pl.kurs.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCriteria {

    private Double minAmount;
    private Double maxAmount;
    private LocalDate start;
    private LocalDate finish;
    private Long userId;
}
