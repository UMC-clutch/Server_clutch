package clutch.clutchserver.contract.repository;

import clutch.clutchserver.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface ContractRepository extends JpaRepository<Contract,Long> {
    Optional<Contract> findByUserId(Long userId);
}
