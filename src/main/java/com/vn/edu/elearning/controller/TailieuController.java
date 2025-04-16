package com.vn.edu.elearning.controller;


import com.nimbusds.jose.JOSEException;
import com.vn.edu.elearning.domain.Dangtai;
import com.vn.edu.elearning.domain.Madangtai;
import com.vn.edu.elearning.domain.Taikhoan;
import com.vn.edu.elearning.domain.Tailieu;
import com.vn.edu.elearning.dto.LichsukiemduyetDto;
import com.vn.edu.elearning.dto.TailieuDto;
import com.vn.edu.elearning.exeception.FileNotFoundException;
import com.vn.edu.elearning.service.*;
import com.vn.edu.elearning.util.Status;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@CrossOrigin
@RequestMapping("/api/v1/tailieu")
public class TailieuController {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    TailieuService tailieuService;

    @Autowired
    TaikhoanService taikhoanService;

    @Autowired
    DangtaiService dangtaiService;

    @Autowired
    KiemduyetService kiemduyetService;

    @Autowired
    MapValidationErrorService mapValidationErrorService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDocument(@Validated @ModelAttribute TailieuDto dto, BindingResult result) throws IOException {
        System.out.println(dto);
        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null) {
            return responseEntity;
        }

        Tailieu tailieu = tailieuService.save(dto);
        Taikhoan taikhoan = taikhoanService.findById(dto.getMataikhoan());

        dangtaiService.save(taikhoan,tailieu);
        LichsukiemduyetDto kiemduyet = new LichsukiemduyetDto();
        kiemduyet.setMatailieu(tailieu.getMatailieu());
        if (taikhoan.getQuyenhan().equals(Status.ADMIN.getValue()))
        {
            kiemduyet.setKetqua(Status.DKD.getValue());
        }else {
            kiemduyet.setKetqua(Status.CKD.getValue());
        }
        kiemduyetService.save(kiemduyet);

        return new ResponseEntity<>(tailieu, HttpStatus.CREATED);
    }

    @GetMapping("/content/{filename:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable String filename, HttpServletRequest request) {
        Resource resource = fileStorageService.loadPDFFileAsResource(filename);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        }catch (Exception ex)
        {
            throw new FileNotFoundException("Không thể mở tệp tin. ");
        }

        if (contentType == null)
        {
            contentType= "application/octet-stream";
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""
                + resource.getFilename() + "\"")
                .body(resource);
    }

    @PatchMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateDocument(
            @PathVariable String id,@Validated @ModelAttribute TailieuDto dto, BindingResult result) throws IOException {

        ResponseEntity<?> responseEntity = mapValidationErrorService.mapValidationFields(result);
        if (responseEntity != null) {
            return responseEntity;
        }
        Tailieu tailieu = tailieuService.update(id,dto);


        return new ResponseEntity<>(tailieu, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<?> getDocuments(){
        return new ResponseEntity<>(tailieuService.findAll(),HttpStatus.OK);
    }
    @GetMapping("/top")
    public ResponseEntity<?> getTop5Documents(){
        return new ResponseEntity<>(tailieuService.getTop5TaiLieuThanhToanNhieuNhat(),HttpStatus.OK);
    }

    @GetMapping("/name/{tentailieu}")
    public ResponseEntity<?> getListDocumentByName(@PathVariable("tentailieu") String tentailieu){
        return new ResponseEntity<>(tailieuService.getListDocumentByName(tentailieu),HttpStatus.OK);
    }

    @GetMapping("/upload-account/{id}")
    public ResponseEntity<?> getDocumentUploadByAccount(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findAllUploadByAccount(id),HttpStatus.OK);
    }

    @GetMapping("/upload-admin")
    public ResponseEntity<?> getDocumentUploadAdmin(){
        return new ResponseEntity<>(tailieuService.findAllUpload(),HttpStatus.OK);
    }

    @GetMapping("/pay-account/{id}")
    public ResponseEntity<?> getDocumentPayByAccount(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findAllPayByAccount(id),HttpStatus.OK);
    }

    @GetMapping("/pay-admin")
    public ResponseEntity<?> getDocumentPayAdmin(){
        return new ResponseEntity<>(tailieuService.findAllPay(),HttpStatus.OK);
    }

    @GetMapping("/admin-document")
    public ResponseEntity<?> getAllDocumentAdmin(){
        return new ResponseEntity<>(tailieuService.findAllDocumentAdmin(),HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getDocumentAllPayAmin(){
        return new ResponseEntity<>(tailieuService.findAllTransactionAdmin(),HttpStatus.OK);
    }

    @GetMapping("/collection-account/{id}")
    public ResponseEntity<?> getDocumentCollectionByAccount(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findAllDocumentCollectionByAccount(id),HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getDocumentsByCategory(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findAllByCategory(id),HttpStatus.OK);
    }

    @GetMapping("/censorship")
    public ResponseEntity<?> getAllDocumentCensorship(){
        return new ResponseEntity<>(tailieuService.findAllDocumentCensorship(),HttpStatus.OK);
    }

    @GetMapping("/income")
    public ResponseEntity<?> getAllDocumentIncome(){
        return new ResponseEntity<>(tailieuService.findDocumentIncome(),HttpStatus.OK);
    }

    @GetMapping("/{id}/get")
    public  ResponseEntity<?> getDocument(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/info")
    public  ResponseEntity<?> getDocumentInfo(@PathVariable("id") String id){
        return new ResponseEntity<>(tailieuService.findThongtintailieuById(id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable("id") String id) {

        tailieuService.deleteById(id);

        return  new ResponseEntity<>("Tài liệu có id " + id + " đã được xóa",HttpStatus.OK);
    }

    @GetMapping("/preview/{filename:.+}")
    public ResponseEntity<byte[]> getPDFPreview(@PathVariable String filename) {
        try {
            byte[] previewImage = fileStorageService.getPDFPreviewImage(filename);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(previewImage, headers, HttpStatus.OK);
        } catch (IOException ex) {
            throw new FileNotFoundException("Cannot load preview image for PDF file", ex);
        }
    }

    @GetMapping("/view/{filename}")
    public ResponseEntity<?> viewPDF(@PathVariable("filename") String filename, HttpServletRequest request) throws ParseException, JOSEException {
        Resource resource = fileStorageService.loadPDFFileAsResource(filename);

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            contentType = "application/pdf";
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        boolean isValidToken = taikhoanService.introspect(token);
        if (isValidToken) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ");
        }
    }
}
