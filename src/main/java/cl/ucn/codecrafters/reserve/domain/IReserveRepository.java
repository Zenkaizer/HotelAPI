package cl.ucn.codecrafters.reserve.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReserveRepository extends JpaRepository<Reserve, Integer> {
}
