package clutch.clutchserver.calculate.repository;

import clutch.clutchserver.calculate.entity.Calculate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculateRepository extends JpaRepository<Calculate, Long> {
}
