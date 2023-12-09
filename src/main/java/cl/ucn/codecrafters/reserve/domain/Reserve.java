package cl.ucn.codecrafters.reserve.domain;

import cl.ucn.codecrafters.room.domain.Room;
import cl.ucn.codecrafters.user.domain.entities.User;
import cl.ucn.codecrafters.utils.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserve")
public class Reserve extends Base {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_room", referencedColumnName = "id")
    private Room room;

    @Column(name = "reserve_date_time")
    private Date reserveDateTime;

    @Column(name = "arrive_date_time")
    private Date arriveDateTime;

    @Column(name = "leave_date_time")
    private Date leaveDateTime;

    @Column(name = "confirmed")
    private Boolean confirmed;
}
