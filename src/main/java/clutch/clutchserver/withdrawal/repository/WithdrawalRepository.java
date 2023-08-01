package clutch.clutchserver.withdrawal.repository;

import clutch.clutchserver.withdrawal.entity.Withdrawal;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
}
