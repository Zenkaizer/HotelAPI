package cl.ucn.codecrafters.entities.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomError extends BaseError{

    private String nonNaturalPriceError;

    private String nonNaturalMaxCapacityError;

    private String negativeBedAmountError;

    private String invalidPriceError;

    private String invalidMaxCapacityError;

    private String invalidBedAmountError;

}
