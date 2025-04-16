package com.vn.edu.elearning.controller;


import com.vn.edu.elearning.domain.Lichsukiemduyet;
import com.vn.edu.elearning.domain.Madangtai;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.dto.KiemduyettailieuDto;
import com.vn.edu.elearning.dto.LichsukiemduyetDto;
import com.vn.edu.elearning.service.KiemduyetService;
import com.vn.edu.elearning.service.MapValidationErrorService;
import com.vn.edu.elearning.service.TaikhoanService;
import com.vn.edu.elearning.service.TailieuService;
import com.vn.edu.elearning.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/kiemduyet")
public class KiemduyetController {
    @Autowired
    KiemduyetService kiemduyetService;
    @Autowired
    TailieuService tailieuService;
    @Autowired
    TaikhoanService taikhoanService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createCensorship(@Validated @RequestBody LichsukiemduyetDto dto, BindingResult result){

       ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

       if (responseEntity != null)
       {
           return responseEntity;
       }
       Lichsukiemduyet entity = kiemduyetService.save(dto);
        Taikhoan taikhoan = taikhoanService.findByPostedDocuments(dto.getMatailieu());
        Tailieu tailieu = tailieuService.findById(dto.getMatailieu());
        if (entity.getKetqua().equals(Status.DKD.getValue()))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            String thoigian = LocalDateTime.now().format(formatter);
            kiemduyetService.updateCensorshipTime(thoigian,taikhoan,tailieu);
            tailieuService.updateTrangthai(Status.DKD.getValue(), entity.getTailieu().getMatailieu());
        }else {
            kiemduyetService.updateCensorshipTime("",taikhoan,tailieu);
            tailieuService.updateTrangthai(Status.CCS.getValue(), entity.getTailieu().getMatailieu());
        }


        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PatchMapping("/re-censorship")
    public ResponseEntity<?> updateStatusReCensorship(@Validated @RequestBody LichsukiemduyetDto dto){
        Lichsukiemduyet entity = kiemduyetService.save(dto);
        Taikhoan taikhoan = taikhoanService.findByPostedDocuments(dto.getMatailieu());
        Tailieu tailieu = tailieuService.findById(dto.getMatailieu());
        kiemduyetService.updateCensorshipTime("",taikhoan,tailieu);
        tailieuService.updateTrangthai(Status.CCS.getValue(), entity.getTailieu().getMatailieu());

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PatchMapping("/ban")
    public ResponseEntity<?> updateStatusBan(@Validated @RequestBody LichsukiemduyetDto dto){
        Lichsukiemduyet entity = kiemduyetService.save(dto);
        Taikhoan taikhoan = taikhoanService.findByPostedDocuments(dto.getMatailieu());
        Tailieu tailieu = tailieuService.findById(dto.getMatailieu());
        tailieuService.updateTrangthai(Status.CAM.getValue(), entity.getTailieu().getMatailieu());

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @GetMapping("/document/{id}")
    public  ResponseEntity<?> getCensorshipByDocument(@PathVariable("id") String id){
        return new ResponseEntity<>(kiemduyetService.findAllByDocument(id),HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<?> getCensorshipList(){
        return new ResponseEntity<>(kiemduyetService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public  ResponseEntity<?> getCensorship(@PathVariable("id") String id){
        return new ResponseEntity<>(kiemduyetService.findById(id),HttpStatus.OK);
    }

}
