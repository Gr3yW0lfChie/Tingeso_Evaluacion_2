package estudianteservice.repository;

import estudianteservice.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {

	public EstudianteEntity findByRut(String rut);

	public void deleteByRut(String rut);

}
