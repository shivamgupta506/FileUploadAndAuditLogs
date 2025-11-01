package com.mindprove;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "filemetadata")
public class FileMetadata implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileType")
	private String fileType;
	
	@Column(name = "fileSize")
	private Long fileSize;
	
	@Column(name = "fileUrl")
	private String fileUrl;
	
	@Column(name = "originalFilename")
	private String originalFilename;
	
	@Column(name = "uploadedAt")
	private Instant uploadedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	
	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public Instant getUploadedAt() {
		return uploadedAt;
	}

	public void setUploadedAt(Instant uploadedAt) {
		this.uploadedAt = uploadedAt;
	}

	@Override
	public String toString() {
		return "FileMetadata [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", fileSize=" + fileSize
				+ ", fileUrl=" + fileUrl + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, fileSize, fileType, fileUrl, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileMetadata other = (FileMetadata) obj;
		return Objects.equals(fileName, other.fileName) && Objects.equals(fileSize, other.fileSize)
				&& Objects.equals(fileType, other.fileType) && Objects.equals(fileUrl, other.fileUrl)
				&& Objects.equals(id, other.id);
	}
	
	
	
}
