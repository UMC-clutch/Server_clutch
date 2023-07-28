package clutch.clutchserver.building.repository;

import clutch.clutchserver.building.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Integer> {

    public Building findByBuildingId(Long buildingId);

    public void save(Long buildingId);
}
