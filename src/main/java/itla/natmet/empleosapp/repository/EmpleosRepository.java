
package itla.natmet.empleosapp.repository;

import itla.natmet.empleosapp.domain.Empleo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleosRepository extends JpaRepository<Empleo, Long>{
    
}
