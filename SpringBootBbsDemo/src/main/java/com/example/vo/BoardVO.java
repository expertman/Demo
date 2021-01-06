package com.example.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	private int idx;
	private String name, email, title, contents;
	private Date writedate;
	private int readcount;
	private String filename;
}
