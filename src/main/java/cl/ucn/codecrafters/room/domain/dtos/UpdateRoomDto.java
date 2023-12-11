package cl.ucn.codecrafters.room.domain.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomDto {

    private Integer maxCapacity;
    private Integer individualBeds;
    private Integer dualBeds;
    private Boolean haveBathroom;
    private Integer price;


}
