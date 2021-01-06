package com.example.service;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.BoardDao;
import com.example.vo.BoardVO;

import lombok.extern.slf4j.Slf4j;

@Service("boardService")
@Slf4j
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public void create(BoardVO boardVo, MultipartFile file) throws Exception{
		if(!file.getOriginalFilename().equals("")) {
			String filename = file.getOriginalFilename();
			String saveFolder = System.getProperty("user.dir") + "\\files";
			File fileFile = new File(saveFolder);
			if(!fileFile.exists()) {  //즉, 폴더가 있지 않다면
				fileFile.mkdir();
			}
			String filePath = saveFolder + "\\" + filename;  
			//C:/SpringBootHome/SpringBootBbsDemo/files/aaa.jpg
			file.transferTo(new File(filePath));   //실제로 하드디스크에 저장
			//log.warn("저장경로 = " + filePath);
			boardVo.setFilename(filePath);
		}
		String name = boardVo.getName();
		name = change(name);
		boardVo.setName(name);
		
		String title = boardVo.getTitle();
		title = change(title);
		boardVo.setTitle(title);
		
		String contents = boardVo.getContents();
		contents = change(contents);
		boardVo.setContents(contents);
		this.boardDao.insertBoard(boardVo);
	}

	private String change(String oldStr) {
		String newStr = oldStr.replace("'", "''");   //홑따옴표를 홑홑따옴표로
		newStr = newStr.replace("<", "&lt;");
		newStr = newStr.replace(">", "&gt;");
		return newStr;
	}
	
	@Override
	public void read(Map map) {
		this.boardDao.selectBoard(map);
	}

	@Override
	public void readAll(Map map) {
		this.boardDao.selectAllBoard(map);
	}

	@Override
	public void update(BoardVO boardVo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int idx) {
		// TODO Auto-generated method stub
		
	}
	
}
