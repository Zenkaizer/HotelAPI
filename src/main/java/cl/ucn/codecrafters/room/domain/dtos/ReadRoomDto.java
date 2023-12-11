package cl.ucn.codecrafters.room.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadRoomDto {

    private Integer id;
    private Integer individualBeds;
    private Integer dualBeds;
    private Integer maxCapacity;
    private Boolean haveBathroom;
    private Integer price;

}
