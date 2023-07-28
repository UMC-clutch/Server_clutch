package clutch.clutchserver.report.service;

import clutch.clutchserver.building.entity.Building;
import clutch.clutchserver.building.repository.BuildingRepository;
import clutch.clutchserver.report.dto.ReportRequestDto;
import clutch.clutchserver.report.dto.ReportResponseDto;
import clutch.clutchserver.report.repository.ReportRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import io.codef.api.EasyCodefServiceType;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final BuildingRepository buildingRepository;

    //신고 접수 내용 저장


    //시세 구하기
    public ReportResponseDto getPrice(ReportRequestDto reportRequestDto) throws UnsupportedEncodingException, JsonProcessingException, InterruptedException, ParseException {

        Building building = buildingRepository.findByBuildingId(reportRequestDto.getBuildingId());

        //시세 codef api 이용해서 가져오기
        EasyCodef easyCodef = new EasyCodef();

        //퍼블릭키, 클라이언트 id, secret 설정
        easyCodef.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz9yg/EolzQpRRsgyYVHb89GcESgCzMSePNOq5ShrmVeQapWLWQ3DxFEoKPA2xg9oXYxD+h530EZRJuYkkEIUDRb74l2eYF2oufNAeNelSnkNK58BvEmZ5YwdXkboTSv8CkBALnTwayldQsKk1pOpqhdCw0CnbXQald3BdNyXac68xa0cUFtIQ6PBuUfBYZkG65YRQCQWifWBwdZ4eiwStCyFEV4VEVyUU7YIDylq6DjHZk4Q5UESKVaY9kbVurqowIJ9euDNtfMt7Dp0CspeIxGTGmrvKD8uX8xu7S46GKiYzkAb62zrzq+8eIlFLmzgs7oKdiz8+PloryrqvM646QIDAQAB");
        easyCodef.setClientInfoForDemo("a97ed80d-8ff0-4a70-8414-639ae41d3b14", "46895f2a-b174-4c9e-b011-b1fe911c2115");

        //요청 파라미터 설정(단지 일련번호 조회)
        HashMap<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("organization", "0004");
        parameterMap.put("address", building.getAddress());

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
        System.out.println("-------------------------");
        System.out.println("complexNo = " + complexNo);
        System.out.println("-------------------------");

        //요청 파라미터 설정(시세 정보 조회) -> 필요한 파라미터(기관 코드, 단지 일련번호)
        HashMap<String, Object> parameterMap1 = new HashMap<String, Object>();
        parameterMap1.put("organization", "0004");
        parameterMap1.put("complexNo", complexNo);

        //시세 정보 조회
        String productUrl1 = "/v1/kr/etc/ld/kb/market-price-information";
        String priceResult = easyCodef.requestProduct(productUrl1, EasyCodefServiceType.DEMO, parameterMap1);
        System.out.println(priceResult);

        //다건의 경우이고, 시세 정보 파싱
        JSONObject jsonResultObject = (JSONObject) jsonParser.parse(priceResult);
        JSONObject jsonDetailObject = (JSONObject) jsonResultObject.get("data");
        JSONArray jsonPriceArray = (JSONArray) jsonDetailObject.get("resDetailList");

        // 만약 사용자가 자신의 집 평형을 입력하면, 그 정보를 codef api 에서 가져온 데이터들과 비교하여, 해당하는 평형의 시세를 찾는다.
        // 반복문으로 "resType1"(평형) 일치하는지 비교. 그리고 일치하면 매매_일반가(시세) 출력.
        for (Object o : jsonPriceArray) {
            JSONObject chosenObject = (JSONObject) o;
            String pyeong = (String) chosenObject.get("resType1");

            // "resType1"(평형) 비교문
            if (pyeong.equals("142A")) {
                building.setPrice((Integer) chosenObject.get("resGeneralPrice"));
//                System.out.println("------------------------------");
//                System.out.println("generalPrice = " + generalPrice);
//                System.out.println("------------------------------");
                break;
            } else {
                building.setPrice((Integer) chosenObject.get("resGeneralPrice"));
//                System.out.println("------------------------------");
//                System.out.println("generalPrice = " + generalPrice);
//                System.out.println("------------------------------");
                break;
            }
        }


        ReportResponseDto reportResponseDto = new ReportResponseDto();

        reportResponseDto.setBuildingPrice(building.getPrice());

        return reportResponseDto;
    }

}
