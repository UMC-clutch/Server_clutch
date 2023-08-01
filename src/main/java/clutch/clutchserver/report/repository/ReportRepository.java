package clutch.clutchserver.report.repository;

import clutch.clutchserver.report.entity.Report;
import lombok.Builder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
