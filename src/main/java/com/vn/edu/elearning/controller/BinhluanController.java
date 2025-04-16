package com.vn.edu.elearning.controller;

import com.vn.edu.elearning.domain.Binhluan;
import com.vn.edu.elearning.dto.BinhluanDto;
import com.vn.edu.elearning.service.BinhluanService;
import com.vn.edu.elearning.service.MapValidationErrorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/binhluan")
public class BinhluanController {
    @Autowired
    BinhluanService binhluanService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody BinhluanDto dto, BindingResult result){

       ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

       if (responseEntity != null)
       {
           return responseEntity;
       }
        Binhluan entity = binhluanService.save(dto);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getComments(){
        return new ResponseEntity<>(binhluanService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getCommentsAdmin(){
        return new ResponseEntity<>(binhluanService.findBinhluans(),HttpStatus.OK);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<?> getCommentsByAccount(@PathVariable("id") String id){
        return new ResponseEntity<>(binhluanService.findAllByAccount(id),HttpStatus.OK);
    }

    @GetMapping("/document/{id}")
    public ResponseEntity<?> getCommentsByDocument(@PathVariable("id") String id){
        return new ResponseEntity<>(binhluanService.findBinhluansByMatailieu(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public  ResponseEntity<?> getComment(@PathVariable("id") String id){
        return new ResponseEntity<>(binhluanService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{matk}/{matl}/{mabl}")
    public ResponseEntity<?> deleteComment(@PathVariable("matk") String matk,@PathVariable("matl") String matl,@PathVariable("mabl") String mabl)
    {
        binhluanService.deleteById(mabl);
        return  new ResponseEntity<>("Xóa thành công",HttpStatus.OK);
    }

    @PatchMapping("/block/{mabinhluan}")
    public ResponseEntity<?> blockCommentAndReplies(@PathVariable String mabinhluan) {
        binhluanService.blockCommentAndReplies(mabinhluan);
        return  new ResponseEntity<>("Chặn thành công",HttpStatus.OK);
    }

}
