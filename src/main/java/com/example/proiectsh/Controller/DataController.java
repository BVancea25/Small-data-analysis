package com.example.proiectsh.Controller;

import com.example.proiectsh.Models.Data;
import com.example.proiectsh.Repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataRepository dataRepository;

    @PostMapping("/send-data")
    @ResponseBody
    public String postData(@RequestBody List<Data> dataPoints){

        dataRepository.saveAll(dataPoints);
        return "Data received and saved successfully";

    }

    @GetMapping("/")
    public String showAnalytics(Model model){


//        Instant instant = Instant.parse("2007-12-03T10:16:30.00Z");
//        Instant instant1 = Instant.parse("2007-12-03T10:17:30.00Z");
//        Instant instant2 = Instant.parse("2007-12-03T10:18:30.00Z");
//
//        Data p=new Data(1L,4.23f,instant);
//        Data p1=new Data(2L,4.23f,instant1);
//        Data p2=new Data(3L,4.23f,instant2);
//        dataRepository.save(p);
//        dataRepository.save(p1);
//        dataRepository.save(p2);

        List<Data> dataPoints=dataRepository.findAll();
        Integer totalDataPoints=dataPoints.size();

        List<Float> humidityData = new ArrayList<>();
        List<String> timestamps = new ArrayList<>();
        Float averageHumidity=0f;
        for(Data point : dataPoints){
            humidityData.add(point.getHumidity());
            averageHumidity+=point.getHumidity();
            timestamps.add(point.getTimeStamp());
        }
        averageHumidity/=dataPoints.size();

        model.addAttribute("averageHumidity",averageHumidity);
        model.addAttribute("humidityData", humidityData);
        model.addAttribute("timestamps", timestamps);
        model.addAttribute("totalDataPoints",totalDataPoints);
        model.addAttribute("dataPoints",dataPoints);

        return "index";
    }

}
