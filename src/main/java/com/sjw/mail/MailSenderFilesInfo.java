package com.sjw.mail;

import java.io.InputStream;

/**
 *
 * 标题：项目研发
 *
 * 模块：公共部分
 *
 * 公司: yyang
 *
 * 作者：meteors，2009-7-3
 *
 * 描述：
 *
 * 说明:
 *
 */
public class MailSenderFilesInfo {

	// Fields

	private String id; // id
	private String name;// 名字
	private Float size; // 大小
	private String type;// 类型
	private byte[] content;// 内容

	private java.sql.Blob fileobject;//内容对象

	private InputStream fileInputStream;//流

	// Constructors

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	/** default constructor */
	public MailSenderFilesInfo() {
	}

	/** full constructor */
	public MailSenderFilesInfo(String name, Float size, String type,
							   byte[] content) {
		this.name = name;
		this.size = size;
		this.type = type;
		this.content = content;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getSize() {
		return size;
	}

	public void setSize(Float size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public java.sql.Blob getFileobject() {
		return fileobject;
	}

	public void setFileobject(java.sql.Blob fileobject) {
		this.fileobject = fileobject;
	}
}
