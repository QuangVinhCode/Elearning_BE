package com.vn.edu.elearning.service;

import com.vn.edu.elearning.config.FileStorageProperties;
import com.vn.edu.elearning.exeception.FileNotFoundException;
import com.vn.edu.elearning.exeception.FileStorageException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path filePDFStorageLocation;
    @Value("${file.upload-filepdf-dir}")
    private String uploadFilepdfDir;
    public  String storePDFFile(MultipartFile file)
    {
        return storeFile(filePDFStorageLocation,file);
    }

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.filePDFStorageLocation = Paths.get(fileStorageProperties.getUploadFilepdfDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(filePDFStorageLocation);
        }catch (Exception ex){
            throw new FileStorageException("không thể tạo thư mục lưu trữ các tập tin tải lên",ex);
        }
    }
    private String storeFile(Path location, MultipartFile file) {
        UUID uuid = UUID.randomUUID();

        String exit = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String filename = uuid.toString() + "." + exit;

        try {
            if (filename.contains("..")){
                throw new FileStorageException("Xin lỗi, tên tệp chứa chuỗi đường dẫn không hợp lệ " + filename);
            }

            Path targetLocation = location.resolve(filename);
            Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        }catch (Exception ex)
        {
            throw new FileStorageException("Không thể lưu trữ tập tin " + filename + ". Vui lòng thử lại sau",ex);
        }
    }
    public Resource loadPDFFileAsResource(String filename)
    {
        return loadFileAsResource(filePDFStorageLocation,filename);
    }
    private Resource loadFileAsResource(Path location,String filename){
        try {
            Path filePath = location.resolve(filename).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists())
            {
                return  resource;
            }else {
                throw  new FileNotFoundException("Không tìm thất tệp tin " + filename);
            }
        }catch (Exception ex)
        {
            throw new FileNotFoundException("Không tìm thấy tệp tin " + filename,ex);
        }
    }


    public void  deletePDFFile(String filename){
        deleteFile(filePDFStorageLocation,filename);
    }
    private void deleteFile(Path location,String filename) {
        try {
            Path filePath = location.resolve(filename).normalize();

            if(!Files.exists(filePath))
            {
                throw  new FileNotFoundException("Không tìm thấy tệp tin " + filename);
            }

            Files.delete(filePath);
        }catch (Exception ex)
        {
            throw new FileNotFoundException("Không tim thấy tệp tin " + filename,ex);
        }
    }
    public byte[] getPDFPreviewImage(String filename) throws IOException {
        Path filePath = Paths.get(uploadFilepdfDir).resolve(filename).normalize();

        try (PDDocument document = PDDocument.load(filePath.toFile())) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImage(0); // Render first page

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();

            return baos.toByteArray();
        } catch (IOException ex) {
            if (ex.getMessage().contains("JPEG2000")) {
                throw new FileNotFoundException("Cannot read JPEG2000 image: Java Advanced Imaging (JAI) Image I/O Tools are not installed.", ex);
            }
            throw new FileNotFoundException("Error converting PDF to image", ex);
        }
    }
}
