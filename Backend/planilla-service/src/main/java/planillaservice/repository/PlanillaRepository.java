package planillaservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import planillaservice.entity.PlanillaEntity;

import java.util.ArrayList;

@Repository
public interface PlanillaRepository extends JpaRepository<PlanillaEntity, Long> {

	public PlanillaEntity findByRutAlumno(String rut);
}
