package clutch.clutchserver.building.service;

import clutch.clutchserver.address.entity.Address;
import clutch.clutchserver.address.repository.AddressRepository;
import clutch.clutchserver.building.dto.BuildingPriceRequestDto;
import clutch.clutchserver.building.dto.BuildingPriceResponseDto;
import clutch.clutchserver.building.dto.BuildingRequestDto;
import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.global.DefaultAssert;
import clutch.clutchserver.global.common.enums.LogicType;
import clutch.clutchserver.global.payload.ApiResponse;
import clutch.clutchserver.user.entity.User;
import clutch.clutchserver.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Builder
@Service
public class BuildingService {

    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final AddressRepository addressRepository;


    //건물 시세 구하기
    public BigInteger getPrice(Building building) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException, ParseException {

        //시세 codef api 이용해서 가져오기
        EasyCodef easyCodef = new EasyCodef();

        //퍼블릭키, 클라이언트 id, secret 설정
        easyCodef.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz9yg/EolzQpRRsgyYVHb89GcESgCzMSePNOq5ShrmVeQapWLWQ3DxFEoKPA2xg9oXYxD+h530EZRJuYkkEIUDRb74l2eYF2oufNAeNelSnkNK58BvEmZ5YwdXkboTSv8CkBALnTwayldQsKk1pOpqhdCw0CnbXQald3BdNyXac68xa0cUFtIQ6PBuUfBYZkG65YRQCQWifWBwdZ4eiwStCyFEV4VEVyUU7YIDylq6DjHZk4Q5UESKVaY9kbVurqowIJ9euDNtfMt7Dp0CspeIxGTGmrvKD8uX8xu7S46GKiYzkAb62zrzq+8eIlFLmzgs7oKdiz8+PloryrqvM646QIDAQAB");
        easyCodef.setClientInfoForDemo("a97ed80d-8ff0-4a70-8414-639ae41d3b14", "46895f2a-b174-4c9e-b011-b1fe911c2115");

        //요청 파라미터 설정(단지 일련번호 조회)
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("organization", "0004");
        parameterMap.put("address", building.getAddress().getAddress());

        //단지 일련번호 조회
        String productUrl = "/v1/kr/etc/ld/kb/serial-number";
        String result = easyCodef.requestProduct(productUrl, EasyCodefServiceType.DEMO, parameterMap);
        System.out.println(result);

        //JSON 파서 생성자 생성
        JSONParser jsonParser = new JSONParser();

        //단건인 경우이고, 단지 일련번호 파싱
        JSONObject jsonCNObject = (JSONObject) jsonParser.parse(result);
        JSONObject jsonCNData = (JSONObject) jsonCNObject.get("data");
        String complexNo = (String) jsonCNData.get("commComplexNo");

        //요청 파라미터 설정(시세 정보 조회) -> 필요한 파라미터(기관 코드, 단지 일련번호)
        HashMap<String, Object> parameterMap1 = new HashMap<String, Object>();
        parameterMap1.put("organization", "0004");
        parameterMap1.put("complexNo", complexNo);

        //시세 정보 조회
        String productUrl1 = "/v1/kr/etc/ld/kb/market-price-information";
        String priceResult = easyCodef.requestProduct(productUrl1, EasyCodefServiceType.DEMO, parameterMap1);

        //다건의 경우이고, 시세 정보 파싱
        JSONObject jsonResultObject = (JSONObject) jsonParser.parse(priceResult);
        JSONObject jsonDetailObject = (JSONObject) jsonResultObject.get("data");
        JSONArray jsonPriceArray = (JSONArray) jsonDetailObject.get("resDetailList");

        // 아파트의 경우 동 호수가 null 값임을 이용해 평형으로 조회할지 동 호수로 조회할지 정함
        boolean isPyeong = ((JSONObject) jsonPriceArray.get(0)).get("reqDong").equals("");

        // 만약 사용자가 자신의 집 평형을 입력하면, 그 정보를 codef api 에서 가져온 데이터들과 비교하여, 해당하는 평형의 시세를 찾는다.
        // 반복문으로 "resType1"(평형) 일치하는지 비교. 그리고 일치하면 매매_일반가(시세) 출력.
        BigInteger price = BigInteger.valueOf(0);
        if (isPyeong) {

            for (Object o : jsonPriceArray) {
                JSONObject chosenObject = (JSONObject) o;
                String pyeong = (String) chosenObject.get("resType1");

                // "resType1"(평형) 비교문
                if (pyeong.equals(building.getArea())) {
                    price = new BigInteger((String) chosenObject.get("resGeneralPrice"));
//                System.out.println("------------------------------");
//                System.out.println("generalPrice = " + generalPrice);
//                System.out.println("------------------------------");
                    break;
                }
            }

            return price;
        } else {
            for (Object o : jsonPriceArray) {
                JSONObject chosenObject = (JSONObject) o;
                String dong = (String) chosenObject.get("reqDong");
                String ho = (String) chosenObject.get("reqHo");

                // "resType1"(평형) 비교문
                if (dong.equals(building.getAddress().getDong())) {
                    if (ho.equals(building.getAddress().getHo())) {
                        price = new BigInteger((String) chosenObject.get("resGeneralPrice"));
            //      System.out.println("------------------------------");
            //      System.out.println("generalPrice = " + generalPrice);
            //      System.out.println("------------------------------");
                        break;
                    }
                }
            }

            return price;
        }
    }

