package com.vn.edu.elearning.controller;

import com.vn.edu.elearning.domain.Danhmuc;
import com.vn.edu.elearning.dto.DanhmucDto;
import com.vn.edu.elearning.service.DanhmucService;
import com.vn.edu.elearning.service.GiaodichService;
import com.vn.edu.elearning.service.MapValidationErrorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/giaodich")
public class GiaodichController {
    @Autowired
    GiaodichService giaodichService;
    @GetMapping("/account/{id}")
    public  ResponseEntity<?> getTransactionsbyAccount(@PathVariable("id") String id){
        return new ResponseEntity<>(giaodichService.findAllByAccount(id),HttpStatus.OK);
    }
    @GetMapping("/revenue/{id}")
    public  ResponseEntity<?> getRevenues(@PathVariable("id") String id){
        return new ResponseEntity<>(giaodichService.findAllTransactionByAccount(id),HttpStatus.OK);
    }

    @GetMapping("/transaction")
    public  ResponseEntity<?> getTransactions(){
        return new ResponseEntity<>(giaodichService.findGiaodichsAdmin(),HttpStatus.OK);
    }
}
