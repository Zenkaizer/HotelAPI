package cl.ucn.codecrafters.reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReserveRepository extends JpaRepository<Reserve, Integer> {
}
