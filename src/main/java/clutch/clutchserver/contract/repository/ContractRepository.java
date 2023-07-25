package clutch.clutchserver.contract.repository;

import clutch.clutchserver.contract.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface ContractRepository extends JpaRepository<Contract,Long> {
}
