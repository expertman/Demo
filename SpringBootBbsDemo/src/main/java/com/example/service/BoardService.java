package com.example.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.vo.BoardVO;

public interface BoardService {
	void create(BoardVO boardVo, MultipartFile file)  throws Exception;
	void read(Map map);
	void readAll(Map map);
	void update(BoardVO boardVo);
	void delete(int idx);
}
