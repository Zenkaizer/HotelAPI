package cl.ucn.codecrafters.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserve")
public class Reserve extends Base{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_room", referencedColumnName = "id")
    private Room room;

    @Column(name = "reserve_date_time")
    private LocalDateTime reserveDateTime;

    @Column(name = "arrive_date_time")
    private LocalDateTime arriveDateTime;

    @Column(name = "leave_date_time")
    private LocalDateTime leaveDateTime;

    @Column(name = "confirmed")
    private Boolean confirmed;
}
