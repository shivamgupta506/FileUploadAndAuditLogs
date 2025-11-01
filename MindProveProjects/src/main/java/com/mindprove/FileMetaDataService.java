package com.mindprove;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileMetaDataService {
	private final FileMetaDataRepository fileMetaDataRepository;

	private final Path storageLocation;

	private final Set<String> allowedTypes = Set.of("application/pdf", "image/png", "image/jpeg", "image/jpg",
			"image/gif");

	public FileMetaDataService(@Value("${file.storage.location}") String storageLocation,
			FileMetaDataRepository repository) {
		this.storageLocation = Paths.get(storageLocation).toAbsolutePath().normalize();
		this.fileMetaDataRepository = repository;
	}

	@PostConstruct
	public void init() throws IOException {
		Files.createDirectories(storageLocation);
	}

	public FileMetaDataDto uploadFile(MultipartFile file) throws IOException {
		String contentType = file.getContentType();
		if (contentType == null || !allowedTypes.contains(contentType)) {
			throw new IllegalArgumentException("File type not allowed: " + contentType);
		}
		String original = StringUtils.cleanPath(file.getOriginalFilename());
		String ext = "";
		int i = original.lastIndexOf('.');
		if (i >= 0) ext = original.substring(i);
		String stored = UUID.randomUUID().toString() + ext;

		Path target = storageLocation.resolve(stored);
		Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

		FileMetadata meta = new FileMetadata();
		meta.setFileName(stored);
		meta.setFileSize(file.getSize());
		meta.setFileType(contentType);
		meta.setFileUrl(target.toString());
		meta.setOriginalFilename(original);
		meta.setUploadedAt(Instant.now());
		fileMetaDataRepository.save(meta);
		return this.convetIntoDto(meta);
		
	}

	private FileMetaDataDto convetIntoDto(FileMetadata meta) {
		FileMetaDataDto dto = new FileMetaDataDto();
		dto.setId(meta.getId());
		dto.setFileName(meta.getFileName());
		dto.setFileSize(meta.getFileSize());
		dto.setFileType(meta.getFileType());
		return dto;
	}

	public Resource loadAsResource(String fileName) throws MalformedURLException {
		System.err.println(fileName);
		FileMetadata meta = fileMetaDataRepository.findByFileNames(fileName).orElseThrow(() -> new IllegalArgumentException("File not found"));
		Path file = storageLocation.resolve(meta.getFileName()).normalize();
		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
			return resource;
		} else {
			throw new IllegalArgumentException("Could not read file: " + meta.getFileName());
		}
	}
}
