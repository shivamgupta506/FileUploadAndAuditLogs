package com.mindprove;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "api_logs")
public class ApiLogs implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "method")
	private String method;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "queryString")
	private String queryString;
	
	@Column(name = "clientIp")
	private String clientIp;
	
	@Column(name = "status")
	private Integer status;
	
	@Column(name = "durationMs")
	private Long durationMs;
	
	@Column(name = "timestamp")
	private Instant timestamp;


	public ApiLogs(String method, String path, String clientIp, Instant timestamp) {
		super();
		this.method = method;
		this.path = path;
		this.clientIp = clientIp;
		this.timestamp = timestamp;
	}


	public ApiLogs() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getDurationMs() {
		return durationMs;
	}

	public void setDurationMs(Long durationMs) {
		this.durationMs = durationMs;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ApiLogs [id=" + id + ", method=" + method + ", path=" + path + ", queryString=" + queryString
				+ ", clientIp=" + clientIp + ", status=" + status + ", durationMs=" + durationMs + ", timestamp="
				+ timestamp + "]";
	}
	
}
