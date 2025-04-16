package com.vn.edu.elearning.controller;

import com.vn.edu.elearning.domain.Danhmuc;
import com.vn.edu.elearning.dto.DanhmucDto;
import com.vn.edu.elearning.service.DanhmucService;
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
@RequestMapping("/api/v1/danhmuc")
public class DanhmucController {
    @Autowired
    DanhmucService danhmucService;
    @Autowired
    MapValidationErrorService mapValidationErrorService;
    @PostMapping
    public ResponseEntity<?> createCategory(@Validated @RequestBody DanhmucDto dto, BindingResult result){

       ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);

       if (responseEntity != null)
       {
           return responseEntity;
       }
        Danhmuc entity = new Danhmuc();

        entity = danhmucService.save(dto);

        dto.setMadanhmuc(entity.getMadanhmuc());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") String id, @RequestBody DanhmucDto dto){
        Danhmuc entity = new Danhmuc();
        BeanUtils.copyProperties(dto,entity);

        entity = danhmucService.update(id,dto);

        dto.setMadanhmuc(entity.getMadanhmuc());

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<?> getCategories(){
        return new ResponseEntity<>(danhmucService.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public  ResponseEntity<?> getCategory(@PathVariable("id") String id){
        return new ResponseEntity<>(danhmucService.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") String id)
    {
        danhmucService.deleteById(id);
        return  new ResponseEntity<>("Danh mục có id " + id + " đã được xóa",HttpStatus.OK);
    }
}