    //건물, 주소 DB에 저장하기
    public Building saveBuilding(BuildingRequestDto buildingRequestDto){

        Building building = new Building();
        Address address = new Address();

        //접수 유형
        building.setLogicType(buildingRequestDto.getLogicType());
        if(buildingRequestDto.getLogicType() == LogicType.REPORT)
        {
            /*
                빌딩 정보 set
            */
            building.setLogicType(buildingRequestDto.getLogicType()); //접수 유형
            building.setBuildingName(buildingRequestDto.getBuildingName()); //건물 이름
            building.setType(buildingRequestDto.getType()); //건물 유형
            building.setCollateralDate(buildingRequestDto.getCollateralDate()); //근저당 설정 기준일

        }
        else if(buildingRequestDto.getLogicType() == LogicType.CALCULATE)
        {
            /*
                빌딩 정보 set
             */
            building.setLogicType(buildingRequestDto.getLogicType()); //접수 유형
            building.setArea(buildingRequestDto.getArea()); //건물 평형 수
            building.setType(buildingRequestDto.getType()); //건물 유형

        }

        //주소 정보 set
        address.setAddress(buildingRequestDto.getAddress());
        address.setDong(buildingRequestDto.getDong());
        address.setHo(buildingRequestDto.getHo());

        //빌딩 엔티티에 주소 set
        building.setAddress(address);
        System.out.println(building.getClass());
        System.out.println(building.getBuildingId());

        //입력받은 건물, 주소 DB에 저장.
        buildingRepository.save(building);
        addressRepository.save(address);

        return building;
    }

    // 건물 입력받아 시세 계산하기
    public ResponseEntity<?> getMarketPrice(String userEmail, BuildingPriceRequestDto buildingPriceReq) throws UnsupportedEncodingException, ParseException, JsonProcessingException, InterruptedException {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        DefaultAssert.isTrue(userOptional.isPresent(), "유저가 올바르지 않습니다.");

        Building building = new Building();
        Address address = new Address();

        //접수 유형
        building.setLogicType(LogicType.CALCULATE);
        building.setBuildingName(buildingPriceReq.getBuildingName()); //건물 이름
        building.setType(buildingPriceReq.getType()); //건물 유형
        building.setArea(buildingPriceReq.getArea()); //건물 평형 수

        //주소 정보 set
        address.setAddress(buildingPriceReq.getAddress());
        address.setDong(buildingPriceReq.getDong());
        address.setHo(buildingPriceReq.getHo());

        //빌딩 엔티티에 주소 set
        building.setAddress(address);

        addressRepository.save(address);

        //시세 저장
        building.setPrice(getPrice(building));

        buildingRepository.save(building);

        BuildingPriceResponseDto buildingPriceRes = BuildingPriceResponseDto.builder()
                .buildingName(building.getBuildingName())
                .buildingId(building.getBuildingId())
                .address(address.getAddress())
                .ho(address.getHo())
                .dong(address.getDong())
                .type(building.getType())
                .area(building.getArea())
                .logicType(building.getLogicType())
                .price(building.getPrice())
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(buildingPriceRes)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
