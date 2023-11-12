package estudianteservice.repositories;

import estudianteservice.entities.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Long> {

	public AlumnoEntity findByRut(String rut);

	public void deleteByRut(String rut);

}
