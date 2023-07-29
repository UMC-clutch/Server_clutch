package clutch.clutchserver.report.controller;

import clutch.clutchserver.report.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.codef.api.EasyCodef;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;







}
