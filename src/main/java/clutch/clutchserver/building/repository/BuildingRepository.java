package clutch.clutchserver.building.repository;

import clutch.clutchserver.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    Optional<Building> findByBuildingId(Long buildingId);
}
