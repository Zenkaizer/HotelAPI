package cl.ucn.codecrafters.entities;

import io.ebean.annotation.NotNull;
import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room extends Base{

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