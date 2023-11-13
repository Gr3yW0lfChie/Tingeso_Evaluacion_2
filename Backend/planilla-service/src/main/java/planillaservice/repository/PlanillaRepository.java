package planillaservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import planillaservice.entity.PlanillaEntity;

@Repository
public interface PlanillaRepository extends JpaRepository<PlanillaEntity, Long> {
}
