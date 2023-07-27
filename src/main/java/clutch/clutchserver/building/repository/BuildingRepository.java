package clutch.clutchserver.building.repository;

import clutch.clutchserver.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

    Optional<Building> findByBuildingId(Long buildingId);
}
