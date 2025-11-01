package com.mindprove;



import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileMetaDataController {
	@Autowired private FileMetaDataService fileMetaDataService;
	
	@GetMapping("/")
	public ResponseEntity<String> ping(){
		return ResponseEntity.ok("OK");
	}
	
	@PostMapping("/files/upload")
	public ResponseEntity<FileMetaDataDto> upload(@RequestParam("file") MultipartFile file) throws Exception {
		try {
			long maxBytes = 5L * 1024 * 1024;
			if (file.getSize() > maxBytes) {
				return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(null);
			}
			FileMetaDataDto fileObject = fileMetaDataService.uploadFile(file);
			return ResponseEntity.ok(fileObject);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@GetMapping("/files/download/{fileName}")
	public ResponseEntity<Resource> getFile(@PathVariable String fileName)throws Exception{
		try {
			Resource resource = fileMetaDataService.loadAsResource(fileName);
			String contentType = Files.probeContentType(resource.getFile().toPath());
	        if (contentType == null) {
	            contentType = "application/octet-stream"; // default if unknown
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
