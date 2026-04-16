package samuelvalentini.u5d9ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import samuelvalentini.u5d9ex.entity.Autore;


@Repository
public interface AutoreRepository extends JpaRepository<Autore, Long> {
}
