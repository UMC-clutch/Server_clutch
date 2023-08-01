package clutch.clutchserver.calculate.repository;

import clutch.clutchserver.calculate.entity.Calculate;
import clutch.clutchserver.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculateRepository extends JpaRepository<Calculate, Long> {

    List<Calculate> findAllByUser(User user);

}
