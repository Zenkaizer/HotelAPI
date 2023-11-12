package cl.ucn.codecrafters.repositories;

import cl.ucn.codecrafters.entities.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReserveRepository extends JpaRepository<Reserve, Integer> {
}
