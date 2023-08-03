package clutch.clutchserver.building.repository;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.global.common.enums.LogicType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Integer> {

    Optional<Building> findByBuildingId(Long buildingId);

    Optional<Building> findAllByBuildingNameAndAddressAndLogicTypeAndArea(
            String buildingName,
            String address,
            LogicType logicType,
            String area
    );
}
