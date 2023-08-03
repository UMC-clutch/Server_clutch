package clutch.clutchserver.address.repository;

import clutch.clutchserver.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByAddress(String address);



}
