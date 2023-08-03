package clutch.clutchserver.report.repository;

import clutch.clutchserver.report.entity.Report;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByContractId(Long contractId);
}
