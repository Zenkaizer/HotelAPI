package cl.ucn.codecrafters.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Integer id;
    private Integer individualBeds;
    private Integer dualBeds;
    private Boolean haveBathroom;
    private Long price;

}
