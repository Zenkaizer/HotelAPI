package cl.ucn.codecrafters.room.domain;

import cl.ucn.codecrafters.utils.Base;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room")
public class Room extends Base {

    @NotNull
    @Column(name = "max_capacity")
    private Integer maxCapacity;

    @NotNull
    @Column(name = "individual_beds")
    private Integer individualBeds;

    @NotNull
    @Column(name = "dual_beds")
    private Integer dualBeds;

    @NotNull
    @Column(name = "have_bathroom")
    private Boolean haveBathroom;

    @NotNull
    @Column(name = "price")
    private Integer price;
}
