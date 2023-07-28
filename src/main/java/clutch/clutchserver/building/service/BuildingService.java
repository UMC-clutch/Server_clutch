package clutch.clutchserver.building.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.address.repository.AddressRepository;
import clutch.clutchserver.building.dto.BuildingInfoDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Builder
@Service
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final AddressRepository addressRepository;

    //건물 관련 정보 입력
    public void saveBuilding(BuildingInfoDto buildingRequestDto,
                             String address,
                             String dong,
                             String ho)
    {
        Address saveAddress = new Address();
        saveAddress.setAddress(address);
        saveAddress.setDong(dong);
        saveAddress.setHo(ho);
        addressRepository.save(saveAddress);
        buildingRepository.save(buildingRequestDto.getBuildingId());
    }

    //건물 정보 가져오기
    public Building getBuildingInfo(Long buildingId){
        Building building = buildingRepository.findByBuildingId(buildingId);

        return building;
    }


}
